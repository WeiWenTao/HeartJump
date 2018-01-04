package com.cucr.myapplication.activity.fenTuan;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.DaShangCatgoryAdapter;
import com.cucr.myapplication.core.daShang.DaShangCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.fenTuan.DaShangListInfo;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class DaShangCatgoryActivity extends BaseActivity {

    //打赏详情
    @ViewInject(R.id.rlv_ds_catgory)
    private RecyclerView rlv_ds_catgory;
    private DaShangCore mCore;
    private int mContentId;
    private DaShangCatgoryAdapter mDsListAdapter;

    @Override
    protected void initChild() {
        initViews();

    }

    private void initViews() {
        mContentId = getIntent().getIntExtra("contentId", -1);
        mCore = new DaShangCore();
        mDsListAdapter = new DaShangCatgoryAdapter();
        rlv_ds_catgory.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_ds_catgory.setAdapter(mDsListAdapter);
        queryDsList();
    }

    private void queryDsList() {
        mCore.queryDsList(mContentId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                DaShangListInfo dsInfo = mGson.fromJson(response.get(), DaShangListInfo.class);
                if (dsInfo.isSuccess()) {
                    List<DaShangListInfo.RowsBean> rows = dsInfo.getRows();
                    mDsListAdapter.setData(rows);
                } else {
                    ToastUtils.showToast(dsInfo.getErrorMsg());
                }
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_da_shang_catgory;
    }

    @OnClick(R.id.tv_ds_record)
    public void dsRecord(View view) {
        startActivity(new Intent(MyApplication.getInstance(), DaShangRecordActivity.class));
    }

}
