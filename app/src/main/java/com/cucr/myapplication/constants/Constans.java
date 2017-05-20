package com.cucr.myapplication.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by 911 on 2017/4/10.
 */

public class Constans {
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;

    public static final int TYPE_OTHER = -1;

    //正文   点赞
    public static final int STATE_LIKE = 4;
    //正文   评论
    public static final int STATE_COMMENT = 5;

    //省市区数据库文件的绝对路径
    public static final String LOCATION_PATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/dataBase",
            "city.db").getAbsolutePath();

    //省市区数据库文件夹路径
    public static final String LOCATIY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/dataBase";

    //表名 “省”
    public static final String TABLENAME_PROVINCE = "province";

    //表名 “市”
    public static final String TABLENAME_CITY = "city";

    //表名 “区”
    public static final String TABLENAME_DISTRICT = "district";

}