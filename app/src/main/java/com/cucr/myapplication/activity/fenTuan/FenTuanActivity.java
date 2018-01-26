package com.cucr.myapplication.activity.fenTuan;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.PagerAdapter.FenTuanPagerAdapter;
import com.cucr.myapplication.fragment.fenTuan.HotFenTuanFragment;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/5/18.
 */

public class FenTuanActivity extends BaseActivity {

    //viewpager
    @ViewInject(R.id.vp_fentuan)
    ViewPager vp_fentuan;

    //导航栏
    @ViewInject(R.id.tablayout_fentuan)
    TabLayout tablayout;

    //粉团页面的所有fragment
    private List<Fragment> mFragments;


    @Override
    protected void initChild() {
        initVP();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_fentuan;
    }

    //初始化VP
    private void initVP() {
        mFragments = new ArrayList<>();
        mFragments.add(new HotFenTuanFragment());
        mFragments.add(new HotFenTuanFragment());

        tablayout.addTab(tablayout.newTab().setText("火热粉团"));
        tablayout.addTab(tablayout.newTab().setText("全部粉团"));
        tablayout.setupWithViewPager(vp_fentuan);//将导航栏和viewpager进行关联

        vp_fentuan.setAdapter(new FenTuanPagerAdapter(getFragmentManager(), mFragments));
    }

    //创建ft
    @OnClick(R.id.iv_fentuan_add)
    public void newFt(View view) {
        startActivity(new Intent(this, CreatFtActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }
}
