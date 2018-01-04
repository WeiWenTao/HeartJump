package com.cucr.myapplication.fragment.personalCenter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.FtAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.StarListForQiYeAdapter;
import com.cucr.myapplication.widget.recyclerView.LoadMoreWrapper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by cucr on 2017/11/28.
 */

public class DongtaiFragment extends Fragment {

    //明星列表
    @ViewInject(R.id.rlv_dongtai)
    private RecyclerView rlv_dongtai;

    //刷新控件
    @ViewInject(R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipe_refresh_layout;

    private StarListForQiYeAdapter mAdapter;
    private Context mContext;
    private LoadMoreWrapper wapper;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = MyApplication.getInstance();
        //view的复用
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_personal_center_dongtai, container, false);
            ViewUtils.inject(this,rootView);
            initView();
        }
        return rootView;
    }

    private void initView() {
        rlv_dongtai.setAdapter(new FtAdapter());
    }
}
