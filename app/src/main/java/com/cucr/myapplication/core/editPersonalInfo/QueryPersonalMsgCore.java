package com.cucr.myapplication.core.editPersonalInfo;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.personalinfo.QueryPersonalInfo;
import com.cucr.myapplication.listener.OnCommonListener;
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
 * Created by 911 on 2017/8/18.
 */

public class QueryPersonalMsgCore implements QueryPersonalInfo {

    private OnCommonListener onCommonListener;
    private OnCommonListener personalListener;
    private RequersCallBackListener mListener;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public QueryPersonalMsgCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    //查询自己的信息
    @Override
    public void queryPersonalInfo( final OnCommonListener onCommonListener) {
        this.onCommonListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_QUERY_USERINFO, RequestMethod.POST);
        // 添加普通参数。

        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    //根据id查用户
    @Override
    public void queryPersonalById(String userId, RequersCallBackListener onCommonListener) {
        this.mListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_USERINFO_BYID, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("otherUserId", userId);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    @Override
    public void queryHytInfoById(String hytId, RequersCallBackListener onCommonListener) {
        this.mListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_HYTINFO_BYID, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("hytId", hytId);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_THREE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    onCommonListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    mListener.onRequestSuccess(what,response);
                    break;

                case Constans.TYPE_THREE:
                    mListener.onRequestSuccess(what,response);
                    break;
            }
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
