package com.cucr.myapplication.fragment.msgFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.MsgGoodAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.MsgBean.MsgInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.msg.MsgCore;
import com.cucr.myapplication.fragment.LazyFragment;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2018/3/16.
 */

public class GoodsFragment extends LazyFragment implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {
    private View rootView;
    private Gson mGson;
    private MsgGoodAdapter mAdapter;
    private int page;
    private int rows;
    private boolean isRefresh;
    private SwipeRecyclerView mSrlv;
    private MsgCore mCore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_common_rlv, container, false);
            initView();
        }
        return rootView;
    }

    @Override
    protected void onFragmentFirstVisible() {
        onRefresh();
    }

    private void initView() {
        rows = 10;
        mGson = MyApplication.getGson();
        mCore = new MsgCore();
        mSrlv = (SwipeRecyclerView) rootView.findViewById(R.id.srlv);
        mAdapter = new MsgGoodAdapter();
        mSrlv.getRecyclerView().setAdapter(mAdapter);
        mSrlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mSrlv.setOnLoadListener(this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        MsgInfo msgInfo = mGson.fromJson(response.get(), MsgInfo.class);
        if (msgInfo.isSuccess()) {
            if (isRefresh) {
                mAdapter.setDate(msgInfo.getRows());
            } else {
                mAdapter.addDate(msgInfo.getRows());
            }
            if (msgInfo.getTotal() <= page * rows) {
                mSrlv.onNoMore("没有更多了");
            } else {
                mSrlv.complete();
            }
        } else {
            ToastUtils.showToast(msgInfo.getErrorMsg());
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
        if (what == Constans.TYPE_ONE) {
            if (mSrlv.isRefreshing()) {
                mSrlv.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (mSrlv.isLoadingMore()) {
                mSrlv.stopLoadingMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mSrlv.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryMsgInfo(page, rows, 5, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        mSrlv.onLoadingMore();
        mCore.queryMsgInfo(page, rows, 5, this);
    }
}
