package com.cucr.myapplication.listener.load;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/15.
 */

public interface OnRegistListener {
    void OnRegistSuccess(Response<String> response);
    void onRegistFailed();
}
