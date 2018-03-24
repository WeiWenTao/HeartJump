package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/9/6.
 */

public interface MyFocusStars {
    void queryMyFocusStars(int queryUserId, int page, int rows, RequersCallBackListener onCommonListener);

    void queryMyFocusOthers(int page, int rows, RequersCallBackListener requersCallBackListener);

    void queryMyFens(int page, int rows, RequersCallBackListener requersCallBackListener);
}
