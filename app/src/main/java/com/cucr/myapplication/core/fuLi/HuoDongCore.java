package com.cucr.myapplication.core.fuLi;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.fuli.HuoDongInterf;
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
 * Created by cucr on 2017/12/6.
 */

public class HuoDongCore implements HuoDongInterf {

    private Context mContext;
    private OnCommonListener publishListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public HuoDongCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void publishActive(String activeName, String activePlace, String activeAdress,
                              String activeStartTime, int ys, String activeInfo, int openys,
                              String picurl,int page,int rows, OnCommonListener onCommonListener) {
        this.publishListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_ACTIVE_PUBLISH, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("activeName", activeName)
                .add("activePlace", activePlace)
                .add("activeAdress", activeAdress)
                .add("activeStartTime", activeStartTime)
                .add("activeInfo", activeInfo)
                .add("openys", openys)

                .add("picurl", picurl)

                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_FULI_GOODS);
//        //没有缓存才去请求网络
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //回调
    private OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    publishListener.onRequestSuccess(response);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, mContext);
        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
