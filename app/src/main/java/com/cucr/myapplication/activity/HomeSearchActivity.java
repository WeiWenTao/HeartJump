package com.cucr.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HomeSearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ViewUtils.inject(this);

    }

    @OnClick(R.id.iv_search_back)
    public void clickBack(View view){
        finish();
    }
}
