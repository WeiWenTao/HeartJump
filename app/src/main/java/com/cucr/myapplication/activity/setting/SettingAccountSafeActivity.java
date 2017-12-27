package com.cucr.myapplication.activity.setting;

import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.dialog.DialogChangePswStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingAccountSafeActivity extends BaseActivity {

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;


    private DialogChangePswStyle mPswDialog;

    @Override
    protected void initChild() {
        initViews();

        initDialog();
    }

    private void initViews() {
        initTitle("账号安全");
        tv_phone.setText(((String) SpUtil.getParam(SpConstant.USER_NAEM, "")));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_account_safe;
    }

    private void initDialog() {
        mPswDialog = new DialogChangePswStyle(this, R.style.ShowAddressStyleTheme);
    }

    //修改密码
    @OnClick(R.id.rl_modify_psw)
    public void modifyPsw(View view){
        mPswDialog.show();
    }

}
