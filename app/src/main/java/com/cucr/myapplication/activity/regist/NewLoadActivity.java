package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.listener.OnLoginListener;
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

public class NewLoadActivity extends Activity {

    @ViewInject(R.id.et_accunt)
    private EditText mEt_accunt;

    @ViewInject(R.id.et_psw)
    private EditText mEt_psw;

    private LoginCore mLoginCore;

    private Intent mIntent;
    private Set<String> tags;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_load);

        ViewUtils.inject(this);


        initViews();
    }

    private void initViews() {
        tags = new HashSet<>();
        mGson = MyApplication.getGson();
        mIntent = new Intent(MyApplication.getInstance(), NewRegistActivity.class);
        //控制层
        mLoginCore = new LoginCore(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        //如果是添加账号就不用回显
        if (getIntent().getBooleanExtra("isAdd", false)) {
            return;
        }
        //回显账号和密码  如果没有就设置为空串  账号密码由注册时保存到sp中
        mEt_accunt.setText(((String) SpUtil.getParam(SpConstant.USER_NAEM, "")));
        mEt_psw.setText(((String) SpUtil.getParam(SpConstant.PASSWORD, "")));

    }

    //注册
    @OnClick(R.id.tv_regist)
    public void regist(View view) {
        mIntent.putExtra("isRegist", true);
        startActivity(mIntent);
    }

    //忘记密码
    @OnClick(R.id.tv_forget_psw)
    public void forgetPsw(View view) {
        mIntent.putExtra("isRegist", false);
        startActivity(mIntent);
    }

    @OnClick(R.id.tv_load)
    public void load(View view) {

        //账号密码
        final String accunt = mEt_accunt.getText().toString();
        String psw = mEt_psw.getText().toString();

        //账号有误
        if (!accunt.matches(Constans.PHONE_REGEX)) {
            ToastUtils.showToast("手机号有误");
            return;
        }

        //密码有误
        if (psw.length() < 6) {
            ToastUtils.showToast("密码最少为6位哦");
            return;
        }
        final String userName = mEt_accunt.getText().toString();
        final String passWord = mEt_psw.getText().toString();
        //TODO 输入判断
        mLoginCore.login(userName, passWord, new OnLoginListener() {
            @Override
            public void onSuccess(Response<String> response) {

            }

            @Override
            public void onFailed() {
                MyLogger.jLog().i("登录失败");
            }
        });
    }

    @OnClick(R.id.iv_cancle)
    public void click(View view) {
        finish();
    }
}
