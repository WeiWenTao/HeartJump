package com.cucr.myapplication.interf.personalinfo;

import android.content.Context;

import com.cucr.myapplication.bean.EditPersonalInfo.PersonalInfo;
import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by 911 on 2017/8/15.
 */

public interface SavePersonalInfo {
    void save(Context context, PersonalInfo personalInfo, RequersCallBackListener commonListener);
}
