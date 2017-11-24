package com.cucr.myapplication.fragment.yuyue;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.HomeSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.adapter.PagerAdapter.YuYuePagerAdapter;
import com.cucr.myapplication.adapter.SpinnerAdapter.MySp1Adapter;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.fragment.star.FragmentStarRecommend;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.starList.StarListInfos;
import com.cucr.myapplication.model.starList.StarListKey;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/4/10.
 */

public class ApointmentFragmentA extends BaseFragment implements Spinner.OnItemSelectedListener, OnCommonListener {

    //ViewPager
    @ViewInject(R.id.vp_recommed_star)
    ViewPager vp_recommed_star;

    //sp1
    @ViewInject(R.id.sp_1)
    Spinner sp1;

    //sp2
    @ViewInject(R.id.sp_2)
    Spinner sp2;

    //sp3
    @ViewInject(R.id.sp_3)
    Spinner sp3;

    //头部
    @ViewInject(R.id.head)
    RelativeLayout head;

    private List<Fragment> mFragments;
    private QueryStarListCore mCore;
    private List<StarListInfos.RowsBean> mRows;
    private List<StarListKey.RowsBean> userTypes;     //明星类型
    private List<StarListKey.RowsBean> userCoasts;    //明星价格
    private List<StarListKey.RowsBean> types;         //推荐 关注
    private int page;
    private int rows;
    private MySp1Adapter mSpAdapter1;
    private MySp1Adapter mSpAdapter2;
    private MySp1Adapter mSpAdapter3;
    private int type;
    private String userType;
    private String userCost;
    private int refresh;
    private FragmentStarRecommend mFragment;


    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        mCore = new QueryStarListCore();

        rows = 10;
        page = 1;

        initVP();

        initSP();

        queryKey();

        initHead();

    }

    //查询列表字段
    private void queryKey() {
        //查询类型
        mCore.queryZiDuan("type", this);

        //明星类型
        mCore.queryZiDuan("userType", this);

        //价格区间
        mCore.queryZiDuan("userCost", this);
    }

    private void initSP() {
        userCoasts = new ArrayList<>();
        userTypes = new ArrayList<>();
        types = new ArrayList<>();

        mSpAdapter1 = new MySp1Adapter();
        mSpAdapter2 = new MySp1Adapter();
        mSpAdapter3 = new MySp1Adapter();
        sp1.setAdapter(mSpAdapter1);
        sp2.setAdapter(mSpAdapter2);
        sp3.setAdapter(mSpAdapter3);

        sp1.setOnItemSelectedListener(this);
        sp2.setOnItemSelectedListener(this);
        sp3.setOnItemSelectedListener(this);
    }

    private void queryStar(int type, String userCost, String userType) {
        //企业用户查询的是整页明星  所以不需要starId
        mCore.queryStar(type, page, rows, -1, userCost, userType, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starListInfos.isSuccess()) {
                    mRows = starListInfos.getRows();
                    mFragment.setData(mRows);
                    MyLogger.jLog().i("starList:" + mRows);
                } else {
                    ToastUtils.showToast(mContext, starListInfos.getErrorMsg());
                }
            }
        });
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(mContext, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private void initVP() {
        mFragments = new ArrayList<>();
//        for (int i = 0; i < mRows.size(); i++) {
//            MyLogger.jLog().i("i=" + i + ",mRows:" + mRows.get(i));
//        }
        mFragment = new FragmentStarRecommend(getActivity());
        mFragments.add(mFragment);
//      快速导航栏
//      mFragments.add(new FragmentStarClassify());
//        mFragments.add(new FragmentStarRecommend(mRows));
        vp_recommed_star.setAdapter(new YuYuePagerAdapter(getFragmentManager(), mFragments));
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    //返回子类布局
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_yuyue;
    }

    //跳转搜索界面
    @OnClick(R.id.iv_search)
    public void goSearch(View view) {
        startActivity(new Intent(mContext, HomeSearchActivity.class));
    }

    //跳转消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        startActivity(new Intent(mContext, MessageActivity.class));
    }

    //停止请求
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCore.stopRequest();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        MyLogger.jLog().i("sp_onNothingSelected");
    }

    //联网请求字段
    @Override
    public void onRequestSuccess(Response<String> response) {
        StarListKey starKey = mGson.fromJson(response.get(), StarListKey.class);
        if (starKey.isSuccess()) {
            List<StarListKey.RowsBean> rows = starKey.getRows();
            switch (rows.get(0).getActionCode()) {
                case "type":
                    types.clear();
                    types.addAll(rows);
                    mSpAdapter1.setData(types);
                    sp1.setSelection(types.size() - 1);
                    break;

                case "userType":
                    userTypes.clear();
                    userTypes.addAll(rows);
                    mSpAdapter2.setData(userTypes);
                    sp2.setSelection(userTypes.size() - 1);
                    break;

                case "userCost":
                    userCoasts.clear();
                    userCoasts.addAll(rows);
                    mSpAdapter3.setData(userCoasts);
                    sp3.setSelection(userCoasts.size() - 1);
                    break;
            }
        } else {
            ToastUtils.showToast(starKey.getErrorMsg());
        }
    }

    //sp条目选择事件
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refresh++;
        switch (parent.getId()) {
            case R.id.sp_1:
                type = Integer.parseInt(types.get(position).getKeyFild());
                break;

            case R.id.sp_2:
                if (position == userTypes.size() - 1) {
                    userType = null;
                } else {
                    userType = userTypes.get(position).getKeyFild();
                }
                break;

            case R.id.sp_3:
                if (position == userCoasts.size() - 1) {
                    userCost = null;
                } else {
                    userCost = userCoasts.get(position).getKeyFild();
                }
                break;
        }
        //进入页面时每个sp都会调用   用计数器做限制
        if (refresh >= 3) {
            MyLogger.jLog().i("type:" + type + ", userCost:" + userCost + ", userType:" + userType);
            queryStar(type, userCost, userType);
        }
    }
}
