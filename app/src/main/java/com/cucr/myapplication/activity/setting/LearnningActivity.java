package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.app.MyApplication;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LearnningActivity extends BaseActivity {

    private Intent mIntent;

    @Override
    protected void initChild() {
        initTitle("新手教程");
        mIntent = new Intent();
        mIntent.setClass(MyApplication.getInstance(),LearnCatgoryActivity.class);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_learnning;
    }
    //认证教程
    @OnClick(R.id.rl_learn_rz)
    public void click3(View view) {
        mIntent.putExtra("type",1);
        startActivity(mIntent);
    }

    //预约教程
    @OnClick(R.id.rl_learn_yy)
    public void click1(View view) {
        mIntent.putExtra("type",2);
        startActivity(mIntent);
    }

    //打赏教程
    @OnClick(R.id.rl_learn_ds)
    public void click2(View view) {
        mIntent.putExtra("type",3);
        startActivity(mIntent);
    }


    //招募教程
    @OnClick(R.id.rl_learn_zm)
    public void click4(View view) {
        mIntent.putExtra("type",4);
        startActivity(mIntent);
    }

}
