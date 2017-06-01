package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlAdapter.StarClassifyRlAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.lantouzi.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarClassify extends Fragment {

    private RecyclerView rl_star_classify;
    private StarClassifyRlAdapter mRlAdapter;
    List<String> items;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
       if (view == null){
           view = inflater.inflate(R.layout.fragment_star_classify, container, false);
       }
        rl_star_classify = (RecyclerView) view.findViewById(R.id.rl_star_classify);
        WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);
        initWheelView(wheelview);
        initRl(inflater.getContext());
        return view;
    }

    private void initWheelView(WheelView wheelview) {
        initItems();
        wheelview.setItems(items);
        wheelview.selectIndex(2);
        //设置单位 避免bug
        wheelview.setAdditionCenterMark("  ");
        wheelview.setAttrs(true);

        //设置监听
        wheelview.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(WheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(WheelView wheelView, int position) {
                ToastUtils.showToast(wheelView.getContext(),position+"");
            }

        });
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

    private void initRl(Context context) {
        mRlAdapter = new StarClassifyRlAdapter(context);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        rl_star_classify.setLayoutManager(layoutManager);
        int dp_10 = CommonUtils.px2dip(context, 10.0f);
//        rl_star_classify.addItemDecoration(new SpaceItemDecoration(dp_10,dp_10));
        rl_star_classify.setAdapter(mRlAdapter);

    }
}
