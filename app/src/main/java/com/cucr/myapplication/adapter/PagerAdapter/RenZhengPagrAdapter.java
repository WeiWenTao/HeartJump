package com.cucr.myapplication.adapter.PagerAdapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/6/16.
 */

public class RenZhengPagrAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public RenZhengPagrAdapter(FragmentManager supportFragmentManager, List<Fragment> fragmentList) {
        super(supportFragmentManager);
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
                return "明星";

            case 1:
                return "企业";

        }

        return null;
    }
}
