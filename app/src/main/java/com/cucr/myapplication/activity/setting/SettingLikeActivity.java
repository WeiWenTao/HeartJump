package com.cucr.myapplication.activity.setting;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class SettingLikeActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("点赞设置");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_like;
    }
    
}
