package com.cucr.myapplication.fragment.hyt;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;

/**
 * Created by cucrx on 2018/1/16.
 */

public class Fragment_yyhd extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hyt_yyhd, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        RecyclerView rlv_yyhd = (RecyclerView) rootView.findViewById(R.id.rlv_yyhd);
        rlv_yyhd.addItemDecoration(new SpaceItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 10)));
        rlv_yyhd.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_yyhd.setAdapter(new YyhdAdapter());
    }
}
