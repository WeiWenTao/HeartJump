package com.cucr.myapplication.activity.fenTuan;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.DaShangCatgoryAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.DaShangListInfo;
import com.cucr.myapplication.core.daShang.DaShangCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

public class DaShangCatgoryActivity extends BaseActivity implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    //打赏详情
    @ViewInject(R.id.rlv_ds_catgory)
    private SwipeRecyclerView rlv_ds_catgory;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;
    private boolean needShowLoading;
    private boolean isRefresh;
    private int rows;
    private int page;

    private DaShangCore mCore;
    private int mContentId;
    private DaShangCatgoryAdapter mDsListAdapter;

    @Override
    protected void initChild() {
        initViews();

    }

    private void initViews() {
        page = 1;
        rows = 15;
        needShowLoading = true;
        mContentId = getIntent().getIntExtra("contentId", -1);
        mCore = new DaShangCore();
        mDsListAdapter = new DaShangCatgoryAdapter();
        rlv_ds_catgory.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_ds_catgory.setAdapter(mDsListAdapter);
        rlv_ds_catgory.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_da_shang_catgory;
    }

    @OnClick(R.id.tv_ds_record)
    public void dsRecord(View view) {
        startActivity(new Intent(MyApplication.getInstance(), DaShangRecordActivity.class));
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_ds_catgory.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryDsList(rows, page, mContentId, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_ds_catgory.onLoadingMore();
        mCore.queryDsList(rows, page, mContentId, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        DaShangListInfo list = mGson.fromJson(response.get(), DaShangListInfo.class);
        if (list.isSuccess()) {
            if (isRefresh) {
                if (list.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mDsListAdapter.setData(list.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mDsListAdapter.addData(list.getRows());
            }
            if (list.getTotal() <= page * rows) {
                rlv_ds_catgory.onNoMore("没有更多了");
            } else {
                rlv_ds_catgory.complete();
            }
        } else {
            ToastUtils.showToast(list.getErrorMsg());
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
        if (rlv_ds_catgory.isRefreshing()) {
            rlv_ds_catgory.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_ds_catgory.isLoadingMore()) {
            rlv_ds_catgory.stopLoadingMore();
        }

    }
}
