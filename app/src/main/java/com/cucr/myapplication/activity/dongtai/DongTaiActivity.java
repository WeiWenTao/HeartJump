package com.cucr.myapplication.activity.dongtai;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.RlvDongTaiAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DongTaiActivity extends BaseActivity {

    //动态列表
    @ViewInject(R.id.rlv_dongtai)
    RecyclerView rlv_dongtai;


    @Override
    protected void initChild() {
        initTitle("我发布的");
        initRLV();
    }

    //返回布局给父类
    @Override
    protected int getChildRes() {
        return R.layout.activity_dong_tai;
    }

    //初始化动态列表
    private void initRLV() {
        rlv_dongtai.setLayoutManager(new LinearLayoutManager(this));
        rlv_dongtai.setAdapter(new RlvDongTaiAdapter(this));
    }

}
