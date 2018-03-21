package com.cucr.myapplication.activity.star;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.adapter.GvAdapter.StarRecommendAdapter;
import com.cucr.myapplication.adapter.PagerAdapter.StarListPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.StarListForQiYeAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.star.AllStarListFragemnt;
import com.cucr.myapplication.fragment.star.RecommendStarListFragemnt;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class StarListForAddActivity extends Activity implements StarListForQiYeAdapter.OnItemClickListener {

    //明星pager
    @ViewInject(R.id.vp_star_pager)
    private ViewPager vp_star_pager;

    //导航
    @ViewInject(R.id.tablayout)
    private TabLayout tablayout;

    //进入心跳
    @ViewInject(R.id.tv_enter)
    private TextView tv_enter;

    //返回键
    @ViewInject(R.id.iv_base_back)
    private ImageView iv_base_back;

    private QueryStarListCore mCore;
    private Gson mGson;
    private int type;   //查全部
    private int rows;   //每页显示数目
    private int page;   //页数
    private List<StarListInfos.RowsBean> mRows;
    private StarRecommendAdapter mGvAdapter;
    private List<Fragment> mFragmentslist;
    private List<String> tytles;
    private StarListForQiYeAdapter mAdapter;
    private QueryFocus mFocusCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_list_for_add);
        ViewUtils.inject(this);
        mFragmentslist = new ArrayList<>();
        tytles = new ArrayList<>();
        mAdapter = new StarListForQiYeAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mFocusCore = new QueryFocus();
        initView();
//        queryData();
    }

    private void initView() {
        tv_enter.setVisibility((getIntent().getBooleanExtra("formLoad", false)) ? View.VISIBLE : View.GONE);
        iv_base_back.setVisibility((getIntent().getBooleanExtra("formLoad", false)) ? View.GONE : View.VISIBLE);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
        mCore = new QueryStarListCore();
        mGson = new Gson();
//        mRows = new ArrayList<>();
//        mGvAdapter = new StarRecommendAdapter(this, mRows);
        mFragmentslist.add(new RecommendStarListFragemnt());
        mFragmentslist.add(new AllStarListFragemnt());
        tablayout.addTab(tablayout.newTab().setText("推荐"));
        tablayout.addTab(tablayout.newTab().setText("全部"));
        tytles.add("推荐");
        tytles.add("全部");
        tablayout.setupWithViewPager(vp_star_pager);//将导航栏和viewpager进行关联
        vp_star_pager.setAdapter(new StarListPagerAdapter(getFragmentManager(), mFragmentslist, tytles));
    }


    //    查询数据
    private void queryData() {
        type = 2;
        rows = 10;
        page = 1;
        //参数: 2:查全部
        mCore.queryStar(2, page, rows, -1, null, null, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starListInfos.isSuccess()) {
                    mRows = starListInfos.getRows();
                    mAdapter.setData(mRows);
                } else {
                    ToastUtils.showToast(starListInfos.getErrorMsg());
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

    //返回
    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        finish();
    }

    //跳转主界面
    @OnClick(R.id.tv_enter)
    public void enterApp(View view) {
        mFocusCore.queryMyFocusStars(-1, 1, 10, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FocusInfo Info = mGson.fromJson(response.get(), FocusInfo.class);
                if (Info.isSuccess()) {
                    if (Info.getRows().size() > 0) {     //有关注明星
                        startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
                        //登录之后保存登录数据  下次登录判断是否第一次登录
                        SharedPreferences.Editor edit = SpUtil.getNewSp().edit();
                        edit.putBoolean(((int) SpUtil.getParam(SpConstant.USER_ID, -1)) + "", true).commit();
                        finish();
                    } else {
                        ToastUtils.showToast("至少要关注一位明星哦");
                    }
                } else {
                    ToastUtils.showToast(Info.getErrorMsg());
                }
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

    @Override
    public void onClickItems(int id) {
        Intent intent = new Intent(MyApplication.getInstance(), StarPagerActivity.class);
        intent.putExtra("starId", id);
        startActivity(intent);
    }
}
