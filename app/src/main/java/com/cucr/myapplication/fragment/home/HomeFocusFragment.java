package com.cucr.myapplication.fragment.home;

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
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.fragment.LazyFragment;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;


/**
 * Created by 911 on 2017/4/10.
 */

public class HomeFocusFragment extends LazyFragment implements SwipeRecyclerView.OnLoadListener {

    private QueryFtInfoCore mDataCore;
    private QueryFtInfos mQueryFtInfos;
    private SwipeRecyclerView rlv_focus_news;
    private XingWenAdapter mAdapter;
    private int page;
    private int rows;
    private View rootView;
    private Gson mGson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_foucs, container, false);

        }
        return rootView;
    }

    protected void initView() {
        page = 1;
        rows = 20;
        mGson = MyApplication.getGson();
        mDataCore = new QueryFtInfoCore();
        rlv_focus_news = (SwipeRecyclerView) rootView.findViewById(R.id.rlv_focus_news);
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rlv_focus_news.getRecyclerView().addItemDecoration(decor);
        rlv_focus_news.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_focus_news.setOnLoadListener(this);
        mAdapter = new XingWenAdapter();
        rlv_focus_news.setAdapter(mAdapter);
        onRefresh();
    }

    @Override
    protected void onFragmentFirstVisible() {
        initView();
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (!rlv_focus_news.getSwipeRefreshLayout().isRefreshing()) {
            rlv_focus_news.getSwipeRefreshLayout().setRefreshing(true);
        }

        mDataCore.queryFtInfo(true, -1, 0, -1, false, page, rows, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what,Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    mAdapter.setData(mQueryFtInfos);
                    rlv_focus_news.getRecyclerView().smoothScrollToPosition(0);
                    if (mQueryFtInfos.getTotal() == mQueryFtInfos.getRows().size()) {
                        rlv_focus_news.onNoMore("木有了");
                    } else {
                        rlv_focus_news.complete();
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
                rlv_focus_news.setRefreshing(false);
            }

            @Override
            public void onRequestStar(int what) {

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
        rlv_focus_news.onLoadingMore();
        page++;
        MyLogger.jLog().i("page = " + page);
        mDataCore.queryFtInfo(true, -1, 0, -1, false, page, rows, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what,Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    if (mQueryFtInfos.getRows().size() != 0) {
                        mAdapter.addData(mQueryFtInfos.getRows());
                    }
                    //判断是否还有数据
                    if (mQueryFtInfos.getTotal() <= page * rows) {
                        rlv_focus_news.onNoMore("没有更多了");
                    } else {
                        rlv_focus_news.complete();
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

            }
        });
    }
}
