package com.cucr.myapplication.fragment.picWall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.picWall.PicWallCatgoryActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MinePicWallAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.user.PicWallCore;
import com.cucr.myapplication.fragment.LazyFragment_app;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpacesItemDecoration;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cucr on 2018/1/2.
 */

public class MyFavoritePicWallFragment extends LazyFragment_app implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener, MinePicWallAdapter.OnItemClickListener {

    private View rootView;
    private PicWallCore mCore;
    private int page;
    private int rows;
    private boolean isRefresh;
    private SwipeRecyclerView mRlv_my_pics;
    private Gson mGson;
    private MinePicWallAdapter mAdapter;
    private PicWallInfo mInfo;
    private Intent mIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_my_picwall_, container, false);
        }
        return rootView;
    }

    @Override
    protected void onFragmentFirstVisible() {
        init();
    }

    private void init() {
        page = 1;
        rows = 15;
        mInfo = new PicWallInfo();
        mCore = new PicWallCore();
        mGson = MyApplication.getGson();
        mIntent = new Intent();
        mRlv_my_pics = (SwipeRecyclerView) rootView.findViewById(R.id.rlv_my_pics);
        mRlv_my_pics.getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRlv_my_pics.getRecyclerView().addItemDecoration(new SpacesItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 2.5f)));
        mAdapter = new MinePicWallAdapter();
        mAdapter.setOnItemClickListener(this);
        mRlv_my_pics.setAdapter(mAdapter);
        mRlv_my_pics.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mRlv_my_pics.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryMyFavoritePic(page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        mRlv_my_pics.onLoadingMore();
        mCore.queryMyFavoritePic(page, rows, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_FORE) {
            //todo 删除后的我喜欢的 会返回null
            PicWallInfo picWallInfo = mGson.fromJson(response.get(), PicWallInfo.class);
            if (picWallInfo.isSuccess()) {
                if (isRefresh) {
                    mAdapter.setData(picWallInfo.getRows());
                    mInfo = picWallInfo;
                } else {
                    mAdapter.addData(picWallInfo.getRows());
                    mInfo.getRows().addAll(picWallInfo.getRows());
                }
                if (picWallInfo.getTotal() <= page * rows) {
                    mRlv_my_pics.onNoMore("没有更多了");
                } else {
                    mRlv_my_pics.complete();
                }
            } else {
                ToastUtils.showToast(picWallInfo.getErrorMsg());
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
        if (what == Constans.TYPE_FORE) {
            if (mRlv_my_pics.isRefreshing()) {
                mRlv_my_pics.getSwipeRefreshLayout().setRefreshing(false);
            }
        }
    }

    @Override
    public void clickUser(int userId) {
        mIntent.setClass(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mIntent.putExtra("userId", userId);
        startActivity(mIntent);
    }

    @Override
    public void clickItem(int position, PicWallInfo.RowsBean rowsBean, ImageView imageView) {
        mIntent.setClass(MyApplication.getInstance(), PicWallCatgoryActivity.class);
        mIntent.putExtra("data", mInfo);
        mIntent.putExtra("posotion", position);
        startActivityForResult(mIntent, 2);
    }

    @Override
    public void longClickItem(int dataId,int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2:
                    PicWallInfo Info = (PicWallInfo) data.getSerializableExtra("data");
                    mInfo.getRows().clear();
                    mInfo.getRows().addAll(Info.getRows());
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
