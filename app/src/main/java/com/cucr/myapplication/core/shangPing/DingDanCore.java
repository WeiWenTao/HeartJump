package com.cucr.myapplication.core.shangPing;

import android.app.Activity;
import android.content.Context;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.dingDan.DuiHuanDingDan;
import com.cucr.myapplication.interf.nohttp.HttpListener;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/21.
 */

public class DingDanCore extends BaseCore implements DuiHuanDingDan {

    private OnCommonListener listener;
    private Context context;
    private Activity activity;

    public DingDanCore(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Activity getChildActivity() {
        return activity;
    }

    @Override
    public void onDuiHuan(String local, String address, String rececivedPerson,
                          String rececivedPhone, int num, int shopId, final OnCommonListener listener) {
        this.context = context;
        this.listener = listener;

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_SHOP, RequestMethod.POST);

        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("shopId", shopId);
        request.add("num", num);
        request.add("adress", local);
        request.add("adressDetails", address);
        request.add("addressee", rececivedPerson);
        request.add("addresseePhone", rececivedPhone);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(activity, request.getParamKeyValues()));

        //回调
        HttpListener<String> callback = new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                MyLogger.jLog().i("请求成功");
                listener.onRequestSuccess(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MyLogger.jLog().i("请求失败");
            }
        };

        request(0, request, callback, false, true);
    }
}
