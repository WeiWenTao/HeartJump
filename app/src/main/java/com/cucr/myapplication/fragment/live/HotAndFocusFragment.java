package com.cucr.myapplication.fragment.live;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlAdapter.LiveFoucsAdapter;

/**
 * Created by 911 on 2017/5/2.
 */

public class HotAndFocusFragment extends Fragment {

    private RecyclerView rlv_live_my_focus;

    public HotAndFocusFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_twitter, container, false);
        rlv_live_my_focus = (RecyclerView) view.findViewById(R.id.rlv_live_my_focus);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlv_live_my_focus.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rlv_live_my_focus.setAdapter(new LiveFoucsAdapter());


    }

}
