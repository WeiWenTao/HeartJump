package com.cucr.myapplication.adapter.PagerAdapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.cucr.myapplication.model.others.FragmentInfos;
import com.cucr.myapplication.utils.MyLogger;

import java.util.List;

/**
 * 明星主页的pagerAdapter
 */

public class StarPagerAdapter extends FragmentPagerAdapter {
    List<FragmentInfos> fragmentInfos;

    public StarPagerAdapter(FragmentManager fm, List<FragmentInfos> fragmentInfos) {
        super(fm);
        this.fragmentInfos = fragmentInfos;
    }


    @Override
    public int getCount() {
        MyLogger.jLog().i("size:" + fragmentInfos.size());
        return fragmentInfos == null ? 0 : fragmentInfos.size();
    }

    @Override
    public Fragment getItem(int position) {
        MyLogger.jLog().i("fragmentInfos:" + fragmentInfos);
        return fragmentInfos.get(position).getFragment();
    }

//    @Override
//    public int getItemPosition(Object object) {
//        TextView textView = (TextView) object;
//        String text = textView.getText().toString();
//        int index = mDataList.indexOf(text);
//        if (index >= 0) {
//            return index;
//        }
//        return POSITION_NONE;
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentInfos.get(position).getTitle();
    }
}
