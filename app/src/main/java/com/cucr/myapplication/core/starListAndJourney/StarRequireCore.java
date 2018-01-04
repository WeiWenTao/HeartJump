package com.cucr.myapplication.core.starListAndJourney;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.starList.StarRequires;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by cucr on 2017/11/1.
 */

public class StarRequireCore implements StarRequires {

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private OnCommonListener addStarRequire;
    private OnCommonListener queryStarRequire;

    public StarRequireCore() {
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void addRequires(int id, int assistantNum, int activeScene, int firstClass,
                            int economyClass, int carNum, int bed, int hzs, int fsjj,
                            String qtyq, List<String> startTimeList, OnCommonListener listener) {
        addStarRequire = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_ADD_REQUIREMENT, RequestMethod.POST);
        //id < 0 表示不用传  数据id  不传就是新增 传就是修改
        if (id > 0) {
            request.add("id", id);
        }
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("assistantNum", assistantNum)
                .add("activeScene", activeScene)
                .add("firstClass", firstClass)
                .add("economyClass", economyClass)
                .add("carNum", carNum)
                .add("bed", bed)
                .add("hzs", hzs)
                .add("fsjj", fsjj)
                .add("qtyq", qtyq)
                .add("startTimeList", new Gson().toJson(startTimeList))
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);

    }

    @Override
    public void queryStarRequire(int StarId, OnCommonListener onCommonListener) {
        queryStarRequire = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_REQUIREMENT, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("startId", StarId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            MyLogger.jLog().i("onStart");
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            MyLogger.jLog().i("onSucceed，结果来自：" + (response.isFromCache() ? "缓存" : "网络"));
            switch (what) {
                //添加明星要求
                case Constans.TYPE_ONE:
                    addStarRequire.onRequestSuccess(response);
                    break;


                case Constans.TYPE_TWO:
                    queryStarRequire.onRequestSuccess(response);
                    break;


            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            MyLogger.jLog().i("onFailed");
        }

        @Override
        public void onFinish(int what) {
            MyLogger.jLog().i("onFinish");
        }
    };

    public void stopRequire(){
        mQueue.cancelAll();
    }
}
