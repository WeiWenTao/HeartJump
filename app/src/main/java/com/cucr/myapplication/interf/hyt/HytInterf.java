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
                    int activeType, String amount, String yzsm,  String yyje,  String bgInfoIds,
                    String city, String scale,String amount3,String explains,String picUrl,
                    RequersCallBackListener commonListener);

    void queryBigPadInfo(RequersCallBackListener commonListener);

    //后援活动查询
    void queryHytActive(int page, int rows, int starId, RequersCallBackListener commonListener);


//    void leaveHyt();
}
