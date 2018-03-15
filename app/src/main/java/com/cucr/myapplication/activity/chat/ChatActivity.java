package com.cucr.myapplication.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.hyt.HytMemberListActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDissolveHyt;
import com.cucr.myapplication.widget.dialog.DialogExitHyt;
import com.cucr.myapplication.widget.dialog.DialogHytMembers;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

public class ChatActivity extends FragmentActivity implements DialogHytMembers.OnClickBt, DialogExitHyt.OnClickConfirm, RequersCallBackListener, DialogDissolveHyt.OnClickConfirm {

    private DialogHytMembers mDialog;
    private DialogExitHyt mExitHyt;
    private DialogDissolveHyt mDissloveHyt;
    private String mHytId;
    private Intent mIntent;
    private HytCore mCore;
    private Gson mGson;
    private boolean mIsCaptain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        mCore = new HytCore();
        mGson = new Gson();
        initTitle();
    }

    private void initTitle() {
//        targetId  title
//        Uri data = getIntent().getData();
        mIntent = new Intent(MyApplication.getInstance(), HytMemberListActivity.class);
        mHytId = getIntent().getData().getQueryParameter("targetId");
        String ctretId = getIntent().getData().getQueryParameter("ctretId");
        mIsCaptain = Integer.parseInt(ctretId) == ((int) SpUtil.getParam(SpConstant.USER_ID, -1));

        mIntent.putExtra("id", mHytId);
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//                return null;
//            }
//        }, true);
        mDialog = new DialogHytMembers(this, R.style.MyDialogStyle);
        mExitHyt = new DialogExitHyt(this, R.style.BirthdayStyleTheme);
        mDissloveHyt = new DialogDissolveHyt(this, R.style.BirthdayStyleTheme);

        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mDialog.setOnClickBt(this);
        mExitHyt.setOnClickConfirm(this);
        mDissloveHyt.setOnClickConfirm(this);
    }

    //返回键
    public void back(View view) {
        finish();
    }

    //显示dialog
    public void members(View view) {
        mDialog.show();
    }

    //成员列表
    @Override
    public void clickBt1() {
        startActivity(mIntent);
    }

    //退出后援团
    @Override
    public void clickBt2() {
        mDialog.dismiss();
        //如果是团长
        if (mIsCaptain) {
            mDissloveHyt.show();
        } else {
            mExitHyt.show();
        }

    }

    //确定退出
    @Override
    public void OnConfirm() {
        mCore.leaveHyt(mHytId, this);
        mExitHyt.dismiss();
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_FOURTEEN) {
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                Intent need = getIntent().putExtra("need", true);
                setResult(1, need);
                finish();
                ToastUtils.showToast("退出成功!");
            } else {
                ToastUtils.showToast(msg.getMsg());
            }
        }else if(what == Constans.TYPE_EIGHTEEN) {
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                Intent need = getIntent().putExtra("need", true);
                setResult(1, need);
                finish();
                ToastUtils.showToast("已解散该后援团");
            } else {
                ToastUtils.showToast(msg.getMsg());
            }
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

    }

    //解散后援团
    @Override
    public void OnClickDissolve() {
        mCore.dissolveHyt(mHytId, this);
    }
}
