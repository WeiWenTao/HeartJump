package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ViewUtils.inject(this);
        initBar();

        initView();
    }

    private void initView() {
        et_phone_num.addTextChangedListener(this);
        et_yanzhengm.addTextChangedListener(this);
        et_nickname.addTextChangedListener(this);
        et_set_psw.addTextChangedListener(this);
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

    private CountDownTimer downTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            tv_get_yanzhengm.setText("("+(l / 1000)+"s)重新获取");
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
    public void clickGetYzm(View view){
        downTimer.start();
    }
}
