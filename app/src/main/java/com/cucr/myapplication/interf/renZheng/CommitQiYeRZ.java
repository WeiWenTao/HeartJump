package com.cucr.myapplication.interf.renZheng;

import android.graphics.Bitmap;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by 911 on 2017/8/23.
 */

public interface CommitQiYeRZ {

    void onCommStarRZ(String companyName, String userName, String contact, String companyContact, Bitmap pic1, Bitmap pic2, Bitmap pic3, OnCommonListener listener);
}
