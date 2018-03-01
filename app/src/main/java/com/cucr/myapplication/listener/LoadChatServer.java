package com.cucr.myapplication.listener;

import io.rong.imlib.RongIMClient;

/**
 * Created by cucr on 2018/2/28.
 */

public interface LoadChatServer {

    void onLoadSuccess(String userid);

    void onLoadFial(RongIMClient.ErrorCode errorCode);
}
