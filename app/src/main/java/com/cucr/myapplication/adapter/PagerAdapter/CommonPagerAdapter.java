package com.cucr.myapplication.adapter.PagerAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/5/19.
 *
 * 粉团页面的pagerAdapter
 */

public class CommonPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> tytles;

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tytles) {
        super(fm);
        this.fragments = fragments;
        this.tytles = tytles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tytles.get(position);
    }
}
