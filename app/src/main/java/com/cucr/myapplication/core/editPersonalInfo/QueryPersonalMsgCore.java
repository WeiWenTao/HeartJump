package com.cucr.myapplication.core.editPersonalInfo;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.personalinfo.QueryPersonalInfo;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
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

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public QueryPersonalMsgCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }
    @Override
    public void queryPersonalInfo(String userId,final OnCommonListener onCommonListener) {
        this.onCommonListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_USERINFO, RequestMethod.POST);
        // 添加普通参数。

        request.add("userId", userId);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
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
