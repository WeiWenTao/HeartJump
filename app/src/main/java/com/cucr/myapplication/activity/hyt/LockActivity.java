package com.cucr.myapplication.activity.hyt;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytLockAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LockActivity extends BaseActivity implements HytLockAdapter.OnClickItem {

    @ViewInject(R.id.rlv_list)
    private RecyclerView rlv_list;
    private HytLockAdapter mAdapter;

    @Override
    protected void initChild() {
        mAdapter = new HytLockAdapter();
        rlv_list.setAdapter(mAdapter);
        rlv_list.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter.setOnClickItem(this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_un_lock;
    }

    //跳转禁言天数选择
    @Override
    public void clickItem() {

    }
}
