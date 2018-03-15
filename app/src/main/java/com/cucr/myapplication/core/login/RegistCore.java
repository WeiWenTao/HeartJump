package com.cucr.myapplication.core.login;

import android.content.Context;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.interf.load.Regist;
import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.listener.load.OnRegistListener;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
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
    private static final int REQUEST_YZM = 999;

    //登录请求
    private static final int REQUEST_REGIST = 998;

    //注册接口
    private OnRegistListener registListener;

    //获取验证码接口
    private OnGetYzmListener getYzmListener;

    //三方平台接口
    private RequersCallBackListener thirdLinstener;

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


    //注册
    @Override
    public void regist(Context context, String yzm, String phoneNum, String psw, OnRegistListener registListener, boolean isRegist) {
        this.mContext = context;
        this.registListener = registListener;
        if (isRegist) {
            Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_REGIST, RequestMethod.POST);
            request.add("checkCode", yzm) // 账号。
                    .add("phone", phoneNum)
                    .add("password", psw) // 密码。
                    // 设置取消标志。
                    .setCancelSign(flag);

            mQueue.add(REQUEST_REGIST, request, responseListener);
        } else {
            Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FORGET_PSW, RequestMethod.POST);
            request.add("phoneNumber", phoneNum) // 账号。
                    .add("code", yzm)
                    .add("password", psw) // 密码。
                    // 设置取消标志。
                    .setCancelSign(flag);

            mQueue.add(REQUEST_REGIST, request, responseListener);
        }
    }

    //获取验证码
    @Override
    public void getYzm(Context context, String userName, OnGetYzmListener getYzmListener) {
        this.mContext = context;
        this.getYzmListener = getYzmListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_YZM, RequestMethod.GET);
        request.add("phoneNumber", userName) // 账号。
                // 设置取消标志。
                .setCancelSign(flag);

        mQueue.add(REQUEST_YZM, request, responseListener);
    }

    //三方登录
    @Override
    public void thirdPlatformLoad(String loginType, String openId, String msgRegId, RequersCallBackListener commonListener) {
        thirdLinstener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_OTHER_LOAD, RequestMethod.POST);
        MyLogger.jLog().i("loginType:" + loginType);
        request.add("loginType", loginType)
                .add("openId", openId)
                .add("msgRegId", msgRegId);

        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    //三方注册
    @Override
    public void thirdPlatformRegist(String phone, String checkCode, String loginType, String openId,
                                    String name, String gender, String iconurl, RequersCallBackListener commonListener) {
        thirdLinstener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_OTHER_REGIST, RequestMethod.POST);
        request.add("phone", phone) // 账号。
                .add("checkCode", checkCode)
                .add("loginType", loginType)
                .add("name", name)
                .add("gender", gender)
                .add("iconurl", iconurl)
                .add("openId", openId);

        mQueue.add(Constans.TYPE_THREE, request, responseListener);
    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            switch (what) {
                case Constans.TYPE_TWO:
                    thirdLinstener.onRequestStar(what);
                    break;
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            int responseCode = response.getHeaders().getResponseCode();
            switch (what) {
                case REQUEST_YZM:
                    getYzmListener.onSuccess(response);
                    break;

                case REQUEST_REGIST:
                    registListener.OnRegistSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    thirdLinstener.onRequestSuccess(what, response);
                    break;

                case Constans.TYPE_THREE:
                    thirdLinstener.onRequestSuccess(what, response);
                    break;
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
            }else if ( what == Constans.TYPE_TWO){
                thirdLinstener.onRequestError(what,response);
            }
        }

        @Override
        public void onFinish(int what) {
            switch (what) {
                case Constans.TYPE_TWO:
                    thirdLinstener.onRequestFinish(what);
                    break;
            }

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