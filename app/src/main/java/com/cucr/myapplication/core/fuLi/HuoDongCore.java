package com.cucr.myapplication.core.fuLi;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.fuli.HuoDongInterf;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cucr on 2017/12/6.
 */

public class HuoDongCore implements HuoDongInterf {

    private Context mContext;
    private OnCommonListener publishListener;   //活动发布
    private OnCommonListener queryListener;     //活动查询
    private OnCommonListener giveUpListener;    //活动点赞
    private OnCommonListener commentListener;   //活动评论
    private OnCommonListener commentQueryListener;   //活动评论查询
    private OnCommonListener commentGoodListener;   //活动评论点赞
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public HuoDongCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    //发布活动
    @Override
    public void publishActive(String activeName, String activePlace, String activeAdress,
                              String activeStartTime, int ys, String activeInfo, int openys, int peopleCount,
                              String picurl, OnCommonListener onCommonListener) {
        this.publishListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ACTIVE_PUBLISH, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("activeName", activeName)
                .add("activePlace", CommonUtils.replaceOtherChars(activePlace)) //去除特殊字符
                .add("activeAdress", activeAdress)
                .add("activeStartTime", activeStartTime)
                .add("activeInfo", activeInfo)
                .add("openys", openys)
                .add("ys", ys)
                .add("peopleCount", peopleCount)
                .add("picurl", picurl)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));

        BitmapBinary binary1 = new BitmapBinary(CommonUtils.decodeBitmap(picurl), picurl.substring(picurl.lastIndexOf("/")));
        request.add("picurl", binary1);
//        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_FULI_GOODS);
//        //没有缓存才去请求网络
//        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    //活动查询
    @Override
    public void queryActive(boolean byMe, int dataId, int page, int rows, OnCommonListener onCommonListener) {
        this.queryListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_QUERY_ACTIVE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("byMe", byMe)
                .add("page", page)
                .add("rows", rows);
        if (dataId != -1) {
            request.add("dataId", dataId);
        }
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    //活动点赞
    @Override
    public void activeGiveUp(int dataId, OnCommonListener onCommonListener) {
        this.giveUpListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ACTIVE_GOOD, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, callback);
    }

    //活动评论
    @Override
    public void activeComment(int dataId, String comment, int commentParentId, OnCommonListener onCommonListener) {
        this.commentListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ACTIVE_COMMENT, RequestMethod.POST);
        if (commentParentId != -1) {
            //一级评论不用传
            request.add("commentParentId", commentParentId);
        }
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add("comment", comment)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FORE, request, callback);
    }


    //活动评论查询
    @Override
    public void activeCommentQuery(int contentId, int parentId, int page, int rows, OnCommonListener onCommonListener) {
        this.commentQueryListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ACTIVE_COMMENT_QUERY, RequestMethod.POST);
        if (parentId != -1) {
            //一级评论不用传
            request.add("parentId", parentId);
        }
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("contentId", contentId)
                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FIVE, request, callback);
    }

    //评论点赞
    @Override
    public void activeCommentGood(int contentId, int dataId, OnCommonListener onCommonListener) {
        this.commentGoodListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_ACTIVE_COMMENT_GOOD, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("contentId", contentId)
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_SIX, request, callback);
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
                    publishListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:
                    queryListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_THREE:
                    giveUpListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_FORE:
                    commentListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_FIVE:
                    commentQueryListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_SIX:
                    commentGoodListener.onRequestSuccess(response);
                    break;

            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, mContext);
        }

        @Override
        public void onFinish(int what) {
            switch (what) {
                case Constans.TYPE_TWO:
                case Constans.TYPE_FIVE:
                    //用来区分是哪个接口请求
                    EventBus.getDefault().post(new EventRequestFinish(HttpContans.ADDRESS_ACTIVE_COMMENT_QUERY));
                    break;
            }
        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
