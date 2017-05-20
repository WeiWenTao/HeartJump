package com.cucr.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.ListViewMessageAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


public class MessageActivity extends Activity {

    @ViewInject(R.id.lv_message)
    ListView lv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ViewUtils.inject(this);

        initView();

    }


    private void initView() {
        lv_message.setAdapter(new ListViewMessageAdapter());
    }

    @OnClick(R.id.iv_msg_back)
    public void msgBack(View view){
        finish();
    }
}
