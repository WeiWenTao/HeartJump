package com.cucr.myapplication.core.fuLi;

import android.app.Activity;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.fuli.QueryFuLi;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/8/25.
 */

public class FuLiCore implements QueryFuLi {

    private Activity mActivity;
    private OnCommonListener fuLiListener;
    private OnCommonListener huoDongListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    public FuLiCore(Activity activiry) {
        mActivity = activiry;
    }

    @Override
    public void QueryDuiHuan(int page, int rows, final OnCommonListener listener) {
        this.fuLiListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_FULI_GOODS, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(mActivity, SpConstant.USER_ID, -1)))
                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));
        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_FULI_GOODS);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }


    @Override
    public void QueryHuoDong(int page, int rows, OnCommonListener listener) {
        this.huoDongListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_FULI_ACTIVE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(mActivity, SpConstant.USER_ID, -1)))
                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));

        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_FULI_ACTIVE);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_TWO, request, callback);
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
                    MyLogger.jLog().i("福利商品请求成功，Cache?"+response.isFromCache());
                    fuLiListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("福利活动请求成功，Cache?"+response.isFromCache());
                    huoDongListener.onRequestSuccess(response);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, mActivity);
            switch (what) {
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("福利商品请求失败");
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("福利活动请求失败");
                    break;
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
