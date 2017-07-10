package com.cucr.myapplication.activity.pay;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonUtils;
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

    //沉浸栏
    @ViewInject(R.id.head)
    RelativeLayout head;



    private Map<Integer,Integer> moneys;
    private DialogPayStyle mDailogPayStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_center);
        ViewUtils.inject(this);
        initHead();

        moneys = new HashMap<>();

        mDailogPayStyle = new DialogPayStyle(this, R.style.ShowAddressStyleTheme);

        findRG();
        initRBS();
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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
