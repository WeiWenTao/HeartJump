package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.widget.dialog.DialogCommentSettingStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zcw.togglebutton.ToggleButton;

public class SettingMessageActivity extends BaseActivity {

    //关注开关
    @ViewInject(R.id.toggle_focus_tip)
    ToggleButton toggle_focus_tip;

    //消息开关
    @ViewInject(R.id.toggle_message_tip)
    ToggleButton toggle_message_tip;

    //评论设置
    @ViewInject(R.id.rl_comment)
    RelativeLayout rl_comment;

    private DialogCommentSettingStyle mDailogCommentSetting;

    @Override
    protected void initChild() {

        initTitle("消息设置");

        //初始化开关
        initToggle();

        //初始化对话框
        initDialog();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_message;
    }

    private void initDialog() {
        mDailogCommentSetting = new DialogCommentSettingStyle(this, R.style.ShowAddressStyleTheme);
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

    //点赞设置
    @OnClick(R.id.rl_like)
    public void setLike(View view){
        startActivity(new Intent(this,SettingLikeActivity.class));
    }


    //@我的
    @OnClick(R.id.rl_comment)
    public void setomment(View view){
        mDailogCommentSetting.show();
    }
}
