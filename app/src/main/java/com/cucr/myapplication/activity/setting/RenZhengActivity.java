package com.cucr.myapplication.activity.setting;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.PagerAdapter.RenZhengPagrAdapter;
import com.cucr.myapplication.fragment.renzheng.QiYeRZ;
import com.cucr.myapplication.fragment.renzheng.StarRZ;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class RenZhengActivity extends BaseActivity {

    //顶部的TabLayout
    @ViewInject(R.id.tl_tab)
    TabLayout tl_tab;

    //ViewPager
    @ViewInject(R.id.vp_ren_zheng)
    ViewPager vp_ren_zheng;

    List<Fragment> mFragmentList;

    @Override
    protected void initChild() {
        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ren_zhenng;
    }

    private void initView() {
        mFragmentList = new ArrayList<>();

        tl_tab.addTab(tl_tab.newTab().setText("明星"));
        tl_tab.addTab(tl_tab.newTab().setText("企业"));

        mFragmentList.add(new StarRZ());
        mFragmentList.add(new QiYeRZ());

        vp_ren_zheng.setAdapter(new RenZhengPagrAdapter(getFragmentManager(),mFragmentList));

        //TabLayou绑定ViewPager
        tl_tab.setupWithViewPager(vp_ren_zheng,true);
    }

}
