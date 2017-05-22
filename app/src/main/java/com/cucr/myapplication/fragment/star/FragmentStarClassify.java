package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.GvAdapter.StarClassifyAdapter;
import com.lantouzi.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarClassify extends Fragment {

    private GridView mGv_star_classify;
    private StarClassifyAdapter mGvAdapter;
    List<String> items;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
       if (view == null){
           view = inflater.inflate(R.layout.fragment_star_classify, container, false);
       }
        mGv_star_classify = (GridView) view.findViewById(R.id.gv_star_classify);
        WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);
        initWheelView(wheelview);
        initGV(inflater.getContext());
        return view;
    }

    private void initWheelView(WheelView wheelview) {
        initItems();
        wheelview.setItems(items);
        wheelview.selectIndex(2);
        wheelview.setAdditionCenterMark("  ");
        wheelview.setAttrs(true);
    }

    private void initItems() {
        if (items == null){
            items = new ArrayList<>();
        }

        if (items.size() == 0){
            items.add("艺术");
            items.add("音乐");
            items.add("演员");
            items.add("主持");
            items.add("时尚");
            items.add("境外");
        }
    }

    private void initGV(Context context) {
        mGvAdapter = new StarClassifyAdapter(context);
        mGv_star_classify.setAdapter(mGvAdapter);
        mGv_star_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGvAdapter.setCheck(position);
            }
        });
    }
}
