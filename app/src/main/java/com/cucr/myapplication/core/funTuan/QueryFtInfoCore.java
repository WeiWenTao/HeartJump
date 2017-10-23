package com.cucr.myapplication.core.funTuan;

import android.content.Context;

import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.funTuan.QueryFtInfoInterf;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/22.
 */

public class QueryFtInfoCore implements QueryFtInfoInterf {

    private OnCommonListener ftQuerylistener;
    private OnCommonListener ftGoodlistener;
    private OnCommonListener toCommentlistener;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    private Context context;

    public QueryFtInfoCore(Context context) {
        this.context = context;
        mQueue = NoHttp.newRequestQueue();
    }


    @Override
    public void queryFtInfo(int starId, boolean queryMine, int page, int rows, OnCommonListener listener) {
        ftQuerylistener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_FT_INFO, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(context, SpConstant.USER_ID, -1)));
        MyLogger.jLog().i("userId=" + ((int) SpUtil.getParam(context, SpConstant.USER_ID, -1)));
        request.add("startId", starId)
                .add("page", page)
                .add("rows", rows)
                .add("queryMine", false)    //false查询所有人 ， true查询自己发的；
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_FT_INFO);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    @Override
    public void ftGoods(int contentId, OnCommonListener listener) {
        ftGoodlistener = listener;
        MyLogger.jLog().i("contentId=" + contentId);
        MyLogger.jLog().i("userId=" + ((int) SpUtil.getParam(context, SpConstant.USER_ID, -1)));
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_FT_GOOD, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(context, SpConstant.USER_ID, -1)))
                .add("dataId", contentId)    //数据id
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    @Override
    public void toComment(int contentId, int commentId, String content, OnCommonListener listener) {
        toCommentlistener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_FT_COMMENT, RequestMethod.POST);
        // 添加普通参数。
        if (commentId != -1) {
            //一级评论不用传
            request.add("commentParentId", commentId);
        }
        request.add("userId", ((int) SpUtil.getParam(context, SpConstant.USER_ID, -1)))
                .add("dataId", contentId)    //文章id
                .add("comment", content)    //数据
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_THREE, request, callback);
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
                    MyLogger.jLog().i("粉团文章查询成功，Cache?" + response.isFromCache());
                    ftQuerylistener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("粉团点赞查询成功，Cache?" + response.isFromCache());
                    ftGoodlistener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_THREE:
                    MyLogger.jLog().i("粉团评论成功，Cache?" + response.isFromCache());
                    toCommentlistener.onRequestSuccess(response);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);
            switch (what) {
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("粉团查询请求失败");
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("粉团点赞请求失败");
                    break;

                case Constans.TYPE_THREE:
                    MyLogger.jLog().i("粉团评论请求失败");
                    break;
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stopRequest() {
        mQueue.cancelAll();
        mQueue.stop();
    }
}
