package com.cucr.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cucr.myapplication.BuildConfig;
import com.cucr.myapplication.constants.SpConstant;


/**
 * Created by Teacher on 2017/2/26.
 */

public class SpUtil {
    public static void writeFirstRun(Context context){
        SharedPreferences sp = context.getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(SpConstant.IS_FIRST_RUN, BuildConfig.VERSION_CODE).commit();
    }

    public static boolean readFirstRun(Context context){
        return  context.getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE)
                .getInt(SpConstant.IS_FIRST_RUN, 0) < BuildConfig.VERSION_CODE;
    }
}
