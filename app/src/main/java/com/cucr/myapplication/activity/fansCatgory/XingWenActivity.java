package com.cucr.myapplication.activity.fansCatgory;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

public class XingWenActivity extends BaseActivity implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    @ViewInject(R.id.rlv_news)
    private SwipeRecyclerView rlv_news;

    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private XingWenAdapter mAdapter;
    private QueryFtInfoCore mNewsCore;
    private int mStarId;


    @Override
    protected void initChild() {
        initTitle("星闻资讯");
        mStarId = getIntent().getIntExtra("starId", -1);
        mAdapter = new XingWenAdapter();
        mNewsCore = new QueryFtInfoCore();
        initRlv();
        onRefresh();
    }

    private void initRlv() {
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rlv_news.getRecyclerView().addItemDecoration(decor);
        rlv_news.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_news.setAdapter(mAdapter);
        rlv_news.setOnLoadListener(this);
        rlv_news.onLoadingMore();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_xing_wen;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mNewsCore.queryFtInfo(mStarId, 0, -1, false, page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_news.onLoadingMore();
        mNewsCore.queryFtInfo(mStarId, 0, -1, false, page, rows, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        QueryFtInfos newsInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
        if (newsInfos.isSuccess()) {
            if (isRefresh) {
                if (newsInfos.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mAdapter.setData(newsInfos);
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(newsInfos.getRows());
            }
            if (newsInfos.getTotal() <= page * rows) {
                rlv_news.onNoMore("没有更多了");
            } else {
                rlv_news.complete();
            }
        } else {
            ToastUtils.showToast(newsInfos.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (needShowLoading && isRefresh) {
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
        if (rlv_news.isRefreshing()) {
            rlv_news.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_news.isLoadingMore()) {
            rlv_news.stopLoadingMore();
        }
    }

    @OnClick(R.id.ll_error)
    public void refres(View view) {
        needShowLoading = true;
        onRefresh();
    }
}
