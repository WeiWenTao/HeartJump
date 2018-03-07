package com.cucr.myapplication.activity.setting;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zcw.togglebutton.ToggleButton;

public class SettingMessageActivity extends BaseActivity {

    //关注开关
    @ViewInject(R.id.toggle_focus_tip)
    private ToggleButton toggle_focus_tip;

    //消息开关
    @ViewInject(R.id.toggle_message_tip)
    private ToggleButton toggle_message_tip;

    @Override
    protected void initChild() {

        initTitle("消息设置");
        //初始化开关
        initToggle();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_message;
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
}
