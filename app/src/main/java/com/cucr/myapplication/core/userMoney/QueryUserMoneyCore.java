package com.cucr.myapplication.core.userMoney;

import android.content.Context;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.userMoney.QueryUserMoney;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/8/30.
 */

public class QueryUserMoneyCore implements QueryUserMoney {

    private Context context;
    private Object flag;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public QueryUserMoneyCore(Context context) {
        this.context = context;
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void queryMoney() {
        flag = new Object();
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_REGIST, RequestMethod.POST);
        request.add(SpConstant.USER_ID, (int)SpUtil.getParam(SpConstant.USER_ID,-1))
        .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()))

        // 设置取消标志。
                .setCancelSign(flag);

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {

        }

        @Override
        public void onFailed(int what, Response response) {

        }

        @Override
        public void onFinish(int what) {

        }
    };
}
