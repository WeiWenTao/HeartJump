package com.cucr.myapplication.fragment.load;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.DongTaiLoadCore;
import com.cucr.myapplication.listener.OnDongTaiLoginListener;
import com.cucr.myapplication.listener.OnGetYzmListener;
import com.cucr.myapplication.model.login.LoadSuccess;
import com.cucr.myapplication.model.login.LoadUserInfo;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.text.MyClickRegist;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/10.
 */

public class DongtaiLoadFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private View rootView;
    private TextView mTv_regist;
    private EditText mEt_accunt;
    private EditText mEt_yzm;
    private TextView mTv_load;
    private TextView mTv_yzm;
    private DongTaiLoadCore mCore;
    private Context mContext;
    private Gson mGson;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = container.getContext();
        mGson = new Gson();
        mCore = new DongTaiLoadCore();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_load_dongtai, container, false);
            initView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mEt_accunt = (EditText) rootView.findViewById(R.id.et_accunt);
        mEt_yzm = (EditText) rootView.findViewById(R.id.et_yzm);
        mTv_load = (TextView) rootView.findViewById(R.id.tv_load);
        mTv_regist = (TextView) rootView.findViewById(R.id.tv_regist);
        mTv_yzm = (TextView) rootView.findViewById(R.id.tv_yan_zheng_ma);
        mTv_yzm.setOnClickListener(this);
        mTv_load.setOnClickListener(this);
        mEt_accunt.addTextChangedListener(this);
        mEt_yzm.addTextChangedListener(this);


        SpannableString sp = new SpannableString("还没注册？");
        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击 传入明星
        MyClickRegist mySpan = new MyClickRegist();
        sp.setSpan(mySpan, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //SpannableString对象设置给TextView
        mTv_regist.setText(sp);
        //设置TextView可点击
        mTv_regist.setMovementMethod(LinkMovementMethod.getInstance());
        //将点击背景设为透明
        mTv_regist.setHighlightColor(getResources().getColor(android.R.color.transparent));

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEt_accunt.length() > 0 && mEt_yzm.length() > 0) {
            mTv_load.setEnabled(true);
        } else {
            mTv_load.setEnabled(false);
        }
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            mTv_yzm.setText("("+(l / 1000)+"s)重新获取");
            mTv_yzm.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mTv_yzm.setText("重新获取");
            mTv_yzm.setEnabled(true);
        }
    };

    @Override
    public void onClick(View v) {
        String account = mEt_accunt.getText().toString().trim();
        final String yzm = mEt_yzm.getText().toString();

        //手机号有误
        if (!account.matches(Constans.PHONE_REGEX)) {
            ToastUtils.showToast(v.getContext(), "手机号有误");
            return;
        }

        switch (v.getId()){
            //获取验证码
            case R.id.tv_yan_zheng_ma:
                mCore.getYzm(mContext, account, new OnGetYzmListener() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.get();
                        ReBackMsg yzmInfo = mGson.fromJson(result, ReBackMsg.class);
                        if (!yzmInfo.isSuccess()){
                            //success = false 密码错误
                            // 显示服务器返回的错误信息
                            ToastUtils.showToast(mContext, yzmInfo.getMsg());
                            MyLogger.jLog().i("验证码获取失败");
                        }else {
                            MyLogger.jLog().i("验证码获取成功");
                        }
                    }

                    @Override
                    public void onFailed() {
                        MyLogger.jLog().i("获取验证码失败");
                    }
                });
                downTimer.start();
                break;

            //点击登陆
            case R.id.tv_load:
                mCore.login(mContext, account, yzm, new OnDongTaiLoginListener() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.get();

                        LoadUserInfo loadUserInfo = mGson.fromJson(s, LoadUserInfo.class);
//                登录成功 保存密钥
                        if (loadUserInfo.isSuccess()) {
                            LoadSuccess loadSuccess = mGson.fromJson(loadUserInfo.getMsg(), LoadSuccess.class);
//                    保存密钥
                            SpUtil.setParam(mContext, SpConstant.SIGN, loadSuccess.getSign());
//                    保存用户id
                            SpUtil.setParam(mContext, SpConstant.USER_ID, loadSuccess.getUserId());
                            MyLogger.jLog().i("DTuseid:"+loadSuccess.getUserId());

//                    显示吐司
                            ToastUtils.showToast(mContext, "登录成功");
//                    跳转到主界面
                            mContext.startActivity(new Intent(mContext, MainActivity.class));
                            getActivity().finish();
                        } else {
                            //success = false 密码错误
                            // 显示服务器返回的错误信息
                            ToastUtils.showToast(mContext, loadUserInfo.getMsg());
                        }
                    }

                    @Override
                    public void onFailed() {
                        MyLogger.jLog().i("登录失败");
                    }
                });
                break;

        }

    }

    //和生命周期绑定
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCore.stopReques();
    }
}
