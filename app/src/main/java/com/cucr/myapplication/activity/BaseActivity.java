package com.cucr.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;

import org.zackratos.ultimatebar.UltimateBar;

public abstract class BaseActivity extends Activity {

    //标题
    protected TextView tv_title;
    protected Gson mGson;
    protected boolean isRefresh;
    protected int page;
    protected int rows;
    protected MyWaitDialog waitDialog;
    protected boolean needShowLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getChildRes());
        ViewUtils.inject(this);
        mGson = MyApplication.getGson();
        waitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        page = 1;
        rows = 15;
        isRefresh = true;
        needShowLoading = true;
        //初始化沉浸栏、标题内容、返回键
        initBar();

        //子类初始化操作
        initChild();

    }

    //子类初始化操作
    protected abstract void initChild();

    //获取子类布局
    protected abstract int getChildRes();


    //初始化标题
    public void initTitle(String title) {
        tv_title.setText(title);
    }


    //初始化沉浸栏、标题内容、返回键
    protected void initBar() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        //这里要用findViewbyid的形式找控件，用注解会为空
        tv_title = (TextView) findViewById(R.id.tv_base_title);

        //返回键
        findViewById(R.id.iv_base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackBefore();
                finish();
            }
        });
    }

    protected void onBackBefore() {

    }

    ;
}
