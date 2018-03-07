package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.widget.dialog.DialogCleanCacheStyle;
import com.cucr.myapplication.widget.dialog.DialogExitApp;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingActivity extends BaseActivity {

    private DialogCleanCacheStyle mCleanDialog;
    private DialogExitApp mExitAppDialog;

    @Override
    protected void initChild() {

        initTitle("设置");

        //对话框
        mCleanDialog = new DialogCleanCacheStyle(this, R.style.ShowAddressStyleTheme);
        mExitAppDialog = new DialogExitApp(this, R.style.ShowAddressStyleTheme);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting;
    }


    //消息设置
    @OnClick(R.id.rl_message_setting)
    public void msgSetting(View view) {
        startActivity(new Intent(MyApplication.getInstance(), SettingMessageActivity.class));
    }

    //关于我们
    @OnClick(R.id.rl_above_us)
    public void aboveUs(View view) {
        startActivity(new Intent(MyApplication.getInstance(), SettingAboveUsActivity.class));
    }

    //清除缓存
    @OnClick(R.id.rl_clean_cache)
    public void cleanCatch(View view) {
        mCleanDialog.show();
    }

    //账号安全
    @OnClick(R.id.rl_account_safe)
    public void accountSafe(View view) {
        startActivity(new Intent(MyApplication.getInstance(), SettingAccountSafeActivity.class));
    }

    //账号管理
    @OnClick(R.id.rl_account_manager)
    public void accountManager(View view) {
        startActivity(new Intent(MyApplication.getInstance(), SettingAccountManagerActivity.class));
    }

    //退出
    @OnClick(R.id.rl_exit)
    public void exit(View view) {
        mExitAppDialog.show();
    }


}
