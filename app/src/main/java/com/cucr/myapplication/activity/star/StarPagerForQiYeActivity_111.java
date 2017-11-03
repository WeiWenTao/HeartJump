package com.cucr.myapplication.activity.star;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.PagerAdapter.StarPagerAdapter;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.fragment.star.Fragment_star_fentuan;
import com.cucr.myapplication.fragment.star.Fragment_star_shuju;
import com.cucr.myapplication.fragment.star.Fragment_star_xingcheng;
import com.cucr.myapplication.fragment.star.Fragment_star_xingwen;
import com.cucr.myapplication.model.others.FragmentInfos;
import com.cucr.myapplication.model.starList.StarListInfos;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/11/3.
 * 企业用户看的明星主页
 */

public class StarPagerForQiYeActivity_111 extends FragmentActivity {

    private List<FragmentInfos> mDataList;

    //ViewPager
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;

    //指示器
    @ViewInject(R.id.magic_star_forqiye)
    private MagicIndicator magicIndicator;

    //粉丝数量
    @ViewInject(R.id.tv_fans)
    private TextView tv_fans;

    //明星姓名
    @ViewInject(R.id.tv_starname)
    private TextView tv_starname;

    //标题姓名
    @ViewInject(R.id.tv_base_title)
    private TextView tv_base_title;

    //明星封面
    @ViewInject(R.id.backdrop)
    private ImageView backdrop;

    //关注
    @ViewInject(R.id.tv_focus_forqiye)
    private TextView tv_focus_forqiye;

    private StarListInfos.RowsBean mData;
    private FocusCore mCore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_pager_forqiye);
        ViewUtils.inject(this);
        mCore = new FocusCore();
        getDatas();
        initView();
    }

    protected void initView() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        initIndicator();
        initVp();


    }


    private void initVp() {
        mViewPager.setAdapter(new StarPagerAdapter(getSupportFragmentManager(), mDataList));
        mViewPager.setOffscreenPageLimit(2);
    }

    //初始化标签栏
    private void initIndicator() {

        mDataList = new ArrayList<>();
        //TODO
        mDataList.add(new FragmentInfos(new Fragment_star_xingwen(), "星闻"));
        //        if (明星用户) {
        mDataList.add(new FragmentInfos(new Fragment_star_shuju(), "数据"));
//        }
        mDataList.add(new FragmentInfos(new Fragment_star_fentuan(mData.getId()), "粉团"));
        mDataList.add(new FragmentInfos(new Fragment_star_xingcheng(), "行程"));

        //背景
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator7 = new CommonNavigator(MyApplication.getInstance());
        commonNavigator7.setScrollPivotX(0.5f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context, 4.0f);
                simplePagerTitleView.setText(mDataList.get(index).getTitle());
                simplePagerTitleView.setNormalColor(Color.parseColor("#bfbfbf"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ff4f49"));
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
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 18));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#ff4f49"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    //出演要求
    @OnClick(R.id.tv_request)
    public void request(View view) {

    }

    //关注
    @OnClick(R.id.tv_focus_forqiye)
    public void focus(View view) {
        //是否已经关注该明星
        if (mData.getIsfollow() == 1) {
            mCore.cancaleFocus(mData.getId());
            mData.setIsfollow(0);
        }else {
            mCore.toFocus(mData.getId());
            mData.setIsfollow(1);
        }
        tv_focus_forqiye.setText(mData.getIsfollow() == 1 ? "已关注" : "关注");
    }

    //跳转预约界面
    @OnClick(R.id.tv_yuyue)
    public void goYuYue(View view) {
        startActivity(new Intent(this, YuYueCatgoryActivity.class));
    }

    public void getDatas() {
        //获取数据
        mData = (StarListInfos.RowsBean) getIntent().getSerializableExtra("data");
        //并初始化
        tv_fans.setText("粉丝 " + mData.getFansCount());
        tv_starname.setText(mData.getRealName());
        tv_base_title.setText(mData.getRealName());
        tv_focus_forqiye.setText(mData.getIsfollow() == 1 ? "已关注" : "关注");
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mData.getUserPicCover(), backdrop, MyApplication.getImageLoaderOptions());
    }

    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        finish();
    }
}
