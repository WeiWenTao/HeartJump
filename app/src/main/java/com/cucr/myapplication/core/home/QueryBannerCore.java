package com.cucr.myapplication.core.home;

import android.app.Activity;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.interf.home.QueryHomeBanner;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/8/30.
 */

public class QueryBannerCore implements QueryHomeBanner {

    private Object flag;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue = NoHttp.newRequestQueue();
    private OnCommonListener commonListener;

    private Activity activity;

    public QueryBannerCore(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void queryBanner(OnCommonListener commonListener) {
        this.commonListener = commonListener;
        flag = new Object();

        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_HOME_BANNER, RequestMethod.POST);

        request.setCancelSign(flag);
        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_HOME_BANNER);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            MyLogger.jLog().i("banner:"+response.isFromCache());
            commonListener.onRequestSuccess(response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, activity);
        }

        @Override
        public void onFinish(int what) {

        }
    };

}
