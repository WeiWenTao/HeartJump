package com.cucr.myapplication.interf.yuyue;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/11/3.
 */

public interface YuYueInterf {

    void yuYueStar(int starId, String activeName, String activePlace, String activeAdress,
                   String activeStartTime, String activeEndTime, int activeScene, String activeInfo,
                   int peopleCount, OnCommonListener listener);

}
