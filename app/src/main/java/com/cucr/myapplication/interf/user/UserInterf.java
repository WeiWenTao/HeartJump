package com.cucr.myapplication.interf.user;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/11/29.
 */

public interface UserInterf {
    //查询用户中心信息
    void queryUserCenterInfo(int userId, OnCommonListener listener);
}
