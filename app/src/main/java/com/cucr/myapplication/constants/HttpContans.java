package com.cucr.myapplication.constants;

/**
 * Created by 911 on 2017/8/14.
 * Http常量
 */

public class HttpContans {
    //服务器
    public static final String HTTP_HOST = "http://101.132.96.199";

//    public static final String HTTP_HOST = "http://192.168.1.122:8080";

    //注册
    public static final String ADDRESS_REGIST = "/interface/user/regist";

    //验证码
    public static final String ADDRESS_YZM = "/interface/checkCode/getCheckCode";

    //动态登录
    public static final String ADDRESS_DONGTAI_LOAD = "/interface/user/dynamicLogin";

    //忘记密码
    public static final String ADDRESS_FORGET_PSW = "/interface/user/forgivePass";

    //密码登录
    public static final String ADDRESS_PSW_LOAD = "/interface/user/login";

    //用户信息编辑
    public static final String ADDRESS_EDIT_USERINFO = "/interface/user/edit";

    //用户信息查询
    public static final String ADDRESS_QUERY_USERINFO = "/interface/user/queryMine";

    //商品购买
    public static final String ADDRESS_SHOP = "/interface/mobileOrder/createOrder";

    //明星认证
    public static final String ADDRESS_STAR_RZ = "/interface/authenticationAction/start";

    //企业认证
    public static final String ADDRESS_QIYE_RZ = "/interface/authenticationAction/company";

    //认证查询
    public static final String ADDRESS_QUERY_RZ = "/interface/authenticationAction/authenticationResult";

    //福利商品查询
    public static final String ADDRESS_FULI_GOODS = "/interface/shopAction/shopList";

    //福利活动查询
    public static final String ADDRESS_FULI_ACTIVE = "/interface/mobileWelfareActiveAction/list";

    //首页banner
    public static final String ADDRESS_HOME_BANNER = "/interface/mobileBannerAction/banner";

    //用户余额
    public static final String ADDRESS_USER_MONEY = "/interface/userMoneyAction/balance";

    //明星查询
    public static final String ADDRESS_QUERY_STAR = "/interface/mobileStartAction/query";

    //关注
    public static final String ADDRESS_TO_FOCUS = "/interface/mobileStartAction/follow";

    //取消关注
    public static final String ADDRESS_CANCLE_FOCUS = "/interface/mobileStartAction/cancelFollow";

    //我的关注
    public static final String ADDRESS_MY_FOCUS = "/interface/mobileStartAction/myFollow";

    //添加行程
    public static final String ADDRESS_ADD_JOURNEY = "/interface/mobileStartTripAction/addTrip";

    //删除行程
    public static final String ADDRESS_DELETE_JOURNEY = "/interface/mobileStartTripAction/delTrip";

    //查询 “ 我的行程 ” 列表 （根据时间表查询行程）
    public static final String ADDRESS_QUERY_JOURNEY = "/interface/mobileStartTripAction/startTrip";

    //查询行程时间表
    public static final String ADDRESS_QUERY_JOURNEY_SCHEDULE = "/interface/mobileStartTripAction/tripTimeGroup";

    //粉团发布动态
    public static final String ADDRESS_PUBLISH_FT_INFO = "/interface/mobileContentAction/applyInfo";

    //查询粉团信息
    public static final String ADDRESS_QUERY_FT_INFO = "/interface/mobileContentAction/queryInfo";

    //支付宝支付接口
    public static final String ADDRESS_ALIPAY_PAY = "/interface/pay/alipaySign";

    //支付宝验证接口
    public static final String ADDRESS_ALIPAY_CHECK = "/interface/pay/payCheck";

    //粉团文章点赞
    public static final String ADDRESS_FT_GOOD = "/interface/mobileContentAction/giveUp";

    //粉团评论查询
    public static final String ADDRESS_FT_COMMENT_QUERY = "/interface/mobileContentAction/commentQuery";

    //粉团评论
    public static final String ADDRESS_FT_COMMENT = "/interface/mobileContentAction/comment";

    //粉团评论点赞
    public static final String ADDRESS_FT_COMMENT_GOODS = "/interface/mobileContentAction/commentGiveUp";

    //榜单查询
    public static final String ADDRESS_BANG_DAN_INFO = "/interface/mobileBillboardAction/billboardList";

    //打榜
    public static final String ADDRESS_DA_BANG = "/interface/mobileBillboardAction/dabang";

    //添加要求
    public static final String ADDRESS_ADD_REQUIREMENT = "/interface/mobileRequirementAction/addRequirement";

    //预约
    public static final String ADDRESS_ADD_APPOINMENT = "/interface/mobileStartAppointment/appointment";

    //礼物信息
    public static final String ADDRESS_QUERY_GFITINFO = "/interface/mobileGiftAction/idealMoneyList";

    //背包信息
    public static final String ADDRESS_QUERY_BACKPACKINFO = "/interface/mobileGiftAction/mineIdealMoneyList";

    //粉团打赏
    public static final String ADDRESS_DA_SHANG = "/interface/mobileGiftAction/reward";

    //打赏列表
    public static final String ADDRESS_DS_LIST = "/interface/mobileGiftAction/rewardList";

    //打赏我的
    public static final String ADDRESS_DS_ME = "/interface/mobileGiftAction/rewardHis";

    //道具提现
    public static final String ADDRESS_GIFT_TX = "/interface/mobileTransactionAction/propWithdrawals";

    //道具提现
    public static final String ADDRESS_STAR_DATA = "/interface/dataPicAction/pic1";
}
