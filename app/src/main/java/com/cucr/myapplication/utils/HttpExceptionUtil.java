package com.cucr.myapplication.utils;

import android.content.Context;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/14.
 */

public class HttpExceptionUtil {
    public static void showTsByException(Response response, Context context) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            ToastUtils.showToast("请检查网络");

        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastUtils.showToast("请求超时");

        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastUtils.showToast("找不到服务器");

        } else if (exception instanceof URLError) {// URL是错的
            ToastUtils.showToast("URl错误");

        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
        } else {
            ToastUtils.showToast("未知错误:"+ response.getHeaders().getResponseCode());

        }
        Logger.e("错误：" + exception.getMessage());
        return;
    }
}
