package com.cucr.myapplication.core;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
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
    private OnCommonListener codeListener;

    public AppCore() {
        mQueue  = NoHttp.newRequestQueue();
    }

    public void queryCode(OnCommonListener listener) {
        codeListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_UPDATA, RequestMethod.POST);
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            codeListener.onRequestSuccess(response);
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
