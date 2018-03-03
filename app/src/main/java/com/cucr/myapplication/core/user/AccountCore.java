package com.cucr.myapplication.core.user;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.user.AccountCenterInterf;
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
 * Created by cucr on 2018/3/3.
 */

public class AccountCore implements AccountCenterInterf {
    private RequersCallBackListener mListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public AccountCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void relasePsw(String oldPsw, String newPsw, RequersCallBackListener listener) {
        this.mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_RELEASE_PSW, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("oldpassword", oldPsw)
                .add("newpassword", newPsw)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(null, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            mListener.onRequestSuccess(what,response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {
            mListener.onRequestFinish(what);
        }
    };
}
