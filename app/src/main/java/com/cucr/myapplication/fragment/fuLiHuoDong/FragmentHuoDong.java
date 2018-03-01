package com.cucr.myapplication.fragment.fuLiHuoDong;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.huodong.HuoDongCatgoryActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.ActivitysAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.fuLi.HuoDongCore;
import com.cucr.myapplication.fragment.LazyFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by cucr on 2017/9/8.
 */

public class FragmentHuoDong extends LazyFragment implements SwipeRecyclerView.OnLoadListener, ActivitysAdapter.ClickListener {

    //活动列表
    @ViewInject(R.id.rlv_actives)
    private SwipeRecyclerView rlv_actives;

    private View mView;
    private Context mContext;
    private HuoDongCore mCore;
    private int page;
    private int rows;
    private Gson mGson;
    private ActivitysAdapter mAdapter;
    private Intent mIntent;
    private Integer giveNum;
    private int position;
    private List<QiYeHuoDongInfo.RowsBean> mRowBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        MyLogger.jLog().i("111注册");

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_huo_dong, container, false);
            ViewUtils.inject(this, mView);

        }
        return mView;
    }

    //    startActivity(new Intent(mContext, HuoDongCatgoryActivity.class));
//    new HuoDongTaiAdapter(mContext)
    private void initRLV() {
        mContext = MyApplication.getInstance();
        mCore = new HuoDongCore();
        mGson = MyApplication.getGson();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        page = 1;
        rows = 3;

        mAdapter = new ActivitysAdapter();
        mAdapter.setOnClickListener(this);
        rlv_actives.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        rlv_actives.setAdapter(mAdapter);
        rlv_actives.setOnLoadListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        MyLogger.jLog().i("111注销");
        if (mCore != null) { //判断是否初始化
            mCore.stop();
        }
    }

    //请求完成  如果还在加载  就停止加载(无网络情况)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinish(EventRequestFinish event) {
        if (rlv_actives.isRefreshing()) {
            rlv_actives.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {

        page = 1;
        //dataId  查询单条  传-1查所有
        mCore.queryActive(false, -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                // TODO: 2018/3/1  
                QiYeHuoDongInfo info = mGson.fromJson(response.get(), QiYeHuoDongInfo.class);
                if (info.isSuccess()) {
                    mRowBeans = info.getRows();
                    mAdapter.setData(mRowBeans);
                    if (rows > mRowBeans.size()) {
                        rlv_actives.onNoMore("");
                    } else {
                        rlv_actives.complete();
                    }
                } else {
                    ToastUtils.showToast(info.getErrorMsg());
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        page++;
        rlv_actives.onLoadingMore();
        //dataId  查询单条  传-1查所有
        mCore.queryActive(false, -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                QiYeHuoDongInfo info = mGson.fromJson(response.get(), QiYeHuoDongInfo.class);
                if (info.isSuccess()) {
                    List<QiYeHuoDongInfo.RowsBean> rowBeans = info.getRows();
                    mAdapter.addData(rowBeans);
                    if (rows > rowBeans.size()) {
                        rlv_actives.onNoMore("");
                    } else {
                        rlv_actives.complete();
                    }
                    MyLogger.jLog().i("onLoadMore" + mRowBeans.size());
                } else {
                    ToastUtils.showToast(info.getErrorMsg());
                }
            }
        });
    }

    @Override
    protected void onFragmentFirstVisible() {
        initRLV();
        onRefresh();
    }

    @Override
    public void onClickGoods(int position, final QiYeHuoDongInfo.RowsBean rowsBean) {
        MyLogger.jLog().i("onClickGoods");
        mCore.activeGiveUp(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (rowsBean.getIsSignUp() == 1) {
                        giveNum = rowsBean.getGiveUpCount() - 1;
                        rowsBean.setIsSignUp(0);
                        rowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = rowsBean.getGiveUpCount() + 1;
                        rowsBean.setIsSignUp(1);
                        rowsBean.setGiveUpCount(giveNum);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });

    }

    //isFromComment 用以区分是点击条目进去的 还是点击评论进去的
    @Override
    public void onClickCommends(int position, QiYeHuoDongInfo.RowsBean rowsBean, boolean isFromComment) {
        this.position = position;
        Intent intent = new Intent(MyApplication.getInstance(), HuoDongCatgoryActivity.class);
        intent.putExtra("data", rowsBean);
        intent.putExtra("isFromComment", isFromComment);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    @Override
    public void onClickPerson(int personId) {
        mIntent.setClass(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mIntent.putExtra("userId", personId);
        startActivity(mIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
            //接收的数据
            QiYeHuoDongInfo.RowsBean rowBeans = (QiYeHuoDongInfo.RowsBean) data.getSerializableExtra("rowsBean");
            //原来的数据
            QiYeHuoDongInfo.RowsBean rowsBean = mRowBeans.get(position);
            //将原来的数据设置成更新后的数据
            rowsBean.setGiveUpCount(rowBeans.getGiveUpCount());
            rowsBean.setIsSignUp(rowBeans.getIsSignUp());
            rowsBean.setCommentCount(rowBeans.getCommentCount());
            mAdapter.notifyDataSetChanged();
        }
    }

}
