package com.cucr.myapplication.core.dabang;

import android.content.Context;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.dabang.BangDan;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.eventBus.EventRequestFinish;
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

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cucr on 2017/10/28.
 */

public class BangDanCore implements BangDan {

    //请求队列
    private RequestQueue mQueue;
    private Context mContext;
    private OnCommonListener bangDanListener;
    private OnCommonListener daBangListener;

    public BangDanCore(Context mContext) {
        this.mContext = mContext;
        mQueue = NoHttp.newRequestQueue();
    }

    //查询榜单信息
    @Override
    public void queryBangDanInfo(int page, int rows, OnCommonListener listener) {
        bangDanListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_BANG_DAN_INFO, RequestMethod.POST);
        // 添加普通参数。
        request.add("page",page);
        request.add("rows",rows);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //打榜
    @Override
    public void daBang(int money, int starId, OnCommonListener listener) {
        daBangListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_DA_BANG, RequestMethod.POST);
        // 添加普通参数。
        request.add("money", money);
        request.add("startId", starId);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    //回调
    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    bangDanListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    daBangListener.onRequestSuccess(response);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, mContext);
            MyLogger.jLog().i("请求失败");
        }

        @Override
        public void onFinish(int what) {
            EventBus.getDefault().post(new EventRequestFinish());
        }
    };

    public void stopRequest() {
        mQueue.cancelAll();
    }
}
