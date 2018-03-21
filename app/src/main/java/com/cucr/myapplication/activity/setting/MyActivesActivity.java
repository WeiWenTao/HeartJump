package com.cucr.myapplication.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.huodong.HuoDongCatgoryActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.ActivitysAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.fuLi.HuoDongCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class MyActivesActivity extends BaseActivity implements ActivitysAdapter.ClickListener, SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    //活动列表
    @ViewInject(R.id.rlv_actives)
    private SwipeRecyclerView rlv_actives;

    private Context mContext;
    private HuoDongCore mCore;
    private int page;
    private int rows;
    private Gson mGson;
    private ActivitysAdapter mAdapter;
    private Intent mIntent;
    private Integer giveNum;
    private int position;
    private List<QiYeHuoDongInfo.RowsBean> mRowBeans;
    private boolean isRefresh;

    @Override
    protected void initChild() {
        initTitle("我的活动");
        initRLV();
        onRefresh();
    }

    private void initRLV() {
        mContext = MyApplication.getInstance();
        mCore = new HuoDongCore();
        mGson = MyApplication.getGson();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        page = 1;
        rows = 3;
        mAdapter = new ActivitysAdapter();
        mAdapter.setOnClickListener(this);
        rlv_actives.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        rlv_actives.setAdapter(mAdapter);
        rlv_actives.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_actives.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryActive(true, -1, page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_actives.onLoadingMore();
        mCore.queryActive(true, -1, page, rows, this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_actives;
    }

    @Override
    public void onClickGoods(int position, final QiYeHuoDongInfo.RowsBean rowsBean) {
        MyLogger.jLog().i("onClickGoods");
        mCore.activeGiveUp(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (rowsBean.getIsSignUp() == 1) {
                        giveNum = rowsBean.getGiveUpCount() - 1;
                        rowsBean.setIsSignUp(0);
                        rowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = rowsBean.getGiveUpCount() + 1;
                        rowsBean.setIsSignUp(1);
                        rowsBean.setGiveUpCount(giveNum);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });

    }

    //isFromComment 用以区分是点击条目进去的 还是点击评论进去的
    @Override
    public void onClickCommends(int position, QiYeHuoDongInfo.RowsBean rowsBean, boolean isFromComment) {
        this.position = position;
        Intent intent = new Intent(MyApplication.getInstance(), HuoDongCatgoryActivity.class);
        intent.putExtra("data", rowsBean);
        intent.putExtra("isFromComment", isFromComment);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    @Override
    public void onClickPerson(int personId) {
        mIntent.setClass(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mIntent.putExtra("userId", personId);
        startActivity(mIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
            //接收的数据
            QiYeHuoDongInfo.RowsBean rowBeans = (QiYeHuoDongInfo.RowsBean) data.getSerializableExtra("rowsBean");
            //原来的数据
            QiYeHuoDongInfo.RowsBean rowsBean = mRowBeans.get(position);
            //将原来的数据设置成更新后的数据
            rowsBean.setGiveUpCount(rowBeans.getGiveUpCount());
            rowsBean.setIsSignUp(rowBeans.getIsSignUp());
            rowsBean.setCommentCount(rowBeans.getCommentCount());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        QiYeHuoDongInfo info = mGson.fromJson(response.get(), QiYeHuoDongInfo.class);
        if (info.isSuccess()) {
            if (isRefresh) {
                mRowBeans = info.getRows();
                mAdapter.setData(info.getRows());
            } else {
                mRowBeans.addAll(info.getRows());
                mAdapter.addData(info.getRows());
            }
            if (info.getTotal() <= page * rows) {
                rlv_actives.onNoMore("没有更多了");
            } else {
                rlv_actives.complete();
            }
        } else {
            ToastUtils.showToast(info.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_TWO) {
            if (rlv_actives.isRefreshing()) {
                rlv_actives.getSwipeRefreshLayout().setRefreshing(false);
            }
        }
    }
}
