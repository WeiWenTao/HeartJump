package com.cucr.myapplication.activity.fansCatgory;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.hyt.YyhdActivity_1;
import com.cucr.myapplication.activity.hyt.YyhdActivity_2;
import com.cucr.myapplication.activity.hyt.YyhdActivity_3;
import com.cucr.myapplication.activity.hyt.YyhdCatgoryActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.dialog.DialogYyhd;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class YyzcActivity extends BaseActivity implements DialogYyhd.OnClickBt, SwipeRecyclerView.OnLoadListener, YyhdAdapter.OnClickItems, RequersCallBackListener {

    @ViewInject(R.id.iv_fabu)
    private ImageView iv_fabu;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private YyhdAdapter mAdapter;
    private Intent mIntent;
    private int starId;
    private DialogYyhd mDialog;
    private HytCore mCore;
    private int page;
    private int rows;
    private boolean isRefresh;
    private SwipeRecyclerView mRlv_yyhd;

    @Override
    protected void initChild() {
        initTitle("应援众筹");
        init();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_yyzc;
    }

    private void init() {
        page = 1;
        rows = 8;
        starId = getIntent().getIntExtra("starId", -1);
        iv_fabu.setVisibility(starId != -1 ? View.VISIBLE : View.GONE);
        mCore = new HytCore();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra("starId", starId);
        mAdapter = new YyhdAdapter();
        mDialog = new DialogYyhd(this, R.style.MyDialogStyle);
        Window dialogWindow = mDialog.getWindow();
        mDialog.setOnClickBt(this);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mRlv_yyhd = (SwipeRecyclerView) findViewById(R.id.rlv_yyhd);
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
                    if (activeInfo.getTotal() == 0) {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        mAdapter.setData(activeInfo.getRows());
                    }
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
        if (what == Constans.TYPE_SEVEN) {
            if (needShowLoading) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                needShowLoading = false;
            }
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
        if (what == Constans.TYPE_SEVEN) {
            if (mRlv_yyhd.getSwipeRefreshLayout().isRefreshing()) {
                mRlv_yyhd.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (mRlv_yyhd.isLoadingMore()) {
                mRlv_yyhd.stopLoadingMore();
            }
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
    public void onClickItem(YyhdInfos.RowsBean rowsBean) {
        mIntent.setClass(MyApplication.getInstance(), YyhdCatgoryActivity.class);
        mIntent.putExtra("date", rowsBean);
        startActivityForResult(mIntent, 1);
    }


    @OnClick(R.id.iv_fabu)
    public void OnCLickCreat(View view) {
        mDialog.show();
    }

    //空白页面处理
    @OnClick(R.id.tv_creat)
    public void clickCreat(View view){
        mDialog.show();
    }
}
