package com.cucr.myapplication.constants;

/**
 * Created by 911 on 2017/8/14.
 * Http常量
 */

public class HttpContans {

    //服务器
//    public static final String IMAGE_HOST =HOST+ "http://101.132.96.199";

    //EN数据
    public static final String HTTP_EN = "http://210.14.157.131:8149/clker_inter/";

    public static final String IMAGE_HOST = "";

    public static final String LOGO_ADDRESS = HttpContans.HOST + "/static/512.png";

    //线上环境
    public static final String HOST = "http://www.cucrxt.com";

    //测试环境
//    public static final String HOST = "http://192.168.1.147:8080";
//    public static final String HOST = "http://192.168.1.149:8080";

    //splish
    public static final String ADDRESS_SPLISH = HOST + "/interface/mobileMainAdvertisementAction/main";

    //服务协议
    public static final String HTTP_YHXY = "http://101.132.96.199/fwxy.html";

    //注册
    public static final String ADDRESS_REGIST = HOST + "/interface/user/regist";

    //重新获取token
    public static final String ADDRESS_GET_TOKEN = HOST + "/interface/user/reloadToken";

    //验证码
    public static final String ADDRESS_YZM = HOST + "/interface/checkCode/getCheckCode";

    //三方登录
    public static final String ADDRESS_OTHER_LOAD = HOST + "/interface/user/otherLogin";

    //三方注册
    public static final String ADDRESS_OTHER_REGIST = HOST + "/interface/user/otherRegist";

    //动态登录
    public static final String ADDRESS_DONGTAI_LOAD = HOST + "/interface/user/dynamicLogin";

    //忘记密码
    public static final String ADDRESS_FORGET_PSW = HOST + "/interface/user/forgivePass";

    //密码登录
    public static final String ADDRESS_PSW_LOAD = HOST + "/interface/user/login";

    //用户信息编辑
    public static final String ADDRESS_EDIT_USERINFO = HOST + "/interface/user/edit";

    //用户信息查询
    public static final String ADDRESS_QUERY_USERINFO = HOST + "/interface/user/queryMine";

    //商品购买
    public static final String ADDRESS_SHOP = HOST + "/interface/mobileOrder/createOrder";

    //明星认证
    public static final String ADDRESS_STAR_RZ = HOST + "/interface/authenticationAction/start";

    //企业认证
    public static final String ADDRESS_QIYE_RZ = HOST + "/interface/authenticationAction/company";

    //认证查询
    public static final String ADDRESS_QUERY_RZ = HOST + "/interface/authenticationAction/authenticationResult";

    //福利商品查询
    public static final String ADDRESS_FULI_GOODS = HOST + "/interface/shopAction/shopList";

    //企业活动发布
    public static final String ADDRESS_ACTIVE_PUBLISH = HOST + "/interface/mobileStartAppointment/applyActive";

    //福利活动查询
    public static final String ADDRESS_FULI_ACTIVE = HOST + "/interface/mobileWelfareActiveAction/list";

    //首页banner
    public static final String ADDRESS_HOME_BANNER = HOST + "/interface/mobileBannerAction/banner";

    //用户余额
    public static final String ADDRESS_USER_MONEY = HOST + "/interface/userMoneyAction/balance";

    //明星查询
    public static final String ADDRESS_QUERY_STAR = HOST + "/interface/mobileStartAction/query";

    //明星添加
    public static final String ADDRESS_ADD_STAR = HOST + "/interface/user/addStartIdea";

    //关注
    public static final String ADDRESS_TO_FOCUS = HOST + "/interface/mobileStartAction/follow";

    //取消关注
    public static final String ADDRESS_CANCLE_FOCUS = HOST + "/interface/mobileStartAction/cancelFollow";

    //我关注的明星
    public static final String ADDRESS_MY_FOCUS = HOST + "/interface/mobileStartAction/myFollow";

    //我关注的其他人(不包括明星)
    public static final String ADDRESS_MY_FOCUS_OTHER = HOST + "/interface/mobileStartAction/myFollowOther";

    //关注我的粉丝(不包括明星)
    public static final String ADDRESS_MY_FANS = HOST + "/interface/mobileStartAction/followMeOther";

