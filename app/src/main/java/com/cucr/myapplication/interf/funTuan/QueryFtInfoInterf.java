package com.cucr.myapplication.interf.funTuan;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/22.
 */

public interface QueryFtInfoInterf {
    //查询粉团文章
    void queryFtInfo(int strId,int dataType,int queryUserId, boolean queryMine, int page, int rows, OnCommonListener listener);

    void ftGoods(int contentId, OnCommonListener listener);

    void toComment(int contentId,int commentId,String content, OnCommonListener listener);

    void queryGift(OnCommonListener listener);

    void queryBackpackInfo(OnCommonListener listener);

}
