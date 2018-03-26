package com.cucr.myapplication.interf.fuli;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/12/6.
 */

public interface HuoDongInterf {

    //发布活动
    void publishActive(String activeName,String activeAdress,
                       String activeStartTime, int ys, String activeInfo, int openys, int peopleCount,
                       String picurl, RequersCallBackListener onCommonListener);

    //查询活动
    void queryActive(boolean byMe, int dataId, int page, int rows, RequersCallBackListener onCommonListener);

    //活动点赞
    void activeGiveUp(int dataId, OnCommonListener onCommonListener);

    //活动评论
    void activeComment(int dataId,String comment,int commentParentId, OnCommonListener onCommonListener);

    //活动评论查询
    void activeCommentQuery(int contentId,int parentId, int page, int rows, OnCommonListener onCommonListener);

    //活动评论点赞
    void activeCommentGood(int contentId, int dataId, OnCommonListener onCommonListener);

}
