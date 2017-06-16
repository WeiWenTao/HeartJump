package com.cucr.myapplication.fragment.renzheng;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/16.
 */

public class AgentRZ extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ren_zheng_agent, container, false);
        return rootView;
    }
}
