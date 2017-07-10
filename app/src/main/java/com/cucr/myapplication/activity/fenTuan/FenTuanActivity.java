package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.cucr.myapplication.adapter.PagerAdapter.FenTuanPagerAdapter;
import com.cucr.myapplication.fragment.fenTuan.HotFenTuanFragment;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/5/18.
 */

public class FenTuanActivity extends Activity {

    //viewpager
    @ViewInject(R.id.vp_fentuan)
    ViewPager vp_fentuan;

    //头部
    @ViewInject(R.id.rl_head)
    RelativeLayout head;

    //导航栏
    @ViewInject(R.id.tablayout_fentuan)
    TabLayout tablayout;

    //粉团页面的所有fragment
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fentuan);
        ViewUtils.inject(this);
        initHead();
        //初始化VP
        initVP();



    }
    //初始化VP
    private void initVP() {

        mFragments.add(new HotFenTuanFragment());
        mFragments.add(new HotFenTuanFragment());

        tablayout.addTab(tablayout.newTab().setText("火热粉团"));
        tablayout.addTab(tablayout.newTab().setText("全部粉团"));
        tablayout.setupWithViewPager(vp_fentuan);//将导航栏和viewpager进行关联

        vp_fentuan.setAdapter(new FenTuanPagerAdapter(getFragmentManager(),mFragments));
    }

    //初始化头部 沉浸栏
    private void initHead() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this,73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    //返回
    @OnClick(R.id.iv_fentuan_back)
    public void back(View view){
        finish();
    }

    //创建ft
    @OnClick(R.id.iv_fentuan_add)
    public void newFt(View view){
       startActivity(new Intent(this,CreatFtActivity.class));
    }
}
