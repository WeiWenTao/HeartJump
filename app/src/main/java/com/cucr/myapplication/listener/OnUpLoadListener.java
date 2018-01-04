package com.cucr.myapplication.listener;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/29.
 */

public interface OnUpLoadListener {
    void OnUpLoadPicListener(Response<String> response);

    void OnUpLoadVideoListener(Response<String> response);

    void OnUpLoadTextListener(Response<String> response);
}
