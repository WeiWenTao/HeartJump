package com.cucr.myapplication.interf.fuli;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/12/6.
 */

public interface HuoDongInterf {
    void publishActive(String activeName, String activePlace, String activeAdress,
                       String activeStartTime, int ys, String activeInfo, int openys,
                       String picurl, int page, int rows, OnCommonListener onCommonListener);
}
