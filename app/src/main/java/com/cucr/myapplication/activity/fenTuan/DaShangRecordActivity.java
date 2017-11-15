package com.cucr.myapplication.activity.fenTuan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.RewardPagerAdapter;
import com.cucr.myapplication.fragment.DaBang.MyRewardFragment;
import com.cucr.myapplication.fragment.DaBang.RewardMeFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class DaShangRecordActivity extends FragmentActivity {

    //导航栏
    @ViewInject(R.id.tablayout)
    TabLayout tablayout;

    //ViewPager
    @ViewInject(R.id.vp_ds_record)
    private ViewPager vp_ds_record;

    private List<Fragment> mFragments;
    private List<String> tytles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_shang_record);
        ViewUtils.inject(this);
        initBar();
        initViews();
    }

    private void initBar() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
    }

    private void initViews() {
        mFragments = new ArrayList<>();
        tytles = new ArrayList<>();
        tytles.add("打赏我的");
        tytles.add("我打赏的");
        mFragments.add(new RewardMeFragment());
        mFragments.add(new MyRewardFragment());
        tablayout.addTab(tablayout.newTab().setText("打赏我的"));
        tablayout.addTab(tablayout.newTab().setText("我打赏的"));
        tablayout.setupWithViewPager(vp_ds_record);//将导航栏和viewpager进行关联
        vp_ds_record.setAdapter(new RewardPagerAdapter(getSupportFragmentManager(), mFragments, tytles));

    }

    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        finish();
    }


}
