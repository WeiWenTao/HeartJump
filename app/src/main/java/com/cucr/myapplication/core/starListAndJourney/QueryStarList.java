package com.cucr.myapplication.core.starListAndJourney;

import android.app.Activity;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.nohttp.HttpListener;
import com.cucr.myapplication.interf.starList.StarListInfo;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/5.
 */

public class QueryStarList extends BaseCore implements StarListInfo {
    private Activity activity;
    private OnCommonListener onCommonListener;


    public QueryStarList(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void queryStar(int type, int page, int row, int startId, final OnCommonListener onCommonListener) {
        this.onCommonListener = onCommonListener;

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_STAR, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("type", type);
        request.add("page", page);
        request.add("rows", row);

        if (startId != 0) {
            request.add("startId", startId);
        }
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(activity, request.getParamKeyValues()));

        //回调
        HttpListener<String> callback = new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                MyLogger.jLog().i("请求成功");
                onCommonListener.onRequestSuccess(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MyLogger.jLog().i("请求失败");
            }
        };

        //缓存主键 默认URL  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_STAR);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);

        request(0, request, callback, false, true);

    }


    @Override
    public Activity getChildActivity() {
        return activity;
    }
}
