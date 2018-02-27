package com.cucr.myapplication.fragment.hyt;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.hyt.CreatHytActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytListInfos;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucrx on 2018/1/16.
 */

@SuppressLint("ValidFragment")
public class Fragment_hyt extends Fragment implements HytAdapter.OnClickItems {

    private View rootView;
    private Intent mIntent;
    private HytCore mCore;
    private int startId;
    private Gson mGson;
    private HytAdapter mAdapter;
    private int page;
    private int rows;
    private MyWaitDialog mMyWaitDialog;
    private SwipeRecyclerView mRlv_hyt;
    private boolean isLoadFinish;

    public Fragment_hyt(int startId) {
        this.startId = startId;
    }

    public Fragment_hyt() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hyt_hyt, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        page = 1;
        rows = 15;
        mGson = MyApplication.getGson();
        mCore = new HytCore();
        mMyWaitDialog = new MyWaitDialog(rootView.getContext(), R.style.MyWaitDialog);
        mIntent = new Intent(MyApplication.getInstance(), CreatHytActivity.class);
        mIntent.putExtra("starId", startId);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mRlv_hyt = (SwipeRecyclerView) rootView.findViewById(R.id.rlv_hyt);
        mRlv_hyt.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new HytAdapter();
        mAdapter.setOnClickItems(this);
        mRlv_hyt.setAdapter(mAdapter);
        queryHyt();
    }

    //查询
    private void queryHyt() {
        mCore.queryHyt(startId, page, rows, mCommonListener);
    }

    private RequersCallBackListener mCommonListener = new RequersCallBackListener() {
        @Override
        public void onRequestSuccess(int what, Response<String> response) {
            HytListInfos hytListInfos = mGson.fromJson(response.get(), HytListInfos.class);
            mAdapter.setData(hytListInfos.getRows());
        }

        @Override
        public void onRequestStar(int what) {
            mMyWaitDialog.show();
        }

        @Override
        public void onRequestFinish(int what) {
            mMyWaitDialog.dismiss();
        }
    };

    //点击条目
    @Override
    public void onClickItem(int position) {
        //创建后援团
        if (position == 0) {
            startActivity(mIntent);
        } else {
            ToastUtils.showToast("跳转详情页");
        }
    }

    //点击加入
    @Override
    public void onClickJoin(int hytId) {
        //todo 登录聊天账号
//        startActivity(new Intent(MyApplication.getInstance(), HytChatActivity.class));
    }
}
