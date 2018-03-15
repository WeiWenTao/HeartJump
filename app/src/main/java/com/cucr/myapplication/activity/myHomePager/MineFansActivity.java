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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class MineFansActivity extends BaseActivity implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv)
    private SwipeRecyclerView rlv;

    private QueryFocus core;
    private MyFocusAdapter mAdapter;


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
        return R.layout.activity_mine_fans;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        switch (what) {
            case Constans.TYPE_THREE:
                FocusInfo focusInfo = MyApplication.getGson().fromJson(response.get(), FocusInfo.class);
                if (focusInfo.isSuccess()) {
                    if (isRefresh) {
                        mAdapter.setData(focusInfo.getRows());
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

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_THREE) {
            if (rlv.isRefreshing()) {
                rlv.getSwipeRefreshLayout().setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv.getSwipeRefreshLayout().setRefreshing(true);
        core.queryMyFens(page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv.onLoadingMore();
        core.queryMyFens(page, rows, this);
    }
}
