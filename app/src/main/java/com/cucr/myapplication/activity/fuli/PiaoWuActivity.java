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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class PiaoWuActivity extends BaseActivity implements RequersCallBackListener, MyActivesAdapter.OnClickItem, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv)
    private SwipeRecyclerView rlv;

    private MyActivesAdapter mAdapter;
    private FuLiCore mCore;
    private MyActives mMyActives;

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
                mAdapter.setData(mMyActives.getRows());
            } else {
                mAdapter.addData(mMyActives.getRows());
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

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (rlv.isRefreshing()) {
            rlv.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(ErWeiMaInfo info) {
        // TODO: 2018/3/2 每次弹窗都会new一个对象
        DialogErWeiMa dialog = new DialogErWeiMa(this, R.style.MyWaitDialog);
        dialog.setDate(info);
        dialog.show();
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
