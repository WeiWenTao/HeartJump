package com.cucr.myapplication.activity.pay;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.TxRecordAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.user.XbRecord;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class TxRecordActivity extends BaseActivity implements OnCommonListener {

    //提现记录
    @ViewInject(R.id.lv_record)
    private ListView lv_record;

    //星币余额
    @ViewInject(R.id.tv_value)
    private TextView tv_value;

    //星币余额
    @ViewInject(R.id.tv_has)
    private TextView tv_has;

    @Override
    protected void initChild() {
        initTitle("星币");
        PayCenterCore core = new PayCenterCore();
        core.queryUserMoney(this);
        View headView = View.inflate(MyApplication.getInstance(), R.layout.head_txrecord, null);
        ViewUtils.inject(this, headView);
        lv_record.addHeaderView(headView);
        final TxRecordAdapter adapter = new TxRecordAdapter();
        lv_record.setAdapter(adapter);
        //5表示提现
        core.queryTxRecoed(5, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                XbRecord xbRecord = mGson.fromJson(response.get(), XbRecord.class);
                if (xbRecord.isSuccess()) {
                    if (xbRecord.getTotal() == 0) {
                        tv_has.setText("暂无记录");
                    }
                    adapter.setDate(xbRecord.getRows());
                } else {
                    ToastUtils.showToast(xbRecord.getErrorMsg());
                }
            }
        });
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

    @Override
    public void onRequestSuccess(Response<String> response) {
        ReBackMsg reBackMsg = new Gson().fromJson(response.get(), ReBackMsg.class);
        if (reBackMsg.isSuccess()) {
            tv_value.setText(reBackMsg.getMsg());
        } else {
            ToastUtils.showToast(reBackMsg.getMsg());
        }

    }
}
