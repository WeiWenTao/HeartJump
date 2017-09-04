package com.cucr.myapplication.fragment.star;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.RlvPersonalJourneyAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lantouzi.wheelview.WheelView;

import java.util.List;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_xingcheng extends android.app.Fragment {

    private View view;
    private WheelView mWheelview;
    private RecyclerView mRlv_journey;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = container.getContext();
        //view的复用
        if (view == null){
            view = inflater.inflate(R.layout.item_personal_pager_journey, container, false);
            initView();
        }

        return view;
    }

    private void initView() {
        mRlv_journey = (RecyclerView) view.findViewById(R.id.rlv_journey);
        mWheelview = (WheelView) view.findViewById(R.id.wheelview);
        //设置单位（没啥用，设置属性的时候才有用 ）
        mWheelview.setAdditionCenterMark(" ");
        initWheelView();
    }

    //初始化日期滚轮控件 和 recyclerView
    private void initWheelView() {
        List<String> dateList = CommonUtils.getDateList();
        mWheelview.setItems(dateList);
        mWheelview.selectIndex(4);
        mRlv_journey.setLayoutManager(new LinearLayoutManager(mContext));
        mRlv_journey.setAdapter(new RlvPersonalJourneyAdapter(mContext));
//        mRlv_journey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mContext.startActivity(new Intent(mContext, JourneyCatgoryActivity.class));
//            }
//        });
    }
}
