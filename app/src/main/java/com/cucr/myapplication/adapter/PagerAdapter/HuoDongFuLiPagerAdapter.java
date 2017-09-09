package com.cucr.myapplication.adapter.PagerAdapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/5/19.
 *
 * 粉团页面的pagerAdapter
 */

public class HuoDongFuLiPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public HuoDongFuLiPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
        switch (position){
            case 0:
                return "福利";

            case 1:
                return "活动";
        }
        return null;
    }
}
