package com.cucr.myapplication.interf.funTuan;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/22.
 */

public interface QueryFtInfoInterf {
    void queryFtInfo(int strId, boolean queryMine, int page, int rows, OnCommonListener listener);

    void ftGoods(int contentId, OnCommonListener listener);
}
