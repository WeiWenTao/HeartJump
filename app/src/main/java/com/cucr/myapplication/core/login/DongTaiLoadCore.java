package com.cucr.myapplication.core.login;

import android.content.Context;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.interf.load.LoadByDongTai;
import com.cucr.myapplication.listener.OnDongTaiLoginListener;
import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 911 on 2017/8/14.
 */

public class DongTaiLoadCore implements LoadByDongTai {

    //验证码请求
    private static final int REQUEST_YZM = 1;

    //登录请求
    private static final int REQUEST_LOAD = 2;


    //取消标记
    private Object flag = new Object();

    private Context context;
    //验证码接口
    private OnGetYzmListener yzmListener;
    //登录接口
    private OnDongTaiLoginListener dongTaiListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    //登录
    @Override
    public void login(Context context, String userName, String yzm, OnDongTaiLoginListener loginListener) {
        this.dongTaiListener = loginListener;
        this.context = context;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_DONGTAI_LOAD, RequestMethod.POST);
        request.add("phone", userName) // 账号。
                .add("checkCode", yzm)
                .add("driverId", CommonUtils.getDiverID(context)) // 设备id。
                .add("msgRegId", JPushInterface.getRegistrationID(context))
                // 设置取消标志。
                .setCancelSign(flag);

        mQueue.add(REQUEST_LOAD, request, responseListener);
    }

    //获取验证码
    @Override
    public void getYzm(Context context, String userName, OnGetYzmListener yzmListener) {
        this.yzmListener = yzmListener;
        this.context = context;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_YZM, RequestMethod.GET);
        request.add("phoneNumber", userName) // 账号。
                // 设置取消标志。
                .setCancelSign(flag);

        mQueue.add(REQUEST_YZM, request, responseListener);

    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
//            ToastUtils.showToast(context,"开始登录");
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            int responseCode = response.getHeaders().getResponseCode();
            if (what == REQUEST_YZM) {
                if (yzmListener != null && responseCode == 200) {
                    yzmListener.onSuccess(response);
                } else {
                    ToastUtils.showToast(context, "未知错误:" + responseCode);
                }
            } else if (what == REQUEST_LOAD) {
                if (dongTaiListener != null && responseCode == 200) {
                    dongTaiListener.onSuccess(response);
                } else {
                    ToastUtils.showToast(context, "未知错误:" + responseCode);
                }
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {

            HttpExceptionUtil.showTsByException(response, context);

            if (what == REQUEST_YZM) {

                if (yzmListener != null) {
                    yzmListener.onFailed();
                }

            } else if (what == REQUEST_LOAD) {

                if (dongTaiListener != null) {
                    dongTaiListener.onFailed();
                }
            }
        }

        @Override
        public void onFinish(int what) {
//            ToastUtils.showToast(context,"登录完成");
        }
    };


    //activity destory 时调用
    public void stopReques() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(flag);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();

    }

}
