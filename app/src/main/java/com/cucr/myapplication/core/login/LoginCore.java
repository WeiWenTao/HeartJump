package com.cucr.myapplication.core.login;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.load.LoadByPsw;
import com.cucr.myapplication.listener.OnLoginListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 911 on 2017/8/11.
 * TODO 这里进行联网操作
 */

public class LoginCore implements LoadByPsw {
    /**
     * 用来标志请求的what, 类似handler的what一样，这里用来区分请求。
     */
    private static final int NOHTTP_WHAT_1 = 1;


    private OnLoginListener loginListener;
    private Context context;

    /**
     * 请求的时候等待框。
     */
//    private WaitDialog mWaitDialog;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue = NoHttp.newRequestQueue();

    //标记
    private Object flag = new Object();

    @Override
    public void login( String userName, String psw, final OnLoginListener loginListener) {
        this.loginListener = loginListener;
        this.context = MyApplication.getInstance();
        String sign = (String) SpUtil.getParam(SpConstant.SIGN, "");
        // 创建请求对象。
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PSW_LOAD, RequestMethod.POST);

        // 如果有密钥 添加加密后的请求参数。
//        if (!TextUtils.isEmpty(sign)){
//            request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context,request.getParamKeyValues()));
//        }
        request.add("userName", userName) // 账号。
                .add("driverId", CommonUtils.getDiverID(context)) // 设备id。
                .add("password", psw) // 密码。
                .add("msgRegId", JPushInterface.getRegistrationID(context)) // 极光推送。

                // 单个请求的超时时间，不指定就会使用全局配置。
                .setConnectTimeout(10 * 1000) // 设置连接超时。
                .setReadTimeout(20 * 1000) // 设置读取超时时间，也就是服务器的响应超时。

                // 请求头，是否要添加头，添加什么头，要看开发者服务器端的要求。
//                .addHeader("Author", "sample")
//                .setHeader("User", "Jason")

                // 设置一个tag, 在请求完(失败/成功)时原封不动返回; 多数情况下不需要。
                .setTag(new Object())
                // 设置取消标志。
                .setCancelSign(flag);


		/*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样。
		 * request: 请求对象。
		 * onResponseListener 回调对象，接受请求结果。
		 */

        mQueue.add(NOHTTP_WHAT_1, request, responseListener);

    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            MyLogger.jLog().i("密码登录开始");
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            //状态码
            int responseCode = response.getHeaders().getResponseCode();
            if (what == NOHTTP_WHAT_1) {
                if (loginListener != null && responseCode == 200) {
                    loginListener.onSuccess(response);

                }else {
                    ToastUtils.showToast(context,"未知错误:"+responseCode);
                }
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);
            if (loginListener != null) {
                loginListener.onFailed();
            }
        }

        @Override
        public void onFinish(int what) {
            MyLogger.jLog().i("密码登录结束");
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
