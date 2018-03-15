package com.cucr.myapplication.fragment.fuLiHuoDong;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.huodong.FaBuHuoDongActivity;
import com.cucr.myapplication.adapter.PagerAdapter.CommonPagerAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/8/31.
 */

public class FragmentHuoDongAndFuLi extends BaseFragment {

    //ViewPager
    @ViewInject(R.id.vp_fuli_huodong)
    ViewPager vp_fuli_huodong;

    //福利
    @ViewInject(R.id.tv_title1)
    TextView tv_title1;

    //活动
    @ViewInject(R.id.tv_title2)
    TextView tv_title2;

    //小三角
    @ViewInject(R.id.iv_sjx)
    ImageView iv_sjx;

    //popupWindow背景
    @ViewInject(R.id.fl_pop_bg)
    FrameLayout fl_pop_bg;

    private List<Fragment> mFragments;
    private List<String> tytles;
    private PopupWindow genderPopWindow;


    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        initVP();
    }

    private void initVP() {
        mFragments = new ArrayList<>();
        tytles = new ArrayList<>();

        mFragments.add(new FragmentFuLi());
        mFragments.add(new FragmentHuoDong());
        tytles.add("福利");
        tytles.add("活动");
//        mFragments.add(new FragmentStarRecommend());
//      快速导航栏
//      mFragments.add(new FragmentStarClassify());
//        mFragments.add(new FragmentStarRecommend());

        vp_fuli_huodong.setAdapter(new CommonPagerAdapter(getFragmentManager(), mFragments, tytles));
        vp_fuli_huodong.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                isClickFuLi(position == 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //返回子类布局
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_fuli_huodong;
    }

    //跳转消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        startActivity(new Intent(mContext, MessageActivity.class));
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    //点击福利
    @OnClick(R.id.tv_title1)
    public void clickFuLi(View view) {
        isClickFuLi(true);
    }

    //点击活动
    @OnClick(R.id.tv_title2)
    public void clickHuoDong(View view) {
        if (iv_sjx.getVisibility() == View.VISIBLE) {
            showPopwindow(view);
        }
        isClickFuLi(false);
    }

    //点击sjx
    @OnClick(R.id.iv_sjx)
    public void clickSjx(View view) {
        if (iv_sjx.getVisibility() == View.VISIBLE) {
            showPopwindow(view);
        }
        isClickFuLi(false);
    }

    private void showPopwindow(View parent) {
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.alpha = 0.3f;
        getActivity().getWindow().setAttributes(attributes);


        if (genderPopWindow == null) {
            View genderView = inflater.inflate(R.layout.pop_active_publish, null);
            genderPopWindow = new PopupWindow(genderView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initGenderPop(genderView);
        }
        genderPopWindow.setAnimationStyle(R.style.AnimationFade);
        genderPopWindow.setFocusable(true);
        genderPopWindow.setOutsideTouchable(true);

        genderPopWindow.setBackgroundDrawable(new BitmapDrawable());

        genderPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        genderPopWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        genderPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                attributes.alpha = 1.0f;
                attributes.windowAnimations = 1;
                getActivity().getWindow().setAttributes(attributes);

            }
        });
    }

    private void initGenderPop(View popView) {

        popView.findViewById(R.id.rl_popWindow_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderPopWindow.dismiss();
            }
        });

        popView.findViewById(R.id.ll_up_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, FaBuHuoDongActivity.class));
                genderPopWindow.dismiss();
            }
        });
        popView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderPopWindow.dismiss();
            }
        });
    }

    public void isClickFuLi(boolean is) {
        if (is) {
            vp_fuli_huodong.setCurrentItem(0);
            tv_title1.setTextColor(getResources().getColor(R.color.xtred));
            tv_title2.setTextColor(getResources().getColor(R.color.zongse));
            iv_sjx.setVisibility(View.GONE);
        } else {
            vp_fuli_huodong.setCurrentItem(1);
            tv_title1.setTextColor(getResources().getColor(R.color.zongse));
            tv_title2.setTextColor(getResources().getColor(R.color.xtred));
            iv_sjx.setVisibility(View.VISIBLE);
        }
    }
}
