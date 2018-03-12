package com.cucr.myapplication.fragment.home;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.HomeXingWenAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Home.HomeBannerInfo;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.home.QueryBannerCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 911 on 2017/4/10.
 */

public class HomeHotFragment extends BaseFragment implements OnItemClickListener, SwipeRecyclerView.OnLoadListener, HomeXingWenAdapter.OnClickBanner {

    private SwipeRecyclerView mRlv_home;
    private QueryBannerCore mCore;
    private List<String> pics;
    private QueryFtInfoCore mDataCore;
    private HomeXingWenAdapter mAdapter;
    private QueryFtInfos mQueryFtInfos;
    private int page;
    private int rows;
    private HomeBannerInfo mHomeBannerInfo;

    @Override
    protected void initView(View childView) {
        page = 1;
        rows = 20;
        mCore = new QueryBannerCore(getActivity());
        mDataCore = new QueryFtInfoCore();
        initRlv(childView);
        queryBanner();
//        queryFtInfo();

        onRefresh();
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    private void initRlv(View childView) {
        mRlv_home = (SwipeRecyclerView) childView.findViewById(R.id.rlv_home);
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRlv_home.getRecyclerView().addItemDecoration(decor);
        mRlv_home.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mRlv_home.setOnLoadListener(this);
        //去掉头部分割线
//        mLv_home.setHeaderDividersEnabled(false);
        //用父类的Context
        mAdapter = new HomeXingWenAdapter();
        mAdapter.setClickBanner(this);
        mRlv_home.setAdapter(mAdapter);
    }

//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//
//            //签到
//            case R.id.ll_sign_in:
//                if (ClickUtil.isFastClick())
//                    startActivity(new Intent(mContext, SignActivity.class));
//                break;
//
//            //福利
//            case R.id.ll_fuli:
//                if (ClickUtil.isFastClick())
//                    startActivity(new Intent(mContext, FuLiActiviry.class));
//                break;
//
//            //粉团
//            case R.id.ll_fentuan:
//                if (ClickUtil.isFastClick())
//                    startActivity(new Intent(mContext, FenTuanActivity.class));
//                break;
//
//            //活动
//            case R.id.ll_active:
//                if (ClickUtil.isFastClick())
//                    startActivity(new Intent(mContext, HuoDongActivity.class));
//                break;
//
//        }
//
//    }

    private void queryBanner() {
        pics = new ArrayList<>();
        mCore.queryBanner(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mHomeBannerInfo = mGson.fromJson(response.get(), HomeBannerInfo.class);
                if (mHomeBannerInfo.isSuccess()) {
                    for (HomeBannerInfo.ObjBean objBean : mHomeBannerInfo.getObj()) {
                        pics.add(objBean.getFileUrl());
                    }
                   mAdapter.setBanner(pics);
                } else {
                    ToastUtils.showToast(mHomeBannerInfo.getMsg());
                }

            }
        });
    }

    //把布局传给父类
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_home_hot;
    }


    //convenientBanner的点击监听
    @Override
    public void onItemClick(int position) {
        Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
//        convenientBanner.setCanLoop(true);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        // 停止自动翻页
//        convenientBanner.setCanLoop(false);
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (!mRlv_home.getSwipeRefreshLayout().isRefreshing()) {
            mRlv_home.getSwipeRefreshLayout().setRefreshing(true);
        }

        mDataCore.queryFtInfo(-1, 0, -1, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    mAdapter.setData(mQueryFtInfos);
                    mRlv_home.getRecyclerView().smoothScrollToPosition(0);
                    if (mQueryFtInfos.getTotal() == mQueryFtInfos.getRows().size()) {
                        mRlv_home.onNoMore("木有了");
                    } else {
                        mRlv_home.complete();
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
                mRlv_home.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoadMore() {
        page++;
        MyLogger.jLog().i("page = " + page);
        mDataCore.queryFtInfo(-1, 0, -1, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    if (mQueryFtInfos.getRows().size() != 0) {
                        mAdapter.addData(mQueryFtInfos.getRows());
                    }
                    //判断是否还有数据
                    if (mQueryFtInfos.getTotal() <= page * rows) {
                        mRlv_home.onNoMore("没有更多了");
                    } else {
                        mRlv_home.complete();
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
            }
        });
    }

    //点击的轮播图
    @Override
    public void onBannerClick(int position) {
        HomeBannerInfo.ObjBean objBean = mHomeBannerInfo.getObj().get(position);
    }
}