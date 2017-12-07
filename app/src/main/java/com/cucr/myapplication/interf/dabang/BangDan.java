package com.cucr.myapplication.interf.dabang;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/10/28.
 */

public interface BangDan {
    void queryBangDanInfo(int page,int rows,OnCommonListener listener);

    void daBang(int money,int starId,OnCommonListener listener);
}
