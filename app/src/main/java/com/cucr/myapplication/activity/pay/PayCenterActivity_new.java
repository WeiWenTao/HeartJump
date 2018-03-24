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
import com.cucr.myapplication.alipay.WxPayInfo;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.CommentEvent;
import com.cucr.myapplication.bean.eventBus.EventIsSuccess;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ToastUtils;
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

public class PayCenterActivity_new extends BaseActivity implements RadioGroup.OnCheckedChangeListener, DialogPerfirmPayResult.OnClickYes {

    @ViewInject(R.id.rg1)
    private RadioGroup rg1;

    @ViewInject(R.id.rg2)
    private RadioGroup rg2;

    //立即充值
    @ViewInject(R.id.tv_pay_now)
    private TextView tv_pay_now;

    //支付宝选择
    @ViewInject(R.id.iv_alipay_d)
    private ImageView iv_alipay_d;

    //微信选择
    @ViewInject(R.id.iv_wx_d)
    private ImageView iv_wx_d;

    //用户余额
    @ViewInject(R.id.tv_user_money)
    private TextView tv_user_money;

    //用户账号
    @ViewInject(R.id.tv_account)
    private TextView tv_account;

    //上次勾选
    private int preId;
    //记录支付方式默认支付宝支付  1:支付宝 , 2:微信
    private int payStyle = 1;
    private Map<Integer, Integer> moneys;
    private MyWaitDialog mWaitDialog;

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
                                // TODO: 2018/3/23
//--------------------------------------------------------------------------------------------------------
//                                if (reBackMsg.isSuccess()) {
                                //暂时以支付宝后台为准   以后再调
                                mPerfirmPayResulterfirmDialog.setDialog("交易成功", false);
//                                } else {
//                                    mPerfirmPayResulterfirmDialog.setDialog("交易失败", false);
//                                    ToastUtils.showToast(reBackMsg.getMsg());
//                                }
                            }

                            @Override
                            public void onRequestStar() {
                                mPerfirmPayResulterfirmDialog.show();
                            }
                        });
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastUtils.showToast("支付成功");
                        //去服务器查询结果
//--------------------------------------------------------------------------------------------------------
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
        EventBus.getDefault().register(this);
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mPerfirmPayResulterfirmDialog = new DialogPerfirmPayResult(this, R.style.MyWaitDialog);
        payCore = new PayCenterCore();
        queryMoney();
        mPerfirmPayResulterfirmDialog.setOnClickYes(this);
        tv_account.setText(SpUtil.getParam(SpConstant.USER_NAEM, "") + "");
        findRG();
        initRBS();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        mWaitDialog.show();
        switch (payStyle) {
            case 1://支付宝
                alipay();
                break;

            case 2://微信
                wxPay();
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
        payCore.aliPay(finalMoney, "心跳充值", Constans.TYPE_ZERO, -1, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
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

    private IWXAPI mIwxapi;
    private String orderNo;

    //微信支付
    public void wxPay() {
        if (!UMShareAPI.get(MyApplication.getInstance()).isInstall(this, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.showToast("请先装微信客户端");
            return;
        }
        //初始化微信api
        mIwxapi = WXAPIFactory.createWXAPI(MyApplication.getInstance(), "wxbe72c161183cf70da");
        mIwxapi.registerApp("wxbe72c16183cf70da"); //注册appid  appid可以在开发平台获取

        payCore.wxPay((int) (money * 10), "微信充值", 0, -1, new RequersCallBackListener() {
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

    //--------------------------------------------------------------------------------------------------------
    //微信的回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResult(EventIsSuccess event) {
        getPayResult(orderNo);
    }

    private void getPayResult(String orderNo) {
        payCore.queryResult(orderNo, new PayLisntener() {
            @Override
            public void onSuccess(Response<String> response) {
                CommonRebackMsg reBackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
//                if (reBackMsg.isSuccess()) {
                mPerfirmPayResulterfirmDialog.setDialog("交易成功", false);
                EventBus.getDefault().post(new CommentEvent(999));

//                } else {
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

    //--------------------------------------------------------------------------------------------------------
    @Override
    public void clickYes() {
        mPerfirmPayResulterfirmDialog.dismiss();
        finish();
    }
}
