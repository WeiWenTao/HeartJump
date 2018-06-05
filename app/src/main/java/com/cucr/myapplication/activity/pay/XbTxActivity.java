package com.cucr.myapplication.activity.pay;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class XbTxActivity extends BaseActivity implements RequersCallBackListener, TextWatcher, OnCommonListener {

    @ViewInject(R.id.et_num)
    private EditText et_account;

    @ViewInject(R.id.et_xb)
    private EditText et_xb;

    @ViewInject(R.id.et_name)
    private EditText et_name;

    @ViewInject(R.id.bt_confirm)
    private Button bt_confirm;

    @ViewInject(R.id.tv_have)
    private TextView tv_have;

    private PayCenterCore mCore;
    private MyWaitDialog mDialog;
    private String str;

    @Override
    protected void initChild() {
        str = "";
        mCore = new PayCenterCore();
        mDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        et_xb.addTextChangedListener(this);
        mCore.queryUserMoney(this);
    }


    @OnClick(R.id.iv_rule)
    public void showRule(View view) {
        startActivity(new Intent(MyApplication.getInstance(), TxRuleActivity.class));
    }

    @OnClick(R.id.ll_xb)
    public void xb(View view) {
        et_xb.setFocusable(true);
        et_xb.setFocusableInTouchMode(true);
        et_xb.requestFocus();
        CommonUtils.hideKeyBorad(MyApplication.getInstance(), et_xb, false);
    }

    //全部提现
    @OnClick(R.id.all)
    public void allTx(View view) {
        et_xb.setText(str);
    }

    @OnClick(R.id.bt_confirm)
    public void click(View view) {
        String account = et_account.getText().toString().trim();
        String amount = et_xb.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showToast("还没有填写账号哟");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("还没有填写姓名哦哟");
            return;
        }
        if (TextUtils.isEmpty(amount) || Integer.parseInt(amount) <= 0) {
            ToastUtils.showToast("还没有填写金额哟");
            return;
        }
        mCore.TxRequest(account, name, amount + "00", this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_tx;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
        if (msg.isSuccess()) {
            Toast.makeText(MyApplication.getInstance(), "已提交兑换申请,将在5个工作日内到账!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            ToastUtils.showToast(msg.getMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        mDialog.show();
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        mDialog.dismiss();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s) && Integer.parseInt(s.toString()) > 0) {
            double i = Double.parseDouble(s.toString() + "0");
            bt_confirm.setText("兑换成" + CommonUtils.getTxMoney(i) + "元,确认提现");
        } else {
            bt_confirm.setText("请输入金额");
        }
    }

    @Override
    public void onRequestSuccess(Response<String> response) {
        ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
        if (reBackMsg.isSuccess()) {
            Double money = Double.parseDouble(reBackMsg.getMsg());
            if (money != 0) {
                money = money / 100;
                str = (money + "").split("\\.")[0];
            }
            tv_have.setText("可提现数量" + str + "00");
        } else {
            ToastUtils.showToast(reBackMsg.getMsg());
        }
    }

    @OnClick(R.id.tv_zero)
    public void clickZero(View view) {
        et_xb.setSelection(et_xb.getText().length());
    }
}