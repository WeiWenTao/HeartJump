package com.cucr.myapplication.bean.others;


import android.support.v4.app.Fragment;

/**
 * Created by cucr on 2017/8/31.
 */

public class FragmentInfos {
    private Fragment mFragment;
    private String title;

    public FragmentInfos() {
    }

    public FragmentInfos(Fragment fragment, String title) {
        mFragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
