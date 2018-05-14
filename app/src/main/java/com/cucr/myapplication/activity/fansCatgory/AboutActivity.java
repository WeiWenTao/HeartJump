package com.cucr.myapplication.activity.fansCatgory;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("关于Ta");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_about;
    }
}
