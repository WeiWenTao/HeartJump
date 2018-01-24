package com.cucr.myapplication.core.hyt;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.hyt.HytInterf;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;

/**
 * Created by cucrx on 2018/1/17.
 */

public class HytCore implements HytInterf {

    private RequestQueue mQueue;
    private RequersCallBackListener commonListener;

    public HytCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    //创建后援团
    @Override
    public void creatHyt(String hytName, String sfzNum, String email, String realName,
                         String positivePic, String negativePic, String coverPic, int startId,
                         String phoneNum, String address,
                         RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_CREATE_HYT, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("startId", startId)
                .add("name", hytName)
                .add("idCard", sfzNum)
                .add("email", email)
                .add("realUserName", realName)
                .add("userContact", phoneNum)
                .add("city", address);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));

        request.add("idCardPic1", new FileBinary(new File(positivePic), positivePic.substring(positivePic.lastIndexOf("/"))));
        request.add("idCardPic2", new FileBinary(new File(negativePic), negativePic.substring(negativePic.lastIndexOf("/"))));
        request.add("picUrl", new FileBinary(new File(coverPic), coverPic.substring(coverPic.lastIndexOf("/"))));

        mQueue.add(Constans.TYPE_ONE, request, listener);
    }

    //后援团查询
    @Override
    public void queryHyt(int startId, int page, int rows, RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_HYT, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("startId", startId)
                .add("page", page)
                .add("rows", rows);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, listener);
    }

    //加入后援团
    @Override
    public void joinHyt(int hytId, RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_JOIN_HYT, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("hytId", hytId);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, listener);
    }

    @Override
    public void querYyhdJE(String actionCode, RequersCallBackListener commonListener) {
        this.commonListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_STAR_KEY, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("actionCode", actionCode);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FORE, request, listener);
    }

    //应援活动创建
    @Override
    public void createYyhd(String activeName, String endTime, String activeContent, int startId,
                           int activeType, String amount, String yzsm, String yyje,
                           String bgInfoIds, String city, String scale, String amount3,
                           String explains, String picUrl, RequersCallBackListener commonListener) {

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_YYHD_CREATE, RequestMethod.POST);
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("activeName", activeName)
                .add("activeContent", activeContent)
                .add("startId", startId)
                .add("activeType", activeType)
                .add("endTime", endTime);

        switch (activeType) {

            case Constans.TYPE_ONE:
                request.add("amount", amount);
                break;

            case Constans.TYPE_TWO:
                request.add("yzsm", yzsm)
                        .add("yyje", yyje)
                        .add("bgInfoIds", bgInfoIds);
                break;

            case Constans.TYPE_THREE:
                request.add("city", city)
                        .add("scale", scale)
                        .add("amount", amount3)
                        .add("explains", explains);
                break;
        }

        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        request.add("picUrl", new FileBinary(new File(picUrl), picUrl.substring(picUrl.lastIndexOf("/"))));
        mQueue.add(Constans.TYPE_FIVE, request, listener);

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
        }

        @Override
        public void onFinish(int what) {
            commonListener.onRequestFinish(what);
        }
    };
}