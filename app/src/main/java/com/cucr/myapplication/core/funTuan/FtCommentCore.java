package com.cucr.myapplication.core.funTuan;

import android.content.Context;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.funTuan.FenTuanComment;
import com.cucr.myapplication.listener.OnCommonListener;
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

/**
 * Created by cucr on 2017/10/18.
 */

public class FtCommentCore implements FenTuanComment {

    private Context context;
    private RequestQueue mQueue;
    private OnCommonListener queryCommentListener;

    public FtCommentCore(Context context) {
        this.context = context;
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void queryFtComment(Integer dataId, Integer page, Integer rows, OnCommonListener listener) {
        queryCommentListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_FT_COMMENT, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(context, SpConstant.USER_ID, -1)))
                .add("page", page)
                .add("rows", rows)
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_FT_COMMENT);
//        //请求网络失败才去请求缓存
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //回调
    private OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("粉团评论查询成功，Cache?"+response.isFromCache());
                    queryCommentListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);
            switch (what) {
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("粉团评论查询失败");
                    break;

                case Constans.TYPE_TWO:
                    break;
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
