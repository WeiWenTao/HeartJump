package com.cucr.myapplication.fragment.star;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.RlvPersonalJourneyAdapter;
import com.cucr.myapplication.core.starListAndJourney.QueryJourneyList;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.starJourney.StarJourneyList;
import com.cucr.myapplication.model.starJourney.StarScheduleLIst;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lantouzi.wheelview.WheelView;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_xingcheng extends Fragment {

    private View view;
    private WheelView mWheelview;
    private RecyclerView mRlv_journey;
    private Context mContext;
    private QueryJourneyList mCore;
    private Gson mGson;
    private List<StarJourneyList.RowsBean> mRows;
    private RlvPersonalJourneyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyLogger.jLog().i("onCreateView");
        mContext = container.getContext();
        mGson = new Gson();
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_personal_pager_journey, container, false);
            queryJourney();
            initView();
        }

        return view;
    }


    private void queryJourney() {
        mCore = new QueryJourneyList(mContext);
        // TODO: 2017/9/6 starId eventBus获取
        mCore.queryJourneySchedule(1, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarScheduleLIst starScheduleLIst = mGson.fromJson(response.get(), StarScheduleLIst.class);
                if (starScheduleLIst.isSuccess()) {
                    List<String> obj = starScheduleLIst.getObj();
                    mWheelview.setItems(obj);
                    if (obj != null && obj.size()>0){
                        queryJourneyByTime(0);
                    }
                    MyLogger.jLog().i("size" + starScheduleLIst.getObj().size());
                } else {
                    ToastUtils.showToast(mContext, starScheduleLIst.getMsg());
                }
            }
        });

//        queryJourneyByTime(0);
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
//        List<String> dateList = CommonUtils.getDateList();
//        mWheelview.setItems(dateList);
//        mWheelview.selectIndex(4);
        mRlv_journey.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RlvPersonalJourneyAdapter(mContext, mRows);
        mRlv_journey.setAdapter(mAdapter);

        mWheelview.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(WheelView wheelView, int position) {

            }

            @Override
            public void onWheelItemSelected(WheelView wheelView, int position) {
                String s = wheelView.getItems().get(position);
                MyLogger.jLog().i("aaa:" + s);
                queryJourneyByTime(position);
            }
        });
//        mRlv_journey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mContext.startActivity(new Intent(mContext, JourneyCatgoryActivity.class));
//            }
//        });
    }

    public void queryJourneyByTime(int position){
        String s = mWheelview.getItems().get(position);
        mCore.QueyrStarJourney(100, 1, 5, s, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarJourneyList starJourneys = mGson.fromJson(response.get(), StarJourneyList.class);
                if (starJourneys.isSuccess()) {
                    mRows = starJourneys.getRows();
                    MyLogger.jLog().i("mRows:" + mRows.toString());
                    mAdapter.setData(mRows);
                } else {
                    ToastUtils.showToast(mContext, starJourneys.getErrorMsg());
                }
            }
        });
    }
}
