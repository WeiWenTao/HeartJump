package com.cucr.myapplication.core.starListAndJourney;

import android.app.Activity;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.nohttp.HttpListener;
import com.cucr.myapplication.interf.starList.Journey;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/6.
 */

public class StarJourney extends BaseCore implements Journey {

    private Activity mActivity;
    private OnCommonListener addJourneyListener;
    private OnCommonListener delJourneyListener;



    public StarJourney(Activity activity) {
        mActivity = activity;
    }

    @Override
    public Activity getChildActivity() {
        return mActivity;
    }



    //添加行程
    @Override
    public void addJourney(String place, String content, String tripTime, OnCommonListener commonListener) {
        this.addJourneyListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_ADD_JOURNEY, RequestMethod.POST);
        tripTime = tripTime + " 00:00:00";
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("place", CommonUtils.replaceOtherChars(place))
                .add("tripTime", tripTime)
                .add("title", content)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));

        request(Constans.TYPE_ONE, request, callback, false, true);
    }

    //删除行程
    @Override
    public void deleteJourney(int dataId, OnCommonListener commonListener) {
        this.delJourneyListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_DELETE_JOURNEY, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));

        request(Constans.TYPE_TWO, request, callback, false, true);
    }

    //回调
    private HttpListener<String> callback = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {

            switch (what) {

                //添加行程
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("添加行程请求成功");
                    addJourneyListener.onRequestSuccess(response);
                    break;

                //删除行程
                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("删除行程请求成功");
                    delJourneyListener.onRequestSuccess(response);
                    break;

            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("添加行程请求失败");
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("删除行程请求失败");
                    break;
            }
        }
    };
}
