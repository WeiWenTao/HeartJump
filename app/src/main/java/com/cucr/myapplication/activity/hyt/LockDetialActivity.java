package com.cucr.myapplication.activity.hyt;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.HytLockDetialAdapter;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class LockDetialActivity extends BaseActivity implements AdapterView.OnItemClickListener, RequersCallBackListener {

    @ViewInject(R.id.lv)
    private ListView lv;

    private List<Integer> times;
    private HytLockDetialAdapter mAdapter;
    private String mHytId;
    private int mLockId;
    private HytCore mCore;
    private int lockPosi;


    @Override
    protected void initChild() {
        initDate();
        mHytId = getIntent().getStringExtra("hytId");
        mLockId = getIntent().getIntExtra("lockId", -1);
        mAdapter = new HytLockDetialAdapter(times);
        mCore = new HytCore();
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(this);
    }

    private void initDate() {
        times = new ArrayList<>();
        times.add(10);
        times.add(60);
        times.add(720);
        times.add(1440);
        times.add(14400);
        times.add(43200);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_lock_detial;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lockPosi = position;
        mAdapter.setSel(position);
    }

    //确定禁言
    @OnClick(R.id.tv_yes)
    public void click(View view) {
        Integer howlong = times.get(lockPosi);
        mCore.hytLock(mLockId, mHytId, howlong, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
        if (msg.isSuccess()) {
            ToastUtils.showToast("已禁言该用户");
            finish();
        } else {
            ToastUtils.showToast(msg.getMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
