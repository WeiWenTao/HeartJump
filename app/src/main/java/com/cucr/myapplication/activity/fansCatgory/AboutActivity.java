package com.cucr.myapplication.activity.fansCatgory;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.PagerAdapter.CommonPagerAdapter_forapp;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.fragment.star.DesicrbeFragment;
import com.cucr.myapplication.fragment.star.StarWorks;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BaseActivity {

    @ViewInject(R.id.tl_tab)
    private TabLayout tl_tab;

    @ViewInject(R.id.vp)
    private ViewPager vp;

    //封面
    @ViewInject(R.id.iv_cover)
    private ImageView iv_cover;

    //粉丝数量
    @ViewInject(R.id.tv_fans)
    private TextView tv_fans;

    //明星姓名
    @ViewInject(R.id.tv_starname)
    private TextView tv_starname;

    private List<Fragment> mFragmentList;
    private List<String> titles;
    private StarListInfos.RowsBean mMData;


    @Override
    protected void initChild() {
        initTitle("关于Ta");
        initView();
    }

    private void initView() {
        mMData = (StarListInfos.RowsBean) getIntent().getSerializableExtra("data");
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mMData.getUserPicCover(), iv_cover, MyApplication.getImageLoaderOptions());
        tv_starname.setText(mMData.getRealName());
        tv_fans.setText("粉丝 " + (mMData.getFansCount() == null ? "0" : mMData.getFansCount()));

        mFragmentList = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("简介");
        titles.add("作品");

        DesicrbeFragment e = new DesicrbeFragment();
        StarWorks w = new StarWorks();
        Bundle args = new Bundle();
        args.putInt("starId", mMData.getId());
        e.setArguments(args);
        w.setArguments(args);

        mFragmentList.add(e);
        mFragmentList.add(w);

        vp.setAdapter(new CommonPagerAdapter_forapp(getFragmentManager(), mFragmentList, titles));
        tl_tab.setupWithViewPager(vp);

    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_about;
    }

    @OnClick(R.id.tv_yuyue)
    public void clickYuYue(View view) {
        if (CommonUtils.isQiYe()) {
            Intent intent = new Intent(MyApplication.getInstance(), YuYueCatgoryActivity.class);
            intent.putExtra("data", mMData);
            startActivity(intent);
        } else {
            ToastUtils.showToast("企业用户才能预约哦，赶快去认证吧");
        }
    }
}
