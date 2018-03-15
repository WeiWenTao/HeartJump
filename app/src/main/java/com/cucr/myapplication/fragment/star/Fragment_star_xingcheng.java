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
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventStarId;
import com.cucr.myapplication.bean.starJourney.StarJourneyList;
import com.cucr.myapplication.bean.starJourney.StarScheduleLIst;
import com.cucr.myapplication.core.starListAndJourney.QueryJourneyList;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lantouzi.wheelview.WheelView;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_xingcheng extends Fragment {

    private View view;
    private WheelView mWheelview;
    private MultiStateView multiStateView;
    private RecyclerView mRlv_journey;
    private RecyclerView rlv_test;
    private Context mContext;
    private QueryJourneyList mCore;
    private Gson mGson;
    private List<StarJourneyList.RowsBean> mRows;
    private RlvPersonalJourneyAdapter mAdapter;
    private int starId;
    private int page;
    private int rows;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyLogger.jLog().i("onCreateView");
        mContext = MyApplication.getInstance();
        mCore = new QueryJourneyList();
        mGson = new Gson();
        page = 1;
        rows = 10;
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_personal_pager_journey, container, false);
            initView();
            queryJourney();
            queryJourneyByTime(0);
        }

        return view;
    }

    //第一个明星
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(EventFIrstStarId event) {
        MyLogger.jLog().i("行程_EventFIrstStarId");
        starId = event.getFirstId();
    }

    //明星id
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventStarId event) {
        starId = event.getStarId();
        if (mCore == null) {
            mCore = new QueryJourneyList();
        }
        queryJourney();
        queryJourneyByTime(0);
    }


    private void queryJourney() {
        mCore.queryJourneySchedule(starId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarScheduleLIst starScheduleLIst = mGson.fromJson(response.get(), StarScheduleLIst.class);
                if (starScheduleLIst.isSuccess()) {
                    List<String> obj = starScheduleLIst.getObj();
                    mWheelview.setItems(obj);
                    if (obj != null && obj.size() > 0) {
                        queryJourneyByTime(0);
                    } else {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        return;
                    }
                } else {
                    ToastUtils.showToast(mContext, starScheduleLIst.getMsg());
                }
            }
        });

//        queryJourneyByTime(0);
    }

    private void initView() {
        mRlv_journey = (RecyclerView) view.findViewById(R.id.rlv_journey);
        rlv_test = (RecyclerView) view.findViewById(R.id.rlv_test);
        mWheelview = (WheelView) view.findViewById(R.id.wheelview);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
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

        rlv_test.setLayoutManager(new LinearLayoutManager(mContext));
        rlv_test.setAdapter(mAdapter);


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

    public void queryJourneyByTime(int position) {
        if (mWheelview.getItems() == null || mWheelview.getItems().size() == 0) {
            return;
        }
        String s = mWheelview.getItems().get(position);
        MyLogger.jLog().i("journeyStarid = " + starId);
        mCore.QueyrStarJourney(rows, page, starId, s, new OnCommonListener() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
