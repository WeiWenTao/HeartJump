package com.cucr.myapplication.fragment.live;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.LivePagerAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;

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
 * Created by 911 on 2017/4/10.
 */

public class TvFragment extends BaseFragment {
    private static final String[] CHANNELS = new String[]{"我关注的", "热门直播", "人气主播"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private LivePagerAdapter mExamplePagerAdapter = new LivePagerAdapter(mDataList);
    private ViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    List<Fragment> mList = new ArrayList<>();


    @Override
    protected void initView(View childView) {



        //初始化vp和vpi
        mViewPager = (ViewPager) childView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getFragmentManager()));
        mMagicIndicator = (MagicIndicator) childView.findViewById(R.id.magic_indicator7);



        mList.add(new HotAndFocusFragment(mMagicIndicator));
        mList.add(new HotAndFocusFragment(mMagicIndicator));
        mList.add(new HotPopolarFragment(mMagicIndicator));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mMagicIndicator.clearAnimation();
                ObjectAnimator anim = ObjectAnimator.ofFloat(mMagicIndicator, "translationY",mMagicIndicator.getTranslationY(), 0);
                anim.setDuration(800);
                anim.start();
                mMagicIndicator.clearAnimation();

//                mMagicIndicator.setTranslationY(0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initMagicIndicator(childView);
    }




    //初始化标签栏
    private void initMagicIndicator(View childView) {
        mMagicIndicator = (MagicIndicator) childView.findViewById(R.id.magic_indicator7);
        mMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator7 = new CommonNavigator(mContext);
        commonNavigator7.setScrollPivotX(0.5f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                // 3.0f 表示3个标签占一个屏幕大小
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context,3.0f);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#929292"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f68d89"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
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
        mMagicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    //返回子类布局
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_tv;
    }

    @Override
    protected String initHeadText() {
        return "搜索你喜欢的主播";
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        // region Constructors
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        // endregion

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mList.get(0);
                case 1:
                    return mList.get(1);
                case 2:
                    return mList.get(2);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "我关注的";
                case 1:
                    return "热门直播";
                case 2:
                    return "人气主播";
            }
            return null;
        }
    }

}
