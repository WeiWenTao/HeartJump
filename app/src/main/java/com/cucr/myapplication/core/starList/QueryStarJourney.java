package com.cucr.myapplication.core.starList;

import android.app.Activity;

import com.cucr.myapplication.core.BaseCore;
import com.cucr.myapplication.interf.starList.Journey;
import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/6.
 */

public class QueryStarJourney extends BaseCore implements Journey {

    @Override
    public Activity getChildActivity() {
        return null;
    }

    @Override
    public void queryJourneySchedule(int starId, OnCommonListener commonListener) {

    }

    @Override
    public void queryJourneyCatgory(int starId, String time, OnCommonListener commonListener) {

    }
}
