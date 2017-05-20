package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingAboveUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_above_us);
        ViewUtils.inject(this);

    }

    @OnClick(R.id.iv_above_us_back)
    public void back(View view){
        finish();
    }
}
