package com.cucr.myapplication.core.starListAndJourney;

import android.app.Activity;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.starList.QueryJourney;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/15.
 */

public class QueryJourneyList /*extends BaseCore */implements QueryJourney {

    private Activity mActivity;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    public QueryJourneyList(Activity activity) {
        mActivity = activity;
    }

//    @Override
//    public Activity getChildActivity() {
//        return mActivity;
//    }

    @Override
    public void QueyrStarJourney(int rows, int page, int starId, String tripTime, final OnCommonListener listener) {
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_JOURNEY, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(mActivity, SpConstant.USER_ID, -1)))
                .add("rows", 10)
                .add("page", page)
                .add("startId", ((int) SpUtil.getParam(mActivity, SpConstant.USER_ID, -1))) //不传tripTime查所有 明星的starId就是自己
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));

//        //回调
//        HttpListener<String> callback = new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                MyLogger.jLog().i("请求成功");
//                listener.onRequestSuccess(response);
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                MyLogger.jLog().i("请求失败");
//            }
//        };

//        request(0, request, callback, false, true);


        OnResponseListener<String> callback = new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                MyLogger.jLog().i("onStart");
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                MyLogger.jLog().i("onSucceed");
                listener.onRequestSuccess(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MyLogger.jLog().i("onFailed");
            }

            @Override
            public void onFinish(int what) {
                MyLogger.jLog().i("onFinish");
            }
        };

//        //缓存主键 默认URL  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_QUERY_JOURNEY);
//        //没有缓存才去请求网络
//        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        mQueue.add(0, request, callback);

    }

}
