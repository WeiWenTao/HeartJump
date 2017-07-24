package com.cucr.myapplication.activity.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.signcalendar.SignCalendar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignActivity extends Activity {

    //头部
    @ViewInject(R.id.rl_head)
    RelativeLayout head;

    private SignCalendar calendar;
    private String date;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ViewUtils.inject(this);

        initHead();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date = formatter.format(curDate);

        mTv = (TextView) findViewById(R.id.tv);
        calendar = (SignCalendar) findViewById(R.id.sc_main);

        //初始化标题日期
        mTv.setText(calendar.getCalendarYear()+" - "+calendar.getCalendarMonth());

                List<String> list = new ArrayList<String>();
                list.add(date);
//              当天日期标记
                calendar.addMarks(list, 0);

        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                mTv.setText(year+" - "+month);
            }
        });
    }

    //初始化头部 沉浸栏
    private void initHead() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this,73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //点击返回
    @OnClick(R.id.iv_sign_back)
    public void back(View view){
        finish();
    }

}
