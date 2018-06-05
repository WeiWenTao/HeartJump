package com.cucr.myapplication.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by 911 on 2017/4/10.
 */

public class Constans {

    //用户身份
    public static final int STATUS_EVERYONE = 0;  //无角色
    public static final int STATUS_ADMIN = 1;     //管理员
    public static final int STATUS_STAR = 2;    //明星
    public static final int STATUS_QIYE = 3;    //企业
    public static final int STATUS_COMMON_USER = 4;//普通用户

    public static final int TYPE_HEADER = 1; //头部
    public static final int TYPE_ITEM = 2;   //条目
    public static final int TYPE_FOOTER = 3;   //脚部

    public static final int TYPE_999 = 999;
    public static final int TYPE_998 = 998;
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
    public static final int TYPE_TEN = 10;
    public static final int TYPE_ELEVEN = 11;
    public static final int TYPE_TWEVEN = 12;
    public static final int TYPE_THIRTEEN = 13;
    public static final int TYPE_FOURTEEN = 14;
    public static final int TYPE_FIFTEEN = 15;
    public static final int TYPE_SIXTEEN = 16;
    public static final int TYPE_SEVENTEEN = 17;
    public static final int TYPE_EIGHTEEN = 18;
    public static final int TYPE_NINETEEN = 19;
    public static final int TYPE_TWENTY = 20;

    public static final int TYPE_TEXT = 0;

    public static final int TYPE_PICTURE = 1;

    public static final int TYPE_VIDEO = 2;

    public static final int TYPE_ALBUM = 3;

    public static final int TYPE_NEWS_HEAD = 999;

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
    public static final String PHONE_REGEX = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

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

    //splish 文件名
    public static final String SPLISH_IMG = "splish.png";

    //splish 文件夹
    public static final String SPLISH_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/splishImgs/";
}
