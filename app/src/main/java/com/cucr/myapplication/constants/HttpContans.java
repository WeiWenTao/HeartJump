package com.cucr.myapplication.constants;

/**
 * Created by 911 on 2017/8/14.
 * Http常量
 */

public class HttpContans {
    //服务器
    public static final String HTTP_HOST = "http://101.132.96.199";
    //EN数据
    public static final String HTTP_EN = "http://210.14.157.131:8149/clker_inter/";

//    public static final String HTTP_HOST = "http://192.168.1.110:8080";
//    public static final String HTTP_HOST = "http://yin-wiki.51vip.biz";


    //服务协议
    public static final String HTTP_YHXY = "http://www.cucrxt.com/fwxy.html";

    //注册
    public static final String ADDRESS_REGIST = "/interface/user/regist";

    //验证码
    public static final String ADDRESS_YZM = "/interface/checkCode/getCheckCode";

    //三方登录
    public static final String ADDRESS_OTHER_LOAD = "/interface/user/otherLogin";

    //三方注册
    public static final String ADDRESS_OTHER_REGIST = "/interface/user/otherRegist";

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

    //企业活动发布
    public static final String ADDRESS_ACTIVE_PUBLISH = "/interface/mobileStartAppointment/applyActive";

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

    //我关注的明星
    public static final String ADDRESS_MY_FOCUS = "/interface/mobileStartAction/myFollow";

    //我关注的其他人(不包括明星)
    public static final String ADDRESS_MY_FOCUS_OTHER = "/interface/mobileStartAction/myFollowOther";

    //关注我的粉丝(不包括明星)
    public static final String ADDRESS_MY_FANS = "/interface/mobileStartAction/followMeOther";

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

    //微信支付接口
    public static final String ADDRESS_WX_PAY = "/interface/pay/wxPrePay";

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

    //要求查询
    public static final String ADDRESS_QUERY_REQUIREMENT = "/interface/mobileRequirementAction/queryRequirement";

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

    //提现记录
    public static final String ADDRESS_TX_RECORD = "/interface/mobileTransactionAction/transactionQuery";

    //明星数据
    public static final String ADDRESS_STAR_DATA = "/interface/dataPicAction/pic1";

    //明星查询字段
    public static final String ADDRESS_STAR_KEY = "/interface/mobileSysCodeAction/getList";

    //用户中心
    public static final String ADDRESS_USER_CENTER = "/interface/user/userCenter";

    //活动查询(企业)
    public static final String ADDRESS_QUERY_ACTIVE = "/interface/mobileStartAppointment/queryActive";

    //活动点赞
    public static final String ADDRESS_ACTIVE_GOOD = "/interface/mobileStartAppointment/activeGiveUp";

    //活动评论
    public static final String ADDRESS_ACTIVE_COMMENT = "/interface/mobileStartAppointment/applyActiveComment";

    //活动评论查询
    public static final String ADDRESS_ACTIVE_COMMENT_QUERY = "/interface/mobileStartAppointment/activeCommentQuery";

    //活动评论查询
    public static final String ADDRESS_ACTIVE_COMMENT_GOOD = "/interface/mobileStartAppointment/activeCommentGiveUp";

    //福利活动跳转
    public static final String ADDRESS_FULI_ACTIVE_DETIAL = "/interface/mobileWelfareActiveAction/activeDetails";

    //应用更新
    public static final String ADDRESS_APP_UPDATA = "/interface/MobileVersionCheck/versionCheck";

    //图集上传
    public static final String ADDRESS_PIC_UPLOAD = "/interface/mobileStartAtlasAction/upAtlas";

    //图集查询
    public static final String ADDRESS_PIC_QUERY = "/interface/mobileStartAtlasAction/upAtlasQuery";

    //图集点赞
    public static final String ADDRESS_PIC_GOODS = "/interface/mobileStartAtlasAction/giveUp";

    //粉团分享
    public static final String ADDRESS_FT_SHARE = HTTP_HOST + "/interface/newsShare/page?fansNewsContentId=";

    //我的预约
    public static final String ADDRESS_MY_APOINMENT = "/interface/mobileStartAppointment/myAppointment";

    //创建后援团
    public static final String ADDRESS_CREATE_HYT = "/interface/mobileHelpTeamAction/createTeam";

    //后援活动查询
    public static final String ADDRESS_QUERY_HYT_ACTIVE = "/interface/mobileHelpTeamActiveAction/queryActive";

    //后援团查询
    public static final String ADDRESS_QUERY_HYT = "/interface/mobileHelpTeamAction/teamList";

    //加入后援团
    public static final String ADDRESS_JOIN_HYT = "/interface/mobileHelpTeamAction/joinTeam";

    //应援活动创建
    public static final String ADDRESS_YYHD_CREATE = "/interface/mobileHelpTeamActiveAction/createActive";

    //查询BigPad
    public static final String ADDRESS_BIGPAD_QUERY = "/interface/mobileHelpTeamActiveAction/bigpadQuery";

    //后援团活动支持(报名)
    public static final String ADDRESS_HYHD_SUPPORT = "/interface/mobileHelpTeamActiveAction/activeSignUp";

    //后援团活动支持(报名)查询
    public static final String ADDRESS_HYHD_SUPPORT_QUERY = "/interface/mobileHelpTeamActiveAction/activeSignUpQuery";

    //后援团活动评论查询
    public static final String ADDRESS_HYHD_COMMENT_QUERY = "/interface/mobileHelpTeamActiveAction/commentQuery";

    //后援团活动评论
    public static final String ADDRESS_HYHD_COMMENT = "/interface/mobileHelpTeamActiveAction/comment";

    //后援团活动评论点赞
    public static final String ADDRESS_HYHD_COMMENT_GOOD = "/interface/mobileHelpTeamActiveAction/commentGiveUp";

    //后援团活动点赞
    public static final String ADDRESS_HYHD_GOOD = "/interface/mobileHelpTeamActiveAction/giveUp";

    //后援团成员查询
    public static final String ADDRESS_MEMBERS_QUERY = "/interface/mobileHelpTeamAction/helpTeamUserList";

    //退出后援团
    public static final String ADDRESS_HYT_EXIT = "/interface/mobileHelpTeamAction/quitTeam";

    //后援团禁言
    public static final String ADDRESS_HYT_LOCK = "/interface/mobileHelpTeamAction/gag";

    //后援团解除禁言
    public static final String ADDRESS_HYT_UNLOCK = "/interface/mobileHelpTeamAction/cancelGag";

    //后援团禁言列表查询
    public static final String ADDRESS_HYT_LOCK_LIST = "/interface/mobileHelpTeamAction/gagList";

    //邀请注册
    public static final String ADDRESS_INVATE_REGIST = HTTP_HOST + "/interface/joinUsAction/page?userId=";

    //我的福利票务
    public static final String ADDRESS_PIAOWU_QUERY = "/interface/mobileWelfareActiveAction/mineSignUpFlActive";
}
