package com.cucr.myapplication.fragment.yuyue;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.StarListForQiYeAdapter;
import com.cucr.myapplication.adapter.SpinnerAdapter.MySp1Adapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventOnClickCancleFocus;
import com.cucr.myapplication.bean.eventBus.EventOnClickFocus;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.bean.starList.StarListKey;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.recyclerView.EndlessRecyclerOnScrollListener;
import com.cucr.myapplication.widget.recyclerView.LoadMoreWrapper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/4/10.
 */

public class ApointmentFragmentA extends BaseFragment implements Spinner.OnItemSelectedListener, OnCommonListener {

    //sp1
    @ViewInject(R.id.sp_1)
    private Spinner sp1;

    //sp2
    @ViewInject(R.id.sp_2)
    private Spinner sp2;

    //sp3
    @ViewInject(R.id.sp_3)
    private Spinner sp3;

    //头部
    @ViewInject(R.id.head)
    private RelativeLayout head;

    //刷新控件
    @ViewInject(R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipe_refresh_layout;

    //列表
    @ViewInject(R.id.rlv_starlist)
    private RecyclerView rlv_starlist;

    private QueryStarListCore mCore;
    private List<StarListInfos.RowsBean> mRows;
    private List<StarListInfos.RowsBean> allRows;
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
    private int finalPosition;
    private StarListForQiYeAdapter mAdapter;
    private Context mContext;
    private LoadMoreWrapper wapper;
    private Intent mIntent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(View childView) {
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        ViewUtils.inject(this, childView);
        this.mContext = MyApplication.getInstance();
        mCore = new QueryStarListCore();
        mAdapter = new StarListForQiYeAdapter(getActivity());
        allRows = new ArrayList<>();
        if (mIntent == null){
            mIntent = new Intent(mContext, StarPagerActivity.class);
        }
        rows = 16;
        page = 1;

        initRlv();

        initSP();

        queryKey();

//        initHead();


    }

    private void initRlv() {
        rlv_starlist.setLayoutManager(new GridLayoutManager(mContext, 2));
        wapper = new LoadMoreWrapper(mAdapter);
        rlv_starlist.setAdapter(wapper);
        initLoad();

        mAdapter.setOnItemClickListener(new StarListForQiYeAdapter.OnItemClickListener() {
            @Override
            public void onClickItems(int position) {
                final StarListInfos.RowsBean rowsBean = allRows.get(position);
                mIntent.putExtra("starId", rowsBean.getId());
                finalPosition = position;
                startActivityForResult(mIntent, 222);
                //发送明星id到明星主页
                EventBus.getDefault().postSticky(new EventFIrstStarId(rowsBean.getId()));
            }
        });
    }

    //关注同步
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StarListInfos.RowsBean mData = (StarListInfos.RowsBean) data.getSerializableExtra("data");
        allRows.remove(finalPosition);
        allRows.add(finalPosition, mData);
        mAdapter.setData(allRows);
        wapper.notifyDataSetChanged();
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

    //点击关注时
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickfocus(EventOnClickFocus event) {
        wapper.notifyDataSetChanged();
    }

    //点击取消关注时 刷新页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickCanclefocus(EventOnClickCancleFocus event) {
        wapper.notifyDataSetChanged();
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

    //刷新的时候调用
    private void queryStar(int type, String userCost, String userType) {

        page = 1;
        if (!swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(true);
        }
        //企业用户查询的是整页明星  所以不需要starId
        mCore.queryStar(type, page, rows, -1, userCost, userType, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starListInfos.isSuccess()) {
                    mRows = starListInfos.getRows();
                    allRows.clear();
                    allRows.addAll(mRows);
                    mAdapter.setData(allRows);
                    MyLogger.jLog().i("starList:" + mRows + ",isCache:" + response.isFromCache());
                    wapper.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(starListInfos.getErrorMsg());
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
        startActivity(new Intent(mContext, StarSearchActivity.class));
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
        EventBus.getDefault().unregister(this);
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
                //传最后一个表示查全部
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

    //请求完成  如果还在加载  就停止加载(无网络情况)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinish(EventRequestFinish event) {
        if (swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(false);
        }

        if (wapper.getLoadState() == wapper.LOADING) {
            wapper.setLoadState(wapper.LOADING_COMPLETE);
        }

    }

    private void initLoad() {
        //刷新监听
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryStar(type, userCost, userType);
            }
        });

        //上拉监听
        rlv_starlist.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                wapper.setLoadState(wapper.LOADING);
                //企业用户查询的是整页明星  所以不需要starId
                mCore.queryStar(type, page, rows, -1, userCost, userType, new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                        if (starListInfos.isSuccess()) {
                            mRows = starListInfos.getRows();
                            if (starListInfos.getTotal() < rows) {
                                ToastUtils.showEnd();
                                wapper.setLoadState(wapper.LOADING_END);
                            } else {
                                wapper.setLoadState(wapper.LOADING_COMPLETE);
                            }
                            mAdapter.addData(mRows);
                            allRows.addAll(mRows);
                            wapper.notifyDataSetChanged();
                        } else {
                            ToastUtils.showToast(starListInfos.getErrorMsg());
                        }
                    }
                });
            }
        });
    }
}
