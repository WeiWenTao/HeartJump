package com.cucr.myapplication.activity.pay;

import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.core.xinbi.XinBiCore;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class XbTxActivity extends BaseActivity {

    @ViewInject(R.id.et_num)
    private EditText et_num;

    @ViewInject(R.id.et_xb)
    private EditText et_xb;

    private XinBiCore mXinBiCore;

    @Override
    protected void initChild() {
        mXinBiCore = new XinBiCore();
    }


    @OnClick(R.id.iv_rule)
    public void showRule(View view) {

    }

    @OnClick(R.id.ll_xb)
    public void xb(View view) {
        et_xb.setFocusable(true);
        et_xb.setFocusableInTouchMode(true);
        et_xb.requestFocus();
        CommonUtils.hideKeyBorad(MyApplication.getInstance(),et_xb,false);

    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_tx;
    }
}
