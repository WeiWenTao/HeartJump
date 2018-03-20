package com.cucr.myapplication.interf.personalinfo;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by 911 on 2017/8/18.
 */

public interface QueryPersonalInfo {
    void queryPersonalInfo(OnCommonListener onCommonListener);
    void queryPersonalById(String userId,RequersCallBackListener onCommonListener);
    void queryHytInfoById(String userId,RequersCallBackListener onCommonListener);
}
