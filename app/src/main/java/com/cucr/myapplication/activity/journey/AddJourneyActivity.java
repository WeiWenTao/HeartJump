package com.cucr.myapplication.activity.journey;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class AddJourneyActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("添加行程");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_add_journey;
    }
}
