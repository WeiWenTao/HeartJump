package com.cucr.myapplication.core.msg;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.msg.MsgInterf;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2018/3/19.
 */

public class MsgCore implements MsgInterf {

    private RequestQueue mQueue;
    private RequersCallBackListener listener;

    public MsgCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    //评论4  点赞5
    @Override
    public void queryMsgInfo(int page, int rows, int type, RequersCallBackListener callBackListener) {
        this.listener = callBackListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_MSGINFO, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("type", type)
                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, respListener);
    }

    private OnResponseListener<String> respListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            listener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            listener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, null);
            listener.onRequestError(what, response);
        }

        @Override
        public void onFinish(int what) {
            listener.onRequestFinish(what);

        }
    };

}
