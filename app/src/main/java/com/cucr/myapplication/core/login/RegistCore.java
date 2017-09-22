package com.cucr.myapplication.core.login;

import android.content.Context;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.interf.load.Regist;
import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.listener.load.OnRegistListener;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/15.
 */

public class RegistCore implements Regist {

    //验证码请求
    private static final int REQUEST_YZM = 1;

    //登录请求
    private static final int REQUEST_REGIST = 2;

    //注册接口
    private OnRegistListener registListener;

    //获取验证码接口
    private OnGetYzmListener getYzmListener;

    //取消标记
    private Object flag = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    private Context mContext;

    public RegistCore() {
        mQueue = NoHttp.newRequestQueue();
    }


    @Override
    public void regist(Context context, String yzm, String phoneNum, String nickName, String psw, OnRegistListener registListener) {
        this.mContext = context;
        this.registListener = registListener;

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_REGIST, RequestMethod.POST);
        request.add("checkCode", yzm) // 账号。
                .add("phone", phoneNum)
                .add("name", nickName) // 用户名。
                .add("password", psw) // 密码。
                // 设置取消标志。
                .setCancelSign(flag);

        mQueue.add(REQUEST_REGIST, request, responseListener);
    }

    @Override
    public void getYzm(Context context, String userName, OnGetYzmListener getYzmListener) {
        this.mContext = context;
        this.getYzmListener = getYzmListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_YZM, RequestMethod.GET);
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
                if (getYzmListener != null && responseCode == 200) {
                    getYzmListener.onSuccess(response);
                } else {
                    ToastUtils.showToast(mContext, "未知错误:" + responseCode);
                }
            } else if (what == REQUEST_REGIST) {
                if (registListener != null && responseCode == 200) {
                    registListener.OnRegistSuccess(response);
                } else {
                    ToastUtils.showToast(mContext, "未知错误:" + responseCode);
                }
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {

            HttpExceptionUtil.showTsByException(response, mContext);
            if (what == REQUEST_YZM) {

                if (getYzmListener != null) {
                    getYzmListener.onFailed();
                }

            } else if (what == REQUEST_REGIST) {

                if (registListener != null) {
                    registListener.onRegistFailed();
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