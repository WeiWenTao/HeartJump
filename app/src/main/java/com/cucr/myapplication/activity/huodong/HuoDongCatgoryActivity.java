package com.cucr.myapplication.activity.huodong;

import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HuoDongCatgoryActivity extends BaseActivity {

    @ViewInject(R.id.lv_huodong_catgory)
    ListView lv_huodong_catgory;


    @Override
    protected void initChild() {
        initTitle("活动详情");
        initLV();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_huo_dong_catgory;
    }

    //ListView
    private void initLV() {

        View lvHead = View.inflate(this, R.layout.head_huo_dong_catgory, null);

        lv_huodong_catgory.addHeaderView(lvHead,null,true);
        lv_huodong_catgory.setHeaderDividersEnabled(false);

        lv_huodong_catgory.setAdapter(new FtCatgoryAadapter(this));
    }

}
