package com.cucr.myapplication.core.starListAndJourney;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.starList.QueryJourney;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/15.
 */

public class QueryJourneyList implements QueryJourney {

    private OnCommonListener scheduleListener;
    private OnCommonListener catgoryListener;
    private OnCommonListener queryListener;
    private Context context;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public QueryJourneyList() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    //行程时间表
    @Override
    public void queryJourneySchedule(int starId, OnCommonListener commonListener) {
        this.scheduleListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_JOURNEY_SCHEDULE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                // TODO: 2017/9/19 eventBus传值  这里暂用userid代替
                .add("startId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        //缓存主键 默认URL  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_JOURNEY_SCHEDULE);
        //请求网络失败才去请求缓存
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    //行程详细信息
    @Override
    public void queryJourneyCatgory(int starId, String time, OnCommonListener commonListener) {
        this.catgoryListener = commonListener;
    }

    @Override
    public void QueyrStarJourney(int rows, int page, int starId, String tripTime, final OnCommonListener listener) {
        queryListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_JOURNEY, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("rows", 10)
                .add("page", page)
                .add("startId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1))) //不传tripTime查所有 明星的starId就是自己
                .add("tripTime", tripTime)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));


        //缓存主键 默认URL  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_JOURNEY);
        //请求网络失败才去请求缓存
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, callback);

    }

    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            MyLogger.jLog().i("onStart");
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                //行程列表
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("onSucceed，结果来自：" + (response.isFromCache() ? "缓存" : "网络"));
                    queryListener.onRequestSuccess(response);
                    break;

                //行程时间表
                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("onSucceed，结果来自：" + (response.isFromCache() ? "缓存" : "网络"));
                    scheduleListener.onRequestSuccess(response);
                    break;


            }

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

}
