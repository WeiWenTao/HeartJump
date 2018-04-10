package com.cucr.myapplication.core.pay;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.pay.PayCenterInterf;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import static com.cucr.myapplication.alipay.OrderInfoUtil2_0.getOutTradeNo;

/**
 * Created by cucr on 2017/10/16.
 */

public class PayCenterCore implements PayCenterInterf {

    private Context context;
    private RequestQueue mQueue;
    private RequersCallBackListener payListener;
    private PayLisntener payResultListener;
    private OnCommonListener userMoneyListener;
    private RequersCallBackListener commonListener;

    public PayCenterCore() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }


    @Override
    public void aliPay(double howMuch, String subject, int type, int activeId, RequersCallBackListener listener) {
        payListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ALIPAY_PAY, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("timeout_express", "30m");
        request.add("product_code", "QUICK_MSECURITY_PAY");
        request.add("total_amount", howMuch);
        request.add("subject", subject);
        request.add("out_trade_no", getOutTradeNo());
        request.add("body", "真是太好用了!!!");
        //传0充值星币  传1充值现金
        request.add("type", type);
        if (type == 1) {
            request.add("hytActiveId", activeId);
        }
        request.setCancelSign(1);

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    @Override
    public void wxPay(int total_fee, String body, int type, int activeId, RequersCallBackListener listener) {
        payListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_WX_PAY, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("total_fee", total_fee);
        request.add("body", body);

        //传0充值星币  传1充值现金
        request.add("type", type);
        if (type == 1) {
            request.add("hytActiveId", activeId);
        }

        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    @Override
    public void queryResult(String order, PayLisntener listener) {
        payResultListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ALIPAY_CHECK, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("orderNo", order)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        request.setCancelSign(2);

        mQueue.add(Constans.TYPE_THREE, request, responseListener);
    }


    @Override
    public void queryUserMoney(OnCommonListener listener) {
        userMoneyListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_USER_MONEY, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FORE, request, responseListener);
    }

    @Override
    public void queryTxRecoed(int page, int rows, int type, RequersCallBackListener listener) {
        commonListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_TX_RECORD, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        if (type != -1) {
            request.add("type", type);
        }
        request.add("page", page);
        request.add("rows", rows);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FIVE, request, responseListener);
    }

    //提现申请
    @Override
    public void TxRequest(String txAccount, String name, String amount, RequersCallBackListener listener) {
        commonListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_TX_REQUEST, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("account", txAccount)
                .add("name", name)
                .add("xbCount", amount)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_SIX, request, responseListener);
    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {

            switch (what) {
                case Constans.TYPE_ONE:
                case Constans.TYPE_TWO:
                    payListener.onRequestStar(what);
                    break;

                case Constans.TYPE_THREE:
                    payResultListener.onRequestStar();
                    break;

                case Constans.TYPE_FORE:

                    break;

                case Constans.TYPE_FIVE:
                case Constans.TYPE_SIX:
                    commonListener.onRequestStar(what);
                    break;
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {

            switch (what) {
                case Constans.TYPE_ONE:
                case Constans.TYPE_TWO:
                    payListener.onRequestSuccess(what, response);
                    break;

                case Constans.TYPE_THREE:
                    payResultListener.onSuccess(response);

                    break;

                case Constans.TYPE_FORE:
                    userMoneyListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_FIVE:
                    commonListener.onRequestSuccess(what, response);
                    break;

                case Constans.TYPE_SIX:
                    commonListener.onRequestSuccess(what, response);
                    break;
            }
//

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
            switch (what) {
                case Constans.TYPE_ONE:
                case Constans.TYPE_TWO:
                    payListener.onRequestError(what, response);
                    break;

                case Constans.TYPE_FIVE:
                case Constans.TYPE_SIX:
                    commonListener.onRequestError(what, response);
                    break;
            }
        }

        @Override
        public void onFinish(int what) {
            switch (what) {
                case Constans.TYPE_ONE:
                case Constans.TYPE_TWO:
                    payListener.onRequestFinish(what);
                    break;

                case Constans.TYPE_FIVE:
                case Constans.TYPE_SIX:
                    commonListener.onRequestFinish(what);
                    break;
            }
        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
