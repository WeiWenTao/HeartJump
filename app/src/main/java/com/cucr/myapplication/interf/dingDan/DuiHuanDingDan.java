package com.cucr.myapplication.interf.dingDan;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by 911 on 2017/8/21.
 */

public interface DuiHuanDingDan {
    void onDuiHuan(String local, String address, String rececivedPerson, String rececivedPhone, int num, int shopId, OnCommonListener listener);
}
