package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.RenZhengPagrAdapter;
import com.cucr.myapplication.fragment.renzheng.AgentRZ;
import com.cucr.myapplication.fragment.renzheng.QiYeRZ;
import com.cucr.myapplication.fragment.renzheng.StarRZ;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class RenZhenngActivity extends Activity {

    //顶部的TabLayout
    @ViewInject(R.id.tl_tab)
    TabLayout tl_tab;

    //ViewPager
    @ViewInject(R.id.vp_ren_zheng)
    ViewPager vp_ren_zheng;


    List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zhenng);
        ViewUtils.inject(this);

        initView();
    }

    private void initView() {
        mFragmentList = new ArrayList<>();

        tl_tab.addTab(tl_tab.newTab().setText("明星"));
        tl_tab.addTab(tl_tab.newTab().setText("企业"));
        tl_tab.addTab(tl_tab.newTab().setText("经纪"));

        mFragmentList.add(new StarRZ());
        mFragmentList.add(new QiYeRZ());
        mFragmentList.add(new AgentRZ());

        vp_ren_zheng.setAdapter(new RenZhengPagrAdapter(getFragmentManager(),mFragmentList));

        //设置ViewPager 页面缓存个数 （总共3个页面）
        vp_ren_zheng.setOffscreenPageLimit(2);

        //TabLayou绑定ViewPager
        tl_tab.setupWithViewPager(vp_ren_zheng,true);
    }

    //返回
    @OnClick(R.id.iv_ren_zheng_back)
    public void back(View view){
        finish();
    }
}
