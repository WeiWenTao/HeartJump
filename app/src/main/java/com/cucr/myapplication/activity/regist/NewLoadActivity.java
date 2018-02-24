package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.SplishActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;
import com.cucr.myapplication.bean.login.LoadSuccess;
import com.cucr.myapplication.bean.login.ThirdLoadInfo;
import com.cucr.myapplication.bean.login.ThirdPlaformInfo;
import com.cucr.myapplication.bean.login.UserAccountInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.core.login.RegistCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.runtimepermissions.PermissionsManager;
import com.cucr.myapplication.runtimepermissions.PermissionsResultAction;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class NewLoadActivity extends Activity implements RequersCallBackListener {

    @ViewInject(R.id.et_accunt)
    private EditText mEt_accunt;

    @ViewInject(R.id.et_psw)
    private EditText mEt_psw;

    private LoginCore mLoginCore;
    private RegistCore mRegistCore;
    private Gson mGson;
    private Intent mIntent;
    private boolean mIsAdd;
    private List<String> mKeys;//这是存放账户信息的另一个容器  账号管理界面要用
    private Set<String> tags;//极光标签
    private String mUserName;
    private String mPassWord;
    private Intent bindIntent;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_load);
        ViewUtils.inject(this);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);

        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(NewLoadActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(NewLoadActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });


        mRegistCore = new RegistCore();
        bindIntent = new Intent(MyApplication.getInstance(), BindTelActivity.class);
        mDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mGson = MyApplication.getGson();
        mKeys = new ArrayList<>();
        tags = new HashSet<>();
       /* if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            requestPermissions(mPermissionList, 123);
        }*/
        initViews();
    }

  /*  //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


    }*/

    private void initViews() {
        mIntent = new Intent(MyApplication.getInstance(), NewRegistActivity.class);
        //控制层
        mLoginCore = new LoginCore();
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
            ToastUtils.showToast("请输入正确的手机号哦");
            return;
        }

        //密码有误
        if (psw.length() < 6) {
            ToastUtils.showToast("密码最少为6位哦");
            return;
        }
        mUserName = mEt_accunt.getText().toString();
        mPassWord = mEt_psw.getText().toString();
        //TODO 输入判断
        mLoginCore.login(mUserName, mPassWord, this);
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
            UMShareAPI.get(this).release();
        }
    }


    @OnClick(R.id.iv_qq_load)
    public void qqLoad(View view) {
        thirdPlatformLoad(SHARE_MEDIA.QQ);
    }

    @OnClick(R.id.iv_sina_load)
    public void sinaLoad(View view) {
        thirdPlatformLoad(SHARE_MEDIA.SINA);
    }

    @OnClick(R.id.iv_wx_load)
    public void wxLoad(View view) {
        if (!UMShareAPI.get(MyApplication.getInstance()).isInstall(this, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.showToast("请先装微信客户端");
            return;
        }
        thirdPlatformLoad(SHARE_MEDIA.WEIXIN);
    }

    public void thirdPlatformLoad(SHARE_MEDIA share_media) {
        UMShareAPI.get(MyApplication.getInstance())
                .getPlatformInfo(this, share_media, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            bindIntent.putExtra("data", new ThirdPlaformInfo(platform.toString(),
                    data.get("uid"), data.get("name"), data.get("gender"), data.get("iconurl")));


            mRegistCore.thirdPlatformLoad(platform.toString(), data.get("uid"),
                    JPushInterface.getRegistrationID(MyApplication.getInstance()),
                    NewLoadActivity.this);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {

        switch (what) {
            case Constans.TYPE_ONE:
                //逻辑都在loginCore中
                break;

            case Constans.TYPE_TWO:
                //三方登录
                CommonRebackMsg msg = MyApplication.getGson().fromJson(response.get(), CommonRebackMsg.class);
                MyLogger.jLog().i("aaa:" + msg);
                if (msg.isSuccess()) {
                    saveLoad(response);
                } else {
                    //不成功 去绑定手机号
                    startActivity(bindIntent);
                }

                break;
        }

    }

    private void saveLoad(Response<String> response) {
        ThirdLoadInfo loadUserInfo = mGson.fromJson(response.get(), ThirdLoadInfo.class);
        MyLogger.jLog().i("loadUserInfo:" + loadUserInfo);
//                登录成功 保存密钥
        if (loadUserInfo.isSuccess()) {
            LoadSuccess loadSuccess = mGson.fromJson(loadUserInfo.getObj(), LoadSuccess.class);
            //这里保存的信息账号管理界面用-------------------------------------------------------
            UserAccountInfo accountInfo = new UserAccountInfo(loadSuccess.getPhone(), mPassWord,
                    HttpContans.HTTP_HOST + loadSuccess.getUserHeadPortrait(), loadSuccess.getName());
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
            SpUtil.setParam(SpConstant.PASSWORD, "");
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
                startActivity(intent);
            } else {
                MyLogger.jLog().i("isFirst_不是第一次登录");
//                        跳转到主界面
                Intent intent = new Intent(MyApplication.getInstance(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            SpUtil.setParam(SpConstant.IS_FIRST_RUN, true);  //登录之后保存登录数据  下次登录判断是否第一次登录
        } else {
            //success = false 密码错误
            // 显示服务器返回的错误信息
            ToastUtils.showToast(loadUserInfo.getMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        mDialog.show();
    }

    @Override
    public void onRequestFinish(int what) {
        mDialog.dismiss();
    }
}
