package com.cucr.myapplication.interf.hyt;

import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucrx on 2018/1/17.
 */

public interface HytInterf {

    void creatHyt(String hytName, String sfzNum, String email, String realName, String positivePic,
                  String negativePic, String coverPic, int starId,
                  String phoneNum, String address, RequersCallBackListener commonListener);

    void queryHyt(int startId, int page, int rows, RequersCallBackListener commonListener);

    void joinHyt(int hytId, RequersCallBackListener commonListener);

    void querYyhdJE(String actionCode, RequersCallBackListener commonListener);

    void createYyhd(String activeName, String endTime, String activeContent, int startId,
                    int activeType, String amount, String yzsm, String yyje, String bgInfoIds,
                    String city, String scale, String amount3, String explains, String picUrl,
                    RequersCallBackListener commonListener);

    void queryBigPadInfo(RequersCallBackListener commonListener);

    //后援活动查询
    void queryHytActive(int page, int rows, int starId, RequersCallBackListener commonListener);

    //后援活动支持者查询
    void querySupport(int page, int rows, int activeId, RequersCallBackListener commonListener);

    //后援评论查询
    void queryComment(int page, int rows, int activeId, RequersCallBackListener commonListener);

    //后援评论
    void YyhdComment(int activeId, String content, RequersCallBackListener commonListener);

    //后援团评论点赞
    void YyhdCommentGood(int commentId, int activeId, RequersCallBackListener commonListener);

    //后援团点赞
    void YyhdGood(int activeId, RequersCallBackListener commonListener);

    //后援团成员查询
    void queryMembers(int page, int rows, String hytId, RequersCallBackListener commonListener);

    //退出后援团
    void leaveHyt(String hytId, RequersCallBackListener commonListener);

    //解散后援团
    void dissolveHyt(String hytId, RequersCallBackListener commonListener);

    //禁言
    void hytLock(int lockd, String hytId, int howlong, RequersCallBackListener commonListener);

    //解除禁言
    void hytUnLock(String lockd, String hytId, RequersCallBackListener commonListener);

    //获取禁言列表
    void hytLockList(String hytId, RequersCallBackListener commonListener);
/*
    //应援活动支持
    void yyhdSupport(String amount, String activeId, RequersCallBackListener commonListener);*/
}
