package com.cucr.myapplication.fragment.hyt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.hyt.YyhdActivity_1;
import com.cucr.myapplication.activity.hyt.YyhdActivity_2;
import com.cucr.myapplication.activity.hyt.YyhdActivity_3;
import com.cucr.myapplication.activity.hyt.YyhdCatgoryActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.fragment.LazyFragment_app;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.dialog.DialogYyhd;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by cucrx on 2018/1/16.
 */

@SuppressLint("ValidFragment")
public class Fragment_yyhd extends LazyFragment_app implements YyhdAdapter.OnClickItems, DialogYyhd.OnClickBt, RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    private View rootView;
    private YyhdAdapter mAdapter;
    private Intent mIntent;
    private int starId;
    private DialogYyhd mDialog;
    private HytCore mCore;
    private int page;
    private int rows;
    private MyWaitDialog mMyWaitDialog;
    private boolean isRefresh;
    private SwipeRecyclerView mRlv_yyhd;

    public Fragment_yyhd(int starId) {
        this.starId = starId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hyt_yyhd, container, false);
        }
        return rootView;
    }

    private void init() {
        page = 1;
        rows = 2;
        mCore = new HytCore();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra("starId", starId);
        mAdapter = new YyhdAdapter();
        mMyWaitDialog = new MyWaitDialog(rootView.getContext(), R.style.MyWaitDialog);
        mDialog = new DialogYyhd(rootView.getContext(), R.style.MyDialogStyle);
        Window dialogWindow = mDialog.getWindow();
        mDialog.setOnClickBt(this);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mRlv_yyhd = (SwipeRecyclerView) rootView.findViewById(R.id.rlv_yyhd);
        mRlv_yyhd.getRecyclerView().addItemDecoration(new SpaceItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 10)));
        mRlv_yyhd.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mRlv_yyhd.setAdapter(mAdapter);
        mRlv_yyhd.setOnLoadListener(this);
        mAdapter.setOnClickItems(this);
        onRefresh();
    }

    //点亮开屏
    @Override
    public void clickYyhd1() {
        mIntent.setClass(MyApplication.getInstance(), YyhdActivity_1.class);
        startActivity(mIntent);
    }

    //武汉BIGPAD
    @Override
    public void clickYyhd2() {
        mIntent.setClass(MyApplication.getInstance(), YyhdActivity_2.class);
        startActivity(mIntent);
    }

    //粉丝见面会
    @Override
    public void clickYyhd3() {
        mIntent.setClass(MyApplication.getInstance(), YyhdActivity_3.class);
        startActivity(mIntent);
    }


    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_SEVEN) {
            YyhdInfos activeInfo = MyApplication.getGson().fromJson(response.get(), YyhdInfos.class);
            if (activeInfo.isSuccess()) {
                List<YyhdInfos.RowsBean> infos = activeInfo.getRows();
                //是刷新还是加载
                if (isRefresh) {
                    mAdapter.setData(infos);
                } else { //加载数据
                    mAdapter.addData(infos);
                }

                if (infos.size() < rows) {
                    mRlv_yyhd.onNoMore("");
                } else {
                    mRlv_yyhd.complete();
                }

            } else {
                ToastUtils.showToast(activeInfo.getErrorMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {
//        mMyWaitDialog.show();
    }

    @Override
    public void onRequestFinish(int what) {
//        mMyWaitDialog.dismiss();
        if (mRlv_yyhd.getSwipeRefreshLayout().isRefreshing()) {
            mRlv_yyhd.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mCore.queryHytActive(page, rows, starId, this);
    }

    //加载
    @Override
    public void onLoadMore() {
        int itemCount = mAdapter.getItemCount();
        //由于SwipeRecyclerView的原因(无header就不会) 一进来就会调用 如果满足这个条件  说明adapter集合还没有数据 空指针!!!(有header所以是1)
        if (itemCount <= 1) {
            return;
        }
        isRefresh = false;
        page++;
        mRlv_yyhd.onLoadingMore();
        mCore.queryHytActive(page, rows, starId, this);
    }

    @Override
    protected void onFragmentFirstVisible() {
        init();
    }

    @Override
    public void onClickItem(YyhdInfos.RowsBean rowsBean) {
        mIntent.setClass(MyApplication.getInstance(), YyhdCatgoryActivity.class);
        mIntent.putExtra("date",rowsBean);
        startActivityForResult(mIntent,1);
    }


    @Override
    public void OnCLickHeader() {
        mDialog.show();
    }
}
