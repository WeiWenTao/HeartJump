package com.cucr.myapplication.adapter.PagerAdapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/6/16.
 */

public class PersonalMainPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public PersonalMainPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
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

        switch (position){

            case 0:
                return "动态";

            case 1:
                return "明星";

        }

        return null;
    }
}
