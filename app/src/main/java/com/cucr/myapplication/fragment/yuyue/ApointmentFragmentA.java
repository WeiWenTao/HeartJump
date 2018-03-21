package com.cucr.myapplication.fragment.yuyue;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.activity.star.StarSearchActivity;
import com.cucr.myapplication.adapter.RlVAdapter.StarListForQiYeAdapter;
import com.cucr.myapplication.adapter.SpinnerAdapter.MySp1Adapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventOnClickCancleFocus;
import com.cucr.myapplication.bean.eventBus.EventOnClickFocus;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.bean.starList.StarListKey;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
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

public class ApointmentFragmentA extends BaseFragment implements Spinner.OnItemSelectedListener, OnCommonListener, SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    //sp1
    @ViewInject(R.id.sp_1)
    private Spinner sp1;

    //sp2
    @ViewInject(R.id.sp_2)
    private Spinner sp2;

    //sp3
    @ViewInject(R.id.sp_3)
    private Spinner sp3;

    //列表
    @ViewInject(R.id.rlv_starlist)
    private SwipeRecyclerView srlv;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private QueryStarListCore mCore;
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
    private Intent mIntent;

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
        needShowLoading = true;
        if (mIntent == null) {
            mIntent = new Intent(mContext, StarPagerActivity.class);
        }
        rows = 16;
        page = 1;

        initRlv();

        initSP();

        queryKey();

    }

    private void initRlv() {
        srlv.getRecyclerView().setLayoutManager(new GridLayoutManager(mContext, 2));
        srlv.setAdapter(mAdapter);
        srlv.setOnLoadListener(this);

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
        mAdapter.notifyDataSetChanged();
    }

    //点击取消关注时 刷新页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickCanclefocus(EventOnClickCancleFocus event) {
        mAdapter.notifyDataSetChanged();
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
            needShowLoading = true;
            onRefresh();
        }
    }


    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        srlv.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryStar(type, page, rows, -1, userCost, userType, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        srlv.onLoadingMore();
        mCore.queryStar(type, page, rows, -1, userCost, userType, this);
    }

    private boolean needShowLoading;
    private boolean isRefresh;

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        StarListInfos starList = mGson.fromJson(response.get(), StarListInfos.class);
        if (starList.isSuccess()) {
            if (isRefresh) {
                if (starList.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    allRows.clear();
                    mAdapter.setData(starList.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(starList.getRows());
            }
            allRows.addAll(starList.getRows());
            if (starList.getTotal() <= page * rows) {
                srlv.onNoMore("没有更多了");
            } else {
                srlv.complete();
            }
        } else {
            ToastUtils.showToast(starList.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (needShowLoading) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            needShowLoading = false;
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {
        if (isRefresh && response.getException() instanceof NetworkError) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_ONE) {
            if (srlv.isRefreshing()) {
                srlv.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (srlv.isLoadingMore()) {
                srlv.stopLoadingMore();
            }
        }
    }
}
