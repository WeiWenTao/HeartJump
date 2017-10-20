package com.cucr.myapplication.listener.Pay;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2017/10/16.
 */

public interface PayLisntener {
    void onRequestStar();

    void onSuccess(Response<String> response);

}
