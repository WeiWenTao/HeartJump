package com.cucr.myapplication.core.funTuanAndXingWen;

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
 * Created by cucr on 2018/3/22.
 */

public class DeleteCore {

    private Context context;
    private RequestQueue mQueue;
    private RequersCallBackListener mListener;

    public DeleteCore() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    public void delFt(String dataId, RequersCallBackListener listener) {
        this.mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FT_DELETE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_FT_COMMENT);
//        //请求网络失败才去请求缓存
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_999, request, callback);
    }

    public void delActive(int dataId, RequersCallBackListener listener) {
        this.mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ACTIVE_DELETE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_FT_COMMENT);
//        //请求网络失败才去请求缓存
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_998, request, callback);
    }

    //回调
    private OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            mListener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);
            mListener.onRequestError(what, response);
        }

        @Override
        public void onFinish(int what) {
            mListener.onRequestFinish(what);
        }
    };
}
