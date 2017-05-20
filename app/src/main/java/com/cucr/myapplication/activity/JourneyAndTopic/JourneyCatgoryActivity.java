package com.cucr.myapplication.activity.JourneyAndTopic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.JourneyCatgoryAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class JourneyCatgoryActivity extends Activity {

    @ViewInject(R.id.lv_journey_catgory)
    ListView lv_journey_catgory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_catgory);
        ViewUtils.inject(this);

        lv_journey_catgory.setAdapter(new JourneyCatgoryAdapter(this));
    }

    @OnClick(R.id.iv_journey_catgory_back)
    public void back(View view){
        finish();
    }
}
