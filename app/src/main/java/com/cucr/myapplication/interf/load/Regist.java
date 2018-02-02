package com.cucr.myapplication.interf.load;

import android.content.Context;

import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.listener.load.OnRegistListener;

/**
 * Created by 911 on 2017/8/15.
 */

public interface Regist {

    void regist(Context context, String phoneNum, String nickName, String psw, OnRegistListener listener, boolean isRegist);

    void getYzm(Context context, String userName, OnGetYzmListener loginListener);

    void thirdPlatformLoad(String loginType, String openId, String msgRegId, RequersCallBackListener commonListener);

    void thirdPlatformRegist(String phone, String checkCode, String loginType, String openId,
                             String name, String gender, String iconurl, RequersCallBackListener commonListener);
}
