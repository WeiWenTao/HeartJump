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
import com.cucr.myapplication.adapter.RlVAdapter.HytAdapter;
import com.cucr.myapplication.app.MyApplication;

/**
 * Created by cucrx on 2018/1/16.
 */

public class Fragment_hyt extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hyt_hyt, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        RecyclerView rlv_hyt = (RecyclerView) rootView.findViewById(R.id.rlv_hyt);
        rlv_hyt.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_hyt.setAdapter(new HytAdapter());
    }
}