    //添加行程
    public static final String ADDRESS_ADD_JOURNEY = HOST + "/interface/mobileStartTripAction/addTrip";

    //删除行程
    public static final String ADDRESS_DELETE_JOURNEY = HOST + "/interface/mobileStartTripAction/delTrip";

    //查询 “ 我的行程 ” 列表 （根据时间表查询行程）
    public static final String ADDRESS_QUERY_JOURNEY = HOST + "/interface/mobileStartTripAction/startTrip";

    //查询行程时间表
    public static final String ADDRESS_QUERY_JOURNEY_SCHEDULE = HOST + "/interface/mobileStartTripAction/tripTimeGroup";

    //粉团发布动态
    public static final String ADDRESS_PUBLISH_FT_INFO = HOST + "/interface/mobileContentAction/applyInfo";

    //查询粉团信息
    public static final String ADDRESS_QUERY_FT_INFO = HOST + "/interface/mobileContentAction/queryInfo";

    //查询单条粉团信息
    public static final String ADDRESS_QUERY_SIGNLE_FT_INFO = HOST + "/interface/mobileContentAction/queryFtPhoto";

    //支付宝支付接口
    public static final String ADDRESS_ALIPAY_PAY = HOST + "/interface/pay/alipaySign";

    //微信支付接口
    public static final String ADDRESS_WX_PAY = HOST + "/interface/pay/wxPrePay";

    //支付宝验证接口
    public static final String ADDRESS_ALIPAY_CHECK = HOST + "/interface/pay/payCheck";

    //粉团文章点赞
    public static final String ADDRESS_FT_GOOD = HOST + "/interface/mobileContentAction/giveUp";

    //粉团评论查询
    public static final String ADDRESS_FT_COMMENT_QUERY = HOST + "/interface/mobileContentAction/commentQuery";

    //粉团评论
    public static final String ADDRESS_FT_COMMENT = HOST + "/interface/mobileContentAction/comment";

    //粉团阅读量
    public static final String ADDRESS_FT_READ = HOST + "/interface/mobileContentAction/read";

    //粉团评论点赞
    public static final String ADDRESS_FT_COMMENT_GOODS = HOST + "/interface/mobileContentAction/commentGiveUp";

    //榜单查询
    public static final String ADDRESS_BANG_DAN_INFO = HOST + "/interface/mobileBillboardAction/billboardList";

    //打榜
    public static final String ADDRESS_DA_BANG = HOST + "/interface/mobileBillboardAction/dabang";

    //添加要求
    public static final String ADDRESS_ADD_REQUIREMENT = HOST + "/interface/mobileRequirementAction/addRequirement";

    //要求查询
    public static final String ADDRESS_QUERY_REQUIREMENT = HOST + "/interface/mobileRequirementAction/queryRequirement";

    //预约
    public static final String ADDRESS_ADD_APPOINMENT = HOST + "/interface/mobileStartAppointment/appointment";

    //礼物信息
    public static final String ADDRESS_QUERY_GFITINFO = HOST + "/interface/mobileGiftAction/idealMoneyList";

    //背包信息
    public static final String ADDRESS_QUERY_BACKPACKINFO = HOST + "/interface/mobileGiftAction/mineIdealMoneyList";

    //粉团打赏
    public static final String ADDRESS_DA_SHANG = HOST + "/interface/mobileGiftAction/reward";

    //打赏列表
    public static final String ADDRESS_DS_LIST = HOST + "/interface/mobileGiftAction/rewardList";

    //打赏我的
    public static final String ADDRESS_DS_ME = HOST + "/interface/mobileGiftAction/rewardHis";

    //道具提现
    public static final String ADDRESS_GIFT_TX = HOST + "/interface/mobileTransactionAction/propWithdrawals";

    //提现记录
    public static final String ADDRESS_TX_RECORD = HOST + "/interface/mobileTransactionAction/transactionQuery";

    //提现记录
    public static final String ADDRESS_TX_REQUEST = HOST + "/interface/mobileTransactionAction/xbWithdrawals";

    //明星数据
    public static final String ADDRESS_STAR_DATA = HOST + "/interface/dataPicAction/pic1?starId=";

