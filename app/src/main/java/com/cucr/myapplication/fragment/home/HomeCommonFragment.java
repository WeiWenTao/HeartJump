package com.cucr.myapplication.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.core.home.HomeNewsCore;
import com.cucr.myapplication.fragment.LazyFragment;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2018/5/10.
 */

@SuppressLint("ValidFragment")
public class HomeCommonFragment extends LazyFragment implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    @ViewInject(R.id.rlv_news)
    private SwipeRecyclerView rlv_news;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private int type;
    private View rootView;
    private XingWenAdapter mAdapter;
    private int rows;
    private int page;
    private boolean isRefresh;
    private HomeNewsCore mDataCore;
    private Gson mGson;
    private boolean needShowLoading;

    public HomeCommonFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //view的复用
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_common, container, false);
            ViewUtils.inject(this, rootView);
            init();
        }

        return rootView;
    }

    private void init() {
        rows = 20;
        page = 1;
        needShowLoading = true;
        mDataCore = new HomeNewsCore();
        mAdapter = new XingWenAdapter();
        mGson = MyApplication.getGson();
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initRlV();
        onRefresh();
    }

    private void initRlV() {
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rlv_news.getRecyclerView().addItemDecoration(decor);
        rlv_news.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_news.setAdapter(mAdapter);
        rlv_news.setOnLoadListener(this);
        rlv_news.onLoadingMore();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mDataCore.queryHomeNews(rows, page, type, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_news.onLoadingMore();
        mDataCore.queryHomeNews(rows, page, type, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        QueryFtInfos newsInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
        if (newsInfos.isSuccess()) {
            if (isRefresh) {
                if (newsInfos.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mAdapter.setData(newsInfos);
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(newsInfos.getRows());
            }
            if (newsInfos.getTotal() <= page * rows) {
                rlv_news.onNoMore("没有更多了");
            } else {
                rlv_news.complete();
            }
        } else {
            ToastUtils.showToast(newsInfos.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (needShowLoading && isRefresh) {
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
        if (rlv_news.isRefreshing()) {
            rlv_news.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_news.isLoadingMore()) {
            rlv_news.stopLoadingMore();
        }
    }

    @OnClick(R.id.ll_error)
    public void refres(View view) {
        needShowLoading = true;
        onRefresh();
    }
}
