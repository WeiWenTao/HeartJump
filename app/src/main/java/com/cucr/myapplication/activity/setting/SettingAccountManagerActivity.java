package com.cucr.myapplication.activity.setting;

import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.widget.dialog.DialogQuitAccountStyle;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingAccountManagerActivity extends BaseActivity {

    private DialogQuitAccountStyle mQuitDialog;


    @Override
    protected void initChild() {
        initTitle("账号管理");
        initDialog();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_account_manager;
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

}
