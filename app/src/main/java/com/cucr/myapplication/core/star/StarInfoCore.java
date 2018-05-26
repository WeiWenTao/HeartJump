package com.cucr.myapplication.core.star;

import android.content.Context;

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
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2018/5/23.
 */

public class StarInfoCore {

    private RequersCallBackListener listener;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public StarInfoCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    public void queryStarDesrc(int startId, RequersCallBackListener listener) {
        this.listener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_STAR_DESCRIBE, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("startId", startId);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));


//        //缓存主键 默认URL  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_QUERY_RZ);
//        //没有缓存才去请求网络
//        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            listener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {

        }
    };
}
