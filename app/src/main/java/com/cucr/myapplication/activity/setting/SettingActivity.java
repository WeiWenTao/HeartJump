package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.dialog.DialogCleanCacheStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingActivity extends Activity {

    @ViewInject(R.id.head)
    RelativeLayout head;

    private DialogCleanCacheStyle mCleanDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ViewUtils.inject(this);
        //沉浸栏
        initHead();
        //对话框
        mCleanDialog = new DialogCleanCacheStyle(this, R.style.ShowAddressStyleTheme);
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //消息设置
    @OnClick(R.id.rl_message_setting)
    public void msgSetting(View view) {
        startActivity(new Intent(this, SettingMessageActivity.class));
    }

    //关于我们
    @OnClick(R.id.rl_above_us)
    public void aboveUs(View view) {
        startActivity(new Intent(this, SettingAboveUsActivity.class));
    }

    //返回
    @OnClick(R.id.iv_setting_back)
    public void back(View v) {
        finish();
    }

    //清除缓存
    @OnClick(R.id.rl_clean_cache)
    public void cleanCatch(View view) {
        mCleanDialog.show();
    }

    //账号安全
    @OnClick(R.id.rl_account_safe)
    public void accountSafe(View view) {
        startActivity(new Intent(this, SettingAccountSafeActivity.class));
    }

    //账号管理
    @OnClick(R.id.rl_account_manager)
    public void accountManager(View view) {
        startActivity(new Intent(this, SettingAccountManagerActivity.class));
    }


}
