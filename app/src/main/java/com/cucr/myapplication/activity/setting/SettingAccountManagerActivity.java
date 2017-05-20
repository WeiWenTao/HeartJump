package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.dialog.DialogQuitAccountStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingAccountManagerActivity extends Activity {

    private DialogQuitAccountStyle mQuitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account_manager);
        ViewUtils.inject(this);

        initDialog();



    }

    private void initDialog() {
        mQuitDialog = new DialogQuitAccountStyle(this, R.style.ShowAddressStyleTheme);
        mQuitDialog.setOnClickConfirmQuit(new DialogQuitAccountStyle.OnClickConfirmQuit() {
            @Override
            public void clickConfirmQuit() {
                finish();
            }
        });
    }

    //退出当前账号
    @OnClick(R.id.tv_quit_account)
    public void quitAccount(View view){
        mQuitDialog.show();
    }

    //返回
    @OnClick(R.id.iv_account_manager_back)
    public void back(View view){
        finish();
    }
}
