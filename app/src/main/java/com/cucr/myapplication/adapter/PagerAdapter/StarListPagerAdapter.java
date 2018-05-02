package com.cucr.myapplication.adapter.PagerAdapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cucr on 2017/11/28.
 */

public class StarListPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentslist;

    public StarListPagerAdapter(FragmentManager fm, List<Fragment> mFragmentslist) {
        super(fm);
        this.mFragmentslist = mFragmentslist;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentslist.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentslist == null ? 0 : mFragmentslist.size();
    }

}
