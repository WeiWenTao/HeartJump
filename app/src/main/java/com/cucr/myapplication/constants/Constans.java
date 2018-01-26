package com.cucr.myapplication.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by 911 on 2017/4/10.
 */

public class Constans {

    //用户身份
    public static final int TYPE_EVERYONE = 0;  //无角色
    public static final int TYPE_ADMIN = 1;     //管理员
    public static final int STATUS_STAR = 2;    //明星
    public static final int STATUS_QIYE = 3;    //企业
    public static final int TYPE_COMMON_USER = 4;//普通用户

    public static final int TYPE_HEADER = 1; //头部
    public static final int TYPE_ITEM = 2;   //条目

    public static final int TYPE_ZERO = 0;
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public static final int TYPE_FORE = 4;
    public static final int TYPE_FIVE = 5;
    public static final int TYPE_SIX = 6;
    public static final int TYPE_SEVEN = 7;
    public static final int TYPE_EIGHT = 8;
    public static final int TYPE_NINE = 9;

    public static final int TYPE_TEXT = 0;

    public static final int TYPE_PICTURE = 1;

    public static final int TYPE_VIDEO = 2;



    public static final int TYPE_OTHER = -1;

    public static final String ACCOUNTS = "accounts";

    public static final String POSITION = "position";

    //正文   点赞
    public static final int STATE_LIKE = 4;
    //正文   评论
    public static final int STATE_COMMENT = 5;

    //省市区数据库文件的绝对路径
    public static final String LOCATION_PATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase",
            "city.db").getAbsolutePath();

    //省市区数据库文件夹路径
    public static final String LOCATIY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase";

    //表名 “省”
    public static final String TABLENAME_PROVINCE = "province";

    //表名 “市”
    public static final String TABLENAME_CITY = "city";

    //表名 “区”
    public static final String TABLENAME_DISTRICT = "district";

    //电话号码正则
    public static final String PHONE_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";

    //用户名正则
    public static final String USERNAME_REGEX = "[~!/@#$%^&*()\\\\-_=+\\\\|[{}];:\\'\\\",<.>/?]+";

    //明星认证
    public static final int RZ_STAR = 0;

    //企业认证
    public static final int RZ_QIYE = 1;

    //Activity跳转请求码
    public static final int REQUEST_CODE = 999;

    //Activity跳转结果码
    public static final int RESULT_CODE = 998;

    //礼物类型 么么哒
    public static final int GIFT_KISS = 1;

    //礼物类型 666
    public static final int GIFT_666 = 2;

    //礼物类型 钻石
    public static final int GIFT_DIAMON = 3;

    //礼物类型 火箭
    public static final int GIFT_ROCKET = 4;
}
