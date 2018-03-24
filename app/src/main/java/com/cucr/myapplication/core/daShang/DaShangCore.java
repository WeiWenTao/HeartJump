package com.cucr.myapplication.core.daShang;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.daShang.DaShangInterf;
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
 * Created by cucr on 2017/11/8.
 */

public class DaShangCore implements DaShangInterf {
    //请求队列
    private RequestQueue mQueue;
    private Context mContext;
    private RequersCallBackListener mListener;


    public DaShangCore() {
        mQueue = NoHttp.newRequestQueue();
        mContext = MyApplication.getInstance();
    }

    //打赏
    @Override
    public void reward(int rewardContentId, int payType, int rewardType, int rewardMoney, RequersCallBackListener commonListener) {
        mListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_DA_SHANG, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("rewardContentId", rewardContentId) //打赏内容id
                .add("rewardType", rewardType)           //虚拟货币（道具）编号
                .add("rewardMoney", rewardMoney)         //虚拟货币数量
                .add("payType", payType)                 //支付类型
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //打赏列表
    @Override
    public void queryDsList(int rows,int page,int rewardContentId, RequersCallBackListener commonListener) {
        mListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_DS_LIST, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("rewardContentId", rewardContentId) //打赏内容id
                .add("rows", rows)
                .add("page", page)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    //打赏我的
    @Override
    public void queryDsMe(int queryMine, int rows, int page, RequersCallBackListener commonListener) {
        mListener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_DS_ME, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("queryMine", queryMine)
                .add("rows", rows)
                .add("page", page)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, callback);
    }

    //回调
    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            mListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            mListener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, mContext);
            mListener.onRequestError(what, response);
        }

        @Override
        public void onFinish(int what) {
            mListener.onRequestFinish(what);
        }
    };

    public void stopRequest() {
        mQueue.cancelAll();
    }
}
