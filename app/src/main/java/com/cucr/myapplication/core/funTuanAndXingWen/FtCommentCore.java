package com.cucr.myapplication.core.funTuanAndXingWen;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.funTuan.FenTuanComment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
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

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cucr on 2017/10/18.
 */

public class FtCommentCore implements FenTuanComment {

    private Context context;
    private RequestQueue mQueue;
    private OnCommonListener queryCommentListener;
    private OnCommonListener commentGoodsListener;

    public FtCommentCore() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    //粉团评论查询
    @Override
    public void queryFtComment(Integer dataId, Integer parentId, Integer page, Integer rows, OnCommonListener listener) {
        queryCommentListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FT_COMMENT_QUERY, RequestMethod.POST);
        if (parentId != -1) {
            request.add("parentId", parentId);
        }
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
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

    @Override
    public void ftCommentGoods(Integer contentId, Integer commentId, OnCommonListener listener) {
        MyLogger.jLog().i("粉团评论点赞:contentId:" + contentId + ",commentId:" + commentId);
        commentGoodsListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_FT_COMMENT_GOODS, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("commentId", commentId)
                .add("contentId", contentId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_FT_COMMENT);
//        //请求网络失败才去请求缓存
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_TWO, request, callback);
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
                    MyLogger.jLog().i("粉团评论查询成功，Cache?" + response.isFromCache());
                    queryCommentListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    MyLogger.jLog().i("粉团评论点赞成功，Cache?" + response.isFromCache());
                    commentGoodsListener.onRequestSuccess(response);
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
                    MyLogger.jLog().i("粉团评论点赞失败");
                    break;
            }
        }

        @Override
        public void onFinish(int what) {
            EventBus.getDefault().post(new EventRequestFinish(HttpContans.ADDRESS_FT_COMMENT_QUERY));
        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
