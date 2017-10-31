package com.cucr.myapplication.activity.home;

import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.widget.signcalendar.SignCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignActivity extends BaseActivity {


    private SignCalendar calendar;
    private String date;
    private TextView mTv;

    @Override
    protected void initChild() {
        initTitle("签到");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date = formatter.format(curDate);

        mTv = (TextView) findViewById(R.id.tv);
        calendar = (SignCalendar) findViewById(R.id.sc_main);

        //初始化标题日期
        mTv.setText(calendar.getCalendarYear() + " - " + calendar.getCalendarMonth());

//------------------------------------------------------
        List<String> list = new ArrayList<>();
        list.add(date);
        list.add("2017-10-21");
        MyLogger.jLog().i("date:" + date);
//              当天日期标记
        calendar.addMarks(list, 0);
//------------------------------------------------------
        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                mTv.setText(year + " - " + month);
            }
        });

        calendar.setOnCalendarClickListener(new SignCalendar.OnCalendarClickListener() {
            @Override
            public void onCalendarClick(int row, int col, String dateFormat) {
                if (calendar.hasMarked(dateFormat)) {
                    calendar.removeMark(dateFormat);
                } else {
                    calendar.addMark(dateFormat, 0);
                }
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_sign;
    }
}
