package com.cucr.myapplication.interf.daShang;

import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/11/8.
 */

public interface DaShangInterf {

    void reward(int rewardContentId, int payType, int rewardType, int rewardMoney, RequersCallBackListener commonListener);

    void queryDsList(int rows, int page, int rewardContentId, RequersCallBackListener commonListener);

    void queryDsMe(int queryMine, int rows, int page, RequersCallBackListener commonListener);
}
