package com.cucr.myapplication.activity.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.cucr.myapplication.alipay.WxPayInfo;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventIsSuccess;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.Pay.PayLisntener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogPayStyle;
import com.cucr.myapplication.widget.dialog.DialogPerfirmPayResult;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

public class YyhdPayActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, DialogPayStyle.OnClickPay, DialogPerfirmPayResult.OnClickYes {

    @ViewInject(R.id.rg1)
    private RadioGroup rg1;

    @ViewInject(R.id.rg2)
    private RadioGroup rg2;

    //立即充值
    @ViewInject(R.id.tv_pay_now)
    private TextView tv_pay_now;

    //其他数量
    @ViewInject(R.id.et_other)
    private EditText et_other;

    //单位 星币
    @ViewInject(R.id.tv_other_yuan)
    private TextView tv_other_yuan;

    //支付宝支付红点
    @ViewInject(R.id.iv_alipay_d)
    private ImageView iv_alipay_d;

    //微信支付红点
    @ViewInject(R.id.iv_wx_d)
    private ImageView iv_wx_d;


    private Map<Integer, Double> moneys;
    private DialogPayStyle mDailogPayStyle;

    //上一个rb
    private RadioButton mPreRB;
    private String orderNo;

    //当前rb
    private RadioButton currentRB;
    private double money;
    private PayCenterCore payCore;
    private DialogPerfirmPayResult mPerfirmPayResulterfirmDialog;
    private boolean isWXpay;
    private MyWaitDialog mWaitDialog;


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
                        getPayResult(out_trade_no);
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
    private int mActiveId;
    private double mAmount;

    private void getPayResult(String orderNo) {
        payCore.queryResult(orderNo, new PayLisntener() {
            @Override
            public void onSuccess(Response<String> response) {
                CommonRebackMsg reBackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                MyLogger.jLog().i("reBackMsg" + reBackMsg);
//                if (reBackMsg.isSuccess()) {
                    mPerfirmPayResulterfirmDialog.setDialog("交易成功", false);
//                } else {
//                    mAmount = 0.0;
//                    mPerfirmPayResulterfirmDialog.setDialog("交易失败", false);
//                    ToastUtils.showToast(reBackMsg.getMsg());
//                }
            }

            @Override
            public void onRequestStar() {
                mPerfirmPayResulterfirmDialog.show();
            }
        });
    }

    private IWXAPI mIwxapi;

    @Override
    protected void initChild() {
        EventBus.getDefault().register(this);
        initTitle("充值中心");
        mActiveId = getIntent().getIntExtra("activeId", -1);
        moneys = new HashMap<>();
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mDailogPayStyle = new DialogPayStyle(this, R.style.ShowAddressStyleTheme);
        mPerfirmPayResulterfirmDialog = new DialogPerfirmPayResult(this, R.style.MyWaitDialog);
        mPerfirmPayResulterfirmDialog.setOnClickYes(this);
        mDailogPayStyle.setOnClickPay(this);
        payCore = new PayCenterCore();

        initET();
        findRG();
        initRBS();

    }

