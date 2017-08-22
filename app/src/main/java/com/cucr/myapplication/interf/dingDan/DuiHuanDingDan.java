package com.cucr.myapplication.interf.dingDan;

import android.content.Context;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by 911 on 2017/8/21.
 */

public interface DuiHuanDingDan {
    void onDuiHuan(Context context, String local, String address, String rececivedPerson, String rececivedPhone, int num, int shopId, OnCommonListener listener);
}
