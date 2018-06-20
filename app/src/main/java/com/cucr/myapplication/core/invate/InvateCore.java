package com.cucr.myapplication.core.invate;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
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
 * Created by cucrx on 2018/1/17.
 */

public class InvateCore {

    private RequestQueue mQueue;
    private RequersCallBackListener commonListener;

    public InvateCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    //查询邀请码
    public void querInvateCode(RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_INVATECODE_QUERY, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, listener);
    }

    //领取邀请码
    public void putCode(String code, RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_PUT_INVATECODE, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("code", code);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, listener);
    }

    //邀请码明星搜索
    public void invateSearch(int page, int rows, String name, RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_INVATE_SEACH, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        if (name != null) {
            request.add("realName", name);
        }
        request.add("page", page);
        request.add("rows", rows);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, listener);
    }

    private OnResponseListener<String> listener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            commonListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            commonListener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, null);
            commonListener.onRequestError(what, response);
        }

        @Override
        public void onFinish(int what) {
            commonListener.onRequestFinish(what);

        }
    };
}
