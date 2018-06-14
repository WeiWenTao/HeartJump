package com.cucr.myapplication.core;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.listener.DownLoadListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/12/22.
 */

public class AppCore {
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private DownloadQueue mDownLoadQueue;
    private RequersCallBackListener mListener;
    private DownLoadListener mOnCommonListener;

    public AppCore() {
        mQueue = NoHttp.newRequestQueue();
        mDownLoadQueue = NoHttp.newDownloadQueue();
    }

    //Splish图片
    public void querySplish(String flag, RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_SPLISH, RequestMethod.POST);
        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
//        request.setCacheKey(HttpContans.ADDRESS_SPLISH + flag);
        //请求网络失败才去请求缓存
//        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        mQueue.add(Constans.TYPE_ZERO, request, responseListener);
    }

    public void saveImgs(String url, String path, String fileName, boolean isContinue, boolean isAgain, DownLoadListener listener) {
        mOnCommonListener = listener;
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, path, fileName, isContinue, isAgain);
        mDownLoadQueue.add(Constans.TYPE_FORE, downloadRequest, downloadListener);
    }

    //查询版本信息
    public void queryCode(RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_UPDATA, RequestMethod.POST);
        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    //查询版本信息
    public void closure(RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_CLOSURE, RequestMethod.POST);
        mQueue.add(Constans.TYPE_FIVE, request, responseListener);
    }

    //意见反馈
    public void sendAdvise(String advice, RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_ADVICE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, (int) SpUtil.getParam(SpConstant.USER_ID, -1))
                .add("idea", advice)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_TWO, request, responseListener);
    }

    //举报  1图片墙  2粉团评论  3一二级评论   ,  dataId 被举报内容id , defendUserId 被举报人id
    public void report(int type, String dataId, int defendUserId, RequersCallBackListener listener) {
        mListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_APP_REPORT, RequestMethod.POST);
        request.add(SpConstant.USER_ID, (int) SpUtil.getParam(SpConstant.USER_ID, -1))
                .add("type", type)
                .add("dataId", dataId)
                .add("defendUserId", defendUserId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {
            mListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response response) {
            mListener.onRequestSuccess(what, response);
        }

        @Override
        public void onFailed(int what, Response response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
            mListener.onRequestError(what, response);
        }

        @Override
        public void onFinish(int what) {
            mListener.onRequestFinish(what);
        }
    };


    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadError(int what, Exception exception) {

        }

        @Override
        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

        }

        @Override
        public void onProgress(int what, int progress, long fileCount, long speed) {

        }

        @Override
        public void onFinish(int what, String filePath) {
            mOnCommonListener.onFinish(what, filePath);
        }

        @Override
        public void onCancel(int what) {

        }
    };


}
