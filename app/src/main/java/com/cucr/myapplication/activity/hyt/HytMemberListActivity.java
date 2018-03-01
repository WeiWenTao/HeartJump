package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytMembersAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytMembers;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDelMembers;
import com.yanzhenjie.nohttp.rest.Response;

public class HytMemberListActivity extends BaseActivity implements DialogDelMembers.OnClickBt, RequersCallBackListener {

    private DialogDelMembers mDialog;
    private String mHytId;
    private HytCore mCore;
    private HytMembersAdapter mAdapter;

    @Override
    protected void initChild() {
        RecyclerView rlv_members = (RecyclerView) findViewById(R.id.rlv_members);
        rlv_members.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new HytMembersAdapter();
        rlv_members.setAdapter(mAdapter);
        mCore = new HytCore();
        mHytId = getIntent().getStringExtra("id");
        mCore.queryMembers(mHytId, this);
        mDialog = new DialogDelMembers(this, R.style.MyDialogStyle);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mDialog.setOnClickBt(this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_hyt_member_list;
    }

    //显示删除对话框
    public void del(View view) {
        mDialog.show();
    }

    //解除禁言
    @Override
    public void clickBt1() {
        startActivity(new Intent(MyApplication.getInstance(),UnLockActivity.class));
    }

    //禁言
    @Override
    public void clickBt2() {
        startActivity(new Intent(MyApplication.getInstance(),UnLockActivity.class));
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
