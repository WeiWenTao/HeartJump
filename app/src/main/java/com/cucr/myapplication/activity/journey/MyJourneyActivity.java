package com.cucr.myapplication.activity.journey;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.starListAndJourney.QueryJourneyList;
import com.cucr.myapplication.core.starListAndJourney.StarJourney;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.RZ.RzResult;
import com.cucr.myapplication.model.starJourney.StarJourneyList;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.recyclerView.EndlessRecyclerOnScrollListener;
import com.cucr.myapplication.widget.recyclerView.LoadMoreWrapper;
import com.cucr.myapplication.widget.swipeRlv.DemoAdapter;
import com.cucr.myapplication.widget.swipeRlv.ItemTouchListener;
import com.cucr.myapplication.widget.swipeRlv.SwipeMenuRecyclerView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class MyJourneyActivity extends BaseActivity implements ItemTouchListener {

    @ViewInject(R.id.recyclerView)
    private SwipeMenuRecyclerView recyclerView;

    @ViewInject(R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipe_refresh_layout;

    private DemoAdapter mAdapter;
    private QueryJourneyList mCore;
    private StarJourney mJourneyCore;
    private List<StarJourneyList.RowsBean> mRows;
    private LoadMoreWrapper wapper;
    private int page;
    private int row = 15;

    @Override
    protected void initChild() {
        ViewUtils.inject(this);

        mCore = new QueryJourneyList();
        mJourneyCore = new StarJourney();
        mRows = new ArrayList<>();

        initRlv();
        //查询明星行程
        queryJourney(true);


    }

    //isRefresh 刷新还是加载更多
    private void queryJourney(final boolean isRefresh) {
        MyLogger.jLog().i("isRefresh:" + isRefresh);
        if (isRefresh) {
            page = 1;
            //第一次进来要自己刷新
            if (!swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(true);
            }

        } else {
            if (wapper.getLoadState() == wapper.LOADING_END) {
                ToastUtils.showEnd();
//                return;
            }
            page++;
            wapper.setLoadState(wapper.LOADING);
        }
        //明星用户就用userid
        mCore.QueyrStarJourney(row, page, (int) SpUtil.getParam(SpConstant.USER_ID, -1), null, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarJourneyList starJourneys = mGson.fromJson(response.get(), StarJourneyList.class);
                if (starJourneys.isSuccess()) {
                    if (swipe_refresh_layout.isRefreshing()) {
                        swipe_refresh_layout.setRefreshing(false);
                    }
                    //是否是刷新
                    if (isRefresh) {
                        mRows.clear();
                        mRows.addAll(starJourneys.getRows());
                        mAdapter.setData(starJourneys.getRows());

                        //加载更多
                    } else {
                        mRows.addAll(starJourneys.getRows());
                        mAdapter.addData(starJourneys.getRows());
                        //是否还有数据
                        if (starJourneys.getTotal() <= page * row) {
                            wapper.setLoadState(wapper.LOADING_END);
                        } else {
                            wapper.setLoadState(wapper.LOADING_COMPLETE);
                        }
                    }
                    wapper.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(starJourneys.getErrorMsg());
                    //加载失败 关闭刷新动画
                    swipe_refresh_layout.setRefreshing(false);
                    wapper.setLoadState(wapper.LOADING_COMPLETE);
                }
            }
        });
    }

    private void initRlv() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new DemoAdapter();
        wapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(wapper);
        mAdapter.setItemTouchListener(this);

        // 设置下拉刷新
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryJourney(true);
                MyLogger.jLog().i("queryJourney(true)");
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                queryJourney(false);
                MyLogger.jLog().i("queryJourney(false)");
            }
        });
    }


    @Override
    protected int getChildRes() {
        return R.layout.activity_my_journey;
    }

    //添加行程
    @OnClick(R.id.iv_journey_add)
    public void addJourney(View view) {
        startActivityForResult(new Intent(this, AddJourneyActivity.class), 1);
    }

    //侧滑删除
    @Override
    public void onClcikDelete(View v, final int position) {
        mJourneyCore.deleteJourney(mRows.get(position).getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                RzResult rzResult = mGson.fromJson(response.get(), RzResult.class);
                if (rzResult.isSuccess()) {
                    MyLogger.jLog().i("删除了" + mRows.get(position).getTitle() + " 行程，position：" + position);
                    mRows.remove(position);
                    mAdapter.setData(mRows);
//                    wapper.notifyDataSetChanged();
                    wapper.notifyItemRemoved(position);
                    if (position + 1 >= mRows.size()) {
                        wapper.notifyItemRangeChanged(position, mRows.size());
                    } else {
                        wapper.notifyItemRangeChanged(position + 1, mRows.size());
                    }
                } else {
                    ToastUtils.showToast(rzResult.getMsg());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //查询明星行程
        queryJourney(true);
    }

    @Override
    public void onItemClcik(View v, int position) {
        MyLogger.jLog().i("点击了条目，position：" + position);
    }

}
