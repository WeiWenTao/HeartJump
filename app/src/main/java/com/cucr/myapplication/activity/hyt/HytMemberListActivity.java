package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytMembersAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytMembers;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDelMembers;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class HytMemberListActivity extends BaseActivity implements DialogDelMembers.OnClickBt, RequersCallBackListener {

    @ViewInject(R.id.iv_member)
    private ImageView iv_member;

    private DialogDelMembers mDialog;
    private String mHytId;
    private HytCore mCore;
    private HytMembersAdapter mAdapter;
    private Intent mIntent;

    @Override
    protected void initChild() {
        RecyclerView rlv_members = (RecyclerView) findViewById(R.id.rlv_members);
        rlv_members.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new HytMembersAdapter();
        rlv_members.setAdapter(mAdapter);
        mHytId = getIntent().getStringExtra("id");
        mCore = new HytCore();
        mIntent = new Intent();
        mIntent.putExtra("id", mHytId);
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
    @OnClick(R.id.iv_member)
    public void del(View view) {
        mDialog.show();
    }

    //解除禁言
    @Override
    public void clickBt1() {
        mIntent.setClass(MyApplication.getInstance(), UnLockActivity.class);
        startActivity(mIntent);
    }

    //禁言
    @Override
    public void clickBt2() {
        mIntent.setClass(MyApplication.getInstance(), LockActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        HytMembers hytMembers = mGson.fromJson(response.get(), HytMembers.class);
        if (hytMembers.isSuccess()) {
            mAdapter.setData(hytMembers.getRows());
            if (hytMembers.getRows().get(0).getUser().getId() == ((int) SpUtil.getParam(SpConstant.USER_ID, -1))) {
                iv_member.setVisibility(View.VISIBLE);
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

    }
}
