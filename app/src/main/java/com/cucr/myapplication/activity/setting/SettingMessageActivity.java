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
import com.cucr.myapplication.widget.dialog.DialogCommentSettingStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zcw.togglebutton.ToggleButton;

public class SettingMessageActivity extends Activity {

    //关注开关
    @ViewInject(R.id.toggle_focus_tip)
    ToggleButton toggle_focus_tip;

    //消息开关
    @ViewInject(R.id.toggle_message_tip)
    ToggleButton toggle_message_tip;

    //评论设置
    @ViewInject(R.id.rl_comment)
    RelativeLayout rl_comment;

    @ViewInject(R.id.head)
    RelativeLayout head;

    private DialogCommentSettingStyle mDailogCommentSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        ViewUtils.inject(this);

        //沉浸栏
        initHead();

        //初始化开关
        initToggle();

        //初始化对话框
        initDialog();
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

    //@我的
    @OnClick(R.id.rl_comment)
    public void setomment(View view){
        mDailogCommentSetting.show();
    }
}
