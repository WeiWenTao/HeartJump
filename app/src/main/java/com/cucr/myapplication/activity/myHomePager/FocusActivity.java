package com.cucr.myapplication.activity.myHomePager;

import android.app.Activity;
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
import com.cucr.myapplication.adapter.PagerAdapter.FocusPagerAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Arrays;
import java.util.List;

public class FocusActivity extends Activity {

    @ViewInject(R.id.head)
    RelativeLayout head;

    //导航栏
    @ViewInject(R.id.tablayout_focus)
    TabLayout tablayout_focus;


    private ViewPager mViewPager;
    private static final String[] CHANNELS = new String[]{"全部关注", "推荐关注"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);

        ViewUtils.inject(this);
        //沉浸栏
        initHead();

        initView();
        initTableLayout();

    }

    private void initTableLayout() {
        tablayout_focus.addTab(tablayout_focus.newTab().setText("推荐明星"));
        tablayout_focus.addTab(tablayout_focus.newTab().setText("全部明星"));
        tablayout_focus.setupWithViewPager(mViewPager);//将导航栏和viewpager进行关联

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


    protected void initView() {
        //初始化vp和vpi
        mViewPager = (ViewPager) findViewById(R.id.view_pager_focus);
        mViewPager.setAdapter(new FocusPagerAdapter(mDataList));
    }

    @OnClick(R.id.iv_focus_back)
    public void back(View view){
        finish();
    }

}
