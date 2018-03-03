package com.cucr.myapplication.core.xinbi;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.xinbi.XinBiInterf;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/11/13.
 */

public class XinBiCore implements XinBiInterf {

    private OnCommonListener giftDuiHuanListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public XinBiCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void giftDuiHuan(String giftIds, String giftCounts, OnCommonListener listener) {
        giftDuiHuanListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_GIFT_TX, RequestMethod.POST);
        request.add(SpConstant.USER_ID, (int) SpUtil.getParam(SpConstant.USER_ID, -1))
                .add("rewardType",giftIds)
                .add("count",giftCounts)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            giftDuiHuanListener.onRequestSuccess(response);
        }

        @Override
        public void onFailed(int what, Response response) {
            HttpExceptionUtil.showTsByException(response,MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {

        }
    };
}
