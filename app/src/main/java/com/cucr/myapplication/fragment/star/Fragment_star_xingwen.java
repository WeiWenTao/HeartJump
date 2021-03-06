package com.cucr.myapplication.fragment.star;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventDaBangStarPagerId;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventStarId;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 911 on 2017/6/24.
 */
@SuppressLint("ValidFragment")
public class Fragment_star_xingwen extends Fragment implements SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv_xingwen)
    private SwipeRecyclerView rlv_xingwen;

    @ViewInject(R.id.rlv_test)
    private RecyclerView rlv_test;

    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private View view;
    private QueryFtInfoCore mCore;
    private int starId;
    private int dataType;
    private int page;
    private int rows;
    private QueryFtInfos mQueryFtInfos;
    private Gson mGson;
    private XingWenAdapter mAdapter;
    private boolean needShowLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCore = new QueryFtInfoCore();
        mGson = new Gson();
        mAdapter = new XingWenAdapter();
        dataType = 0;
        page = 1;
        rows = 15;
        needShowLoading = true;
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_personal_pager_xingwen, container, false);
            ViewUtils.inject(this, view);
            initRlV();
            //如果是从粉丝主页跳转过来的 就自动刷新一遍
        }
        EventBus.getDefault().register(this);//订阅
        return view;

    }

    private void initRlV() {
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rlv_xingwen.getRecyclerView().addItemDecoration(decor);
        rlv_xingwen.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_xingwen.setAdapter(mAdapter);
        rlv_test.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_test.setAdapter(mAdapter);
        rlv_xingwen.setOnLoadListener(this);
        rlv_xingwen.onLoadingMore();
    }

    //切换明星的时候
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventStarId event) {
        if (event.getStarId() == 0) {
            return;
        }
        starId = event.getStarId();
        if (mCore == null) {
            mCore = new QueryFtInfoCore();
        }
        onRefresh();
    }

    //查询第一个明星
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onEvent(EventFIrstStarId event) {
        if (starId != event.getFirstId() && event.getFirstId() != 0) {
            starId = event.getFirstId();
            needShowLoading = true;
            onRefresh();
        }
    }

    //查询第一个明星
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onEvent(EventDaBangStarPagerId event) {
        if (starId != event.getFirstId() && event.getFirstId() != 0) {
            starId = event.getFirstId();
            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//注销
        MyLogger.jLog().i("注销");
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (!rlv_xingwen.getSwipeRefreshLayout().isRefreshing()) {
            rlv_xingwen.getSwipeRefreshLayout().setRefreshing(true);
        }
        mCore.queryFtInfo(starId, dataType, -1, false, page, rows, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    if (mQueryFtInfos.getTotal() == 0) {
                        rlv_test.setVisibility(View.VISIBLE);
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        return;
                    }
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    rlv_test.setVisibility(View.GONE);
                    mAdapter.setData(mQueryFtInfos);
                    rlv_xingwen.getRecyclerView().smoothScrollToPosition(0);
                    if (mQueryFtInfos.getTotal() == mQueryFtInfos.getRows().size()) {
                        rlv_xingwen.onNoMore("木有了");
                    } else {
                        rlv_xingwen.complete();
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
                rlv_xingwen.setRefreshing(false);
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

            }

            @Override
            public void onRequestFinish(int what) {

            }
        });
    }

    @Override
    public void onLoadMore() {
        rlv_xingwen.onLoadingMore();
        page++;
        mCore.queryFtInfo(starId, dataType, -1, false, page, rows, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    if (mQueryFtInfos.getRows().size() != 0) {
                        mAdapter.addData(mQueryFtInfos.getRows());
                    }
                    //判断是否还有数据
                    if (mQueryFtInfos.getTotal() <= page * rows) {
                        rlv_xingwen.onNoMore("没有更多了");
                    } else {
                        rlv_xingwen.complete();
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
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
                if (rlv_xingwen.isRefreshing()) {
                    rlv_xingwen.getSwipeRefreshLayout().setRefreshing(false);
                }
                if (rlv_xingwen.isLoadingMore()) {
                    rlv_xingwen.stopLoadingMore();
                }
            }
        });
    }
}
