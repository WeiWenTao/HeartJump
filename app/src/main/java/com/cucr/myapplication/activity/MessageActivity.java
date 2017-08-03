package com.cucr.myapplication.activity;

import com.cucr.myapplication.R;


public class MessageActivity extends BaseActivity {


    @Override
    protected void initChild() {
        initTitle("消息");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_message;
    }

}
