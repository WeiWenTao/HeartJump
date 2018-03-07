package com.cucr.myapplication.core.starListAndJourney;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.starList.MyFocusStars;
import com.cucr.myapplication.listener.OnCommonListener;
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
 * Created by cucr on 2017/9/6.
 */

public class QueryFocus implements MyFocusStars {

    private OnCommonListener onCommonListener;
    private RequersCallBackListener requersCallBackListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public QueryFocus() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }


    //查询我关注的明星
    @Override
    public void queryMyFocusStars(int queryUserId, int page, int rows, final OnCommonListener onCommonListener) {
        this.onCommonListener = onCommonListener;

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_MY_FOCUS, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        if (queryUserId != -1) {
            request.add("queryUserId", queryUserId);
        }
        request.add("page", page);
        request.add("rows", rows);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    //查询我关注的其他人
    @Override
    public void queryMyFocusOthers(int page, int rows, RequersCallBackListener requersCallBackListener) {
        this.requersCallBackListener = requersCallBackListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_MY_FOCUS_OTHER, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("page", page);
        request.add("rows", rows);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    //查询我的粉丝
    @Override
    public void queryMyFens(int page, int rows, RequersCallBackListener requersCallBackListener) {
        this.requersCallBackListener = requersCallBackListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_MY_FANS, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("page", page);
        request.add("rows", rows);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {
            if (requersCallBackListener != null) {
                requersCallBackListener.onRequestStar(what);
            }
        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    onCommonListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    requersCallBackListener.onRequestSuccess(what, response);
                    break;

                case Constans.TYPE_THREE:
                    requersCallBackListener.onRequestSuccess(what, response);
                    break;
            }
        }

        @Override
        public void onFailed(int what, Response response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {
            if (requersCallBackListener != null) {
                requersCallBackListener.onRequestFinish(what);
            }
        }
    };
}
