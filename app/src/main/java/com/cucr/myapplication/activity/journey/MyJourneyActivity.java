package com.cucr.myapplication.activity.journey;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.widget.swipeRlv.DemoAdapter;
import com.cucr.myapplication.widget.swipeRlv.SwipeMenuRecyclerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyJourneyActivity extends BaseActivity {

    @ViewInject(R.id.recyclerView)
    SwipeMenuRecyclerView recyclerView;

    @Override
    protected void initChild() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        DemoAdapter adapter = new DemoAdapter();
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected int getChildRes() {
        return R.layout.activity_my_journey;
    }

    //添加行程
    @OnClick(R.id.iv_journey_add)
    public void addJourney(View view) {
        startActivity(new Intent(this,AddJourneyActivity.class));
    }


}
