package com.cucr.myapplication.fragment.load;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.listener.OnLoginListener;
import com.cucr.myapplication.model.login.LoadSuccess;
import com.cucr.myapplication.model.login.LoadUserInfo;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.text.MyClickRegist;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 911 on 2017/8/10.
 */

public class PswLoadFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private View rootView;
    private TextView mTv_regist;
    private EditText mEt_accunt;
    private EditText mEt_psw;
    private TextView mTv_load;
    private LoginCore mLoginCore;
    private Context mContext;
    private Set<String> tags;
    //是否是第一次
    private boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_load_psw, container, false);
            mTv_regist = (TextView) rootView.findViewById(R.id.tv_regist);
            mEt_accunt = (EditText) rootView.findViewById(R.id.et_accunt);
            mEt_psw = (EditText) rootView.findViewById(R.id.et_psw);
            mTv_load = (TextView) rootView.findViewById(R.id.tv_load);
            mTv_load.setOnClickListener(this);
            mContext = rootView.getContext();
            initView();
            //控制层
            mLoginCore = new LoginCore();
        }
        return rootView;
    }

    private void initView() {
        //回显账号和密码  如果没有就设置为空串  账号密码由注册时保存到sp中
        mEt_accunt.setText(((String) SpUtil.getParam(mContext, SpConstant.USER_NAEM, "")));
        mEt_psw.setText(((String) SpUtil.getParam(mContext, SpConstant.PASSWORD, "")));
        //设置登录 可点击
        if (mEt_accunt.length() > 0 && mEt_psw.length() > 0) {
            mTv_load.setEnabled(true);
        }


        SpannableString sp = new SpannableString("还没注册？");
        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 2, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置点击
        MyClickRegist mySpan = new MyClickRegist();
        sp.setSpan(mySpan, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //SpannableString对象设置给TextView
        mTv_regist.setText(sp);
        //设置TextView可点击
        mTv_regist.setMovementMethod(LinkMovementMethod.getInstance());
        //将点击背景设为透明
        mTv_regist.setHighlightColor(getResources().getColor(android.R.color.transparent));

        mEt_accunt.addTextChangedListener(this);
        mEt_psw.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 12 && isFirst) {
            ToastUtils.showToast(mContext, "密码最多为12位哦");
            isFirst = false;
        } else {
            isFirst = true;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEt_accunt.length() > 0 && mEt_psw.length() > 0) {
            mTv_load.setEnabled(true);
        } else {
            mTv_load.setEnabled(false);
        }
    }

    //点击登陆
    @Override
    public void onClick(View v) {

        //账号密码
        String accunt = mEt_accunt.getText().toString();
        String psw = mEt_psw.getText().toString();

        //账号有误
        if (!accunt.matches(Constans.PHONE_REGEX)) {
            ToastUtils.showToast(v.getContext(), "手机号有误");
            return;
        }

        //密码有误
        if (psw.length() < 6) {
            ToastUtils.showToast(v.getContext(), "密码最少为6位哦");
            return;
        }

        //TODO 输入判断
        mLoginCore.login(mContext, mEt_accunt.getText().toString(), mEt_psw.getText().toString(), new OnLoginListener() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.get();
                tags = new HashSet<>();
                Gson gson = new Gson();
                LoadUserInfo loadUserInfo = gson.fromJson(s, LoadUserInfo.class);
//                登录成功 保存密钥
                if (loadUserInfo.isSuccess()) {
                    LoadSuccess loadSuccess = gson.fromJson(loadUserInfo.getMsg(), LoadSuccess.class);
                    //设置极光推送的tag
                    tags.add(loadSuccess.getRoleId() + "");
                    MyLogger.jLog().i("设置tag成功，tag：" + loadSuccess.getRoleId());
//                    保存密钥
                    SpUtil.setParam(mContext, SpConstant.SIGN, loadSuccess.getSign());
//                    保存用户id
                    SpUtil.setParam(mContext, SpConstant.USER_ID, loadSuccess.getUserId());
//                    保存认证信息
                    SpUtil.getParam(mContext, SpConstant.ROLEID, loadSuccess.getRoleId());
//                    存储账号和密码等信息
                    SpUtil.setParam(mContext, SpConstant.USER_NAEM, mEt_accunt.getText().toString());
                    SpUtil.setParam(mContext, SpConstant.PASSWORD, mEt_psw.getText().toString());

                    MyLogger.jLog().i("PSWuseid:" + loadSuccess.getUserId());
//                    显示吐司
                    ToastUtils.showToast(mContext, "登录成功");

                    JPushInterface.setTags(mContext, tags, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            MyLogger.jLog().i("设置tags成功");
                        }
                    });

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
    }

    @Override
    public void onDestroy() {
        mLoginCore.stopReques();
        super.onDestroy();
    }
}
