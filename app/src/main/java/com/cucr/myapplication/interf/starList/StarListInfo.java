package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/9/1.
 */

public interface StarListInfo {
    void queryStar(int type, int page, int row, int starId, String userCost, String userType, RequersCallBackListener listener);

    void queryZiDuan(String actionCode, OnCommonListener onCommonListener);

    void querStarByName( int row, int page, String code, RequersCallBackListener onCommonListener);
}
