package com.cucr.myapplication.activity.myHomePager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.PagerAdapter.FocusPagerAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.List;

public class FocusActivity extends BaseActivity {

    //导航栏
    @ViewInject(R.id.tablayout_focus)
    TabLayout tablayout_focus;


    private ViewPager mViewPager;
    private static final String[] CHANNELS = new String[]{"全部关注", "推荐关注"};
    private List<String> mDataList = Arrays.asList(CHANNELS);


    @Override
    protected void initChild() {
        initView();
        initTableLayout();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_focus;
    }

    private void initTableLayout() {
        tablayout_focus.addTab(tablayout_focus.newTab().setText("推荐明星"));
        tablayout_focus.addTab(tablayout_focus.newTab().setText("全部明星"));
        tablayout_focus.setupWithViewPager(mViewPager);//将导航栏和viewpager进行关联

    }


    protected void initView() {
        //初始化vp和vpi
        mViewPager = (ViewPager) findViewById(R.id.view_pager_focus);
        mViewPager.setAdapter(new FocusPagerAdapter(mDataList));
    }


}
