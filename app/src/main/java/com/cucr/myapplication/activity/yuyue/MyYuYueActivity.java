package com.cucr.myapplication.activity.yuyue;

import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.MyYuYueAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyYuYueActivity extends BaseActivity {


    @ViewInject(R.id.lv_my_yuyue)
    ListView lv_my_yuyue;

    @Override
    protected void initChild() {
        initTitle("我的预约");
        initLV();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_yu_yue;
    }

    private void initLV() {
        lv_my_yuyue.setAdapter(new MyYuYueAdapter(this));
    }

}
