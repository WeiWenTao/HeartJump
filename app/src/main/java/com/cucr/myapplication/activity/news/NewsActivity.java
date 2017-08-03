package com.cucr.myapplication.activity.news;

import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsActivity extends BaseActivity {


    @ViewInject(R.id.tv_new_title)
    TextView tv_new_title;


    @Override
    protected void initChild() {
        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_news;
    }


    private void initView() {

        //字体加粗
        tv_new_title.getPaint().setFakeBoldText(true);

    }

}
