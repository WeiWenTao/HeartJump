package com.cucr.myapplication.fragment.fuLiHuoDong;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiAdapter;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.fuLi.FuLiCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.fuli.ActiveInfo;
import com.cucr.myapplication.model.fuli.DuiHuanGoosInfo;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by cucr on 2017/9/1.
 */

public class FragmentFuLi extends Fragment {
    View view;

    //活动福利
    @ViewInject(R.id.rlv_fuli)
    RecyclerView rlv_fuli;

    private Gson mGson;
    private FuLiCore mCore;
    private int page;
    private Context mContext;
    //兑换查询结果
    private List<DuiHuanGoosInfo.RowsBean> goodInfos;

    //活动查询结果
    private List<ActiveInfo.RowsBean> activeInfos;
    private FuLiAdapter activeAdapter;
    private Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        page = 1;
        mGson = new Gson();
        mContext = MyApplication.getInstance();
        mIntent = new Intent(MyApplication.getInstance(), TestWebViewActivity.class);
        mCore = new FuLiCore();
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fuli, container, false);
            ViewUtils.inject(this, view);
            initRLV();
            queryDduiHuanInfo();
            queryActiveInfo();
        }
        return view;
    }

    //查询福利活动
    private void queryActiveInfo() {
        //活动
        mCore.QueryHuoDong(page, 15, new OnCommonListener() { //每次请求15条数据
            @Override
            public void onRequestSuccess(Response<String> response) {
                ActiveInfo activeInfo = mGson.fromJson(response.get(), ActiveInfo.class);
                if (activeInfo.isSuccess()) {
                    activeInfos = activeInfo.getRows();
                    activeAdapter.setDate(activeInfos);
                } else {
                    ToastUtils.showToast(mContext, activeInfo.getErrorMsg());
                }
            }
        });
    }

    private void queryDduiHuanInfo() {
        //兑换
        mCore.QueryDuiHuan(1, 1000, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                final DuiHuanGoosInfo duiHuanGoosInfo = mGson.fromJson(response.get(), DuiHuanGoosInfo.class);
                if (duiHuanGoosInfo.isSuccess()) {
                    goodInfos = duiHuanGoosInfo.getRows();
                    //更新数据
                    activeAdapter.setDuiHuan(goodInfos);
                } else {
                    ToastUtils.showToast(mContext, duiHuanGoosInfo.getErrorMsg());
                }

            }
        });
    }


    private void initRLV() {
        rlv_fuli.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        activeAdapter = new FuLiAdapter(mContext, activeInfos);

        rlv_fuli.setAdapter(activeAdapter);
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

}
