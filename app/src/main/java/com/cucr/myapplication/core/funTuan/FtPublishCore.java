package com.cucr.myapplication.core.funTuan;

import android.app.Activity;

import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.funTuan.FenTuanInterf;
import com.cucr.myapplication.interf.nohttp.HttpListener;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/9/21.
 */

public class FtPublishCore extends BaseCore implements FenTuanInterf {

    private Activity activity;
    private List<Binary> files;

    public FtPublishCore(Activity activity) {
        this.activity = activity;
        files = new ArrayList<>();
    }

    @Override
    public void publishFtInfo(int starId, int type, String content, List<LocalMedia> mData, final OnCommonListener listener) {
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_PUBLISH_FT_INFO, RequestMethod.POST);
        // 添加普通参数。
        request.add("userId", ((int) SpUtil.getParam(activity, SpConstant.USER_ID, -1)))
                .add("startId", starId)
                .add("type", type)
                .add("content", content)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(activity, request.getParamKeyValues()));

        //图片
        if (type == 1) {
            for (LocalMedia localMedia : mData) {
                String compressPath = localMedia.getCompressPath();
                MyLogger.jLog().i("picturePath："+compressPath);
                files.add(new FileBinary(new File(compressPath), compressPath.substring(compressPath.lastIndexOf("/"))));
            }
            //视频  视只有一个
        } else if (type == 2) {
            LocalMedia localMedia = mData.get(0);
            String videoPath = localMedia.getPath();
            files.add(new FileBinary(new File(videoPath), videoPath.substring(videoPath.lastIndexOf("/"))));
            MyLogger.jLog().i("videoPath："+videoPath);
        }

        request.add("file", files);

        //回调
        HttpListener<String> callback = new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                MyLogger.jLog().i("请求成功");
                listener.onRequestSuccess(response);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MyLogger.jLog().i("请求失败");
            }
        };


        request(0, request, callback, false, true);
    }

    @Override
    public Activity getChildActivity() {
        return activity;
    }

}
