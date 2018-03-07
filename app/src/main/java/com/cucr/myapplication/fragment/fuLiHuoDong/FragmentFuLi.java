package com.cucr.myapplication.fragment.fuLiHuoDong;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fuli.ActiveInfo;
import com.cucr.myapplication.bean.fuli.DuiHuanGoosInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.fuLi.FuLiCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by cucr on 2017/9/1.
 */

public class FragmentFuLi extends Fragment implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener {
    //活动福利
    @ViewInject(R.id.rlv_fuli)
    private SwipeRecyclerView rlv_fuli;

    private View view;
    private Gson mGson;
    private FuLiCore mCore;
    private int page;
    private boolean isRefresh;
    private int rows;
    private FuLiAdapter activeAdapter;
    private Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        page = 1;
        rows = 2;
        mGson = new Gson();
        mIntent = new Intent(MyApplication.getInstance(), TestWebViewActivity.class);
        mCore = new FuLiCore();
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fuli, container, false);
            ViewUtils.inject(this, view);
            initRLV();
            queryDduiHuanInfo();
            onRefresh();
        }
        return view;
    }

    private void queryDduiHuanInfo() {
        //兑换
        mCore.QueryDuiHuan(1, 1000, this);
    }


    private void initRLV() {
        rlv_fuli.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        activeAdapter = new FuLiAdapter();
        rlv_fuli.setAdapter(activeAdapter);
        rlv_fuli.setOnLoadListener(this);
        activeAdapter.setOnItemListener(new FuLiAdapter.OnItemListener() {
            @Override
            public void OnItemClick(View view, int activeId) {
                //跳转到福利活动详情
                mIntent.putExtra("url", HttpContans.HTTP_HOST + HttpContans.ADDRESS_FULI_ACTIVE_DETIAL
                        + "?activeId=" + activeId + "&userId=" + SpUtil.getParam(SpConstant.USER_ID, -1));
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_TWO) {
            ActiveInfo infos = mGson.fromJson(response.get(), ActiveInfo.class);
            if (infos.isSuccess()) {
                if (isRefresh) {
                    activeAdapter.setDate(infos.getRows());
                } else {
                    activeAdapter.addDate(infos.getRows());
                }
                if (infos.getTotal() <= page * rows) {
                    rlv_fuli.onNoMore("没有更多了");
                } else {
                    rlv_fuli.complete();
                }
            }
        } else if (what == Constans.TYPE_ONE) {
            DuiHuanGoosInfo duiHuanGoosInfo = mGson.fromJson(response.get(), DuiHuanGoosInfo.class);
            if (duiHuanGoosInfo.isSuccess()) {
                //兑换查询结果
                List<DuiHuanGoosInfo.RowsBean> goodInfos = duiHuanGoosInfo.getRows();
                //更新数据
                activeAdapter.setDuiHuan(goodInfos);
            } else {
                ToastUtils.showToast(duiHuanGoosInfo.getErrorMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_TWO) {
            if (rlv_fuli.isRefreshing()) {
                rlv_fuli.getSwipeRefreshLayout().setRefreshing(false);
            }
        }
    }

    //查询福利活动
    //刷新
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_fuli.getSwipeRefreshLayout().setRefreshing(true);
        mCore.QueryHuoDong(page, rows, this);
    }

    //查询福利活动
    //加载
    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_fuli.onLoadingMore();
        mCore.QueryHuoDong(page, rows, this);
    }

}
