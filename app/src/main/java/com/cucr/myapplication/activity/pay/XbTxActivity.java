package com.cucr.myapplication.activity.pay;

import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class XbTxActivity extends BaseActivity {

    @ViewInject(R.id.et_num)
    private EditText et_num;

    @Override
    protected void initChild() {

    }


    @OnClick(R.id.iv_rule)
    public void showRule(View view){

    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_tx;
    }
}
