package com.cucr.myapplication.activity.regist;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.login.LoadSuccess;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.login.ThirdLoadInfo;
import com.cucr.myapplication.bean.login.ThirdPlaformInfo;
import com.cucr.myapplication.bean.user.LoadUserInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.RegistCore;
import com.cucr.myapplication.dao.DaoCore;
import com.cucr.myapplication.gen.LoadUserInfosDao;
import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class BindTelActivity extends BaseActivity implements RequersCallBackListener {

    @ViewInject(R.id.et_tel)
    private EditText et_tel;

    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;

    @ViewInject(R.id.tv_send_yzm)
    private TextView tv_send_yzm;

    //用于记录验证码是否正在获取
    private boolean runningThree;
    private RegistCore mRegistCore;
    private ThirdPlaformInfo mInfo;
    private MyWaitDialog mMyWaitDialog;
    private List<String> mKeys;//这是存放账户信息的另一个容器  账号管理界面要用
    private Set<String> tags;//极光标签
    private LoadUserInfosDao mUserDao;
    //验证码倒计时
    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            tv_send_yzm.setText("(" + (l / 1000) + "s)重新获取");
            tv_send_yzm.setClickable(false);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            tv_send_yzm.setText("重新获取");
            tv_send_yzm.setClickable(true);
        }
    };

    @Override
    protected void initChild() {
        mRegistCore = new RegistCore();
        mKeys = new ArrayList<>();
        tags = new HashSet<>();
        //数据库
        mUserDao = DaoCore.getInstance().getUserDao();
        mMyWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mInfo = (ThirdPlaformInfo) getIntent().getSerializableExtra("data");

    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_bind_tel;
    }

    //绑定手机号
    @OnClick(R.id.tv_comple_bind)
    public void compleBind(View view) {
        String telNumber = et_tel.getText().toString();
        String yzm = et_yzm.getText().toString();

        if (telNumber.length() != 11) {
            ToastUtils.showToast("手机号码有误哦");
            return;
        }

        //密码小于6位
        if (TextUtils.isEmpty(yzm)) {
            ToastUtils.showToast("请输入验证码哦");
            return;
        }
        mRegistCore.thirdPlatformRegist(telNumber, yzm, mInfo.getLoginType(), mInfo.getOpenId(),
                mInfo.getName(), mInfo.getGender(), mInfo.getIconurl(), this);
    }


    //获取验证码
    @OnClick(R.id.tv_send_yzm)
    public void getCheckCode(View view) {
        if (runningThree) {
            return;
        }
        String phone_num = et_tel.getText().toString().trim();

        downTimer.start();

        mRegistCore.getYzm(this, phone_num, new OnGetYzmListener() {
            @Override
            public void onSuccess(Response<String> response) {
                ReBackMsg yzmInfo = mGson.fromJson(response.get(), ReBackMsg.class);
                if (!yzmInfo.isSuccess()) {
                    //success = false 密码错误
                    // 显示服务器返回的错误信息
                    ToastUtils.showToast(yzmInfo.getMsg());
                } else {
                }
            }

            @Override
            public void onFailed() {
                MyLogger.jLog().i("验证码获取失败");
            }
        });
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_THREE) {
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                mRegistCore.thirdPlatformLoad(mInfo.getLoginType(), mInfo.getOpenId(), JPushInterface.getRegistrationID(MyApplication.getInstance()), this);
            } else {
                ToastUtils.showToast(msg.getMsg());
            }

            //三方登录成功以后
        } else if (what == Constans.TYPE_TWO) {
            saveLoad(response);
        }
    }

    @Override
    public void onRequestStar(int what) {
        mMyWaitDialog.show();
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        mMyWaitDialog.dismiss();
    }

    private void saveLoad(Response<String> response) {
        ThirdLoadInfo loadUserInfo = mGson.fromJson(response.get(), ThirdLoadInfo.class);
        MyLogger.jLog().i("loadUserInfo:" + loadUserInfo);
//                登录成功 保存密钥
        if (loadUserInfo.isSuccess()) {
            LoadSuccess loadSuccess = mGson.fromJson(loadUserInfo.getObj(), LoadSuccess.class);

            //这里保存的信息账号管理界面用-------------------------------------------------------
            LoadUserInfos loadUserInfos = new LoadUserInfos(null, loadSuccess.getUserId(), loadSuccess.getRoleId(),
                    loadSuccess.getLoginStatu(), loadSuccess.getPhone(), loadSuccess.getSign(),
                    loadSuccess.getName(), loadSuccess.getUserHeadPortrait(),
                    loadSuccess.getToken(), loadSuccess.getCompanyName(), loadSuccess.getCompanyConcat(), "");
            //先去数据库查询 手机号 唯一标识
            LoadUserInfos unique = mUserDao.queryBuilder()
                    .where(LoadUserInfosDao.Properties.UserId.eq(loadSuccess.getUserId())).build().unique();
            //如果没有查到
            if (unique == null) {
                mUserDao.insert(loadUserInfos);
            } else {//如果有这条数据  就更新这条数据
                mUserDao.update(loadUserInfos);
            }
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
            if (!(boolean) SpUtil.getParam(SpConstant.HAS_LOAD, false)) {
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
        } else {
            //success = false 密码错误
            // 显示服务器返回的错误信息
            ToastUtils.showToast(loadUserInfo.getMsg());
        }
    }
}
