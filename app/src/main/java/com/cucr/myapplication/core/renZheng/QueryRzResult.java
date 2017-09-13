package com.cucr.myapplication.core.renZheng;

import android.app.Activity;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.nohttp.HttpListener;
import com.cucr.myapplication.interf.renZheng.QueryRz;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/12.
 */

public class QueryRzResult extends BaseCore implements QueryRz {

    private Activity mActivity;

    public QueryRzResult(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void queryRz(int type, final OnCommonListener listener) {
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_RZ, RequestMethod.POST);
        // 添加普通参数。
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(mActivity, SpConstant.USER_ID, -1)));
        request.add("type", type);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mActivity, request.getParamKeyValues()));
        //回调
        HttpListener<String> callback = new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                MyLogger.jLog().i("请求成功");
                listener.onRequestSuccess(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MyLogger.jLog().i("请求失败");
            }
        };

//        //缓存主键 默认URL  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_QUERY_RZ);
//        //没有缓存才去请求网络
//        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);

        request(0, request, callback, false, true);
    }

    @Override
    public Activity getChildActivity() {
        return mActivity;
    }
}
