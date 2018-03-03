package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.regist.NewLoadActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.user.AccountCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogChangePswStyle;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class SettingAccountSafeActivity extends BaseActivity implements DialogChangePswStyle.ClickListener {

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    private DialogChangePswStyle mPswDialog;
    private AccountCore mCore;
    private MyWaitDialog mWaitDialog;

    @Override
    protected void initChild() {
        initViews();

        initDialog();
    }

    private void initViews() {
        mCore = new AccountCore();
        initTitle("账号安全");
        tv_phone.setText(((String) SpUtil.getParam(SpConstant.USER_NAEM, "")));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_account_safe;
    }

    private void initDialog() {
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mPswDialog = new DialogChangePswStyle(this, R.style.ShowAddressStyleTheme);
        mPswDialog.setClickListener(this);
    }

    //修改密码
    @OnClick(R.id.rl_modify_psw)
    public void modifyPsw(View view) {
        mPswDialog.show();
    }

    @Override
    public void onClickSave(String oldPsw, final String newPsw) {
        mPswDialog.dismiss();
        mCore.relasePsw(oldPsw, newPsw, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (reBackMsg.isSuccess()) {
                    SpUtil.setParam(SpConstant.PASSWORD, newPsw);       //修改密码成功 跳转登录界面重新登录
                    startActivity(new Intent(MyApplication.getInstance(), NewLoadActivity.class));
                    ToastUtils.showToast("修改成功,请重新登录");
                    mWaitDialog.dismiss();
                    finish();
                } else {
                    ToastUtils.showToast(reBackMsg.getMsg());
                }
            }

            @Override
            public void onRequestStar(int what) {
                mWaitDialog.show();
            }

            @Override
            public void onRequestFinish(int what) {
                if (mWaitDialog.isShowing()) {
                    mWaitDialog.dismiss();
                }
            }
        });
    }
}
