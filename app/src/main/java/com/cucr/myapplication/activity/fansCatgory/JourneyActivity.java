package com.cucr.myapplication.activity.fansCatgory;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.RlvPersonalJourneyAdapter;
import com.cucr.myapplication.app.MyApplication;
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

import java.util.List;

public class JourneyActivity extends BaseActivity {

    private WheelView mWheelview;
    private MultiStateView multiStateView;
    private RecyclerView mRlv_journey;
    private QueryJourneyList mCore;
    private Gson mGson;
    private List<StarJourneyList.RowsBean> mRows;
    private RlvPersonalJourneyAdapter mAdapter;
    private int starId;
    private Context mContext;
    private int page;
    private int rows;

    @Override
    protected void initChild() {
        initTitle("行程");
        initView();
        queryJourney();
        queryJourneyByTime(0);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_journey;
    }

    private void initView() {
        starId = getIntent().getIntExtra("starId", -1);
        mCore = new QueryJourneyList();
        mGson = new Gson();
        mContext = MyApplication.getInstance();
        page = 1;
        rows = 10;
        mRlv_journey = (RecyclerView) findViewById(R.id.rlv_journey);
        mWheelview = (WheelView) findViewById(R.id.wheelview);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        //设置单位（没啥用，设置属性的时候才有用 ）
        mWheelview.setAdditionCenterMark(" ");
        initWheelView();
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
                    ToastUtils.showToast(starScheduleLIst.getMsg());
                }
            }
        });

//        queryJourneyByTime(0);
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
}
