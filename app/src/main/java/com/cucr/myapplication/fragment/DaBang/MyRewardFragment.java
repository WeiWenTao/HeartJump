package com.cucr.myapplication.fragment.DaBang;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.DaShangCatgoryAdapter;
import com.cucr.myapplication.core.daShang.DaShangCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.fenTuan.DaShangListInfo;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by cucr on 2017/11/10.
 */

public class MyRewardFragment extends BaseFragment {

    @ViewInject(R.id.rlv_reward_me)
    private RecyclerView rlv;

    private DaShangCatgoryAdapter mDsListAdapter;
    private DaShangCore mCore;
    private int page = 1;
    private int rows = 100;

    public MyRewardFragment() {
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this,childView);
        mCore = new DaShangCore();
        initViews();
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_reward_me;
    }

    private void initViews() {
        mDsListAdapter = new DaShangCatgoryAdapter();
        rlv.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv.setAdapter(mDsListAdapter);
        queryDatas();
    }

    private void queryDatas() {
        mCore.queryDsMe(1, rows, page, new OnCommonListener() {
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
    public void onDestroyView() {
        super.onDestroyView();
        mCore.stopRequest();
    }

}
