package com.cucr.myapplication.core.funTuanAndXingWen;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.funTuan.QueryFtInfoInterf;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
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
 * Created by cucr on 2017/9/22.
 */

public class QueryFtInfoCore implements QueryFtInfoInterf {

    private RequersCallBackListener ftQuerylistener;
    private OnCommonListener ftGoodlistener;
    private OnCommonListener toCommentlistener;
    private OnCommonListener queryGiftListener;
    private OnCommonListener queryBackpackListener;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    private Context context;

    public QueryFtInfoCore() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }


    //查询粉团信息
    @Override
    public void queryFtInfo(int starId, int dataType, int queryUserId, boolean queryMine, int page, int rows, RequersCallBackListener listener) {
        ftQuerylistener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_QUERY_FT_INFO, RequestMethod.POST);
        // 添加普通参数。
        if (starId != -1) {
            request.add("startId", starId);
        }

        if (queryUserId != -1) {
            request.add("queryUserId", queryUserId);
        }
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
//                .add("startId", starId)
                .add("dataType", dataType)
                .add("page", page)
                .add("rows", rows)
                .add("queryMine", queryMine)    //false查询所有人 ， true查询自己发的；
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
      /*  //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_FT_INFO);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);*/
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //查询首页关注新闻 传focus查关注
    @Override
    public void queryFtInfo(boolean focus, int starId, int dataType, int queryUserId, boolean queryMine, int page, int rows, RequersCallBackListener listener) {
        ftQuerylistener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_QUERY_FT_INFO, RequestMethod.POST);
        // 添加普通参数。
        if (starId != -1) {
            request.add("startId", starId);
        }

        if (queryUserId != -1) {
            request.add("queryUserId", queryUserId);
        }
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("queryFollow", focus)
                .add("dataType", dataType)
                .add("page", page)
                .add("rows", rows)
                .add("queryMine", queryMine)    //false查询所有人 ， true查询自己发的；
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
      /*  //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_FT_INFO);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);*/
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //点赞
    @Override
    public void ftGoods(int contentId, OnCommonListener listener) {
        MyLogger.jLog().i("粉团点赞:contentId:" + contentId);
        ftGoodlistener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FT_GOOD, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", contentId)    //数据id
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    //评论
    @Override
    public void toComment(int contentId, int commentId, String content, OnCommonListener listener) {
        toCommentlistener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FT_COMMENT, RequestMethod.POST);
        // 添加普通参数。
        if (commentId != -1) {
            //一级评论不用传
            request.add("commentParentId", commentId);
        }
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", contentId)    //文章id
                .add("comment", content)    //数据
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        mQueue.add(Constans.TYPE_THREE, request, callback);
    }

    //查询礼物
    @Override
    public void queryGift(OnCommonListener listener) {
        queryGiftListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_QUERY_GFITINFO, RequestMethod.POST);
        mQueue.add(Constans.TYPE_FORE, request, callback);
    }


    //背包
    @Override
    public void queryBackpackInfo(OnCommonListener listener) {
        queryBackpackListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_QUERY_BACKPACKINFO, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        //无参数
        mQueue.add(Constans.TYPE_FIVE, request, callback);
    }

    //阅读量
    @Override
    public void ftRead(int dataId) {
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FT_READ, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        //无参数
        mQueue.add(Constans.TYPE_SIX, request, callback);
    }


    //回调
    private OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            switch (what) {
                case Constans.TYPE_ONE:
                    ftQuerylistener.onRequestStar(what);
                    break;
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    MyLogger.jLog().i("粉团文章查询成功，Cache?" + response.isFromCache());
                    ftQuerylistener.onRequestSuccess(what, response);
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("粉团点赞查询成功，Cache?" + response.isFromCache());
                    ftGoodlistener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_THREE:
                    MyLogger.jLog().i("粉团评论成功，Cache?" + response.isFromCache());
                    toCommentlistener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_FORE:
                    MyLogger.jLog().i("虚拟道具查询成功，Cache?" + response.isFromCache());
                    queryGiftListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_FIVE:
                    MyLogger.jLog().i("背包信息查询成功，Cache?" + response.isFromCache());
                    queryBackpackListener.onRequestSuccess(response);
                    break;
                case Constans.TYPE_SIX:
                    MyLogger.jLog().i("阅读量:" + response);
                    break;
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);
            switch (what) {
                case Constans.TYPE_ONE:
                    ftQuerylistener.onRequestStar(what);
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("粉团点赞请求失败");
                    break;

                case Constans.TYPE_THREE:
                    MyLogger.jLog().i("粉团评论请求失败");
                    break;

                case Constans.TYPE_FORE:
                    MyLogger.jLog().i("虚拟道具请求失败");
                    break;

                case Constans.TYPE_FIVE:
                    MyLogger.jLog().i("背包信息请求失败");
                    break;
            }
        }

        @Override
        public void onFinish(int what) {
            switch (what) {
                case Constans.TYPE_ONE:
                    ftQuerylistener.onRequestFinish(what);
                    break;
            }
        }
    };

    public void stopRequest() {
        mQueue.cancelAll();
        mQueue.stop();
    }
}