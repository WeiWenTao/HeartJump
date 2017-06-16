package com.cucr.myapplication.fragment.renzheng;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/16.
 */

public class StarRZ extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ren_zheng_star, container, false);
        initView(rootView);
        return rootView;


    }

    private void initView(View rootView) {
        ImageView iv_pic_positive = (ImageView) rootView.findViewById(R.id.iv_add_pic_positive);
        iv_pic_positive.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case
        }
    }
}
