package com.cucr.myapplication.activity.picWall;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.PicWallPagerAdapter;
import com.cucr.myapplication.fragment.picWall.MyPicWallFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class MyPicWallActivity extends Activity {

    @ViewInject(R.id.tablayout)
    private TabLayout tablayout;

    @ViewInject(R.id.vp_pics)
    private ViewPager vp_pics;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pic_wall);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        ViewUtils.inject(this);
        init();
    }

    private void init() {
        mFragments = new ArrayList<>();
        mFragments.add(new MyPicWallFragment());
        mFragments.add(new MyPicWallFragment());
        vp_pics.setAdapter(new PicWallPagerAdapter(getFragmentManager(), mFragments));
        tablayout.addTab(tablayout.newTab().setText("我的图集"));
        tablayout.addTab(tablayout.newTab().setText("我喜欢的"));
        tablayout.setupWithViewPager(vp_pics);//将导航栏和viewpager进行关联
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}
