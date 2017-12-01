package com.cucr.myapplication.fragment.star;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.eventBus.EventFIrstStarId;
import com.cucr.myapplication.model.eventBus.EventStarId;
import com.cucr.myapplication.model.eventBus.EventXwStarId;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_xingwen extends Fragment {


    @ViewInject(R.id.rlv_xingwen)
    private RecyclerView rlv_xingwen;

    private View view;
    private QueryFtInfoCore mCore;
    private int starId;
    private int dataType;
    private int page;
    private int rows;
    private QueryFtInfos mQueryFtInfos;
    private Gson mGson;
    private XingWenAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//订阅
        MyLogger.jLog().i("注册");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCore = new QueryFtInfoCore();
        mGson = new Gson();
        mAdapter = new XingWenAdapter();
        dataType = 0;
        page = 1;
        rows = 10;
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_personal_pager_xingwen, container, false);
            ViewUtils.inject(this, view);
            initRlV();
        }
        return view;
    }

    private void initRlV() {
        rlv_xingwen.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_xingwen.setAdapter(mAdapter);
    }

    private void queryXwInfo() {
        MyLogger.jLog().i("xwid:" + starId);
        mCore.queryFtInfo(starId, dataType, -1, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    MyLogger.jLog().i("mQueryFtInfos:" + mQueryFtInfos);
                    mAdapter.setData(mQueryFtInfos);
                    rlv_xingwen.smoothScrollToPosition(0);
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
            }
        });

    }

//    private void initRlV(final Context context) {
//        RecyclerView rlv_dongtai = (RecyclerView) view.findViewById(R.id.rlv_xingwen);
//        rlv_dongtai.setLayoutManager(new LinearLayoutManager(context));
//        rlv_dongtai.setAdapter(new XingWenAdapter(context));
//    }

    //切换明星的时候
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventStarId event) {
        starId = event.getStarId();
        page = 1;
        MyLogger.jLog().i("xwEventStarId：" + starId);
        if (mCore == null) {
            mCore = new QueryFtInfoCore();
        }
        queryXwInfo();
    }

    //查询第一个明星
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onEvent(EventFIrstStarId event) {
        starId = event.getFirstId();
        if (mCore == null) {
            mCore = new QueryFtInfoCore();
        }
        queryXwInfo();
        MyLogger.jLog().i("xwEventFIrstStarId：" + starId);
    }

    //查询星闻
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onEvents(EventXwStarId event) {
        starId = event.getStarId();
        if (mCore == null) {
            mCore = new QueryFtInfoCore();
        }
        queryXwInfo();
        MyLogger.jLog().i("EventXwStarId：" + starId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销
        MyLogger.jLog().i("注销");
    }
}
