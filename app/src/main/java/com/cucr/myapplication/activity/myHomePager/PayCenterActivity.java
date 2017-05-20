package com.cucr.myapplication.activity.myHomePager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.dialog.DialogPayStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.Map;

public class PayCenterActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

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



    private Map<Integer,Integer> moneys;
    private DialogPayStyle mDailogPayStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_center);
        ViewUtils.inject(this);
        moneys = new HashMap<>();

        mDailogPayStyle = new DialogPayStyle(this, R.style.ShowAddressStyleTheme);

        findRG();
        initRBS();
    }

    private void initRBS() {
        moneys.put(R.id.rg1_rb1,50);
        moneys.put(R.id.rg1_rb2,100);
        moneys.put(R.id.rg1_rb3,200);

        moneys.put(R.id.rg2_rb1,300);
        moneys.put(R.id.rg2_rb2,500);
        moneys.put(R.id.rg2_rb3,800);

        moneys.put(R.id.rg3_rb1,1000);
        moneys.put(R.id.rg3_rb2,5000);
        moneys.put(R.id.rg3_rb3,10000);
    }

    private void findRG() {
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        rg3.setOnCheckedChangeListener(this);

    }

    @OnClick(R.id.iv_my_pay_back)
    public void back(View view){
        finish();
    }

    private int preId;
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rbNow = ((RadioButton) findViewById(checkedId));
        if (!rbNow.isChecked()){
            rbNow.setChecked(true);
        }
        if (preId != 0){
            RadioButton preRB = (RadioButton) findViewById(preId);
            preRB.setChecked(false);
        }
        preId = checkedId;

        int money = moneys.get(checkedId);
        tv_pay_now.setText("立即充值  "+(money/10)+"元");
    }

    @OnClick(R.id.tv_pay_now)
    public void payNow(View view){

        mDailogPayStyle.show();
    }

}