    private void initET() {
        et_other.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //清除当前RadioButton的勾选状态
                if (currentRB != null) {

                    //清除当前RadioButton的勾选状态
                    currentRB.setChecked(false);

                    //preId清零
                    preId = 0;
                }
                tv_pay_now.setText("立即充值");

                if (et_other.hasFocus()) {
                    CommonUtils.hideKeyBorad(v.getContext(), v, false);
                    tv_other_yuan.setVisibility(View.VISIBLE);
                    et_other.setHint("      ");
                }

                //et无法响应第一次点击事件  获取焦点时处理事件
                if (TextUtils.isEmpty(et_other.getText())) {
                    tv_pay_now.setText("立即充值");
                    tv_pay_now.setEnabled(false);
                    MyLogger.jLog().i("tv_pay_now.setEnabled(false);");
                }

            }
        });

        et_other.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEtOther();

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s)) {
                    tv_pay_now.setText("立即充值  " + (Integer.parseInt(s.toString())) + "元");
                    money = (Integer.parseInt(s.toString()));
                } else {
                    tv_pay_now.setText("立即充值");
                    tv_pay_now.setEnabled(false);
                    MyLogger.jLog().i("tv_pay_now.setEnabled(false);");

                    if ((rg1.getCheckedRadioButtonId() != -1 || rg2.getCheckedRadioButtonId() != -1)) {
                        tv_pay_now.setEnabled(true);
                        MyLogger.jLog().i("tv_pay_now.setEnabled(true);");
                    }

                }
            }
        });

        //不知为何 edittext无法响应第一次点击事件
        et_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEtOther();
                et_other.setCursorVisible(true);
                CommonUtils.hideKeyBorad(v.getContext(), v, false);

                if (currentRB != null) {

                    //清除当前RadioButton的勾选状态
                    currentRB.setChecked(false);

                    //preId清零
                    preId = 0;
                }

                tv_other_yuan.setVisibility(View.VISIBLE);
                et_other.setHint("      ");
                CommonUtils.hideKeyBorad(v.getContext(), v, false);

                if (TextUtils.isEmpty(et_other.getText())) {
                    tv_pay_now.setText("立即充值");
                    tv_pay_now.setEnabled(false);
                    MyLogger.jLog().i("tv_pay_now.setEnabled(false);");
                }

            }
        });
    }

    //根据用户操作修改 “ 立即充值 ” 按钮状态
    private void setEtOther() {
        String s = et_other.getText().toString();
        if (!TextUtils.isEmpty(s.trim()) && Integer.parseInt(s) > 0) {
            tv_pay_now.setEnabled(true);
            MyLogger.jLog().i("setEnabled(true);");
        } else {
            tv_pay_now.setEnabled(false);
            MyLogger.jLog().i("setEnabled(false);");
            if (rg1.getCheckedRadioButtonId() != -1 || rg2.getCheckedRadioButtonId() != -1) {
                tv_pay_now.setEnabled(true);
                MyLogger.jLog().i("setEnabled(true);");
            }
        }
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_yyhd_paycenter;
    }


    private void initRBS() {
        moneys.put(R.id.rg1_rb1, 1.0);
        moneys.put(R.id.rg1_rb2, 5.20);
        moneys.put(R.id.rg1_rb3, 13.14);
        moneys.put(R.id.rg1_rb4, 99.0);

        moneys.put(R.id.rg2_rb1, 520.0);
        moneys.put(R.id.rg2_rb2, 666.0);
        moneys.put(R.id.rg2_rb3, 888.0);
        moneys.put(R.id.rg2_rb4, 1314.0);

    }

    private void findRG() {
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
    }

    private int preId;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //设置为可点击 // TODO: 2017/9/16  
        tv_pay_now.setEnabled(true);
        MyLogger.jLog().i("tv_pay_now.setEnabled(true);");

        //隐藏软键盘
        CommonUtils.hideKeyBorad(this, et_other, true);
        tv_other_yuan.setVisibility(View.GONE);
        et_other.setText("");
        et_other.clearComposingText();
        et_other.setHint("其他金额");
        et_other.setCursorVisible(false);


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
        tv_pay_now.setText("立即充值  " + money + "元");
    }

    @OnClick(R.id.ll_other)
    public void click(View view) {
        if (currentRB != null) {

            //清除当前RadioButton的勾选状态
            currentRB.setChecked(false);

            //preId清零
            preId = 0;
        }
    }

    //监听软键盘的确定按钮
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(YyhdPayActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    //支付宝支付

    public void OnCLickAliPay() {
        mAmount = money;
        payCore.aliPay(money, "心跳充值", Constans.TYPE_ONE, mActiveId, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                final PayInfo payInfo = mGson.fromJson(response.get(), PayInfo.class);
                final String orderInfo = payInfo.getMsg();

                if (payInfo.isSuccess()) {
                    //开启线程支付
                    ThreadUtils.getInstance().execute(new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(YyhdPayActivity.this);
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
                    ToastUtils.showToast(orderInfo);
                }
            }

            @Override
            public void onRequestStar(int what) {

            }

            @Override
            public void onRequestError(int what, Response<String> response) {

            }

            @Override
            public void onRequestFinish(int what) {
                mWaitDialog.dismiss();
            }
        });
    }

    //微信支付
    public void OnClickWxPay() {
        if (!UMShareAPI.get(MyApplication.getInstance()).isInstall(this, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.showToast("请先装微信客户端");
            return;
        }
        //初始化微信api
        mIwxapi = WXAPIFactory.createWXAPI(MyApplication.getInstance(), "wxbe72c161183cf70da");
        mIwxapi.registerApp("wxbe72c16183cf70da"); //注册appid  appid可以在开发平台获取
        mAmount = money;
        payCore.wxPay((int) (money * 100), "微信充值", Constans.TYPE_ONE, mActiveId,
                new RequersCallBackListener() {
                    @Override
                    public void onRequestSuccess(int what, Response<String> response) {
                        final WxPayInfo payInfo = mGson.fromJson(response.get(), WxPayInfo.class);
                        MyLogger.jLog().i("payInfo:" + payInfo);
                        orderNo = payInfo.getObj().getMsg().getOrderNo();
                        if (payInfo.isSuccess()) {
                            //开启线程支付
                            ThreadUtils.getInstance().execute(new Runnable() {
                                @Override
                                public void run() {
                                    PayReq request = new PayReq(); //调起微信APP的对象
                                    //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                                    request.appId = payInfo.getObj().getMsg().getAppid();
                                    request.partnerId = payInfo.getObj().getMsg().getPartnerid();
                                    request.prepayId = payInfo.getObj().getMsg().getPrepayid();
                                    request.packageValue = "Sign=WXPay";
                                    request.nonceStr = payInfo.getObj().getMsg().getNoncestr();
                                    request.timeStamp = payInfo.getObj().getMsg().getTimestamp() + "";
                                    request.sign = payInfo.getObj().getMsg().getSign();
                                    mIwxapi.sendReq(request);//发送调起微信的请求
                                }
                            });
                        } else {
                            ToastUtils.showToast(payInfo.getMsg());
                        }
                    }

                    @Override
                    public void onRequestStar(int what) {

                    }

                    @Override
                    public void onRequestError(int what, Response<String> response) {

                    }

                    @Override
                    public void onRequestFinish(int what) {
                        mWaitDialog.dismiss();
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResult(EventIsSuccess event) {
        getPayResult(orderNo);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //点击充值
    @OnClick(R.id.tv_pay_now)
    public void goToPay(View view) {
        mWaitDialog.show();
        if (isWXpay) {
            OnClickWxPay();
        } else {
            OnCLickAliPay();
        }
    }

    //点击支付宝
    @OnClick(R.id.ll_alipay)
    public void alipay_way(View view) {
        isWXpay = false;
        iv_alipay_d.setImageResource(R.drawable.pc_sel);
        iv_wx_d.setImageResource(R.drawable.pc_nor);
    }

    //点击微信支付
    @OnClick(R.id.ll_wxpay)
    public void wxpay_way(View view) {
        isWXpay = true;
        iv_wx_d.setImageResource(R.drawable.pc_sel);
        iv_alipay_d.setImageResource(R.drawable.pc_nor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
    }

    @Override
    public void clickYes() {
        mPerfirmPayResulterfirmDialog.dismiss();
        Intent intent = getIntent();
        intent.putExtra("result", mAmount);
        setResult(999, intent);
        finish();
    }
}
