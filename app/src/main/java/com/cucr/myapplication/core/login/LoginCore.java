package com.cucr.myapplication.core.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.login.LoadSuccess;
import com.cucr.myapplication.bean.login.LoadUserInfo;
import com.cucr.myapplication.bean.login.UserAccountInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.chat.ChatCore;
import com.cucr.myapplication.interf.load.LoadByPsw;
import com.cucr.myapplication.listener.LoadChatServer;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imlib.RongIMClient;

/**
 * Created by 911 on 2017/8/11.
 * TODO 这里进行联网操作
 */

public class LoginCore implements LoadByPsw, LoadChatServer {

    private RequersCallBackListener loginListener;
    private Context context;
    private ChatCore mChatCore;

    /**
     * 请求的时候等待框。
     */
//    private WaitDialog mWaitDialog;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    //标记
    private Object flag;
    private String mPassWord;
    private Gson mGson;
    private List<String> mKeys;//这是存放账户信息的另一个容器  账号管理界面要用
    private Set<String> tags;//极光标签

    public LoginCore() {
        mQueue = NoHttp.newRequestQueue();
        flag = new Object();
        mGson = MyApplication.getGson();
        context = MyApplication.getInstance();
        tags = new HashSet<>();
        mKeys = new ArrayList<>();
        mChatCore = new ChatCore();
    }

    @Override
    public void login(String userName, String psw, final RequersCallBackListener loginListener) {
        this.loginListener = loginListener;
        mPassWord = psw;
        // 创建请求对象。
        Request<String> request = NoHttp.createStringRequest(HttpContans.IMAGE_HOST + HttpContans.ADDRESS_PSW_LOAD, RequestMethod.POST);

        // 如果有密钥 添加加密后的请求参数。
//        if (!TextUtils.isEmpty(sign)){
//            request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context,request.getParamKeyValues()));
//        }
        request.add("userName", userName) // 账号。
                .add("driverId", CommonUtils.getDiverID(context)) // 设备id。
                .add("password", psw) // 密码。
                .add("msgRegId", JPushInterface.getRegistrationID(context)) // 极光推送。

                // 单个请求的超时时间，不指定就会使用全局配置。
                .setConnectTimeout(10 * 1000) // 设置连接超时。
                .setReadTimeout(20 * 1000) // 设置读取超时时间，也就是服务器的响应超时。

                // 请求头，是否要添加头，添加什么头，要看开发者服务器端的要求。
//                .addHeader("Author", "sample")
//                .setHeader("User", "Jason")

                // 设置一个tag, 在请求完(失败/成功)时原封不动返回; 多数情况下不需要。
                .setTag(new Object())
                // 设置取消标志。
                .setCancelSign(flag);


		/*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样。
		 * request: 请求对象。
		 * onResponseListener 回调对象，接受请求结果。
		 */

        mQueue.add(Constans.TYPE_ONE, request, responseListener);

    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {
        @Override
        public void onStart(int what) {
            loginListener.onRequestStar(what);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == Constans.TYPE_ONE) {
                saveLoad(response);
                loginListener.onRequestSuccess(what, response);
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, context);
        }

        @Override
        public void onFinish(int what) {
            loginListener.onRequestFinish(what);
        }
    };

    private void saveLoad(Response<String> response) {
        LoadUserInfo loadUserInfo = mGson.fromJson(response.get(), LoadUserInfo.class);
//                登录成功 保存密钥
        MyLogger.jLog().i("loadUserInfo:" + loadUserInfo);
        if (loadUserInfo.isSuccess()) {
            LoadSuccess loadSuccess = mGson.fromJson(loadUserInfo.getMsg(), LoadSuccess.class);
            //登录聊天服务器
            //登录融云
            mChatCore.connect(loadSuccess.getToken(),this);

            //这里保存的信息账号管理界面用-------------------------------------------------------
            UserAccountInfo accountInfo = new UserAccountInfo(loadSuccess.getPhone(), mPassWord,
                    HttpContans.IMAGE_HOST + loadSuccess.getUserHeadPortrait(), loadSuccess.getName());
            SharedPreferences.Editor edit = SpUtil.getAccountSp().edit();
            edit.putString(loadSuccess.getPhone(), mGson.toJson(accountInfo).toString()).commit();
            //两个容器 类似于联表查询效果
            String keys = (String) SpUtil.getParam("keys", "");
            if (!TextUtils.isEmpty(keys)) {
                mKeys = MyApplication.getGson().fromJson(keys, List.class);
            }

            if (!mKeys.contains(loadSuccess.getPhone())) {
                mKeys.add(0, loadSuccess.getPhone());
            }
            SpUtil.setParam("keys", MyApplication.getGson().toJson(mKeys).toString());
            //---------------------------------------------------------------------------------

            //设置极光推送的tag
            tags.add(loadSuccess.getRoleId() + "");
//                    保存密钥
            SpUtil.setParam(SpConstant.SIGN, loadSuccess.getSign());
//                    保存用户id
            SpUtil.setParam(SpConstant.USER_ID, loadSuccess.getUserId());
//                    保存身份信息
            SpUtil.setParam(SpConstant.SP_STATUS, loadSuccess.getRoleId());
//                    存储账号和密码等信息
            SpUtil.setParam(SpConstant.USER_NAEM, loadSuccess.getPhone());
            SpUtil.setParam(SpConstant.PASSWORD, mPassWord);
//                    存储企业用户信息  信息不为空时 存储信息
            if (!TextUtils.isEmpty(loadSuccess.getCompanyName())) {
                SpUtil.setParam(SpConstant.SP_QIYE_NAME, loadSuccess.getCompanyName());
                SpUtil.setParam(SpConstant.SP_QIYE_CONTACT, loadSuccess.getCompanyConcat());
            }
//                    显示吐司
            ToastUtils.showToast("登录成功");

            JPushInterface.setTags(MyApplication.getInstance(), tags, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    MyLogger.jLog().i("设置tags成功");
                }
            });

//                  是否是第一次登录  没取到值表示是第一次登录  加个 !
            if (!(boolean) SpUtil.getParam(SpConstant.IS_FIRST_RUN, false)) {
                MyLogger.jLog().i("isFirst_是第一次登录");
//                        跳转关注界面
                Intent intent = new Intent(MyApplication.getInstance(), StarListForAddActivity.class);
                intent.putExtra("formLoad", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                MyLogger.jLog().i("isFirst_不是第一次登录");
//                        跳转到主界面
                Intent intent = new Intent(MyApplication.getInstance(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            SpUtil.setParam(SpConstant.IS_FIRST_RUN, true);  //登录之后保存登录数据  下次登录判断是否第一次登录
        } else {
            //success = false 密码错误
            // 显示服务器返回的错误信息
            ToastUtils.showToast(loadUserInfo.getMsg());
        }
    }

    //activity destory 时调用
    public void stopReques() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(flag);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();

    }

    //==========================登录聊天服务器====================================
    @Override
    public void onLoadSuccess(String userid) {
        MyLogger.jLog().i("登录聊天服务器成功 userId =" + userid);
    }

    @Override
    public void onLoadFial(RongIMClient.ErrorCode errorCode) {
        MyLogger.jLog().i("登录聊天服务器失败 errorCode =" + errorCode);
    }
    //==============================================================
}
