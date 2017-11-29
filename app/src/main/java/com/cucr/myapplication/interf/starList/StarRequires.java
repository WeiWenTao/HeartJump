package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

import java.util.List;

/**
 * Created by cucr on 2017/11/1.
 */

public interface StarRequires {

    void addRequires(int id, int assistantNum, int activeScene, int firstClass, int economyClass,
                     int carNum, int bed, int hzs, int fsjj, String qtyq, List<String> startTimeList
            , OnCommonListener listener);

    void queryStarRequire(int StarId,OnCommonListener onCommonListener);
}
