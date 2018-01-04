package com.cucr.myapplication.activity.pay;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.TxRecordAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TxRecordActivity extends BaseActivity {

    //提现记录
    @ViewInject(R.id.lv_record)
    ListView lv_record;

    @Override
    protected void initChild() {
        initTitle("星币");
        View headView = View.inflate(MyApplication.getInstance(),R.layout.head_txrecord,null);
        lv_record.addHeaderView(headView);
        lv_record.setAdapter(new TxRecordAdapter());
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_star_money;
    }

    //充值
    @OnClick(R.id.tv_cz)
    public void goToPay(View view) {
        startActivity(new Intent(this, PayCenterActivity_new.class));
    }

    //提现
    @OnClick(R.id.tv_tx)
    public void goTiXian(View view) {
        startActivity(new Intent(this, XbTxActivity.class));
    }

}
