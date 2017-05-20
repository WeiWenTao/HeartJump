package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.dialog.DialogChangePswStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingAccountSafeActivity extends Activity {

    private DialogChangePswStyle mPswDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account_safe);
        ViewUtils.inject(this);

        initDialog();

    }

    private void initDialog() {
        mPswDialog = new DialogChangePswStyle(this, R.style.ShowAddressStyleTheme);
    }

    @OnClick(R.id.rl_modify_psw)
    public void modifyPsw(View view){
        mPswDialog.show();
    }



    //返回
    @OnClick(R.id.iv_account_safe_back)
    public void back(View view){
        finish();
    }
}
