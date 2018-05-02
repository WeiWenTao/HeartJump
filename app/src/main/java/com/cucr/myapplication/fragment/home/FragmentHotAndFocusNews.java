package com.cucr.myapplication.fragment.home;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.star.StarSearchActivity;
import com.cucr.myapplication.adapter.PagerAdapter.HomeNewsPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.HomeNewsCatgorysAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Home.HomeNewsCatgory;
import com.cucr.myapplication.bean.eventBus.EventHomeNewsCatgory;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/9/9.
 */

public class FragmentHotAndFocusNews extends BaseFragment implements HomeNewsCatgorysAdapter.OnClickItem {

    //ViewPager
    @ViewInject(R.id.vp_hot_focus)
    private ViewPager vp_hot_focus;

    //福利
    @ViewInject(R.id.tv_title1)
    private TextView tv_title1;

    //福利
    @ViewInject(R.id.ll_title1)
    private LinearLayout ll_title1;

    //活动
    @ViewInject(R.id.tv_title2)
    private TextView tv_title2;

    //小三角
    @ViewInject(R.id.iv_sjx)
    private ImageView iv_sjx;

    //类别
    @ViewInject(R.id.drawer_rcv)
    private RecyclerView drawer_rcv;

    //下拉布局
    @ViewInject(R.id.ll_drawer)
    private LinearLayout ll_drawer;

    //背景
    @ViewInject(R.id.bg)
    private View bg;

    private List<Fragment> mFragments;
    //是否需要显示
    private boolean isShow = true;
    private List<HomeNewsCatgory> list;

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        mGson = MyApplication.getGson();

        initView();
        initRlv();
    }

    private void initRlv() {
        drawer_rcv.setLayoutManager(new GridLayoutManager(mContext, 4));
        HomeNewsCatgorysAdapter adapter = new HomeNewsCatgorysAdapter();
        drawer_rcv.setAdapter(adapter);
        initData();
        adapter.setData(list);
        adapter.setOnClickItem(this);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new HomeNewsCatgory("热门", 0));
        list.add(new HomeNewsCatgory("影视", 1));
        list.add(new HomeNewsCatgory("音乐", 2));
        list.add(new HomeNewsCatgory("体育", 3));
        list.add(new HomeNewsCatgory("时尚", 4));
        list.add(new HomeNewsCatgory("网红", 5));
        list.add(new HomeNewsCatgory("财经", 6));
        list.add(new HomeNewsCatgory("其他", 7));
    }


    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_home_hot_focus_news;
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeHotFragment());
        mFragments.add(new HomeFocusFragment());
        vp_hot_focus.setAdapter(new HomeNewsPagerAdapter(getFragmentManager(), mFragments));

        vp_hot_focus.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                isClickHot(position == 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.iv_search)
    public void toSearch(View view) {
        startActivity(new Intent(MyApplication.getInstance(), StarSearchActivity.class));
    }


    @OnClick(R.id.iv_header_msg)
    public void toMsg(View view) {
        startActivity(new Intent(MyApplication.getInstance(), MessageActivity.class));
    }

    @Override
    protected boolean needHeader() {
        return false;
    }


    public void isClickHot(boolean is) {
        if (is) {
            vp_hot_focus.setCurrentItem(0);
            tv_title1.setTextColor(getResources().getColor(R.color.xtred));
            tv_title2.setTextColor(getResources().getColor(R.color.zongse));
            iv_sjx.setVisibility(View.VISIBLE);
        } else {
            vp_hot_focus.setCurrentItem(1);
            tv_title1.setTextColor(getResources().getColor(R.color.zongse));
            tv_title2.setTextColor(getResources().getColor(R.color.xtred));
            iv_sjx.setVisibility(View.GONE);
        }
    }

    //点击热门
    @OnClick(R.id.ll_title1)
    public void clickFuLi(View view) {
        if (iv_sjx.getVisibility() == View.VISIBLE) {
            initDrawer(isShow);
        }
        isClickHot(true);
    }

    //点击关注
    @OnClick(R.id.tv_title2)
    public void clickHot(View view) {
        isClickHot(false);
        if (isShow == false) {
            initDrawer(isShow);
        }
    }

    public void initDrawer(boolean needShow) {
        if (needShow) {
            ll_title1.setEnabled(false);
            ll_drawer.setVisibility(View.VISIBLE);
            ll_drawer.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.classify_menu_in));
            CommonUtils.animationAlpha(bg, true);
            CommonUtils.animationRotate(iv_sjx, true, 300);
            bg.setEnabled(false);
            bg.setVisibility(View.VISIBLE);
            isShow = false;
        } else {
            bg.setEnabled(false);
            ll_title1.setEnabled(false);
            ll_drawer.clearAnimation();
            ll_drawer.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.classify_menu_out));
            isShow = true;
            ll_drawer.setVisibility(View.GONE);
            CommonUtils.animationAlpha(bg, false);
            CommonUtils.animationRotate(iv_sjx, false, 300);
        }

        CommonUtils.setAnimationListener(new CommonUtils.AnimationListener() {
            @Override
            public void onInAnimationFinish() {
                ll_title1.setEnabled(true);
                bg.setVisibility(View.GONE);
            }

            @Override
            public void onOutAnimationFinish() {
                ll_title1.setEnabled(true);
                bg.setEnabled(true);
            }
        });
    }


    @OnClick(R.id.bg)
    public void clickBg(View view) {
        initDrawer(false);
    }

    @Override
    public void clickItem(int tpye, String name) {
        tv_title1.setText(name);
        initDrawer(false);
        EventBus.getDefault().post(new EventHomeNewsCatgory(tpye));
    }
}
