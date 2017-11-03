package com.cucr.myapplication.fragment.yuyue;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.HomeSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.adapter.PagerAdapter.YuYuePagerAdapter;
import com.cucr.myapplication.adapter.SpinnerAdapter.MySpAdapter;
import com.cucr.myapplication.core.starListAndJourney.QueryStarList;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.fragment.star.FragmentStarRecommend;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.starList.StarListInfos;
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

public class ApointmentFragmentA extends BaseFragment {

    private List<String> mList;

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
    private QueryStarList mCore;
    private List<StarListInfos.RowsBean> mRows;

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        mCore = new QueryStarList(getActivity());
        initSP();

        queryStar();

        initHead();

    }

    private void initSP() {
        mList = new ArrayList<>();
        mList.add("qqqq");
        mList.add("w");
        mList.add("dddd");
        mList.add("100万-150万");
        mList.add("ww");

        sp1.setAdapter(new MySpAdapter(mContext, mList));
        sp2.setAdapter(new MySpAdapter(mContext, mList));
        sp3.setAdapter(new MySpAdapter(mContext, mList));


    }

    private void queryStar() {
        mCore.queryStarList(1, 1, 10, 0, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starListInfos.isSuccess()) {
                    mRows = starListInfos.getRows();
                    MyLogger.jLog().i("queryStarmRows:" + mRows);
                    initVP();
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
        mFragments.add(new FragmentStarRecommend(mRows));
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
}
