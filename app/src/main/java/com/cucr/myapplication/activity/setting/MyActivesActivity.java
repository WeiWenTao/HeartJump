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
import com.cucr.myapplication.bean.eventBus.CommentEvent;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.fuLi.HuoDongCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyActivesActivity extends BaseActivity implements ActivitysAdapter.ClickListener, SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    //活动列表
    @ViewInject(R.id.rlv_actives)
    private SwipeRecyclerView rlv_actives;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private boolean needShowLoading;
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
        needShowLoading = true;
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
    public void onClickGoods(int position, final QiYeHuoDongInfo.RowsBean rowsBean, ShineButton sib) {

        if (rowsBean.getIsSignUp() == 1) {
            sib.setChecked(false, true);
            sib.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));
        } else {
            sib.setChecked(true, true);
        }

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

        mCore.activeGiveUp(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                } else {
//                    ToastUtils.showToast(commonRebackMsg.getMsg());
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
        intent.putExtra("showMore", true);
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
        } else if (requestCode == Constans.REQUEST_CODE && resultCode == 999) {
            boolean delete = data.getBooleanExtra("delete", false);
            if (delete) {
//                mAdapter.delData(position);
                onRefresh();
                EventBus.getDefault().post(new CommentEvent(999));
            }
        }
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        QiYeHuoDongInfo info = mGson.fromJson(response.get(), QiYeHuoDongInfo.class);
        if (info.isSuccess()) {
            if (isRefresh) {
                if (info.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mRowBeans = info.getRows();
                    mAdapter.setData(info.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
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
        if (needShowLoading) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            needShowLoading = false;
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {
        if (isRefresh && response.getException() instanceof NetworkError) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_TWO) {
            if (rlv_actives.isRefreshing()) {
                rlv_actives.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (rlv_actives.isLoadingMore()) {
                rlv_actives.stopLoadingMore();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
