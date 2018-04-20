package com.cucr.myapplication.core.home;

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
 * Created by cucr on 2018/4/20.
 */

public class HomeNewsCore {
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private RequersCallBackListener commonListener;

    public HomeNewsCore() {
        mQueue = NoHttp.newRequestQueue();
    }


    public void queryHomeNews(int rows, int page, int newsType, RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_HOME_NEWS, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataType", 0)
                .add("newsType", newsType)
                .add("rows", rows)
                .add("page,", page)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_HOME_BANNER);
//        //没有缓存才去请求网络
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {
            commonListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            commonListener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, null);
            commonListener.onRequestError(what, response);
        }

        @Override
        public void onFinish(int what) {
            commonListener.onRequestFinish(what);
        }
    };

}
