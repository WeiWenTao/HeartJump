package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.SplishActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.eventBus.EventChageAccount;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.Map;

public class NewLoadActivity extends Activity {

    @ViewInject(R.id.et_accunt)
    private EditText mEt_accunt;

    @ViewInject(R.id.et_psw)
    private EditText mEt_psw;

    private LoginCore mLoginCore;

    private Intent mIntent;
    private boolean mIsAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_load);
        ViewUtils.inject(this);
        initViews();
    }

    private void initViews() {
        mIntent = new Intent(MyApplication.getInstance(), NewRegistActivity.class);
        //控制层
        mLoginCore = new LoginCore(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        //如果是添加账号就不用回显
        mIsAdd = getIntent().getBooleanExtra("isAdd", false);
        if (mIsAdd) {
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
//        mIntent.putExtra("isRegist", false);
//        startActivity(mIntent);
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
        ShareWeb("http://101.132.96.199/static/yuanshi_image/ed59fe93-05e7-4591-8b6f-5781b11fc4f2.jpg");
    }


    private void ShareWeb(String thumb_img) {
        UMImage thumb = new UMImage(MyApplication.getInstance(), thumb_img);
        UMWeb web = new UMWeb("www.cucrxt.com");
        web.setThumb(thumb);
        web.setDescription("测试描述");
        web.setTitle("测试标题");
        new ShareAction(this)
                .withMedia(web).
                setPlatform(SHARE_MEDIA.SINA).
                setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).
                share();
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
        mLoginCore.login(userName, passWord, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
//                finish();
            }
        });
    }


    @OnClick(R.id.iv_cancle)
    public void click(View view) {
//        finish();
        onBackPressed();
    }

    private long firstTime;
    private long secondTime;

    //双击退出程序
    @Override
    public void onBackPressed() {
        if (mIsAdd) {
            super.onBackPressed();
            return;
        }
        secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showToast("再按一次就要退出啦");
            firstTime = secondTime;
        } else {
            Intent intent = new Intent(MyApplication.getInstance(), SplishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            EventBus.getDefault().postSticky(new EventChageAccount());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
