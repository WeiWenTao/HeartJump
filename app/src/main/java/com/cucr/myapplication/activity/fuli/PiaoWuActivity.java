package com.cucr.myapplication.activity.fuli;

import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MyActivesAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fuli.ErWeiMaInfo;
import com.cucr.myapplication.bean.fuli.MyActives;
import com.cucr.myapplication.core.fuLi.FuLiCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogErWeiMa;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class PiaoWuActivity extends BaseActivity implements RequersCallBackListener, MyActivesAdapter.OnClickItem, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv)
    private SwipeRecyclerView rlv;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private MyActivesAdapter mAdapter;
    private FuLiCore mCore;
    private MyActives mMyActives;
    private DialogErWeiMa mDialog;

    @Override
    protected void initChild() {
        page = 1;
        rows = 15;
        mCore = new FuLiCore();
        mAdapter = new MyActivesAdapter();
        mAdapter.setOnClickItem(this);
        rlv.setAdapter(mAdapter);
        rlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv.setOnLoadListener(this);
        mDialog = new DialogErWeiMa(this, R.style.MyWaitDialog);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_piao_wu;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        mMyActives = mGson.fromJson(response.get(), MyActives.class);
        if (mMyActives.isSuccess()) {
            if (isRefresh) {
                if (filter(mMyActives.getRows()).size() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mAdapter.setData(filter(mMyActives.getRows()));
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(filter(mMyActives.getRows()));
            }
            if (mMyActives.getTotal() <= page * rows) {
                rlv.onNoMore("没有更多了");
            } else {
                rlv.complete();
            }
        } else {
            ToastUtils.showToast(mMyActives.getErrorMsg());
        }
    }

    private List<MyActives.RowsBean> filter(List<MyActives.RowsBean> rows) {
        List<MyActives.RowsBean> filterRoews = new ArrayList<>();
        for (MyActives.RowsBean row : rows) {
            if (row.getActive().isShowQRCode()) {
                filterRoews.add(row);
            }
        }
        return filterRoews;
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (rlv.isRefreshing()) {
            rlv.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(ErWeiMaInfo info) {
        mDialog.show();
        mDialog.setDate(info);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv.getSwipeRefreshLayout().setRefreshing(true);
        mCore.QueryMyActive(page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv.onLoadingMore();
        mCore.QueryMyActive(page, rows, this);
    }
}
