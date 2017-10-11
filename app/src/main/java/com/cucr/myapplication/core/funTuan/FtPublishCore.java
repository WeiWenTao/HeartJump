package com.cucr.myapplication.core.funTuan;

import android.app.Activity;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.funTuan.FenTuanInterf;
import com.cucr.myapplication.listener.OnUpLoadListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.dialog.DialogProgress;
import com.cucr.myapplication.widget.dialog.WaitDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.multipart.MIME;
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
 * Created by cucr on 2017/9/21.
 */

public class FtPublishCore implements FenTuanInterf {

    private Activity activity;
    private List<Binary> files;
    private String sign;
    private WaitDialog dialog;
    private DialogProgress dialog_progress;
    private OnUpLoadListener listener;
    private int type;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private final HttpUtils mUtils;
    private HttpHandler<String> mSend;

    public FtPublishCore(Activity activity) {
        this.activity = activity;
        dialog = new WaitDialog(activity,"正在上传...");
        dialog_progress = new DialogProgress(activity, R.style.BirthdayStyleTheme);
        //点击屏幕外部和返回键不响应

        files = new ArrayList<>();
        mQueue = NoHttp.newRequestQueue();
        // 设置连接超时
        mUtils = new HttpUtils(50000);
    }

    @Override
    public void publishFtInfo(int starId, final int type, String content, List<LocalMedia> mData, final OnUpLoadListener listener) {
        this.listener = listener;
        this.type = type;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PUBLISH_FT_INFO, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(activity, SpConstant.USER_ID, -1)))
                .add("startId", starId)
                .add("type", type)
                .add("content", content);
        sign = EncodingUtils.getEdcodingSReslut(activity, request.getParamKeyValues());
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(activity, request.getParamKeyValues()));

        //图片
        if (type == 1) {
            for (int i = 0; i < mData.size(); i++) {
                String compressPath = mData.get(i).getCompressPath();
                BasicBinary binary = new FileBinary(new File(compressPath), compressPath.substring(compressPath.lastIndexOf("/")));
                binary.setUploadListener(i,mOnUploadListener);
                files.add(binary);
            }
            request.add("file", files);
            mQueue.add(Constans.TYPE_ONE, request, callback);

            //视频  视只有一个
        } else if (type == 2) {
            LocalMedia localMedia = mData.get(0);
            String videoPath = localMedia.getPath();
            //文件上传地址
            String uploadHost = HttpContans.HTTP_HOST + HttpContans.ADDRESS_PUBLISH_FT_INFO;
            RequestParams params = new RequestParams();

            // token的值，身份的唯一标识
            params.addBodyParameter("userId", ((int) SpUtil.getParam(activity, SpConstant.USER_ID, -1)) + "");
            params.addBodyParameter("startId", starId + "");
            params.addBodyParameter("type", type + "");
            params.addBodyParameter("content", content + "");
            params.addBodyParameter(SpConstant.SIGN, sign);

            // 文件的路径
            params.addBodyParameter("file", new File(videoPath), videoPath.substring(videoPath.lastIndexOf("/")), MIME.ENC_BINARY, "utf-8");
            MyLogger.jLog().i("videoPath:" + videoPath);
            mSend = mUtils.send(HttpRequest.HttpMethod.POST, uploadHost, params,
                    new RequestCallBack<String>() {

                        @Override
                        public void onStart() {
                            super.onStart();
                            MyLogger.jLog().i("开始上传");
                            dialog_progress.show();
                        }

                        @Override
                        public void onLoading(long total, long current,
                                              boolean isUploading) {
                            super.onLoading(total, current, isUploading);
                            MyLogger.jLog().i(current + "/" + total);
                            dialog_progress.setProgress((int) (current * 100 / total));
                        }

                        @Override
                        public void onSuccess(ResponseInfo<String> arg0) {
                            MyLogger.jLog().i("上传成功");
                            listener.OnUpLoadVideoListener(arg0);
                            dialog_progress.dismiss();
                        }

                        @Override
                        public void onFailure(HttpException arg0, String arg1) {
                            MyLogger.jLog().i(arg1);
                            dialog_progress.dismiss();
                        }
                    });

        }
    }


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
            MyLogger.jLog().i("第"+what+"张:"+progress);
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

    //回调
    private OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            dialog.show();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    if (type == 1) {
                        listener.OnUpLoadPicListener(response);
                    } else if (type == 0) {
                        listener.OnUpLoadTextListener(response);
                    }
                    dialog.dismiss();
                    break;

            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, activity);
            dialog.dismiss();
            switch (what) {
                case Constans.TYPE_ONE:

                    break;

                case Constans.TYPE_TWO:

                    break;
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stopRequest() {
        if (mQueue != null) {
            mQueue.cancelAll();
            mQueue.stop();
        }

        if (mSend != null) {
            mSend.cancel();
        }

    }

   /* *//**
     * 文件上传监听。
     *//*
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            if (what == 0) {
                MyLogger.jLog().i("pic1:onStart");
            } else if (what == 1) {
                MyLogger.jLog().i("pic2:onStart");
            } else {
                MyLogger.jLog().i("pic3:onStart");
            }
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            if (what == 0) {
                MyLogger.jLog().i("pic1:onCancel");
            } else if (what == 1) {
                MyLogger.jLog().i("pic2:onCancel");
            } else {
                MyLogger.jLog().i("pic3:onCancel");
            }
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            if (what == 0) {
                MyLogger.jLog().i("pic1:onProgress" + progress);
            } else if (what == 1) {
                MyLogger.jLog().i("pic2:onProgress" + progress);
            } else {
                MyLogger.jLog().i("pic3:onProgress" + progress);
            }
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            if (what == 0) {
                MyLogger.jLog().i("pic1:onFinish");
            } else {
                MyLogger.jLog().i("pic2:onFinish");
            }
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            if (what == 0) {
                MyLogger.jLog().i("pic1:onError");
            } else if (what == 1) {
                MyLogger.jLog().i("pic2:onError");
            } else {
                MyLogger.jLog().i("pic3:onError");
            }
        }
    };*/
}