    //明星查询字段
    public static final String ADDRESS_STAR_KEY = HOST + "/interface/mobileSysCodeAction/getList";

    //用户中心
    public static final String ADDRESS_USER_CENTER = HOST + "/interface/user/userCenter";

    //重置密码
    public static final String ADDRESS_RELEASE_PSW = HOST + "/interface/user/reppass";

    //活动查询(企业)
    public static final String ADDRESS_QUERY_ACTIVE = HOST + "/interface/mobileStartAppointment/queryActive";

    //活动点赞
    public static final String ADDRESS_ACTIVE_GOOD = HOST + "/interface/mobileStartAppointment/activeGiveUp";

    //活动评论
    public static final String ADDRESS_ACTIVE_COMMENT = HOST + "/interface/mobileStartAppointment/applyActiveComment";

    //活动评论查询
    public static final String ADDRESS_ACTIVE_COMMENT_QUERY = HOST + "/interface/mobileStartAppointment/activeCommentQuery";

    //活动评论点赞
    public static final String ADDRESS_ACTIVE_COMMENT_GOOD = HOST + "/interface/mobileStartAppointment/activeCommentGiveUp";

    //福利活动跳转
    public static final String ADDRESS_FULI_ACTIVE_DETIAL = HOST + "/interface/mobileWelfareActiveAction/activeDetails";

    //应用更新
    public static final String ADDRESS_APP_UPDATA = HOST + "/interface/MobileVersionCheck/versionCheck";

    //意见反馈
    public static final String ADDRESS_APP_ADVICE = HOST + "/interface/user/addIdea";

    //举报
    public static final String ADDRESS_APP_REPORT = HOST + "/interface/mobileUserReport/reportRecord";

    //封号
    public static final String ADDRESS_APP_CLOSURE = HOST + "/interface/user/checkFh";

    //图集上传
    public static final String ADDRESS_PIC_UPLOAD = HOST + "/interface/mobileStartAtlasAction/upAtlas";

    //图集查询
    public static final String ADDRESS_PIC_QUERY = HOST + "/interface/mobileStartAtlasAction/upAtlasQuery";

    //我喜欢的图集
    public static final String ADDRESS_PIC_FAVORITE = HOST + "/interface/mobileStartAtlasAction/like";

    //图集点赞
    public static final String ADDRESS_PIC_GOODS = HOST + "/interface/mobileStartAtlasAction/giveUp";

    //图集删除
    public static final String ADDRESS_PIC_DELETE = HOST + "/interface/mobileStartAtlasAction/del";

    //粉团分享
    public static final String ADDRESS_FT_SHARE = HOST + "/interface/newsShare/page?fansNewsContentId=";

    //首页新闻分享
    public static final String ADDRESS_NEWS_SHARE = HOST + "/interface/newsShare/newsPage?contentId=";

    //首页Banner分享
    public static final String ADDRESS_BANNER_SHARE = HOST + "/interface/newsShare/bannerPage?id=";

    //福利活动分享
    public static final String ADDRESS_FULI_HUOD_SHARE = HOST + "/interface/newsShare/welfarePage?id=";

    //粉团删除
    public static final String ADDRESS_FT_DELETE = HOST + "/interface/mobileContentAction/del";

    //活动删除
    public static final String ADDRESS_ACTIVE_DELETE = HOST + "/interface/mobileStartAppointment/deleteActive";

    //我的预约
    public static final String ADDRESS_MY_APOINMENT = HOST + "/interface/mobileStartAppointment/myAppointment";

    //创建后援团
    public static final String ADDRESS_CREATE_HYT = HOST + "/interface/mobileHelpTeamAction/createTeam";

    //后援活动查询
    public static final String ADDRESS_QUERY_HYT_ACTIVE = HOST + "/interface/mobileHelpTeamActiveAction/queryActive";

    //后援团查询
    public static final String ADDRESS_QUERY_HYT = HOST + "/interface/mobileHelpTeamAction/teamList";

    //加入后援团
    public static final String ADDRESS_JOIN_HYT = HOST + "/interface/mobileHelpTeamAction/joinTeam";

    //应援活动创建
    public static final String ADDRESS_YYHD_CREATE = HOST + "/interface/mobileHelpTeamActiveAction/createActive";

