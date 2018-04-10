package com.cucr.myapplication.activity.pay;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.TxRecordAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.user.XbRecord;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

public class TxRecordActivity extends BaseActivity implements OnCommonListener, RequersCallBackListener, SwipeRecyclerView.OnLoadListener {

    //提现记录
    @ViewInject(R.id.rlv_tx_record)
    private SwipeRecyclerView rlv_record;

    //提现规则
    @ViewInject(R.id.tv_value)
    private TextView tv_value;

    //星币余额
    @ViewInject(R.id.tv_has)
    private TextView tv_has;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;
    private TxRecordAdapter mAdapter;
    private PayCenterCore mCore;

    @Override
    protected void initChild() {
        initTitle("星币");
        mCore = new PayCenterCore();
        mAdapter = new TxRecordAdapter();
        rlv_record.getRecyclerView().addItemDecoration(new SpaceItemDecoration(1));
        rlv_record.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_record.setAdapter(mAdapter);
        rlv_record.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_star_money;
    }

    //充值
    @OnClick(R.id.tv_cz)
    public void goToPay(View view) {
        startActivityForResult(new Intent(this, PayCenterActivity_new.class), 111);
    }

    //提现
    @OnClick(R.id.tv_tx)
    public void goTiXian(View view) {
        startActivityForResult(new Intent(this, XbTxActivity.class), 111);
    }

    @Override
    public void onRequestSuccess(Response<String> response) {
        ReBackMsg reBackMsg = new Gson().fromJson(response.get(), ReBackMsg.class);
        if (reBackMsg.isSuccess()) {
            tv_value.setText(reBackMsg.getMsg());
        } else {
            ToastUtils.showToast(reBackMsg.getMsg());
        }
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        XbRecord xbRecord = mGson.fromJson(response.get(), XbRecord.class);
        if (xbRecord.isSuccess()) {
            if (isRefresh) {
                if (xbRecord.getTotal() == 0) {
                    tv_has.setText("暂无记录");
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mAdapter.setData(xbRecord.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(xbRecord.getRows());
            }
            if (xbRecord.getTotal() <= page * rows) {
                rlv_record.onNoMore("没有更多了");
            } else {
                rlv_record.complete();
            }
        } else {
            ToastUtils.showToast(xbRecord.getErrorMsg());
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
        if (what == Constans.TYPE_FIVE) {
            if (rlv_record.isRefreshing()) {
                rlv_record.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (rlv_record.isLoadingMore()) {
                rlv_record.stopLoadingMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_record.getSwipeRefreshLayout().setRefreshing(true);
        //5表示提现
        mCore.queryTxRecoed(page, rows, 5, this);
        mCore.queryUserMoney(this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_record.onLoadingMore();
        mCore.queryTxRecoed(page, rows, 5, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            onRefresh();
        }
    }
}
