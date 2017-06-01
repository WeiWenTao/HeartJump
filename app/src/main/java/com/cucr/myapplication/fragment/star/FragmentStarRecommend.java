package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.GvAdapter.StarRecommendAdapter;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarRecommend extends Fragment {

    private GridView gv_star_recommend;
    private StarRecommendAdapter mGvAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null){
            view = inflater.inflate(R.layout.fragment_star_recommend, container, false);
        }
        gv_star_recommend = (GridView) view.findViewById(R.id.gv_star_recommend);
        initGV(inflater.getContext());

        return view;
    }

    private void initGV(Context context) {
        mGvAdapter = new StarRecommendAdapter(context);
        gv_star_recommend.setAdapter(mGvAdapter);
        gv_star_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGvAdapter.setCheck(position);
            }
        });
    }
}
