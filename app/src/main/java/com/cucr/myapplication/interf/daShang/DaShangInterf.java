package com.cucr.myapplication.interf.daShang;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/11/8.
 */

public interface DaShangInterf {

    void reward(int rewardContentId, int payType, int rewardType, int rewardMoney, OnCommonListener commonListener);

    void queryDsList(int rewardContentId,OnCommonListener commonListener);

    void queryDsMe(int queryMine,int rows,int page,OnCommonListener commonListener);
}
