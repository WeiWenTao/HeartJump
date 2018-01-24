package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.model.setting.BirthdayDate;
import com.cucr.myapplication.utils.CommonUtils;

import java.util.Calendar;
import java.util.Date;

import static com.cucr.myapplication.R.id.dp;

/**
 * Created by 911 on 2017/4/21.
 */

public class DialogBirthdayStyle extends Dialog {

    private BirthdayDate mBirDate;
    private int year;
    private int month;
    private int day;
    private String week;
    //是否更改过生日日期
    private boolean isChangeBirthday;
    private boolean maxOrMin;

    //boolean maxOrMin true表示有max上限 false则表示没有
    public DialogBirthdayStyle(Context context, int themeResId, boolean maxOrMin) {
        super(context, themeResId);
        this.maxOrMin = maxOrMin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_birthday);

        isChangeBirthday = false;
        setCanceledOnTouchOutside(true);
        String s = CommonUtils.getCurrentDate();
        String y = s.substring(0, 4);
        String m = Integer.parseInt(s.substring(5, 7)) - 1 + "";
        String d = s.substring(8);

        mBirDate = new BirthdayDate(Integer.parseInt(y), Integer.parseInt(m) - 1, Integer.parseInt(d));

        DatePicker datePicker = (DatePicker) findViewById(dp);
        if (maxOrMin) {
            datePicker.setMaxDate(new Date().getTime());
        } else {
            datePicker.setMinDate(new Date().getTime() - 1000);
        }

        //初始化
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mBirDate.setDay(dayOfMonth);
                mBirDate.setMonth(monthOfYear);
                mBirDate.setYear(year);
                isChangeBirthday = true;
                Calendar instance = Calendar.getInstance();
                instance.set(year, monthOfYear, dayOfMonth);
                mBirDate.setWeek(CommonUtils.getWeek(instance));
            }
        });


        TextView tv_birthday_cancle = (TextView) findViewById(R.id.tv_birthday_cancle);
        TextView tv_birthday_complete = (TextView) findViewById(R.id.tv_birthday_complete);

        tv_birthday_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_birthday_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogBtClick != null) {
                    mOnDialogBtClick.onClickComplete(mBirDate, isChangeBirthday);
                }

                dismiss();
            }
        });
        //设置分割线颜色
        CommonUtils.setDatePickerDividerColor(getContext(), datePicker);
    }

    public void initDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }


    public interface onDialogBtClick {
        void onClickComplete(BirthdayDate date, boolean isChange);
    }

    public void setOnDialogBtClick(onDialogBtClick onDialogBtClick) {
        mOnDialogBtClick = onDialogBtClick;
    }

    private onDialogBtClick mOnDialogBtClick;
}
