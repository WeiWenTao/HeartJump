package com.cucr.myapplication.constants;

/**
 * Created by 911 on 2017/8/14.
 * Http常量
 */

public class HttpContans {
    //域名
//    public static final String HTTP_HOST = "http://192.168.1.132:8080";

    public static final String HTTP_HOST = "http://192.168.1.139:8080";

    //注册
    public static final String ADDRESS_REGIST = "/interface/user/regist";

    //验证码
    public static final String ADDRESS_YZM = "/interface/checkCode/getCheckCode";

    //动态登录
    public static final String ADDRESS_DONGTAI_LOAD = "/interface/user/dynamicLogin";

    //动态登录
    public static final String ADDRESS_PSW_LOAD = "/interface/user/login";

    //用户信息编辑
    public static final String ADDRESS_EDIT_USERINFO = "/interface/user/edit";

    //用户信息查询
    public static final String ADDRESS_QUERY_USERINFO = "/interface/user/queryMine";

    //商品购买
    public static final String ADDRESS_SHOP = "/interface/mobileOrder/createOrder";


}
