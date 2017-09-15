package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/15.
 */

public interface QueryJourney {
    void QueyrStarJourney(int rows, int page, int starId, String tripTime, OnCommonListener listener);
}
