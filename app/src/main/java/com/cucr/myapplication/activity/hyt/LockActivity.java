package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytLockAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytMembers;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class LockActivity extends BaseActivity implements HytLockAdapter.OnClickItem, RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv_list)
    private SwipeRecyclerView rlv_list;

    private HytLockAdapter mAdapter;
    private HytCore mCore;
    private String mHytId;

    @Override
    protected void initChild() {
        mAdapter = new HytLockAdapter();
        rlv_list.setAdapter(mAdapter);
        mCore = new HytCore();
        rlv_list.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter.setOnClickItem(this);
        mHytId = getIntent().getStringExtra("id");
        rlv_list.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_un_lock;
    }

    //跳转禁言天数选择
    @Override
    public void clickItem(int lockId) {
        Intent intent = new Intent(MyApplication.getInstance(), LockDetialActivity.class);
        intent.putExtra("hytId", mHytId);
        intent.putExtra("lockId", lockId);
        startActivityForResult(intent, 123);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        HytMembers hytMembers = mGson.fromJson(response.get(), HytMembers.class);
        if (hytMembers.isSuccess()) {
            if (isRefresh) {
                mAdapter.setData(hytMembers.getRows());
            } else {
                mAdapter.addData(hytMembers.getRows());
            }
            if (hytMembers.getTotal() <= page * rows) {
                rlv_list.onNoMore("没有更多了");
            } else {
                rlv_list.complete();
            }
        } else {
            ToastUtils.showToast(hytMembers.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_THIRTEEN) {
            if (rlv_list.isRefreshing()) {
                rlv_list.getSwipeRefreshLayout().setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_list.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryMembers(page, rows, mHytId, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_list.onLoadingMore();
        mCore.queryMembers(page, rows, mHytId, this);
    }
}
