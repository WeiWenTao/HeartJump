package com.cucr.myapplication.fragment.star;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.eventBus.EventFIrstStarId;
import com.cucr.myapplication.model.eventBus.EventStarId;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
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
public class Fragment_star_xingwen extends Fragment implements SwipeRecyclerView.OnLoadListener {


    @ViewInject(R.id.rlv_xingwen)
    private SwipeRecyclerView rlv_xingwen;

    private View view;
    private QueryFtInfoCore mCore;
    private int starId;
    private int dataType;
    private int page;
    private int rows;
    private QueryFtInfos mQueryFtInfos;
    private Gson mGson;
    private XingWenAdapter mAdapter;
    private boolean from;

    public Fragment_star_xingwen(boolean from) {
        this.from = from;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//订阅
        MyLogger.jLog().i("注册");
        mCore = new QueryFtInfoCore();
        mGson = new Gson();
        mAdapter = new XingWenAdapter();
        dataType = 0;
        page = 1;
        rows = 15;
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_personal_pager_xingwen, container, false);
            ViewUtils.inject(this, view);
            initRlV();
            //如果是从粉丝主页跳转过来的 就自动刷新一遍
            if (from) {
                onRefresh();
            }
        }
        return view;
    }

    private void initRlV() {
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rlv_xingwen.getRecyclerView().addItemDecoration(decor);
        rlv_xingwen.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_xingwen.setAdapter(mAdapter);
        rlv_xingwen.setOnLoadListener(this);
        rlv_xingwen.onLoadingMore();
    }


    //切换明星的时候
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventStarId event) {
        starId = event.getStarId();
        if (mCore == null) {
            mCore = new QueryFtInfoCore();
        }
        onRefresh();
    }

    //查询第一个明星
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onEvent(EventFIrstStarId event) {
        starId = event.getFirstId();
        onRefresh();
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
        mCore.queryFtInfo(starId, dataType, -1, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
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
        });
    }

    @Override
    public void onLoadMore() {
        page++;
        MyLogger.jLog().i("page = " + page);
        mCore.queryFtInfo(starId, dataType, -1, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
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
        });
    }
}
