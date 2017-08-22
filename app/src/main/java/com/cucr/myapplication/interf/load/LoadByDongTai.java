package com.cucr.myapplication.interf.load;

import android.content.Context;

import com.cucr.myapplication.listener.OnDongTaiLoginListener;
import com.cucr.myapplication.listener.OnGetYzmListener;

/**
 * Created by 911 on 2017/8/11.
 */

public interface LoadByDongTai {
     void login(Context context, String userName, String yzm, OnDongTaiLoginListener loginListener);
     void getYzm(Context context,String userName,OnGetYzmListener loginListener);
}
