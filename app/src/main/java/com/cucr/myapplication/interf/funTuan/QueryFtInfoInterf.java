package com.cucr.myapplication.interf.funTuan;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/9/22.
 */

public interface QueryFtInfoInterf {
    //查询粉团(新闻)文章
    void queryFtInfo(int strId,int dataType,int queryUserId, boolean queryMine, int page, int rows, RequersCallBackListener listener);

    //查询单条粉团(新闻)文章
    void querySignleFtInfo(String ftId, RequersCallBackListener listener);

    //查询新闻关注文章
    void queryFtInfo(boolean focus,int strId,int dataType,int queryUserId, boolean queryMine, int page, int rows, RequersCallBackListener listener);

    void ftGoods(int contentId, OnCommonListener listener);

    void toComment(int contentId,int commentId,String content, OnCommonListener listener);

    void queryGift(OnCommonListener listener);

    void queryBackpackInfo(OnCommonListener listener);

    void ftRead(String dataId);

}
