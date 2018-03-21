package com.cucr.myapplication.core;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/12/22.
 */

public class AppCore {
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private RequersCallBackListener mListener;

    public AppCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    //Splish图片
    public void querySplish(String flag, RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_SPLISH, RequestMethod.POST);
        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_SPLISH + flag);
        //请求网络失败才去请求缓存
        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        mQueue.add(Constans.TYPE_ZERO, request, responseListener);
    }

    //查询版本信息
    public void queryCode(RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_UPDATA, RequestMethod.POST);
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    //意见反馈
    public void sendAdvise(String advice, RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_ADVICE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, (int) SpUtil.getParam(SpConstant.USER_ID, -1))
                .add("idea", advice)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

            mListener.onRequestStar(what);

        }

        @Override
        public void onSucceed(int what, Response response) {
            mListener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());

            mListener.onRequestError(what, response);

        }

        @Override
        public void onFinish(int what) {
            mListener.onRequestFinish(what);
        }
    };

}
