package com.cucr.myapplication.fragment.personalMainPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.RLVStarAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.fragment.LazyFragment_app;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 911 on 2017/7/19.
 */

@SuppressLint("ValidFragment")
public class StarFragment extends LazyFragment_app implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    private View mView;
    private int userId;
    private RLVStarAdapter mAdapter;
    private QueryFocus mCore;
    private int page;
    private int rows;
    private SwipeRecyclerView mRlv_starlist;
    private Intent mIntent;
    private Gson mGson;
    private MultiStateView multiStateView;
    private boolean needShowLoading;
    private boolean isRefresh;

    public StarFragment(int userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_star, null);
        }
        return mView;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        page = 1;
        rows = 10;
        needShowLoading = true;
        mCore = new QueryFocus();
        mGson = MyApplication.getGson();
        mRlv_starlist = (SwipeRecyclerView) mView.findViewById(R.id.rlv_his_starlist);
        multiStateView = (MultiStateView) mView.findViewById(R.id.multiStateView);
        mRlv_starlist.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mRlv_starlist.setOnLoadListener(this);
        mAdapter = new RLVStarAdapter();
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRlv_starlist.getRecyclerView().addItemDecoration(decor);
        mRlv_starlist.setAdapter(mAdapter);
        //跳转企业用户看的明星主页
        mIntent = new Intent(MyApplication.getInstance(), StarPagerActivity.class);
        mAdapter.setOnItemClick(new RLVStarAdapter.OnItemClick() {
            @Override
            public void onItemClick(int starId) {
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("starId", starId);
                startActivity(mIntent);
                //发送明星id到明星主页
                EventBus.getDefault().postSticky(new EventFIrstStarId(starId));
            }
        });
        onRefresh();
    }


    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_ONE) {
            FocusInfo starList = mGson.fromJson(response.get(), FocusInfo.class);
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
                    mRlv_starlist.onNoMore("没有更多了");
                } else {
                    mRlv_starlist.complete();
                }
            } else {
                ToastUtils.showToast(starList.getErrorMsg());
            }

        }
    }


    @Override
    public void onRequestStar(int what) {
        if (what == Constans.TYPE_ONE) {
            if (needShowLoading) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                needShowLoading = false;
            }
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {
        if (what == Constans.TYPE_ONE) {
            if (isRefresh && response.getException() instanceof NetworkError) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_ONE) {
            if (mRlv_starlist.isRefreshing()) {
                mRlv_starlist.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (mRlv_starlist.isLoadingMore()) {
                mRlv_starlist.stopLoadingMore();
            }
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mRlv_starlist.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryMyFocusStars(userId, page, rows, this);
    }

    //加载
    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        mRlv_starlist.onLoadingMore();
        mCore.queryMyFocusStars(userId, page, rows, this);
    }
}
