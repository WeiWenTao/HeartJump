package com.cucr.myapplication.core.user;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.user.UserInterf;
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
 * Created by cucr on 2017/11/29.
 */

public class UserCore implements UserInterf {
    private RequersCallBackListener userCenterListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public UserCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void queryUserCenterInfo(int userId, RequersCallBackListener listener) {
        this.userCenterListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_USER_CENTER, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("queryUserId", userId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            userCenterListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            userCenterListener.onRequestSuccess(what,response);

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
            userCenterListener.onRequestError(what,response);
        }

        @Override
        public void onFinish(int what) {
            userCenterListener.onRequestFinish(what);
        }
    };
}
