package com.cucr.myapplication.core.funTuanAndXingWen;

import android.app.Activity;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.funTuan.FenTuanInterf;
import com.cucr.myapplication.listener.OnUpLoadListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogProgress;
import com.cucr.myapplication.widget.dialog.WaitDialog;
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
    private WaitDialog dialog;
    private DialogProgress dialog_progress;
    private OnUpLoadListener listener;
    private int type;
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    public FtPublishCore(Activity activity) {
        this.activity = activity;
        dialog = new WaitDialog(activity, "正在上传...");
        dialog_progress = new DialogProgress(activity, R.style.BirthdayStyleTheme);
        //点击屏幕外部和返回键不响应
        files = new ArrayList<>();
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void publishFtInfo(int starId, final int type, String content, List<LocalMedia> mData, final OnUpLoadListener listener) {
        this.listener = listener;
        this.type = type;
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_PUBLISH_FT_INFO, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("startId", starId)
                .add("type", type)
                .add("content", content);
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));

        //文字
        if (type == 0) {
            mQueue.add(Constans.TYPE_ONE, request, callback);
        //图片
        } else if (type == 1) {
            files.clear();
            for (int i = 0; i < mData.size(); i++) {
                String compressPath = mData.get(i).getCompressPath();
                BasicBinary binary = new FileBinary(new File(compressPath), compressPath.substring(compressPath.lastIndexOf("/")));
                binary.setUploadListener(i, mOnUploadListener);
                files.add(binary);

            }
            request.add("file", files);
            mQueue.add(Constans.TYPE_ONE, request, callback);

            //视频  视只有一个
        } else if (type == 2) {
            files.clear();
            LocalMedia localMedia = mData.get(0);
            String videoPath = localMedia.getPath();
            //------------------------------------------------------------
//            MyLogger.jLog().i("Compress_开始压缩");
//            MyLogger.jLog().i("Compress_压缩成功：" + videoPath);
            //------------------------------------------------------------
            BasicBinary binary = new FileBinary(new File(videoPath), videoPath.substring(videoPath.lastIndexOf("/")));
            binary.setUploadListener(10, mOnUploadListener);
            files.add(binary);
            request.add("file", files);
            mQueue.add(Constans.TYPE_ONE, request, callback);

        }
    }

    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            MyLogger.jLog().i("UploadonStart");
            if (what == 10) {
                dialog_progress.show();
                dialog_progress.setProgress(0);
            }

        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            MyLogger.jLog().i("UploadonCancel");
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边刷
            if (what == 10) {
                dialog_progress.setProgress(progress);
                MyLogger.jLog().i("Uploadonvideo" + progress);
            }

        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            MyLogger.jLog().i("UploadonFinish");
            if (what == 10) {
                dialog_progress.dismiss();
            }

        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            MyLogger.jLog().i("UploadonError");
            if (what == 10) {
                ToastUtils.showToast("服务器繁忙，请稍后再试");
                dialog_progress.dismiss();
            }
        }
    };

    //回调
    private OnResponseListener<String> callback = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            if (type != 2) {
                dialog.show();
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    if (type == 1) {
                        listener.OnUpLoadPicListener(response);
                    } else if (type == 0) {
                        listener.OnUpLoadTextListener(response);
                    } else if (type == 2) {
                        dialog_progress.dismiss();
                        listener.OnUpLoadVideoListener(response);
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
    }
}
