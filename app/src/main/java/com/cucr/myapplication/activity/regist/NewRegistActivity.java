package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.core.login.RegistCore;
import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.listener.OnLoginListener;
import com.cucr.myapplication.listener.load.OnRegistListener;
import com.cucr.myapplication.model.login.LoadSuccess;
import com.cucr.myapplication.model.login.LoadUserInfo;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 注册和忘记密码共用一个activity
 */
public class NewRegistActivity extends Activity {

    //电话号码
    @ViewInject(R.id.et_phone_num)
    EditText et_phone_num;

    //验证码
    @ViewInject(R.id.et_yanzhengm)
    EditText et_yanzhengm;

    //获取验证码
    @ViewInject(R.id.tv_get_yanzhengm)
    TextView tv_get_yanzhengm;

    //设置密码
    @ViewInject(R.id.et_set_psw)
    EditText et_set_psw;

    //心跳条款
    @ViewInject(R.id.ll_rules)
    LinearLayout ll_rules;

    //用户行为
    @ViewInject(R.id.tv_action)
    TextView tv_action;

    //注册核心
    private RegistCore mRegistCore;
    private Gson mGson;
    private String mPhoneNum;
    private String mSetPsw;
    private Set<String> tags;
    //用于记录验证码是否正在获取
    private boolean runningThree;

    //验证码倒计时
    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            tv_get_yanzhengm.setText("(" + (l / 1000) + "s)重新获取");
            tv_get_yanzhengm.setClickable(false);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            tv_get_yanzhengm.setText("重新获取");
            tv_get_yanzhengm.setClickable(true);
        }
    };
    private boolean mIsRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_regist);
        ViewUtils.inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        tags = new HashSet<>();
        mGson = new Gson();
        initViews();
    }

    private void initViews() {
        mIsRegist = getIntent().getBooleanExtra("isRegist", false);
        //是否是注册注册行为
        if (!mIsRegist) {
            ll_rules.setVisibility(View.GONE);
            tv_action.setText("登 录");
        }

        mGson = new Gson();
        mRegistCore = new RegistCore();

    }

    //用户行为
    @OnClick(R.id.tv_action)
    public void clickBt(View view) {
        mPhoneNum = et_phone_num.getText().toString().trim();
        String yzm = et_yanzhengm.getText().toString();
        mSetPsw = et_set_psw.getText().toString();

        if (!mPhoneNum.matches(Constans.PHONE_REGEX)) {
            ToastUtils.showToast(this, "手机号码有误哦");
            return;
        }

        //密码小于6位
        if (mSetPsw.length() < 6) {
            ToastUtils.showToast(this, "用户密码最少为6位哦");
            return;
        }

        mRegistCore.regist(this, yzm, mPhoneNum, mSetPsw, new OnRegistListener() {
            @Override
            public void OnRegistSuccess(Response<String> response) {
                //获取结果
                String result = response.get();
                //复用验证码的javabean
                ReBackMsg yzmInfo = mGson.fromJson(result, ReBackMsg.class);
                //判断是否注册成功
                if (yzmInfo.isSuccess()) {
                    ToastUtils.showToast(mIsRegist?"注册成功!":"设置新密码成功!");
//                    存储账号和密码等信息
                    SpUtil.setParam(SpConstant.USER_NAEM, mPhoneNum);
                    SpUtil.setParam(SpConstant.PASSWORD, mSetPsw);

                    //登录请求 // TODO: 2017/10/27  
                    logRequest();

                } else {
                    ToastUtils.showToast(NewRegistActivity.this, yzmInfo.getMsg());
                }
            }

            @Override
            public void onRegistFailed() {

            }
        },mIsRegist);
    }

    //获取验证码
    @OnClick(R.id.ll_getyzm)
    public void clickGetYzm(View view) {
        if (runningThree){
            return;
        }
        String phone_num = et_phone_num.getText().toString().trim();

        if (!phone_num.matches(Constans.PHONE_REGEX)) {
            ToastUtils.showToast(this, "手机号有误哦");
            return;
        }

        downTimer.start();

        mRegistCore.getYzm(this, phone_num, new OnGetYzmListener() {
            @Override
            public void onSuccess(Response<String> response) {
                ReBackMsg yzmInfo = mGson.fromJson(response.get(), ReBackMsg.class);
                if (!yzmInfo.isSuccess()) {
                    //success = false 密码错误
                    // 显示服务器返回的错误信息
                    ToastUtils.showToast(NewRegistActivity.this, yzmInfo.getMsg());
                    MyLogger.jLog().i("验证码获取失败");
                } else {
                    MyLogger.jLog().i("验证码获取成功");
                }
            }

            @Override
            public void onFailed() {
                MyLogger.jLog().i("验证码获取失败");
            }
        });
    }

    //登录请求
    private void logRequest() {
        //TODO 输入判断
        new LoginCore(this).login(mPhoneNum, mSetPsw, new OnLoginListener() {
            @Override
            public void onSuccess(Response<String> response) {

                String s = response.get();
                LoadUserInfo loadUserInfo = mGson.fromJson(s, LoadUserInfo.class);
//                登录成功 保存密钥
                if (loadUserInfo.isSuccess()) {
                    LoadSuccess loadSuccess = mGson.fromJson(loadUserInfo.getMsg(), LoadSuccess.class);
                    //设置极光推送的tag
                    tags.add(loadSuccess.getRoleId() + "");
                    MyLogger.jLog().i("设置tag成功，tag：" + loadSuccess.getRoleId());
                    JPushInterface.setTags(NewRegistActivity.this, tags, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            MyLogger.jLog().i("设置tags成功");
                        }
                    });
//                    保存密钥
                    SpUtil.setParam(SpConstant.SIGN, loadSuccess.getSign());
//                    保存用户id
                    SpUtil.setParam(SpConstant.USER_ID, loadSuccess.getUserId());
//                    保存用户名和密码
                    SpUtil.setParam(SpConstant.USER_ID, loadSuccess.getUserId());
//                    保存身份信息
                    SpUtil.setParam(SpConstant.SP_STATUS, loadSuccess.getRoleId());
//                    存储企业用户信息  信息不为空时 存储信息
                    if (!TextUtils.isEmpty(loadSuccess.getCompanyName())){
                        SpUtil.setParam(SpConstant.SP_QIYE_NAME,loadSuccess.getCompanyName());
                        SpUtil.setParam(SpConstant.SP_QIYE_CONTACT,loadSuccess.getCompanyConcat());
                    }
//                  是否是第一次登录  没取到值表示是第一次登录  加个 !
                    if (!((boolean) SpUtil.getParam(SpConstant.IS_FIRST_RUN, false))){
//                        跳转关注界面
                        Intent intent = new Intent(MyApplication.getInstance(), StarListForAddActivity.class);
                        intent.putExtra("formLoad",true);
                        startActivity(intent);
                    }else {
//                        跳转到主界面
                        startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
                    }

                    SpUtil.setParam(SpConstant.IS_FIRST_RUN,true);  //登录之后保存登录数据  下次登录判断是否第一次登录
                    finish();


                } else {
                    //success = false 密码错误
                    // 显示服务器返回的错误信息
                    ToastUtils.showToast(NewRegistActivity.this, loadUserInfo.getMsg());

                }
            }

            @Override
            public void onFailed() {
                MyLogger.jLog().i("登录失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistCore.stopReques();
    }

    @OnClick(R.id.iv_back)
    public void clickBack(View view){
        finish();
    }


}
