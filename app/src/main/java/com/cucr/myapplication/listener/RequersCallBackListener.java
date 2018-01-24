package com.cucr.myapplication.listener;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/15.
 */

public interface RequersCallBackListener {
    void onRequestSuccess(int what,Response<String> response);
    void onRequestStar(int what);
    void onRequestFinish(int what);
}
