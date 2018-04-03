package com.cucr.myapplication.activity.myHomePager;

import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MyFocusAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

public class FocusActivity extends BaseActivity implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    //导航栏
    @ViewInject(R.id.rlv)
    private SwipeRecyclerView rlv;

    private QueryFocus core;
    private MyFocusAdapter mAdapter;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    @Override
    protected void initChild() {
        core = new QueryFocus();
        rlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new MyFocusAdapter();
        rlv.setAdapter(mAdapter);
        rlv.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_focus;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        switch (what) {
            case Constans.TYPE_TWO:
                FocusInfo focusInfo = MyApplication.getGson().fromJson(response.get(), FocusInfo.class);
                if (focusInfo.isSuccess()) {
                    if (isRefresh) {
                        if (focusInfo.getTotal() == 0) {
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        } else {
                            mAdapter.setData(focusInfo.getRows());
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        }
                    } else {
                        mAdapter.addDate(focusInfo.getRows());
                    }
                    if (focusInfo.getTotal() <= page * rows) {
                        rlv.onNoMore("没有更多了");
                    } else {
                        rlv.complete();
                    }
                } else {
                    ToastUtils.showToast(focusInfo.getErrorMsg());
                }
                break;

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
            if (rlv.isRefreshing()) {
                rlv.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (rlv.isLoadingMore()) {
                rlv.stopLoadingMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv.getSwipeRefreshLayout().setRefreshing(true);
        core.queryMyFocusOthers(page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv.onLoadingMore();
        core.queryMyFocusOthers(page, rows, this);
    }
}
