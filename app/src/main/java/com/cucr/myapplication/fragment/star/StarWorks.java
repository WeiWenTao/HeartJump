package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.MultiFeedAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.star.StarDesrc;
import com.cucr.myapplication.bean.star.StarProduction;
import com.cucr.myapplication.core.star.StarInfoCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2018/5/25.
 */

public class StarWorks extends Fragment implements RequersCallBackListener {

    @ViewInject(R.id.rlv)
    private RecyclerView rlv;

    @ViewInject(R.id.tv_time)
    private TextView tv_time;

    @ViewInject(R.id.suspension_bar)
    private LinearLayout mSuspensionBar;

    private Gson mGson;
    private View view;
    private int mCurrentPosition = 0;

    private int mSuspensionHeight;
    private List<StarProduction> allWorks;
    private MultiFeedAdapter mAdapter;
    private List<StarProduction> mSongList;
    private List<StarProduction> mMovieList;
    private List<StarProduction> mTvList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_star_works, container, false);
            ViewUtils.inject(this, view);
            init();
        }
        return view;

    }

    private void init() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getInstance());
        mAdapter = new MultiFeedAdapter();

        rlv.setLayoutManager(linearLayoutManager);
        rlv.setAdapter(mAdapter);
        rlv.setHasFixedSize(true);
        allWorks = new ArrayList<>();
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mAdapter.getItemViewType(mCurrentPosition + 1) != MultiFeedAdapter.TYPE_TIME) {
                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }
                }

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mSuspensionBar.setY(0);

                    updateSuspensionBar();
                }
            }
        });

        updateSuspensionBar();

        getInfo();
    }

    private void getInfo() {
        mGson = MyApplication.getGson();
        StarInfoCore core = new StarInfoCore();
        if (getArguments() != null) {
            int starId = getArguments().getInt("starId");
            core.queryStarDesrc(starId, this);
        }
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        StarDesrc starDesrc = mGson.fromJson(response.get(), StarDesrc.class);
        if (starDesrc.isSuccess()) {
            String tv = starDesrc.getObj().getTv();
            String song = starDesrc.getObj().getSong();
            String movie = starDesrc.getObj().getMovie();
            mSongList = sortList(song, 1);
            mMovieList = sortList(movie, 2);
            mTvList = sortList(tv, 3);
            allWorks.addAll(mSongList);
            allWorks.addAll(mMovieList);
            allWorks.addAll(mTvList);
            mAdapter.setData(allWorks);
        } else {
            ToastUtils.showToast(starDesrc.getMsg());
        }
    }

    private List<StarProduction> sortList(String json, int type) {
        List<StarProduction> list;
        if (TextUtils.isEmpty(json)) {
            list = new ArrayList<>();
        } else {
            list = mGson.fromJson(json, new TypeToken<List<StarProduction>>() {
            }.getType());
        }
        list.add(0, new StarProduction(type));
        return list;
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

    private void updateSuspensionBar() {
        if (mSongList != null && mMovieList != null && mTvList != null) {
            if (mCurrentPosition < mSongList.size()) {
                tv_time.setText("单曲");
            } else if (mCurrentPosition >= mSongList.size() && mCurrentPosition < mSongList.size() + mMovieList.size()) {
                tv_time.setText("电影");
            } else if (mCurrentPosition >= mSongList.size() + mMovieList.size()) {
                tv_time.setText("电视");
            }
        } else {
            tv_time.setText("单曲");
        }
    }
}
