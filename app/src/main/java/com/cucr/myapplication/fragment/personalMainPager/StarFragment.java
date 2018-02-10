package com.cucr.myapplication.fragment.personalMainPager;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.RLVStarAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 911 on 2017/7/19.
 */

@SuppressLint("ValidFragment")
public class StarFragment extends Fragment implements SwipeRecyclerView.OnLoadListener {

    private View mView;
    private int userId;
    private RLVStarAdapter mAdapter;
    private QueryFocus mCore;
    private int page;
    private int rows;
    private SwipeRecyclerView mRlv_starlist;
    private Intent mIntent;

    public StarFragment(int userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_star, null);
            initView();
        }
        return mView;
    }

    private void initView() {
        page = 1;
        rows = 5;
        mCore = new QueryFocus();
        mRlv_starlist = (SwipeRecyclerView) mView.findViewById(R.id.rlv_his_starlist);
        mRlv_starlist.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mRlv_starlist.setOnLoadListener(this);
        mAdapter = new RLVStarAdapter();
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        mRlv_starlist.getRecyclerView().addItemDecoration(decor);
        mRlv_starlist.setAdapter(mAdapter);
        mRlv_starlist.onLoadingMore();
        //跳转企业用户看的明星主页
        mIntent = new Intent(MyApplication.getInstance(), StarPagerActivity.class);
        mAdapter.setOnItemClick(new RLVStarAdapter.OnItemClick() {
            @Override
            public void onItemClick(int starId) {
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mIntent.putExtra("starId", starId);
                startActivity(mIntent);
                //发送明星id到明星主页
                EventBus.getDefault().postSticky(new EventFIrstStarId(starId));
            }
        });
        onRefresh();
    }


    @Override
    public void onRefresh() {
        if (!mRlv_starlist.getSwipeRefreshLayout().isRefreshing()) {
            mRlv_starlist.getSwipeRefreshLayout().setRefreshing(true);
        }

        page = 1;
        //查询ta的关注明星
        mCore.queryMyFocusStars(userId, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FocusInfo starInfo = MyApplication.getGson().fromJson(response.get(), FocusInfo.class);
                if (starInfo.isSuccess()) {
                    mAdapter.setData(starInfo.getRows());
                    if (starInfo.getTotal() == starInfo.getRows().size()) {
                        mRlv_starlist.onNoMore("木有了");
                    }
                } else {
                    ToastUtils.showToast(response.get());
                }
                mRlv_starlist.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoadMore() {

        page++;
        mCore.queryMyFocusStars(userId, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FocusInfo starInfo = MyApplication.getGson().fromJson(response.get(), FocusInfo.class);
                if (starInfo.isSuccess()) {
                    mAdapter.addData(starInfo.getRows());
                    //判断是否还有数据
                    if (starInfo.getTotal() <= page * rows) {
                        mRlv_starlist.onNoMore("没有更多了");
                    } else {
                        mRlv_starlist.complete();
                    }
                } else {
                    ToastUtils.showToast(starInfo.getErrorMsg());
                }
            }
        });
    }
}
