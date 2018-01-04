package com.cucr.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cucr.myapplication.BuildConfig;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;

import static android.content.Context.MODE_PRIVATE;
import static com.cucr.myapplication.constants.SpConstant.SP_NAME;


/**
 * Created by Teacher on 2017/2/26.
 */

public class SpUtil {

    //sp实例
    private static SharedPreferences sp;
    //账号管理专用sp
    private static SharedPreferences accountSp;

    public static SharedPreferences getSp(){
        if (sp == null) {
            sp = MyApplication.getInstance().getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static SharedPreferences getAccountSp(){
        if (accountSp == null) {
            accountSp = MyApplication.getInstance().getSharedPreferences(Constans.ACCOUNTS, Context.MODE_PRIVATE);
        }
        return accountSp;
    }


    public static void setParam(String key, Object object) {
        if (sp == null) {
            sp = MyApplication.getInstance().getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = sp.edit();
        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }



    public static Object getParam(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        if (sp == null) {
            sp = MyApplication.getInstance().getSharedPreferences(SpConstant.SP_NAME, Context.MODE_PRIVATE);
        }
        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public static void writeFirstRun(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE);
        sp.edit().putInt(SpConstant.IS_FIRST_RUN, BuildConfig.VERSION_CODE).commit();
    }

    public static boolean readNeedUpdata(Context context) {
        return context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getInt(SpConstant.IS_FIRST_RUN, 0) < BuildConfig.VERSION_CODE;
    }

//    public static boolean isFirstRun(Context context) {
//        return context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
//                .getInt(SpConstant.IS_FIRST_RUN, -1) == -1;
//    }
}
