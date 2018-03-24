package com.cucr.myapplication.fragment.DaBang;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.DaShangCatgoryAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.DaShangListInfo;
import com.cucr.myapplication.core.daShang.DaShangCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/11/10.
 */

public class MyRewardFragment extends BaseFragment implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    @ViewInject(R.id.rlv_reward_me)
    private SwipeRecyclerView rlv_my_reward;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private boolean needShowLoading;
    private boolean isRefresh;

    private DaShangCatgoryAdapter mDsListAdapter;
    private DaShangCore mCore;
    private int rows;
    private int page;

    public MyRewardFragment() {
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        mCore = new DaShangCore();
        initViews();
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_reward_me;
    }

    private void initViews() {
        page = 1;
        rows = 15;
        mDsListAdapter = new DaShangCatgoryAdapter();
        rlv_my_reward.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_my_reward.setAdapter(mDsListAdapter);
        rlv_my_reward.setOnLoadListener(this);
        onRefresh();
    }
/*
  mCore.queryDsMe(1, rows, page,
* DaShangListInfo dsInfo = mGson.fromJson(response.get(), DaShangListInfo.class);
                if (dsInfo.isSuccess()) {
                    List<DaShangListInfo.RowsBean> rows = dsInfo.getRows();
                    mDsListAdapter.setData(rows);
                } else {
                    ToastUtils.showToast(dsInfo.getErrorMsg());
                }
* */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCore.stopRequest();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_my_reward.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryDsMe(1, rows, page, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_my_reward.onLoadingMore();
        mCore.queryDsMe(1, rows, page, this);
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
                rlv_my_reward.onNoMore("没有更多了");
            } else {
                rlv_my_reward.complete();
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
        if (rlv_my_reward.isRefreshing()) {
            rlv_my_reward.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_my_reward.isLoadingMore()) {
            rlv_my_reward.stopLoadingMore();
        }

    }
}
