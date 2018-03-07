package com.cucr.myapplication.core.renZheng;

import android.app.Activity;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.renZheng.CommitStarRZ;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.dialog.WaitDialog;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/23.
 */

public class CommitStarRzCore implements CommitStarRZ {

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    private Object sign = new Object();

    private OnCommonListener listener;
    private WaitDialog mWaitDialog;
    private Activity activity;

    public CommitStarRzCore(Activity activity) {
        this.activity = activity;
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void onCommStarRZ(String userName,
                             String belongCompany,
                             String contact,
                             String starPrice,
                             String pic1, String pic2,
                             Integer id,
                             OnCommonListener listener) {
        this.listener = listener;
        mWaitDialog = new WaitDialog(activity,"正在提交...");
        // 创建请求对象。
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST
                + HttpContans.ADDRESS_STAR_RZ, RequestMethod.POST);

        // 添加请求参数。
        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1))) // String型。
                .add("userName", userName)
                .add("contact", contact)
                .add("belongCompany", belongCompany)
                .add("startCost", starPrice);
        if (id != null) {
            request.add("dataId", id); // flocat型。
        }
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(MyApplication.getInstance(), request.getParamKeyValues()));

//        FileBinary binary1 = new FileBinary(new File(pic1));
        //"1"代表用户审核未通过 且未重新选择上传照片

        if (pic1 != "1") {
            BitmapBinary binary1 = new BitmapBinary(CommonUtils.decodeBitmap(pic1), pic1.substring(pic1.lastIndexOf("/")));
            request.add("pic1", binary1);
            binary1.setUploadListener(0, mOnUploadListener);
        }


        if (pic2 != "1") {
//        FileBinary binary2 = new FileBinary(new File(pic2));
            BitmapBinary binary2 = new BitmapBinary(CommonUtils.decodeBitmap(pic2), pic1.substring(pic2.lastIndexOf("/")));
            request.add("pic2", binary2);
            binary2.setUploadListener(1, mOnUploadListener);
        }

        // 设置取消标志。
        request.setCancelSign(sign);

		/*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样。
		 * request: 请求对象。
		 * onResponseListener 回调对象，接受请求结果。
		 */
        mQueue.add(0, request, onResponseListener);
    }

    /**
     * 回调对象，接受请求结果.
     */
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == 0) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。
                listener.onRequestSuccess(response);
            }
        }

        @Override
        public void onStart(int what) {
            mWaitDialog.show();
        }

        @Override
        public void onFinish(int what) {
            mWaitDialog.dismiss();
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            //TODO 特别注意：这里可能有人会想到是不是每个地方都要这么判断，其实不用，请参考HttpResponseListener类的封装，你也可以这么封装。
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }
    };


    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            if (what == 0) {
                MyLogger.jLog().i("pic1:onStart");
            } else {
                MyLogger.jLog().i("pic2:onStart");
            }
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            if (what == 0) {
                MyLogger.jLog().i("pic1:onCancel");
            } else {
                MyLogger.jLog().i("pic2:onCancel");
            }
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            if (what == 0) {
                MyLogger.jLog().i("pic1:onProgress" + progress);
            } else {
                MyLogger.jLog().i("pic2:onProgress" + progress);
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
            } else {
                MyLogger.jLog().i("pic2:onError");
            }
        }
    };

    public void stopReques() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(sign);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();
    }
}
