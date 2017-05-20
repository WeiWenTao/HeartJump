package com.cucr.myapplication.activity.JourneyAndTopic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.TopicCatgoryPagerAdapter;
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

import java.util.Arrays;
import java.util.List;

public class TopicCatgoryActivity extends Activity {

    @ViewInject(R.id.magic_indicator_topic_catgory)
    MagicIndicator magic_indicator_topic_catgory;
    
    @ViewInject(R.id.view_pager_topic_catgory)
    ViewPager view_pager_topic_catgory;


    private static final String[] CHANNELS = new String[]{"热门","新鲜"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_catgory);
        ViewUtils.inject(this);
        //初始化vp
        initVP();

        //初始化标签栏
        initMagicIndicator();
    }

    private void initVP() {
        view_pager_topic_catgory.setAdapter(new TopicCatgoryPagerAdapter(mDataList,this));
    }


    //初始化标签栏
    private void initMagicIndicator() {
        magic_indicator_topic_catgory.setBackgroundColor(Color.parseColor("#ffffff"));
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
                        view_pager_topic_catgory.setCurrentItem(index);
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
        magic_indicator_topic_catgory.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magic_indicator_topic_catgory, view_pager_topic_catgory);
    }

    //点击返回
    @OnClick(R.id.iv_topic_catgory_back)
    public void back(View view){
        finish();
    }
}
