package com.cucr.myapplication.core.editPersonalInfo;

import android.app.Activity;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.nohttp.HttpListener;
import com.cucr.myapplication.interf.personalinfo.QueryPersonalInfo;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/18.
 */

public class QueryPersonalMsgCore extends BaseCore implements QueryPersonalInfo {

    private Activity activity;
    private OnCommonListener onCommonListener;

    public QueryPersonalMsgCore(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void queryPersonalInfo(final OnCommonListener onCommonListener) {
        this.onCommonListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_USERINFO, RequestMethod.GET);
        // 添加普通参数。

        request.add("userId", ((int) SpUtil.getParam(activity, SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(activity,request.getParamKeyValues()));

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

    @Override
    public Activity getChildActivity() {
        return activity;
    }
}
