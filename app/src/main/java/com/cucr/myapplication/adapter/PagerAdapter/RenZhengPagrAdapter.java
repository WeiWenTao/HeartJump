package com.cucr.myapplication.adapter.PagerAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/6/16.
 */

public class RenZhengPagrAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> mTitles;

    public RenZhengPagrAdapter(FragmentManager supportFragmentManager, List<Fragment> fragmentList, List<String> titles) {
        super(supportFragmentManager);
        this.fragmentList = fragmentList;
        this.mTitles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 :fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles.get(position);
    }
}
