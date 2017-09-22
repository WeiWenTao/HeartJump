package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/15.
 */

public interface QueryJourney {

    //查询行程
    void QueyrStarJourney(int rows, int page, int starId, String tripTime, OnCommonListener listener);

    /**
     * 查询行程时间表
     */
    void queryJourneySchedule(int starId, OnCommonListener commonListener);

    /**
     * 查询行程详情
     */
    void queryJourneyCatgory(int starId, String time, OnCommonListener commonListener);

}
