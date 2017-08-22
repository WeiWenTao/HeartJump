package com.cucr.myapplication.interf.personalinfo;

import android.content.Context;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.EditPersonalInfo.PersonalInfo;

/**
 * Created by 911 on 2017/8/15.
 */

public interface SavePersonalInfo {
    void save(Context context, PersonalInfo personalInfo, OnCommonListener commonListener);
}
