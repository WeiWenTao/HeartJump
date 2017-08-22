package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
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
import com.cucr.myapplication.widget.text.NameLengthFilter;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

public class RegistActivity extends Activity implements TextWatcher {

    //电话号码
    @ViewInject(R.id.et_phone_num)
    EditText et_phone_num;

    //验证码
    @ViewInject(R.id.et_yanzhengm)
    EditText et_yanzhengm;

    //获取验证码
    @ViewInject(R.id.tv_get_yanzhengm)
    TextView tv_get_yanzhengm;

    //昵称
    @ViewInject(R.id.et_nickname)
    EditText et_nickname;

    //设置密码
    @ViewInject(R.id.et_set_psw)
    EditText et_set_psw;

    //注册按钮
    @ViewInject(R.id.tv_regist)
    TextView tv_regist;

    //用于记录验证码是否正在获取
    private boolean runningThree;

    //注册核心
    private RegistCore mRegistCore;
    private Gson mGson;
    private String mPhoneNum;
    private String mSetPsw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ViewUtils.inject(this);
        initBar();

        initView();
    }

    private void initView() {

        mGson = new Gson();
        mRegistCore = new RegistCore();
        et_phone_num.addTextChangedListener(this);
        et_yanzhengm.addTextChangedListener(this);
        et_nickname.addTextChangedListener(this);
        et_set_psw.addTextChangedListener(this);

        InputFilter[] inputFilters = new InputFilter[1];
        NameLengthFilter nameLengthFilter = new NameLengthFilter(12);
        inputFilters[0] = nameLengthFilter;
        //限制长度 中文算两个字符
        et_nickname.setFilters(inputFilters);
    }


    protected void initBar() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (et_phone_num.length() > 0
                && et_yanzhengm.length() > 0
                && et_nickname.length() > 0
                && et_set_psw.length() > 0) {
            tv_regist.setEnabled(true);
        } else {
            tv_regist.setEnabled(false);
        }
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            tv_get_yanzhengm.setText("(" + (l / 1000) + "s)重新获取");
            tv_get_yanzhengm.setEnabled(false);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            tv_get_yanzhengm.setText("重新获取");
            tv_get_yanzhengm.setEnabled(true);
        }
    };

    //点击获取验证码
    @OnClick(R.id.tv_get_yanzhengm)
    public void clickGetYzm(View view) {
        String phone_num = et_phone_num.getText().toString().trim();

        if (!phone_num.matches(Constans.PHONE_REGEX)) {
            ToastUtils.showToast(this, "手机号有误哦");
            return;
        }

        downTimer.start();

        mRegistCore.getYzm(this, phone_num, new OnGetYzmListener() {
            @Override
            public void onSuccess(Response<String> response) {
                String result = response.get();
                ReBackMsg yzmInfo = mGson.fromJson(result, ReBackMsg.class);
                if (!yzmInfo.isSuccess()) {
                    //success = false 密码错误
                    // 显示服务器返回的错误信息
                    ToastUtils.showToast(RegistActivity.this, yzmInfo.getMsg());
                    MyLogger.jLog().i("验证码获取失败");
                } else {
                    MyLogger.jLog().i("验证码获取成功");
                }
            }

            @Override
            public void onFailed() {
                MyLogger.jLog().i("验证码获取成功");
            }
        });
    }

    //点击注册
    @OnClick(R.id.tv_regist)
    public void regist(View view) {
        mPhoneNum = et_phone_num.getText().toString().trim();
        String yzm = et_yanzhengm.getText().toString();
        String userName = et_nickname.getText().toString();
        mSetPsw = et_set_psw.getText().toString();

        if (!mPhoneNum.matches(Constans.PHONE_REGEX)){
            ToastUtils.showToast(this, "手机号码有误哦");
            return;
        }

        //用户名检测
        if (userName.matches(Constans.USERNAME_REGEX)) {
            ToastUtils.showToast(this, "用户名不能有特殊字符哦");
            return;
        }

        //密码小于6位
        if (mSetPsw.length()<6){
            ToastUtils.showToast(this, "用户密码最少为6位哦");
            return;
        }

        mRegistCore.regist(this, yzm, mPhoneNum, userName, mSetPsw, new OnRegistListener() {
            @Override
            public void OnRegistSuccess(Response<String> response) {
                //获取结果
                String result = response.get();
                //复用验证码的javabean
                ReBackMsg yzmInfo = mGson.fromJson(result, ReBackMsg.class);
                //判断是否注册成功
                if (yzmInfo.isSuccess()) {
                    ToastUtils.showToast(RegistActivity.this, "注册成功！");
//                    存储账号和密码等信息
                    SpUtil.setParam(RegistActivity.this, SpConstant.USER_NAEM, mPhoneNum);
                    SpUtil.setParam(RegistActivity.this, SpConstant.PASSWORD, mSetPsw);

                    //登录请求
                    logRequest();

                } else {
                    ToastUtils.showToast(RegistActivity.this, yzmInfo.getMsg());
                }
            }

            @Override
            public void onRegistFailed() {

            }
        });
    }

    //登录请求
    private void logRequest() {
        //TODO 输入判断
        new LoginCore().login(RegistActivity.this, mPhoneNum, mSetPsw, new OnLoginListener() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.get();
                Gson gson = new Gson();
                LoadUserInfo loadUserInfo = gson.fromJson(s, LoadUserInfo.class);
//                登录成功 保存密钥
                if (loadUserInfo.isSuccess()) {
                    LoadSuccess loadSuccess = gson.fromJson(loadUserInfo.getMsg(), LoadSuccess.class);
//                    保存密钥
                    SpUtil.setParam(RegistActivity.this, SpConstant.SIGN, loadSuccess.getSign());
//                    保存用户id
                    SpUtil.setParam(RegistActivity.this, SpConstant.USER_ID, loadSuccess.getUserId());
//                    跳转到主界面
                    RegistActivity.this.startActivity(new Intent(RegistActivity.this, MainActivity.class));
                    RegistActivity.this.finish();

                } else {
                    //success = false 密码错误
                    // 显示服务器返回的错误信息
                    ToastUtils.showToast(RegistActivity.this, loadUserInfo.getMsg());

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
}
