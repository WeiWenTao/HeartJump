package com.cucr.myapplication.core.chat;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.listener.LoadChatServer;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by cucr on 2018/2/28.
 */

public class ChatCore {

    //请求队列
    private RequestQueue mQueue;
    private Context context;
    private OnCommonListener onCommonListener;
    public ChatCore() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    public void connect(String token, final LoadChatServer loadChatServer) {
        context = MyApplication.getInstance();
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    loadChatServer.onTokenIncorrect();
                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    MyLogger.jLog().i("成功:" + userid);
                    loadChatServer.onLoadSuccess(userid);
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    MyLogger.jLog().i("失败:" + errorCode);
                    loadChatServer.onLoadFial(errorCode);
                }
            });
        }
    }

    public void reLoad(OnCommonListener onCommonListener){
        this.onCommonListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_GET_TOKEN, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //回调
    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            onCommonListener.onRequestSuccess(response);

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);

        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stopRequest(){
        mQueue.cancelAll();
    }
}
