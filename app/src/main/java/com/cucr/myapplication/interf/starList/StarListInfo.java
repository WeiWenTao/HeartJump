package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/1.
 */

public interface StarListInfo {
    void queryStar(int type, int page, int row, int starId, String userCost, String userType, OnCommonListener onCommonListener);

    void queryZiDuan(String actionCode, OnCommonListener onCommonListener);
}
