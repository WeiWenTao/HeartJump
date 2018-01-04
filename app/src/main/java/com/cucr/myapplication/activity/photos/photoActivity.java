package com.cucr.myapplication.activity.photos;

import android.support.v4.view.ViewPager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.PagerAdapter.HomePhotoPagerAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends BaseActivity {

    //ViewPager
    @ViewInject(R.id.vp_photo)
    ViewPager vp_photo;

    private List<String> photos;


    @Override
    protected void initChild() {
        photos = new ArrayList<>();
        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_photo;
    }

    private void initView() {
        photos.add("1");
        photos.add("2");
        photos.add("3");
        vp_photo.setAdapter(new HomePhotoPagerAdapter(photos, this));
    }

}
