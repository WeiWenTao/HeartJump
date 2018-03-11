package com.cucr.myapplication.activity.hyt;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.fragment.hyt.Fragment_hyt;
import com.cucr.myapplication.fragment.hyt.Fragment_yyhd;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class HYTActivity extends Activity {

    @ViewInject(R.id.vp_hyt)
    private ViewPager vp_hyt;

    //顶部的TabLayout
    @ViewInject(R.id.tl_tab)
    private TabLayout tl_tab;
    private List<Fragment> mFragments;
    private int starId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyt);
        ViewUtils.inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        init();
    }

    private void init() {
        starId = getIntent().getIntExtra("starId", -1);
        mFragments = new ArrayList<>();
        mFragments.add(new Fragment_hyt(starId));
        mFragments.add(new Fragment_yyhd(starId));
        vp_hyt.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "后援团";

                    case 1:
                        return "应援活动";
                }
                return "后援团";
            }
        });
        tl_tab.addTab(tl_tab.newTab().setText("后援团"));
        tl_tab.addTab(tl_tab.newTab().setText("应援活动"));
        tl_tab.setupWithViewPager(vp_hyt);
    }

    @OnClick(R.id.iv_base_back)
    public void clickBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}
