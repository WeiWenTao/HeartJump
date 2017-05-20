package com.cucr.myapplication.activity.myHomePager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.FansAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FansActivity extends Activity {

    @ViewInject(R.id.lv_fans)
    ListView lv_fans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        ViewUtils.inject(this);

        lv_fans.setAdapter(new FansAdapter());

    }

    //返回
    @OnClick(R.id.iv_my_fans_back)
    public void clickBack(View view){
        finish();
    }
}
