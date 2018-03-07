package com.cucr.myapplication.core.starListAndJourney;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.starList.Journey;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
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
 * Created by cucr on 2017/9/6.
 */

public class StarJourney implements Journey {

    private OnCommonListener addJourneyListener;
    private OnCommonListener delJourneyListener;


    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public StarJourney() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }


    //添加行程
    @Override
    public void addJourney(String place, String content, String tripTime, OnCommonListener commonListener) {
        this.addJourneyListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ADD_JOURNEY, RequestMethod.POST);
        tripTime = tripTime + " 00:00:00";
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("place", CommonUtils.replaceOtherChars(place))
                .add("tripTime", tripTime)
                .add("title", content)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    //删除行程
    @Override
    public void deleteJourney(int dataId, OnCommonListener commonListener) {
        this.delJourneyListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_DELETE_JOURNEY, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    addJourneyListener.onRequestSuccess(response);
                    break;
                case Constans.TYPE_TWO:
                    delJourneyListener.onRequestSuccess(response);
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
