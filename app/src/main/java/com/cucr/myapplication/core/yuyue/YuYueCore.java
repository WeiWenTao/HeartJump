package com.cucr.myapplication.core.yuyue;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.yuyue.YuYueInterf;
import com.cucr.myapplication.listener.OnCommonListener;
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
 * Created by cucr on 2017/11/3.
 */

public class YuYueCore implements YuYueInterf {

    private OnCommonListener yuYueListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public YuYueCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void yuYueStar(int starId, String activeName, String activePlace, String activeAdress,
                          String activeStartTime, String activeEndTime, int activeScene,
                          String activeInfo, int peopleCount, OnCommonListener listener) {
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_REGIST, RequestMethod.POST);
        request.add(SpConstant.USER_ID, (int) SpUtil.getParam(SpConstant.USER_ID, -1))
                .add("startId", starId)
                .add("activeName", activeName)
                .add("activePlace", activePlace)
                .add("activeStartTime", activeStartTime)
                .add("activeEndTime", activeEndTime)
                .add("activeScene", activeScene)
                .add("activeInfo", activeInfo)
                .add("peopleCount", peopleCount)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what){
                case Constans.TYPE_ONE:
                    yuYueListener.onRequestSuccess(response);
                    break;
            }
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
