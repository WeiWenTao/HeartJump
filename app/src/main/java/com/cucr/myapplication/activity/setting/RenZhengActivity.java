package com.cucr.myapplication.activity.setting;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.RenZhengPagrAdapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.fragment.renzheng.QiYeRZ;
import com.cucr.myapplication.fragment.renzheng.StarRZ;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class RenZhengActivity extends FragmentActivity {

    private List<String> mTitles;

    //顶部的TabLayout
    @ViewInject(R.id.tl_tab)
    private TabLayout tl_tab;

    //ViewPager
    @ViewInject(R.id.vp_ren_zheng)
    private ViewPager vp_ren_zheng;

    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zhenng);
        ViewUtils.inject(this);
        initView();
    }

    private void initView() {

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);


        mFragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();

        MyLogger.jLog().i("rz_roleId:" + (int)SpUtil.getParam(SpConstant.SP_STATUS,-1));
        //区分身份
        switch ((int)SpUtil.getParam(SpConstant.SP_STATUS,-1)){

            case Constans.STATUS_STAR:
                mTitles.add("明星");
                mFragmentList.add(new StarRZ());
                break;

            case Constans.STATUS_QIYE:
                mTitles.add("企业");
                mFragmentList.add(new QiYeRZ());
                break;

            default:
                mTitles.add("明星");
                mTitles.add("企业");
                mFragmentList.add(new StarRZ());
                mFragmentList.add(new QiYeRZ());
        }




        vp_ren_zheng.setAdapter(new RenZhengPagrAdapter(getSupportFragmentManager(),mFragmentList,mTitles));

        //TabLayou绑定ViewPager
        tl_tab.setupWithViewPager(vp_ren_zheng);
    }

    @OnClick(R.id.iv_base_back)
    public void back(View view){
        finish();
    }

}
