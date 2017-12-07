package com.cucr.myapplication.fragment.personalMainPager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarPagerForFans;
import com.cucr.myapplication.adapter.RlVAdapter.RLVStarAdapter;
import com.cucr.myapplication.core.starListAndJourney.QueryMyFocusStars;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.eventBus.EventFIrstStarId;
import com.cucr.myapplication.model.starList.MyFocusStarInfo;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 911 on 2017/7/19.
 */

public class StarFragment extends Fragment implements SwipeRecyclerView.OnLoadListener {

    private View mView;
    private int userId;
    private RLVStarAdapter mAdapter;
    private QueryMyFocusStars mCore;
    private int page;
    private int rows;
    private SwipeRecyclerView mRlv_starlist;

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
        mCore = new QueryMyFocusStars();
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
        mAdapter.setOnItemClick(new RLVStarAdapter.OnItemClick() {
            @Override
            public void onItemClick(int starId) {
                Intent intent = new Intent(MyApplication.getInstance(), StarPagerForFans.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("starId", starId);
                startActivity(intent);
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
        mCore.queryMyFocuses(userId, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyFocusStarInfo starInfo = MyApplication.getGson().fromJson(response.get(), MyFocusStarInfo.class);
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
        mCore.queryMyFocuses(userId, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyFocusStarInfo starInfo = MyApplication.getGson().fromJson(response.get(), MyFocusStarInfo.class);
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
