package com.cucr.myapplication.activity.yuyue;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MyYuYueAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.core.yuyue.YuYueCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpacesItemDecoration;
import com.cucr.myapplication.widget.recyclerView.EndlessRecyclerOnScrollListener;
import com.cucr.myapplication.widget.recyclerView.LoadMoreWrapper;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class MyYuYueActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyYuYueAdapter.OnClickItem {

    @ViewInject(R.id.rlv_my_yuyue)
    private RecyclerView rlv_my_yuyue;

    @ViewInject(R.id.rlf)
    private SwipeRefreshLayout rlf;

    private MyYuYueAdapter mAdapter;
    private int page;
    private int rows;
    private YuYueCore mCore;
    private LoadMoreWrapper mWapper;

    @Override
    protected void initChild() {
        initTitle("我的预约");
        page = 1;
        rows = 5;
        init();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_yu_yue;
    }

    private void init() {
        mCore = new YuYueCore();
        mAdapter = new MyYuYueAdapter();
        mAdapter.setItem(this);
        rlv_my_yuyue.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_my_yuyue.addItemDecoration(new SpacesItemDecoration(10));
        mWapper = new LoadMoreWrapper(mAdapter);
        rlv_my_yuyue.setAdapter(mWapper);
        rlf.setOnRefreshListener(this);
        onRefresh();
        rlv_my_yuyue.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                mCore.myYuYue(page, rows, new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        QiYeHuoDongInfo infos = MyApplication.getGson().fromJson(response.get(), QiYeHuoDongInfo.class);
                        if (infos.isSuccess()) {
                            mAdapter.addData(infos.getRows());
                            mWapper.notifyDataSetChanged();
                            if (infos.getRows().size() < rows) {
                                mWapper.setLoadState(mWapper.LOADING_END);
                            }
                        } else {
                            ToastUtils.showToast(infos.getErrorMsg());
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        rlf.setRefreshing(true);
        page = 1;
        mCore.myYuYue(page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                QiYeHuoDongInfo infos = MyApplication.getGson().fromJson(response.get(), QiYeHuoDongInfo.class);
                if (infos.isSuccess()) {
                    mAdapter.setData(infos.getRows());
                    mWapper.notifyDataSetChanged();
                    if (infos.getRows().size() < rows) {
                        mWapper.setLoadState(mWapper.LOADING_END);
                    }
                    rlf.setRefreshing(false);
                } else {
                    ToastUtils.showToast(infos.getErrorMsg());
                }
            }
        });
    }

    @Override
    public void onItemClick(QiYeHuoDongInfo.RowsBean rowsBean) {
        Intent intent = new Intent(MyApplication.getInstance(), YuYueResultCatgoryActivity.class);
        intent.putExtra("date",rowsBean);
        startActivity(intent);
    }
}
