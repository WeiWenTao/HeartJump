package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytLockAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytMembers;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class LockActivity extends BaseActivity implements HytLockAdapter.OnClickItem, RequersCallBackListener {

    @ViewInject(R.id.rlv_list)
    private RecyclerView rlv_list;

    private HytLockAdapter mAdapter;
    private HytCore mCore;
    private String mHytId;

    @Override
    protected void initChild() {
        mAdapter = new HytLockAdapter();
        rlv_list.setAdapter(mAdapter);
        mCore = new HytCore();
        rlv_list.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter.setOnClickItem(this);
        mHytId = getIntent().getStringExtra("id");
        mCore.queryMembers(mHytId,this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_un_lock;
    }

    //跳转禁言天数选择
    @Override
    public void clickItem(int lockId) {
        Intent intent = new Intent(MyApplication.getInstance(), LockDetialActivity.class);
        intent.putExtra("hytId",mHytId);
        intent.putExtra("lockId",lockId);
        startActivityForResult(intent,123);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        HytMembers hytMembers = mGson.fromJson(response.get(), HytMembers.class);
        if (hytMembers.isSuccess()) {
            mAdapter.setData(hytMembers.getRows());
        } else {
            ToastUtils.showToast(hytMembers.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
