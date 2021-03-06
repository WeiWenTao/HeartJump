package com.cucr.myapplication.activity.fuli;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.huodong.FaBuHuoDongActivity;
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
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/9/8.
 */

public class HuoDongActivity extends BaseActivity implements SwipeRecyclerView.OnLoadListener, ActivitysAdapter.ClickListener, RequersCallBackListener {

    //活动列表
    @ViewInject(R.id.rlv_actives)
    private SwipeRecyclerView rlv_actives;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    //活动发布按钮
    @ViewInject(R.id.iv_fabu)
    private ImageView iv_fabu;

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

    private void initRLV() {
        iv_fabu.setVisibility(CommonUtils.isQiYe() ? View.VISIBLE : View.GONE);
        mContext = MyApplication.getInstance();
        mCore = new HuoDongCore();
        mGson = MyApplication.getGson();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mRowBeans = new ArrayList<>();
        page = 1;
        rows = 10;
        needShowLoading = true;
        isRefresh = true;
        mAdapter = new ActivitysAdapter();
        mAdapter.setOnClickListener(this);
        rlv_actives.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        rlv_actives.setAdapter(mAdapter);
        rlv_actives.setOnLoadListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mCore != null) { //判断是否初始化
            mCore.stop();
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_actives.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryActive(false, -1, page, rows, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //删除了活动 刷新
    public void onDataSynEvent(CommentEvent event) {
        if (event.getFlag() == 998) {
            onRefresh();
        }
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_actives.onLoadingMore();
        mCore.queryActive(false, -1, page, rows, this);
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
        if (what == Constans.TYPE_TWO) {
            QiYeHuoDongInfo info = mGson.fromJson(response.get(), QiYeHuoDongInfo.class);
            if (info.isSuccess()) {
                if (isRefresh) {
                    if (info.getTotal() == 0) {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        mRowBeans.clear();
                        mRowBeans.addAll(info.getRows());
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
    }

    @Override
    public void onRequestStar(int what) {
        if (what == Constans.TYPE_TWO) {
            if (needShowLoading) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                needShowLoading = false;
            }
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
    protected void initChild() {
        EventBus.getDefault().register(this);
        initTitle("企业活动");
        initRLV();
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.active_huo_dong;
    }

    @OnClick(R.id.iv_fabu)
    public void fuBuHuoDong(View view) {
        startActivity(new Intent(MyApplication.getInstance(), FaBuHuoDongActivity.class));
    }
}
