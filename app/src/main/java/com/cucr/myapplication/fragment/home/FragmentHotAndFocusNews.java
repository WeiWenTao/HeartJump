package com.cucr.myapplication.fragment.home;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.adapter.PagerAdapter.HomeNewsPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.fragment.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/9/9.
 */

public class FragmentHotAndFocusNews extends BaseFragment {

    //ViewPager
    @ViewInject(R.id.vp_hot_focus)
    private ViewPager vp_hot_focus;

    //导航栏
    @ViewInject(R.id.tablayout)
    TabLayout tablayout;

    private List<Fragment> mFragments;

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        mGson = MyApplication.getGson();

        initTableLayout();
        initView();
    }


    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_home_hot_focus_news;
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeHotFragment());
        mFragments.add(new HomeFocusFragment());
        vp_hot_focus.setAdapter(new HomeNewsPagerAdapter(getFragmentManager(), mFragments));
    }

    //初始化标签
    private void initTableLayout() {
        tablayout.addTab(tablayout.newTab().setText("热门"));
        tablayout.addTab(tablayout.newTab().setText("关注"));
        tablayout.setupWithViewPager(vp_hot_focus);//将导航栏和viewpager进行关联
    }


    @OnClick(R.id.iv_search)
    public void toSearch(View view) {
        startActivity(new Intent(MyApplication.getInstance(), StarSearchActivity.class));
    }


    @OnClick(R.id.iv_header_msg)
    public void toMsg(View view) {
        startActivity(new Intent(MyApplication.getInstance(), MessageActivity.class));
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

}
