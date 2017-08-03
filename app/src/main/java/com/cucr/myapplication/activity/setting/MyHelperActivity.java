package com.cucr.myapplication.activity.setting;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class MyHelperActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("我的助手");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_helper;
    }

}
