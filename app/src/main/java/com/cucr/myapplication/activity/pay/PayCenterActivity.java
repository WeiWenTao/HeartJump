package com.cucr.myapplication.activity.pay;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogPayStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

public class PayCenterActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg1)
    RadioGroup rg1;

    @ViewInject(R.id.rg1_rb1)
    RadioButton rg1_rb1;

    @ViewInject(R.id.rg1_rb2)
    RadioButton rg1_rb2;

    @ViewInject(R.id.rg1_rb3)
    RadioButton rg1_rb3;

    @ViewInject(R.id.rg2)
    RadioGroup rg2;

    @ViewInject(R.id.rg2_rb1)
    RadioButton rg2_rb1;

    @ViewInject(R.id.rg2_rb2)
    RadioButton rg2_rb2;

    @ViewInject(R.id.rg2_rb3)
    RadioButton rg2_rb3;

    @ViewInject(R.id.rg3)
    RadioGroup rg3;

    @ViewInject(R.id.rg3_rb1)
    RadioButton rg3_rb1;

    @ViewInject(R.id.rg3_rb2)
    RadioButton rg3_rb2;

    @ViewInject(R.id.rg3_rb3)
    RadioButton rg3_rb3;

    //立即充值
    @ViewInject(R.id.tv_pay_now)
    TextView tv_pay_now;

    //其他数量
    @ViewInject(R.id.et_other)
    EditText et_other;

    //单位 星币
    @ViewInject(R.id.tv_other_yuan)
    TextView tv_other_yuan;


    private Map<Integer, Integer> moneys;
    private DialogPayStyle mDailogPayStyle;

    //上一个rb
    private RadioButton mPreRB;

    //当前rb
    private RadioButton currentRB;
    private int mMoney;


    @Override
    protected void initChild() {

        initTitle("充值中心");

        moneys = new HashMap<>();

        mDailogPayStyle = new DialogPayStyle(this, R.style.ShowAddressStyleTheme);

        initET();
        findRG();
        initRBS();

    }

    private void initET() {
        et_other.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //清除当前RadioButton的勾选状态
                if (currentRB != null) {

                    //清除当前RadioButton的勾选状态
                    currentRB.setChecked(false);

                    //preId清零
                    preId = 0;
                }
                tv_pay_now.setText("立即充值");

                if (et_other.hasFocus()) {
                    CommonUtils.hideKeyBorad(v.getContext(), v, false);
                    tv_other_yuan.setVisibility(View.VISIBLE);
                    et_other.setHint("      ");
                }

                //et无法响应第一次点击事件  获取焦点时处理事件
                if (TextUtils.isEmpty(et_other.getText())) {
                    tv_pay_now.setText("立即充值");
                    tv_pay_now.setEnabled(false);
                    MyLogger.jLog().i("tv_pay_now.setEnabled(false);");
                }

            }
        });

        et_other.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEtOther();

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s)) {
                    tv_pay_now.setText("立即充值  " + (Integer.parseInt(s.toString()) / 10.0f) + "元");
                } else {
                    tv_pay_now.setText("立即充值");
                    tv_pay_now.setEnabled(false);
                    MyLogger.jLog().i("tv_pay_now.setEnabled(false);");

                    if ((rg1.getCheckedRadioButtonId() != -1 || rg2.getCheckedRadioButtonId() != -1 || rg3.getCheckedRadioButtonId() != -1)) {
                        tv_pay_now.setEnabled(true);
                        MyLogger.jLog().i("tv_pay_now.setEnabled(true);");
                    }

                }
            }
        });

        //不知为何 edittext无法响应第一次点击事件
        et_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEtOther();
                et_other.setCursorVisible(true);
                CommonUtils.hideKeyBorad(v.getContext(), v, false);

                if (currentRB != null) {

                    //清除当前RadioButton的勾选状态
                    currentRB.setChecked(false);

                    //preId清零
                    preId = 0;
                }

                tv_other_yuan.setVisibility(View.VISIBLE);
                et_other.setHint("      ");
                CommonUtils.hideKeyBorad(v.getContext(), v, false);

                if (TextUtils.isEmpty(et_other.getText())) {
                    tv_pay_now.setText("立即充值");
                    tv_pay_now.setEnabled(false);
                    MyLogger.jLog().i("tv_pay_now.setEnabled(false);");
                }

            }
        });
    }

    //根据用户操作修改 “ 立即充值 ” 按钮状态
    private void setEtOther() {
        String s = et_other.getText().toString();
        if (!TextUtils.isEmpty(s.trim()) && Integer.parseInt(s) > 0) {
            tv_pay_now.setEnabled(true);
            MyLogger.jLog().i("setEnabled(true);");
        } else {
            tv_pay_now.setEnabled(false);
            MyLogger.jLog().i("setEnabled(false);");
            if (rg1.getCheckedRadioButtonId() != -1 || rg2.getCheckedRadioButtonId() != -1 || rg3.getCheckedRadioButtonId() != -1) {
                tv_pay_now.setEnabled(true);
                MyLogger.jLog().i("setEnabled(true);");
            }
        }
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_pay_center;
    }


    private void initRBS() {
        moneys.put(R.id.rg1_rb1, 50);
        moneys.put(R.id.rg1_rb2, 100);
        moneys.put(R.id.rg1_rb3, 200);

        moneys.put(R.id.rg2_rb1, 300);
        moneys.put(R.id.rg2_rb2, 500);
        moneys.put(R.id.rg2_rb3, 800);

        moneys.put(R.id.rg3_rb1, 1000);
        moneys.put(R.id.rg3_rb2, 5000);
        moneys.put(R.id.rg3_rb3, 10000);

    }

    private void findRG() {
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        rg3.setOnCheckedChangeListener(this);
    }


    private int preId;


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //设置为可点击 // TODO: 2017/9/16  
        tv_pay_now.setEnabled(true);
        MyLogger.jLog().i("tv_pay_now.setEnabled(true);");

        //隐藏软键盘
        CommonUtils.hideKeyBorad(this, et_other, true);
        tv_other_yuan.setVisibility(View.GONE);
        et_other.setText("");
        et_other.clearComposingText();
        et_other.setHint("其他数量");
        et_other.setCursorVisible(false);


        RadioButton rbNow = ((RadioButton) findViewById(checkedId));
        if (!rbNow.isChecked()) {
            rbNow.setChecked(true);
        }
        if (preId != 0) {
            mPreRB = (RadioButton) findViewById(preId);
            mPreRB.setChecked(false);
        }
        preId = checkedId;
        currentRB = (RadioButton) findViewById(preId);
        mMoney = moneys.get(checkedId);
        tv_pay_now.setText("立即充值  " + (mMoney / 10) + "元");
    }

    @OnClick(R.id.tv_pay_now)
    public void payNow(View view) {

        if (mMoney == 0 && TextUtils.isEmpty(et_other.getText())) {
            ToastUtils.showToast("请选择金额");
            return;
        }

        if (!TextUtils.isEmpty(et_other.getText())) {
            MyLogger.jLog().i("金额是1:" + (Integer.parseInt(et_other.getText().toString()) / 10.0f));
        } else {
            MyLogger.jLog().i("金额是2:" + mMoney / 10.0f );
        }
        mDailogPayStyle.show();
    }


    @OnClick(R.id.ll_other)
    public void click(View view) {
        if (currentRB != null) {

            //清除当前RadioButton的勾选状态
            currentRB.setChecked(false);

            //preId清零
            preId = 0;
        }
    }

    //监听软键盘的确定按钮
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(PayCenterActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
