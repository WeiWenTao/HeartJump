package com.cucr.myapplication.activity.huodong;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.HuoDongTaiAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HuoDongActivity extends BaseActivity {

    @ViewInject(R.id.lv_active)
    ListView lv_active;


    @Override
    protected void initChild() {
        initLV();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_huo_dong;
    }


    private void initLV() {
        lv_active.setAdapter(new HuoDongTaiAdapter(this));
        lv_active.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(HuoDongActivity.this,HuoDongCatgoryActivity.class));
            }
        });
    }


    //跳转发布活动界面
    @OnClick(R.id.iv_huodong_add)
    public void faBuHuoDong(View view){
        startActivity(new Intent(this,FaBuHuoDongActivity.class));
    }


}
