package com.cucr.myapplication.listener;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/11.
 */

public interface OnDongTaiLoginListener {
    void onSuccess(Response<String> response);
    void onFailed();
}