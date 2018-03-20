package com.cucr.myapplication.interf.msg;

import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2018/3/19.
 */

public interface MsgInterf {
    void queryMsgInfo(int page, int rows, int type, RequersCallBackListener callBackListener);
}
