package com.cucr.myapplication.activity.myHomePager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MyFocusAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class MineFansActivity extends BaseActivity implements RequersCallBackListener {

    //导航栏
    @ViewInject(R.id.rlv)
    private RecyclerView rlv;

    private QueryFocus core;
    private int page;
    private int rows;
    private MyFocusAdapter mAdapter;


    @Override
    protected void initChild() {
        page = 1;
        rows = 10;
        core = new QueryFocus();
        rlv.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new MyFocusAdapter();
        rlv.setAdapter(mAdapter);
        core.queryMyFens(page, rows, this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_mine_fans;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        switch (what) {
            case Constans.TYPE_THREE:
                FocusInfo focusInfo = MyApplication.getGson().fromJson(response.get(), FocusInfo.class);
                if (focusInfo.isSuccess()) {
                    mAdapter.setData(focusInfo.getRows());
                } else {
                    ToastUtils.showToast(focusInfo.getErrorMsg());
                }
                break;
        }

    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
