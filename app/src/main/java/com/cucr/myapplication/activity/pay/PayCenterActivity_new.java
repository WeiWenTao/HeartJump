package com.cucr.myapplication.activity.pay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.alipay.PayInfo;
import com.cucr.myapplication.alipay.PayResult;
import com.cucr.myapplication.alipay.PayResultInfo;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogPayStyle;
import com.cucr.myapplication.widget.dialog.DialogPerfirmPayResult;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

public class PayCenterActivity_new extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg1)
    RadioGroup rg1;

    @ViewInject(R.id.rg2)
    RadioGroup rg2;

    //立即充值
    @ViewInject(R.id.tv_pay_now)
    TextView tv_pay_now;

    //支付宝选择
    @ViewInject(R.id.iv_alipay_d)
    ImageView iv_alipay_d;

    //微信选择
    @ViewInject(R.id.iv_wx_d)
    ImageView iv_wx_d;

    //用户余额
    @ViewInject(R.id.tv_user_money)
    TextView tv_user_money;

    //上次勾选
    private int preId;
    //记录支付方式默认支付宝支付  1:支付宝 , 2:微信
    private int payStyle = 1;
    private Map<Integer, Integer> moneys;
    private DialogPayStyle mDailogPayStyle;

    //上一个rb
    private RadioButton mPreRB;

    //当前rb
    private RadioButton currentRB;
    private double money;
    private double finalMoney;
    private PayCenterCore payCore;
    private DialogPerfirmPayResult mPerfirmPayResulterfirmDialog;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    final String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    MyLogger.jLog().i("resultInfo" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        PayResultInfo payResultInfo = mGson.fromJson(resultInfo, PayResultInfo.class);
                        String out_trade_no = payResultInfo.getAlipay_trade_app_pay_response().getOut_trade_no();
                        payCore.queryResult(out_trade_no, new PayLisntener() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                CommonRebackMsg reBackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                                MyLogger.jLog().i("reBackMsg" + reBackMsg);
                                if (reBackMsg.isSuccess()) {
                                    mPerfirmPayResulterfirmDialog.setDialog("交易成功", false);
                                    queryMoney();
                                } else {
                                    mPerfirmPayResulterfirmDialog.setDialog("交易失败", false);
                                    ToastUtils.showToast(reBackMsg.getMsg());
                                }
                            }

                            @Override
                            public void onRequestStar() {
                                mPerfirmPayResulterfirmDialog.show();
                            }
                        });
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastUtils.showToast("支付成功");
                        //去服务器查询结果
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showToast("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    protected void initChild() {

        initTitle("充值中心");

        moneys = new HashMap<>();

        mDailogPayStyle = new DialogPayStyle(this, R.style.ShowAddressStyleTheme);
        mPerfirmPayResulterfirmDialog = new DialogPerfirmPayResult(this, R.style.ShowAddressStyleTheme);
        payCore = new PayCenterCore();
        queryMoney();

        findRG();
        initRBS();

    }


    @Override
    protected int getChildRes() {
        return R.layout.activity_pay_center_new;
    }


    private void initRBS() {
        moneys.put(R.id.rg1_rb1, 50);
        moneys.put(R.id.rg1_rb2, 100);
        moneys.put(R.id.rg1_rb3, 200);

        moneys.put(R.id.rg2_rb1, 1000);
        moneys.put(R.id.rg2_rb2, 2000);
        moneys.put(R.id.rg2_rb3, 10000);

    }

    private void findRG() {
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
    }

    //点击支付宝
    @OnClick(R.id.ll_alipay)
    public void alipay(View view) {
        payStyle = 1;
        iv_alipay_d.setImageResource(R.drawable.pc_sel);
        iv_wx_d.setImageResource(R.drawable.pc_nor);
    }

    //微信支付
    @OnClick(R.id.ll_wxpay)
    public void wxpay(View view) {
        payStyle = 2;
        iv_wx_d.setImageResource(R.drawable.pc_sel);
        iv_alipay_d.setImageResource(R.drawable.pc_nor);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //设置为可点击 // TODO: 2017/9/16  
        tv_pay_now.setEnabled(true);
        MyLogger.jLog().i("tv_pay_now.setEnabled(true);");


        RadioButton rbNow = ((RadioButton) findViewById(checkedId));
        if (!rbNow.isChecked()) {
            rbNow.setChecked(true);
        }
        if (preId != 0) {
            mPreRB = (RadioButton) findViewById(preId);
            mPreRB.setChecked(false);
        }
        preId = checkedId;
        currentRB = (RadioButton) findViewById(preId);
        money = moneys.get(checkedId);
        tv_pay_now.setText("立即充值  " + (money / 10) + "元");
    }

    @OnClick(R.id.tv_pay_now)
    public void payNow(View view) {
        if (money == 0) {
            ToastUtils.showToast("请选择充值金额哦~");
            return;
        }
        finalMoney = money / 10.0f;
        switch (payStyle) {
            case 1://支付宝
                alipay();
                MyLogger.jLog().i("支付宝");
                break;

            case 2://微信
                wxpay();
                MyLogger.jLog().i("微信");
                break;
        }
    }

    //监听软键盘的确定按钮
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(PayCenterActivity_new.this.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    public void queryMoney() {
        payCore.queryUserMoney(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (reBackMsg.isSuccess()) {
                    tv_user_money.setText(reBackMsg.getMsg());
                } else {
                    ToastUtils.showToast(reBackMsg.getMsg());
                }
            }
        });
    }

    // TODO: 2017/11/1
    //支付宝支付逻辑
    public void alipay() {
        MyLogger.jLog().i("finalMoney:" + finalMoney);
        payCore.aliPay(finalMoney, "心跳充值", new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                final PayInfo payInfo = mGson.fromJson(response.get(), PayInfo.class);
                final String orderInfo = payInfo.getMsg();
                if (payInfo.isSuccess()) {
                    //开启线程支付
                    ThreadUtils.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(PayCenterActivity_new.this);
                            MyLogger.jLog().i("123:" + orderInfo);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Log.i("msp", result.toString());

                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    });
                } else {
                    ToastUtils.showToast(PayCenterActivity_new.this, orderInfo);
                }
            }
        });
    }

    //微信支付逻辑
    public void wxpay() {

    }
}
