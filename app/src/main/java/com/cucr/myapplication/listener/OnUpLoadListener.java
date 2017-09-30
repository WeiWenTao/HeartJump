package com.cucr.myapplication.listener;

import com.lidroid.xutils.http.ResponseInfo;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/9/29.
 */

public interface OnUpLoadListener {
    void OnUpLoadPicListener(Response<String> response);

    void OnUpLoadVideoListener(ResponseInfo<String> arg0);

    void OnUpLoadTextListener(Response<String> response);
}
