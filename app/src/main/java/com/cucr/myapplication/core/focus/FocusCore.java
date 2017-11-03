package com.cucr.myapplication.core.focus;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.focus.Focus;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/5.
 */

public class FocusCore implements Focus {
    private Context mContext;
    private Gson mGson;
    private OnCommonListener focusListener, cancaleFocusListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public FocusCore() {
        mGson = new Gson();
        this.mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void toFocus(int id) {

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_TO_FOCUS, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("startId", id);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    @Override
    public void cancaleFocus(int id) {

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_CANCLE_FOCUS, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("startId", id);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    public void stopRequest(){
        mQueue.cancelAll();
        mQueue.stop();
    }

    //回调
    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
            if (what == Constans.TYPE_ONE) {
                if (reBackMsg.isSuccess()) {
                    ToastUtils.showToast(mContext, "关注成功！");
                    MyLogger.jLog().i("关注成功");
                } else {
                    ToastUtils.showToast(mContext, reBackMsg.getMsg());
                }

            } else {
                if (reBackMsg.isSuccess()) {
                    ToastUtils.showToast(mContext, "已取消关注！");
                    MyLogger.jLog().i("已取消关注");
                } else {
                    ToastUtils.showToast(mContext, reBackMsg.getMsg());
                }
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, mContext);
            MyLogger.jLog().i("请求失败");
        }

        @Override
        public void onFinish(int what) {

        }
    };

}
