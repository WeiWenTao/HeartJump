package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.StarListForQiYeAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventOnClickCancleFocus;
import com.cucr.myapplication.bean.eventBus.EventOnClickFocus;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.recyclerView.EndlessRecyclerOnScrollListener;
import com.cucr.myapplication.widget.recyclerView.LoadMoreWrapper;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by cucr on 2017/11/28.
 */

public class RecommendStarListFragemnt extends Fragment implements StarListForQiYeAdapter.OnItemClickListener {

    //刷新控件
    @ViewInject(R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipe_refresh_layout;

    //列表
    @ViewInject(R.id.rlv_starlist)
    private RecyclerView rlv_starlist;

    private StarListForQiYeAdapter mAdapter;
    private View rootView;
    private QueryStarListCore mCore;
    private Gson mGson;
    private int page;
    private int rows;
    private LoadMoreWrapper wapper;
    private Context mContext;

    public RecommendStarListFragemnt() {
        mCore = new QueryStarListCore();
        mGson = MyApplication.getGson();
        mContext = MyApplication.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_recommend_starlist, container, false);
            ViewUtils.inject(this, rootView);
            initView();
        }
        return rootView;
    }

    private void initView() {
        rows = 10;
        mAdapter = new StarListForQiYeAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        rlv_starlist.setLayoutManager(new GridLayoutManager(mContext, 2));
        wapper = new LoadMoreWrapper(mAdapter);
        rlv_starlist.setAdapter(wapper);
        initLoad();
        queryStar();

    }

    private void initLoad() {

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryStar();
            }
        });

        rlv_starlist.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                wapper.setLoadState(wapper.LOADING);
                page++;
                mCore.queryStar(1, page, rows, -1, null, null, new RequersCallBackListener() {
                    @Override
                    public void onRequestSuccess(int what, Response<String> response) {
                        StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                        if (starListInfos.isSuccess()) {
                            List<StarListInfos.RowsBean> rowsBeanLists = starListInfos.getRows();
                            if (rowsBeanLists.size() < rows) {
                                wapper.setLoadState(wapper.LOADING_END);
                            } else {
                                wapper.setLoadState(wapper.LOADING_COMPLETE);
                            }
                            if (!response.isFromCache()) {
                                mAdapter.addData(rowsBeanLists);
                            }
                            wapper.notifyDataSetChanged();
                        } else {
                            ToastUtils.showToast(starListInfos.getErrorMsg());
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
        });
    }

    //查询明星列表品
    private void queryStar() {
        if (!swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(true);
        }
        page = 1;
        mCore.queryStar(1, page, rows, -1, null, null, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starListInfos.isSuccess()) {
                    List<StarListInfos.RowsBean> rows = starListInfos.getRows();
                    mAdapter.setData(rows);
                    wapper.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(starListInfos.getErrorMsg());
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

    //点击关注时 刷新页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickfocus(EventOnClickFocus event) {
        wapper.notifyDataSetChanged();
    }

    //点击取消关注时 刷新页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickCanclefocus(EventOnClickCancleFocus event) {
        wapper.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //请求完成  如果还在加载  就停止加载(无网络情况)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinish(EventRequestFinish event) {
        if (swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(false);
        }

        if (wapper.getLoadState() == wapper.LOADING) {
            wapper.setLoadState(wapper.LOADING_COMPLETE);
        }

    }

    @Override
    public void onClickItems(int position, StarListInfos.RowsBean rowsBean) {
        //发送明星id到明星主页
        EventBus.getDefault().postSticky(new EventFIrstStarId(rowsBean.getId()));
        Intent intent = new Intent(MyApplication.getInstance(), StarPagerActivity.class);
        intent.putExtra("starId", rowsBean.getId());
        startActivity(intent);
    }
}
