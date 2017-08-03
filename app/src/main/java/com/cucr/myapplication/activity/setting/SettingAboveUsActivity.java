package com.cucr.myapplication.activity.setting;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class SettingAboveUsActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("关于我们");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_above_us;
    }

}
