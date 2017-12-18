package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.listener.OnLoginListener;
import com.cucr.myapplication.model.login.LoadSuccess;
import com.cucr.myapplication.model.login.LoadUserInfo;
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

public class NewLoadActivity extends Activity {

    private Intent mIntent;

    @ViewInject(R.id.et_accunt)
    private EditText mEt_accunt;

    @ViewInject(R.id.et_psw)
    private EditText mEt_psw;

    private LoginCore mLoginCore;

    private Set<String> tags;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_load);

        ViewUtils.inject(this);
        mIntent = new Intent(MyApplication.getInstance(), NewRegistActivity.class);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        initViews();
    }

    private void initViews() {
        //控制层
        mLoginCore = new LoginCore(this);
        //回显账号和密码  如果没有就设置为空串  账号密码由注册时保存到sp中
        mEt_accunt.setText(((String) SpUtil.getParam(SpConstant.USER_NAEM, "")));
        mEt_psw.setText(((String) SpUtil.getParam(SpConstant.PASSWORD, "")));
        tags = new HashSet<>();
        mGson = new Gson();
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
        String accunt = mEt_accunt.getText().toString();
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

        //TODO 输入判断
        mLoginCore.login(mEt_accunt.getText().toString(), mEt_psw.getText().toString(), new OnLoginListener() {
            @Override
            public void onSuccess(Response<String> response) {
                LoadUserInfo loadUserInfo = mGson.fromJson(response.get(), LoadUserInfo.class);
//                登录成功 保存密钥
                if (loadUserInfo.isSuccess()) {
                    LoadSuccess loadSuccess = mGson.fromJson(loadUserInfo.getMsg(), LoadSuccess.class);
                    //设置极光推送的tag
                    tags.add(loadSuccess.getRoleId() + "");
                    MyLogger.jLog().i("设置tag成功，tag：" + loadSuccess.getRoleId());
//                    保存密钥
                    SpUtil.setParam(SpConstant.SIGN, loadSuccess.getSign());
//                    保存用户id
                    SpUtil.setParam(SpConstant.USER_ID, loadSuccess.getUserId());
//                    保存身份信息
                    MyLogger.jLog().i("load_roleId:" + loadSuccess.getRoleId());
                    SpUtil.setParam(SpConstant.SP_STATUS, loadSuccess.getRoleId());
//                    存储账号和密码等信息
                    SpUtil.setParam(SpConstant.USER_NAEM, mEt_accunt.getText().toString());
                    SpUtil.setParam(SpConstant.PASSWORD, mEt_psw.getText().toString());
//                    存储企业用户信息  信息不为空时 存储信息
                    if (!TextUtils.isEmpty(loadSuccess.getCompanyName())) {
                        SpUtil.setParam(SpConstant.SP_QIYE_NAME, loadSuccess.getCompanyName());
                        SpUtil.setParam(SpConstant.SP_QIYE_CONTACT, loadSuccess.getCompanyConcat());
                    }
                    MyLogger.jLog().i("PSWuseid:" + loadSuccess.getUserId());
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
                        startActivity(intent);
                    } else {
                        MyLogger.jLog().i("isFirst_不是第一次登录");
//                        跳转到主界面
                        startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
                    }
                    finish();

                } else {
                    //success = false 密码错误
                    // 显示服务器返回的错误信息
                    ToastUtils.showToast(loadUserInfo.getMsg());
                }
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
