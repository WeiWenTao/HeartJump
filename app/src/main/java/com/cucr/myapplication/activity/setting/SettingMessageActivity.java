package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.dialog.DialogAtMeSettingStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zcw.togglebutton.ToggleButton;

public class SettingMessageActivity extends Activity {

    @ViewInject(R.id.toggle_focus_tip)
    ToggleButton toggle_focus_tip;

    @ViewInject(R.id.toggle_message_tip)
    ToggleButton toggle_message_tip;
    private DialogAtMeSettingStyle mDailogAtMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        ViewUtils.inject(this);

        //初始化开关
        initToggle();

        //初始化对话框
        initDialog();
    }

    private void initDialog() {
        mDailogAtMe = new DialogAtMeSettingStyle(this, R.style.ShowAddressStyleTheme);
    }

    private void initToggle() {

        //“消息提醒”开关的监听
        toggle_message_tip.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });

        //“关注明星动态”开关的监听
        toggle_focus_tip.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

            }
        });
    }

    //返回
    @OnClick(R.id.iv_msg_setting_back)
    public void back(View v){
        finish();
    }

    //点赞设置
    @OnClick(R.id.rl_like)
    public void setLike(View view){
        startActivity(new Intent(this,SettingLikeActivity.class));
    }

    @OnClick(R.id.rl_at_me)
    public void setAtMe(View view){
        mDailogAtMe.show();
    }
}
