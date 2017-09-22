package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/6.
 */

public interface Journey {

    /**
     * 添加行程
     */
    void addJourney(String place, String content, String tripTime, OnCommonListener commonListener); /**

     * 删除行程
     */
    void deleteJourney(int dataId, OnCommonListener commonListener);
}
