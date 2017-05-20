package com.cucr.myapplication.activity.JourneyAndTopic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.GvAdapter.TopicClassifyGVAdapter;
import com.cucr.myapplication.adapter.PagerAdapter.TopicClassifyAdapter;
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

public class TopicClassifyActivity extends Activity {

    //分类箭头
    @ViewInject(R.id.iv_pull_down)
    private ImageView iv_pull_down;

    //全部话题
    @ViewInject(R.id.tv_all_topic)
    private TextView tv_all_topic;

    //viewpager
    @ViewInject(R.id.vp_topic)
    private ViewPager vp_topic;

    //标签栏
    @ViewInject(R.id.magic_indicator_topic)
    private MagicIndicator mMagicIndicator;

    //灰色背景
    @ViewInject(R.id.fl_bg)
    private FrameLayout fl_bg;

    //分类详情
    @ViewInject(R.id.ll_calssify_catgory)
    private LinearLayout ll_calssify_catgory;

    //分类内容
    @ViewInject(R.id.gv_classify_catgory)
    private GridView gv_classify_catgory;

    private static final String[] CHANNELS = new String[]{"热门", "榜单", "明星", "影视", "综艺", "音乐", "活动", "体育", "媒体", "八卦", "我的"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    //标签栏是否打开
    private boolean isOpen;
    private TopicClassifyGVAdapter mTopicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_classify);
        ViewUtils.inject(this);

        //初始化标签栏
        initMagicIndicator();
        //初始化VP
        initVP();
        //初始化下拉分类栏
        initGV();


    }

    //初始化下拉分类栏
    private void initGV() {

        mTopicAdapter = new TopicClassifyGVAdapter(this, mDataList);
        gv_classify_catgory.setAdapter(mTopicAdapter);
        //默认状态下 第1个被选中
        mTopicAdapter.setCheckPosition(0);

        gv_classify_catgory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //当gv条目被点击的时候 设置vp当前条目
                vp_topic.setCurrentItem(position);

                //设置点击的条目背景（该方法自带刷新适配器功能）
                mTopicAdapter.setCheckPosition(position);

                //关闭分类标签栏
                closeClassify();

            }
        });
    }

    //初始化VP
    private void initVP() {

        vp_topic.setAdapter(new TopicClassifyAdapter(mDataList,this));

        vp_topic.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTopicAdapter.setCheckPosition(position);
            }
        });
    }



    //初始化标签栏
    private void initMagicIndicator() {
        mMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
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
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context,5.35f);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#929292"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f68d89"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp_topic.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2.5));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(2.5f);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#f68d89"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(mMagicIndicator, vp_topic);
    }


    //返回
    @OnClick(R.id.iv_topic_back)
    public void back(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (isOpen){
            closeClassify();
        }else{
            super.onBackPressed();
        }

    }

    //展开和关闭标签
    @OnClick(R.id.iv_pull_down)
    public void openOrClose(View view){
        if (!isOpen){
            openClassify();
        }else {
            closeClassify();
        }
    }

    //打开标签栏
    public void openClassify(){
        ll_calssify_catgory.setVisibility(View.VISIBLE);
        fl_bg.setVisibility(View.VISIBLE);
        ll_calssify_catgory.setAnimation(AnimationUtils.loadAnimation(this, R.anim.classify_menu_in));
        fl_bg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bg_gray_in));
        tv_all_topic.setVisibility(View.VISIBLE);
        mMagicIndicator.setVisibility(View.INVISIBLE);
        iv_pull_down.setImageResource(R.drawable.pull_up);
        isOpen = true;
    }

    //关闭标签栏
    public void closeClassify(){
        ll_calssify_catgory.setAnimation(AnimationUtils.loadAnimation(this, R.anim.classify_menu_out));
        fl_bg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bg_gray_out));
        ll_calssify_catgory.setVisibility(View.GONE);
        fl_bg.setVisibility(View.GONE);
        tv_all_topic.setVisibility(View.GONE);
        mMagicIndicator.setVisibility(View.VISIBLE);
        iv_pull_down.setImageResource(R.drawable.pull_down);
        isOpen = false;
    }

    //点击外部关闭对话框
    @OnClick(R.id.fl_bg)
    public void clickOutsideClose(View view){
        closeClassify();
    }
}
