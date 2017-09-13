package com.cucr.myapplication.core.starListAndJourney;

import android.app.Activity;

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
    private OnCommonListener onCommonListener;


    public StarJourney(Activity activity) {
        mActivity = activity;
    }

    @Override
    public Activity getChildActivity() {
        return mActivity;
    }

    //行程时间表
    @Override
    public void queryJourneySchedule(int starId, OnCommonListener commonListener) {

    }

    //行程详细信息
    @Override
    public void queryJourneyCatgory(int starId, String time, OnCommonListener commonListener) {

    }

    //添加行程
    @Override
    public void addJourney(String place, String content, String tripTime, OnCommonListener commonListener) {
        this.onCommonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_ADD_JOURNEY, RequestMethod.POST);
        tripTime = tripTime + " 00:00:00";
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(mActivity, SpConstant.USER_ID, -1)))
                .add("place", CommonUtils.replaceOtherChars(place))
                .add("tripTime", tripTime)
                .add("title", content)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));
        //回调
        HttpListener<String> callback = new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                MyLogger.jLog().i("请求成功");
                onCommonListener.onRequestSuccess(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MyLogger.jLog().i("请求失败");
            }
        };
        request(0, request, callback, false, true);
    }

}
