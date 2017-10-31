package com.cucr.myapplication.activity.fuli;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiDuiHuanAdapter;
import com.cucr.myapplication.core.fuLi.FuLiCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.fuli.ActiveInfo;
import com.cucr.myapplication.model.fuli.DuiHuanGoosInfo;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.recyclerView.FullyLinearLayoutManager;
import com.cucr.myapplication.widget.recyclerView.MyScrollview;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class FuLiActiviry extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MyScrollview.LoadMoreListener {

    //水平福利
    @ViewInject(R.id.rlv_fuli_duihuan)
    RecyclerView rlv_fuli_duihuan;

    //刷新控件
    @ViewInject(R.id.swr)
    SwipeRefreshLayout swr;

    //scrollView
    @ViewInject(R.id.scroll_view)
    MyScrollview scroll_view;

    //加载布局
    @ViewInject(R.id.ll_load)
    LinearLayout ll_load;

    //垂直福利
    @ViewInject(R.id.rlv_fuli)
    RecyclerView rlv_fuli;

    private FuLiCore mCore;
    private FuLiDuiHuanAdapter mDuihuan;
    private int page;


    //兑换查询结果
    private List<DuiHuanGoosInfo.RowsBean> goodInfos;
    private FullyLinearLayoutManager mFullyLinearLayoutManager;

    //活动查询结果
    private List<ActiveInfo.RowsBean> activeInfos;
    private FuLiAdapter mAdapter;

    @Override
    protected void initChild() {
        mCore = new FuLiCore();
        initTitle("福利");
//        ThreadUtils.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {
                queryDduiHuanInfo();
                queryActiveInfo();
//            }
//        });
        initRLV();
        initRefresh();

    }

    private void initRefresh() {

        scroll_view.setListener(this);

        //改变加载显示的颜色
        swr.setColorSchemeColors(Color.parseColor("#f68d89"));
        //设置背景颜色
        //swr.setBackgroundColor(Color.WHITE);
        //设置初始时的大小
        swr.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        swr.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swr.setDistanceToTriggerSync(150);
        //设置刷新出现的位置
        swr.setProgressViewEndTarget(false, 200);


    }

    private void queryActiveInfo() {
        MyLogger.jLog().i(Thread.currentThread() + "  queryActiveInfo");
        //活动
        mCore.QueryHuoDong(page, 15, new OnCommonListener() { //每次请求15条数据
            @Override
            public void onRequestSuccess(Response<String> response) {
                ActiveInfo activeInfo = mGson.fromJson(response.get(), ActiveInfo.class);
                if (activeInfo.isSuccess()) {
                    activeInfos = activeInfo.getRows();
                    mAdapter.setDate(activeInfos);
                } else {
                    ToastUtils.showToast(FuLiActiviry.this, activeInfo.getErrorMsg());
                }
            }
        });
    }

    private void queryDduiHuanInfo() {

        MyLogger.jLog().i(Thread.currentThread() + "  queryDduiHuanInfo");
        //兑换
        mCore.QueryDuiHuan(1, 1000, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                final DuiHuanGoosInfo duiHuanGoosInfo = mGson.fromJson(response.get(), DuiHuanGoosInfo.class);
                if (duiHuanGoosInfo.isSuccess()) {
                    goodInfos = duiHuanGoosInfo.getRows();
                    //更新数据
                    mDuihuan.setDate(goodInfos);

                } else {
                    ToastUtils.showToast(FuLiActiviry.this, duiHuanGoosInfo.getErrorMsg());
                }

            }
        });
    }


    private void initRLV() {

        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        //设置为横向滑动
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlv_fuli_duihuan.setLayoutManager(layoutManager);
        mDuihuan = new FuLiDuiHuanAdapter(this, goodInfos);
        rlv_fuli_duihuan.setAdapter(mDuihuan);
        mDuihuan.setOnItemListener(new FuLiDuiHuanAdapter.OnItemListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), DuiHuanCatgoryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


        mFullyLinearLayoutManager = new FullyLinearLayoutManager(this);
        rlv_fuli.setLayoutManager(mFullyLinearLayoutManager);
        mAdapter = new FuLiAdapter(this, activeInfos);

        rlv_fuli.setAdapter(mAdapter);
        mAdapter.setOnItemListener(new FuLiAdapter.OnItemListener() {
            @Override
            public void OnItemClick(View view, int position) {
//                startActivity(new Intent(FuLiActiviry.this,FuLiCatgoryActivity.class));
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_fu_li_activiry;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        ThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swr.setRefreshing(false);
                    }
                });
            }
        });
    }


    //上拉加载
    @Override
    public void onLoadMore() {
        scroll_view.setIsLoading(true);
        ll_load.setVisibility(View.VISIBLE);
        ThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ll_load.setVisibility(View.GONE);
                        scroll_view.setIsLoading(false);
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCore.stop();
    }
}
