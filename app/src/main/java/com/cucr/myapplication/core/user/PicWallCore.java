package com.cucr.myapplication.core.user;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.user.PicturesWall;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2018/1/2.
 */

public class PicWallCore implements PicturesWall {

    private RequersCallBackListener listener;
    private OnCommonListener goodListener;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;
    private List<Binary> files;

    public PicWallCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
        files = new ArrayList<>();
    }

    //图集查询
    @Override
    public void queryPic(int page, int rows, int orderType, boolean queryMine, int startId, RequersCallBackListener onCommonListener) {
        this.listener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PIC_QUERY, RequestMethod.POST);
        if (startId != -1) {
            request.add("startId", startId);
        }
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("orderType", orderType)
                .add("queryMine", queryMine)
                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_ONE, request, callback);
    }

    @Override
    public void queryMyFavoritePic(int page, int rows, RequersCallBackListener onCommonListener) {
        this.listener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PIC_FAVORITE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("page", page)
                .add("rows", rows)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FORE, request, callback);
    }

    @Override
    public void upLoadPic(int startId, List<LocalMedia> mData, RequersCallBackListener commonListener) {
        this.listener = commonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PIC_UPLOAD, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("startId", startId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        for (int i = 0; i < mData.size(); i++) {
            String compressPath = mData.get(i).getCompressPath();
            BasicBinary binary = new FileBinary(new File(compressPath), compressPath.substring(compressPath.lastIndexOf("/")));
            binary.setUploadListener(i, mOnUploadListener);
            files.add(binary);
        }
        MyLogger.jLog().i("filesSize:" + files.size());
        request.add("pic", files);
        mQueue.add(Constans.TYPE_TWO, request, callback);
    }

    @Override
    public void picGoods(int dataId, int giveUpCount, OnCommonListener onCommonListener) {
        this.goodListener = onCommonListener;
        MyLogger.jLog().i("dataId:" + dataId + ",giveUpCount:" + giveUpCount);
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PIC_GOODS, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add("giveUpCount", giveUpCount)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_THREE, request, callback);
    }

    //图集删除
    @Override
    public void delPic(int dataId, RequersCallBackListener onCommonListener) {
        this.listener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PIC_DELETE, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("dataId", dataId)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FIVE, request, callback);
    }


    OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            switch (what) {
                case Constans.TYPE_ONE:
                case Constans.TYPE_TWO:
                case Constans.TYPE_FORE:
                case Constans.TYPE_FIVE:
                    listener.onRequestStar(what);
                    break;
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {

                case Constans.TYPE_ONE:
                case Constans.TYPE_TWO:
                case Constans.TYPE_FORE:
                case Constans.TYPE_FIVE:
                    listener.onRequestSuccess(what, response);
                    break;

                case Constans.TYPE_THREE:
                    goodListener.onRequestSuccess(response);
                    break;

            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {
            switch (what) {
                case Constans.TYPE_TWO:
                    files.clear();
                case Constans.TYPE_ONE:
                case Constans.TYPE_FORE:
                case Constans.TYPE_FIVE:
                    listener.onRequestFinish(what);
                    break;
            }
        }
    };


    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            MyLogger.jLog().i("UploadonStart");
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            MyLogger.jLog().i("UploadonCancel");
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            MyLogger.jLog().i("第" + what + "张:" + progress);
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            MyLogger.jLog().i("UploadonFinish");
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            MyLogger.jLog().i("UploadonError");
        }
    };
}
