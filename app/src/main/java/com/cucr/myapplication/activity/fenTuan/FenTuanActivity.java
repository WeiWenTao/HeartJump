package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.FenTuanPagerAdapter;
import com.cucr.myapplication.fragment.fenTuan.HotFentuanFragment;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 911 on 2017/5/18.
 */

public class FenTuanActivity extends Activity {

    //粉团标签栏
    @ViewInject(R.id.magic_indicator_fentuan)
    MagicIndicator magic_indicator_fentuan;

    //viewpager
    @ViewInject(R.id.view_pager_fentuan)
    ViewPager view_pager_fentuan;

    //标题
    private static final String[] CHANNELS = new String[]{"热门粉团", "我的粉团"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    //粉团页面的所有fragment
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fentuan);
        ViewUtils.inject(this);

        //初始化VP
        initVP();

        //初始化标签栏
        initMagicIndicator();


    }
    //初始化VP
    private void initVP() {
        mFragments.add(new HotFentuanFragment(this));
        mFragments.add(new HotFentuanFragment(this));
        view_pager_fentuan.setAdapter(new FenTuanPagerAdapter(getFragmentManager(),mFragments));
    }

    //初始化标签栏
    private void initMagicIndicator() {
        magic_indicator_fentuan.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.5f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                // 3.0f 表示3个标签占一个屏幕大小
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context,2.0f);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#929292"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f68d89"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_pager_fentuan.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2.5));
                indicator.setLineWidth(UIUtil.dip2px(context, 60));
                indicator.setRoundRadius(2.5f);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#f68d89"));
                return indicator;
            }
        });
        magic_indicator_fentuan.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magic_indicator_fentuan, view_pager_fentuan);
    }

    //返回
    @OnClick(R.id.iv_fentuan_back)
    public void back(View view){
        finish();
    }
}