    //查询BigPad
    public static final String ADDRESS_BIGPAD_QUERY = HOST + "/interface/mobileHelpTeamActiveAction/bigpadQuery";

    //后援团活动支持(报名)
    public static final String ADDRESS_HYHD_SUPPORT = HOST + "/interface/mobileHelpTeamActiveAction/activeSignUp";

    //后援团活动支持(报名)查询
    public static final String ADDRESS_HYHD_SUPPORT_QUERY = HOST + "/interface/mobileHelpTeamActiveAction/activeSignUpQuery";

    //后援团活动评论查询
    public static final String ADDRESS_HYHD_COMMENT_QUERY = HOST + "/interface/mobileHelpTeamActiveAction/commentQuery";

    //后援团活动评论
    public static final String ADDRESS_HYHD_COMMENT = HOST + "/interface/mobileHelpTeamActiveAction/comment";

    //后援团活动评论点赞
    public static final String ADDRESS_HYHD_COMMENT_GOOD = HOST + "/interface/mobileHelpTeamActiveAction/commentGiveUp";

    //后援团活动点赞
    public static final String ADDRESS_HYHD_GOOD = HOST + "/interface/mobileHelpTeamActiveAction/giveUp";

    //后援团成员查询
    public static final String ADDRESS_MEMBERS_QUERY = HOST + "/interface/mobileHelpTeamAction/helpTeamUserList";

    //退出后援团
    public static final String ADDRESS_HYT_EXIT = HOST + "/interface/mobileHelpTeamAction/quitTeam";

    //解散后援团
    public static final String ADDRESS_HYT_CANCLE = HOST + "/interface/mobileHelpTeamAction/cancelTeam";

    //后援团禁言
    public static final String ADDRESS_HYT_LOCK = HOST + "/interface/mobileHelpTeamAction/gag";

    //后援团解除禁言
    public static final String ADDRESS_HYT_UNLOCK = HOST + "/interface/mobileHelpTeamAction/cancelGag";

    //后援团禁言列表查询
    public static final String ADDRESS_HYT_LOCK_LIST = HOST + "/interface/mobileHelpTeamAction/gagList";

    //邀请注册
    public static final String ADDRESS_INVATE_REGIST = HOST + "/interface/joinUsAction/page?userId=";

    //我的福利票务
    public static final String ADDRESS_PIAOWU_QUERY = HOST + "/interface/mobileWelfareActiveAction/mineSignUpFlActive";

    //根据id查用户(融云IM)
    public static final String ADDRESS_USERINFO_BYID = HOST + "/interface/user/queryUserInfo";

    //根据id查后援团群组(融云IM)
    public static final String ADDRESS_HYTINFO_BYID = HOST + "/interface/mobileHelpTeamAction/queryHytPhoto";

    //查询消息信息
    public static final String ADDRESS_MSGINFO = HOST + "/interface/mobileMessageQueryAction/queryMessage";

    //首页新闻分类
    public static final String ADDRESS_HOME_NEWS = HOST + "/interface/mobileContentAction/queryInfoSX";

    //福利banner查询
    public static final String ADDRESS_FULI_BANNER = HOST + "/interface/mobileBannerAction/welfareBanner";

    //新闻详情
    public static final String ADDRESS_NEWS_CATGORY = HOST + "/interface/newsShare/newsDetail?contentId=";

    //明星简介查询
    public static final String ADDRESS_STAR_DESCRIBE = HOST + "/interface/mobileStartAction/queryIntroduce";

    //抽奖分享
    public static final String ADDRESS_CHOU_JIANG_SHARE = HOST + "/interface/newsShare/completeShare?userId=";

    //抽奖
    public static final String ADDRESS_CHOU_JIANG = HOST + "/interface/newsShare/complete?userId=";

    //在线商城
    public static final String ADDRESS_SHOPPING = HOST + "/interface/newsShare/mallList";

    //后援会招募
    public static final String ADDRESS_RECRUIT = HOST + "/interface/newsShare/recruiting";

    //后援会招募分享
    public static final String ADDRESS_RECRUIT_SHARE = HOST + "/interface/newsShare/recruitingNew";

}
