package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.RenZhengPagrAdapter;
import com.cucr.myapplication.fragment.renzheng.QiYeRZ;
import com.cucr.myapplication.fragment.renzheng.StarRZ;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class RenZhengActivity extends Activity {

    //顶部的TabLayout
    @ViewInject(R.id.tl_tab)
    TabLayout tl_tab;

    //ViewPager
    @ViewInject(R.id.vp_ren_zheng)
    ViewPager vp_ren_zheng;

    @ViewInject(R.id.head)
    RelativeLayout head;


    List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zhenng);
        ViewUtils.inject(this);
        initHead();
        initView();
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initView() {
        mFragmentList = new ArrayList<>();

        tl_tab.addTab(tl_tab.newTab().setText("明星"));
        tl_tab.addTab(tl_tab.newTab().setText("企业"));

        mFragmentList.add(new StarRZ());
        mFragmentList.add(new QiYeRZ());

        vp_ren_zheng.setAdapter(new RenZhengPagrAdapter(getFragmentManager(),mFragmentList));

        //TabLayou绑定ViewPager
        tl_tab.setupWithViewPager(vp_ren_zheng,true);
    }

    //返回
    @OnClick(R.id.iv_ren_zheng_back)
    public void back(View view){
        finish();
    }
}
