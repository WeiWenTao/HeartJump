package com.cucr.myapplication.activity.hyt;

import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdSupportRecord;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdSupports;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class MoreSupportsActivity extends BaseActivity implements RequersCallBackListener {

    @ViewInject(R.id.rlv_record)
    private SwipeRecyclerView srlv;

    private int page;
    private int rows;
    private YyhdSupportRecord mAdapter;

    @Override
    protected void initChild() {
        page = 1;
        rows = 10;
        int activeId = getIntent().getIntExtra("activeId", -1);
        srlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new YyhdSupportRecord();
        srlv.getRecyclerView().setAdapter(mAdapter);
        srlv.getRecyclerView().addItemDecoration(new SpaceItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 10)));
        HytCore core = new HytCore();
        core.querySupport(page, rows, activeId, this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_more_supports;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_EIGHT) {
            YyhdSupports yyhdSupports = mGson.fromJson(response.get(), YyhdSupports.class);
            if (yyhdSupports.isSuccess()) {
                List<YyhdSupports.RowsBean> rows = yyhdSupports.getRows();
                mAdapter.setData(rows);
            } else {
                ToastUtils.showToast(yyhdSupports.getErrorMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
