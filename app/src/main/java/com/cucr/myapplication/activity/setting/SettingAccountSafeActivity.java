package com.cucr.myapplication.activity.setting;

import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.widget.dialog.DialogChangePswStyle;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingAccountSafeActivity extends BaseActivity {

    private DialogChangePswStyle mPswDialog;

    @Override
    protected void initChild() {
        initTitle("账号安全");
        initDialog();
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
