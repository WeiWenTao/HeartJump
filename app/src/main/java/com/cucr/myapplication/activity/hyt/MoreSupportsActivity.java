package com.cucr.myapplication.activity.hyt;

import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdSupportRecord;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdSupports;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

public class MoreSupportsActivity extends BaseActivity implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv_record)
    private SwipeRecyclerView srlv;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;


    private int page;
    private int rows;
    private YyhdSupportRecord mAdapter;
    private HytCore mCore;
    private boolean needShowLoading;
    private boolean isRefresh;
    private int mActiveId;

    @Override
    protected void initChild() {
        page = 1;
        rows = 10;
        needShowLoading = true;
        mActiveId = getIntent().getIntExtra("activeId", -1);
        mCore = new HytCore();
        srlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new YyhdSupportRecord();
        srlv.setAdapter(mAdapter);
        srlv.getRecyclerView().addItemDecoration(new SpaceItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 10)));
        srlv.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_more_supports;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {

        YyhdSupports starList = mGson.fromJson(response.get(), YyhdSupports.class);
        if (starList.isSuccess()) {
            if (isRefresh) {
                if (starList.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mAdapter.setData(starList.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(starList.getRows());
            }
            if (starList.getTotal() <= page * rows) {
                srlv.onNoMore("没有更多了");
            } else {
                srlv.complete();
            }
        } else {
            ToastUtils.showToast(starList.getErrorMsg());
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        srlv.getSwipeRefreshLayout().setRefreshing(true);
        mCore.querySupport(page, rows, mActiveId, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        srlv.onLoadingMore();
        mCore.querySupport(page, rows, mActiveId, this);
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
        if (srlv.isRefreshing()) {
            srlv.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (srlv.isLoadingMore()) {
            srlv.stopLoadingMore();
        }
    }

}
