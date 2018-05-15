package com.cucr.myapplication.activity.fuli;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Home.HomeBannerInfo;
import com.cucr.myapplication.bean.fuli.ActiveInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.fuLi.FuLiCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by cucr on 2017/9/1.
 */

public class FragmentFuLi extends BaseFragment implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    //活动福利
    @ViewInject(R.id.rlv_fuli)
    private SwipeRecyclerView rlv_fuli;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private Gson mGson;
    private FuLiCore mCore;
    private int page;
    private boolean isRefresh;
    private int rows;
    private FuLiAdapter activeAdapter;
    private Intent mIntent;

    @Override
    protected void initView(View childView) {
        page = 1;
        rows = 10;
        mGson = new Gson();
        mIntent = new Intent(MyApplication.getInstance(), TestWebViewActivity.class);
        mCore = new FuLiCore();
        ViewUtils.inject(this, childView);
        initRLV();
        onRefresh();
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_fuli;
    }

    //是否需要头部
    @Override
    protected boolean needHeader() {
        return false;
    }

    private void initRLV() {
        mCore.QueryFuLiBanner(this);
        rlv_fuli.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        activeAdapter = new FuLiAdapter();
        rlv_fuli.setAdapter(activeAdapter);
        rlv_fuli.setOnLoadListener(this);
        activeAdapter.setOnItemListener(new FuLiAdapter.OnItemListener() {
            @Override
            public void OnItemClick(View view, int activeId, String title) {
                //跳转到福利活动详情
                mIntent.putExtra("url", HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FULI_ACTIVE_DETIAL
                        + "?activeId=" + activeId + "&userId=" + SpUtil.getParam(SpConstant.USER_ID, -1));
                mIntent.putExtra("activeId", activeId);
                mIntent.putExtra("activeTitle", title);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_TWO) {
            ActiveInfo infos = mGson.fromJson(response.get(), ActiveInfo.class);
            if (infos.isSuccess()) {
                if (isRefresh) {
                    if (infos.getTotal() == 0) {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        activeAdapter.setDate(infos.getRows());
                    }
                } else {
                    activeAdapter.addDate(infos.getRows());
                }
                if (infos.getTotal() <= page * rows) {
                    rlv_fuli.onNoMore("没有更多了");
                } else {
                    rlv_fuli.complete();
                }
            }
        } else if (what == Constans.TYPE_FORE) {
            HomeBannerInfo infos = mGson.fromJson(response.get(), HomeBannerInfo.class);
            List<HomeBannerInfo.ObjBean> obj = infos.getObj();
            activeAdapter.setBanner(obj);
        }
    }

    @Override
    public void onRequestStar(int what) {

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
            if (rlv_fuli.isRefreshing()) {
                rlv_fuli.getSwipeRefreshLayout().setRefreshing(false);
            }
        }
    }

    //查询福利活动
    //刷新
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_fuli.getSwipeRefreshLayout().setRefreshing(true);
        mCore.QueryHuoDong(page, rows, this);
    }

    //查询福利活动
    //加载
    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_fuli.onLoadingMore();
        mCore.QueryHuoDong(page, rows, this);
    }

    //跳转到消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        startActivity(new Intent(MyApplication.getInstance(), MessageActivity.class));
    }
}
