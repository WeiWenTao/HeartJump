package com.cucr.myapplication.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.adapter.PagerAdapter.StarPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Home.HomeBannerInfo;
import com.cucr.myapplication.bean.others.FragmentInfos;
import com.cucr.myapplication.core.home.QueryBannerCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.cucr.myapplication.temp.NetworkImageHolderView;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

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
import java.util.List;

/**
 * Created by cucr on 2018/5/9.
 */

public class FragmentHomePage extends BaseFragment implements OnItemClickListener {

    @ViewInject(R.id.banner)
    private ConvenientBanner banner;

    //指示器
    @ViewInject(R.id.magic_indicator_personal_page)
    private MagicIndicator magicIndicator;

    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;

    private List<String> pics;
    private QueryBannerCore mCore;
    private HomeBannerInfo mHomeBannerInfo;
    private List<FragmentInfos> mDataList;


    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        mCore = new QueryBannerCore();
        getBannerInfo();
        initIndicator();
        initVP();
    }

    private void initVP() {
        viewpager.setAdapter(new StarPagerAdapter(getFragmentManager(), mDataList));
        viewpager.setCurrentItem(1);
    }

    private void initIndicator() {
        mDataList = new ArrayList<>();
        mDataList.add(new FragmentInfos(new HomeCommonFragment(9), "关注"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(0), "热门"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(1), "影视"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(2), "音乐"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(3), "体育"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(4), "时尚"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(5), "网红"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(6), "财经"));
        mDataList.add(new FragmentInfos(new HomeCommonFragment(7), "其他"));
        CommonNavigator commonNavigator7 = new CommonNavigator(mContext);
        commonNavigator7.setScrollPivotX(0.5f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context, 6.5f);
                simplePagerTitleView.setText(mDataList.get(index).getTitle());
                simplePagerTitleView.setNormalColor(Color.parseColor("#bfbfbf"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ff4f49"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewpager.setCurrentItem(index);
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
        ViewPagerHelper.bind(magicIndicator, viewpager);
    }

    private void initBanner() {

        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, pics)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.cricle_nor, R.drawable.cricle_sel})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this)
                .startTurning(4000)
                .setManualPageable(true);//设置不能手动影响
    }

    private void getBannerInfo() {
        pics = new ArrayList<>();
        mCore.queryBanner(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mHomeBannerInfo = mGson.fromJson(response.get(), HomeBannerInfo.class);
                if (mHomeBannerInfo.isSuccess()) {
                    for (HomeBannerInfo.ObjBean objBean : mHomeBannerInfo.getObj()) {
                        pics.add(objBean.getFileUrl());
                    }
                    initBanner();
                } else {
                    ToastUtils.showToast(mHomeBannerInfo.getMsg());
                }
            }
        });
    }

    //不需要头部
    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_home_page;
    }

    //点击轮播图
    @Override
    public void onItemClick(int position) {
        HomeBannerInfo.ObjBean objBean = mHomeBannerInfo.getObj().get(position);
        Intent intent = new Intent(MyApplication.getInstance(), TestWebViewActivity.class);
        intent.putExtra("url", objBean.getLocationUrl());
        intent.putExtra("bannershare", objBean.getId());
        startActivity(intent);
    }

    //跳转到消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        mContext.startActivity(new Intent(mContext, MessageActivity.class));
    }
}
