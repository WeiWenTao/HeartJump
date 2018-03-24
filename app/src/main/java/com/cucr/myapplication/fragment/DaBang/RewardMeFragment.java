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

public class RewardMeFragment extends BaseFragment implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    @ViewInject(R.id.rlv_reward_me)
    private SwipeRecyclerView rlv_reward_me;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private boolean needShowLoading;
    private boolean isRefresh;
    private int rows;
    private int page;

    private DaShangCatgoryAdapter mDsListAdapter;
    private DaShangCore mCore;

    public RewardMeFragment() {
    }

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        initViews();
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_reward_me;
    }

    private void initViews() {
        page = 1;
        rows = 15;
        needShowLoading = true;
        mCore = new DaShangCore();
        mDsListAdapter = new DaShangCatgoryAdapter();
        rlv_reward_me.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_reward_me.setAdapter(mDsListAdapter);
        rlv_reward_me.setOnLoadListener(this);
        onRefresh();
    }

    /*
    * DaShangListInfo dsInfo = mGson.fromJson(response.get(), DaShangListInfo.class);
                if (dsInfo.isSuccess()) {
                    List<DaShangListInfo.RowsBean> rows = dsInfo.getRows();
                    mDsListAdapter.setData(rows);
                } else {
                    ToastUtils.showToast(dsInfo.getErrorMsg());
                }
    * */

    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCore.stopRequest();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_reward_me.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryDsMe(0, rows, page, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_reward_me.onLoadingMore();
        mCore.queryDsMe(0, rows, page, this);
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
                rlv_reward_me.onNoMore("没有更多了");
            } else {
                rlv_reward_me.complete();
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
        if (rlv_reward_me.isRefreshing()) {
            rlv_reward_me.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_reward_me.isLoadingMore()) {
            rlv_reward_me.stopLoadingMore();
        }

    }
}
