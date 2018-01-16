package com.cucr.myapplication.activity.hyt;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class HYTActivity extends Activity {

    @ViewInject(R.id.vp_hyt)
    private ViewPager vp_hyt;

    //顶部的TabLayout
    @ViewInject(R.id.tl_tab)
    private TabLayout tl_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyt);
        ViewUtils.inject(this);

        init();

    }

    private void init() {

        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new )
        vp_hyt.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
        tl_tab.setupWithViewPager(vp_hyt);
    }
}
