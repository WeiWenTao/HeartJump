package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/6.
 */

public interface MyFocusStars {
    void queryMyFocuses(int queryUserId, int page, int rows, OnCommonListener onCommonListener);
}
