package com.cucr.myapplication.activity.star;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.adapter.PagerAdapter.StarListPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.HomeStarsCatgorysAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventHomeStarsCatgory;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.bean.starList.StarListKey;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.star.AllStarListFragemnt;
import com.cucr.myapplication.fragment.star.RecommendStarListFragemnt;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class StarListForAddActivity extends Activity implements OnCommonListener, HomeStarsCatgorysAdapter.OnClickItem {

    //明星pager
    @ViewInject(R.id.vp_star_pager)
    private ViewPager vp_star_pager;

    //进入心跳
    @ViewInject(R.id.tv_enter)
    private TextView tv_enter;

    //返回键
    @ViewInject(R.id.iv_base_back)
    private ImageView iv_base_back;


    //推荐
    @ViewInject(R.id.tv_title1)
    private TextView tv_title1;

    //推荐
    @ViewInject(R.id.tv_title2)
    private TextView tv_title2;

    //全部
    @ViewInject(R.id.ll_title2)
    private LinearLayout ll_title2;

    //小三角
    @ViewInject(R.id.iv_sjx)
    private ImageView iv_sjx;

    //背景
    @ViewInject(R.id.bg)
    private View bg;

    //下拉布局
    @ViewInject(R.id.ll_drawer)
    private LinearLayout ll_drawer;

    //类别
    @ViewInject(R.id.drawer_rcv)
    private RecyclerView drawer_rcv;


    private Gson mGson;
    private List<Fragment> mFragmentslist;
    //是否需要显示
    private boolean isShow = true;
    private QueryFocus mFocusCore;
    private HomeStarsCatgorysAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_list_for_add);
        ViewUtils.inject(this);
        mFragmentslist = new ArrayList<>();
        mFocusCore = new QueryFocus();
        initView();
        initRlv();
    }

    private void initRlv() {
        drawer_rcv.setLayoutManager(new GridLayoutManager(MyApplication.getInstance(), 4));
        mAdapter = new HomeStarsCatgorysAdapter();
        drawer_rcv.setAdapter(mAdapter);
        mAdapter.setOnClickItem(this);
        initData();
    }

    private void initData() {
        QueryStarListCore core = new QueryStarListCore();
        core.queryZiDuan("userType", this);
    }

    private void initView() {
        tv_enter.setVisibility((getIntent().getBooleanExtra("formLoad", false)) ? View.VISIBLE : View.GONE);
        iv_base_back.setVisibility((getIntent().getBooleanExtra("formLoad", false)) ? View.GONE : View.VISIBLE);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
        mGson = new Gson();
        mFragmentslist.add(new RecommendStarListFragemnt());
        mFragmentslist.add(new AllStarListFragemnt());

        vp_star_pager.setAdapter(new StarListPagerAdapter(getFragmentManager(), mFragmentslist));
    }

    //返回
    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        finish();
    }

    //跳转主界面
    @OnClick(R.id.tv_enter)
    public void enterApp(View view) {
        mFocusCore.queryMyFocusStars(-1, 1, 10, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                FocusInfo Info = mGson.fromJson(response.get(), FocusInfo.class);
                if (Info.isSuccess()) {
                    if (Info.getRows().size() > 0) {     //有关注明星
                        startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
                        //登录之后保存登录数据  下次登录判断是否第一次登录
                        SpUtil.setParam(SpConstant.HAS_LOAD, true);
                        finish();
                    } else {
                        ToastUtils.showToast("至少要关注一位明星哦");
                    }
                } else {
                    ToastUtils.showToast(Info.getErrorMsg());
                }
            }

            @Override
            public void onRequestStar(int what) {

            }

            @Override
            public void onRequestError(int what, Response<String> response) {

            }

            @Override
            public void onRequestFinish(int what) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentslist.clear();
    }

    //跳转搜索界面
    @OnClick(R.id.iv_search)
    public void goSearch(View view) {
        startActivity(new Intent(MyApplication.getInstance(), StarSearchActivity.class));
    }

    //点击推荐
    @OnClick(R.id.tv_title1)
    public void clickFuLi(View view) {
        if (isShow == false) {
            initDrawer(isShow);
        }
        isClickHot(true);
    }

    //点击全部
    @OnClick(R.id.ll_title2)
    public void clickHot(View view) {
        if (iv_sjx.getVisibility() == View.VISIBLE) {
            initDrawer(isShow);
        }
        isClickHot(false);
    }

    public void initDrawer(boolean needShow) {
        if (needShow) {
            ll_title2.setEnabled(false);
            ll_drawer.setVisibility(View.VISIBLE);
            ll_drawer.setAnimation(AnimationUtils.loadAnimation(MyApplication.getInstance(), R.anim.classify_menu_in));
            CommonUtils.animationAlpha(bg, true);
            CommonUtils.animationRotate(iv_sjx, true, 300);
            bg.setEnabled(false);
            bg.setVisibility(View.VISIBLE);
            isShow = false;
        } else {
            bg.setEnabled(false);
            ll_title2.setEnabled(false);
            ll_drawer.clearAnimation();
            ll_drawer.setAnimation(AnimationUtils.loadAnimation(MyApplication.getInstance(), R.anim.classify_menu_out));
            isShow = true;
            ll_drawer.setVisibility(View.GONE);
            CommonUtils.animationAlpha(bg, false);
            CommonUtils.animationRotate(iv_sjx, false, 300);
        }

        CommonUtils.setAnimationListener(new CommonUtils.AnimationListener() {
            @Override
            public void onInAnimationFinish() {
                ll_title2.setEnabled(true);
                bg.setVisibility(View.GONE);
            }

            @Override
            public void onOutAnimationFinish() {
                ll_title2.setEnabled(true);
                bg.setEnabled(true);
            }
        });
    }

    public void isClickHot(boolean is) {
        if (is) {
            vp_star_pager.setCurrentItem(0);
            tv_title1.setTextColor(getResources().getColor(R.color.xtred));
            tv_title2.setTextColor(getResources().getColor(R.color.zongse));
            iv_sjx.setVisibility(View.INVISIBLE);
        } else {
            vp_star_pager.setCurrentItem(1);
            tv_title1.setTextColor(getResources().getColor(R.color.zongse));
            tv_title2.setTextColor(getResources().getColor(R.color.xtred));
            iv_sjx.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.bg)
    public void clickBg(View view) {
        initDrawer(false);
    }

    @Override
    public void onRequestSuccess(Response<String> response) {
        StarListKey starKey = mGson.fromJson(response.get(), StarListKey.class);
        if (starKey.isSuccess()) {
            mAdapter.setData(starKey.getRows());
        } else {
            ToastUtils.showToast(starKey.getErrorMsg());
        }
    }

    @Override
    public void clickItem(String tpye, String name) {
        tv_title2.setText(name);
        initDrawer(false);
        EventBus.getDefault().post(new EventHomeStarsCatgory(tpye));
    }
}
