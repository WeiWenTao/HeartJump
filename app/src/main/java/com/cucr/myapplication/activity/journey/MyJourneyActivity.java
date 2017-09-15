package com.cucr.myapplication.activity.journey;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.core.starListAndJourney.QueryJourneyList;
import com.cucr.myapplication.core.starListAndJourney.StarJourney;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.starJourney.StarJourneyList;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.swipeRlv.DemoAdapter;
import com.cucr.myapplication.widget.swipeRlv.ItemTouchListener;
import com.cucr.myapplication.widget.swipeRlv.SwipeMenuRecyclerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class MyJourneyActivity extends BaseActivity implements ItemTouchListener {

    @ViewInject(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;

    private DemoAdapter mAdapter;
    private QueryJourneyList mCore;
    private int dataId;             //数据id
    private StarJourney mJourneyCore;
    private List<StarJourneyList.RowsBean> mRows;

    @Override
    protected void initChild() {
        ThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                queryJourney();
            }
        });

    }

    private void queryJourney() {
        mCore = new QueryJourneyList(this);
        mJourneyCore = new StarJourney(null);
        mCore.QueyrStarJourney(1, 1, 1, null, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarJourneyList starJourneys = mGson.fromJson(response.get(), StarJourneyList.class);
                if (starJourneys.isSuccess()) {
                    mRows = starJourneys.getRows();
                    initRlv(mRows);
                } else {
                    ToastUtils.showToast(MyJourneyActivity.this, starJourneys.getErrorMsg());
                }
            }
        });
    }

    private void initRlv(List<StarJourneyList.RowsBean> rows) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new DemoAdapter(rows);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemTouchListener(this);
    }


    @Override
    protected int getChildRes() {
        return R.layout.activity_my_journey;
    }

    //添加行程
    @OnClick(R.id.iv_journey_add)
    public void addJourney(View view) {
        startActivity(new Intent(this, AddJourneyActivity.class));
    }

    //侧滑删除
    @Override
    public void onClcikDelete(View v, final int position) {
        mJourneyCore.deleteJourney(mRows.get(position).getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyLogger.jLog().i("删除了" + mRows.get(position).getTitle() + " 行程，position：" + position);
                mRows.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mRows.size());
            }
        });
    }

    @Override
    public void onItemClcik(View v, int position) {
        MyLogger.jLog().i("点击了条目，position：" + position);
    }

}
