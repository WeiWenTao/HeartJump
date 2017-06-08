package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.utils.CommonUtils;

/**
 * Created by 911 on 2017/4/21.
 */

public class DialogBirthdayStyle extends Dialog {

    private BirthdayDate mBirDate;
    private int year;
    private int month;
    private int day;
    //是否更改过生日日期
    private boolean isChangeBirthday;

    public DialogBirthdayStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_birthday);

        isChangeBirthday = false;

        String s = CommonUtils.getCurrentDate();
        String y = s.substring(0, 4);
        String m = s.substring(s.indexOf("年") + 1, s.indexOf("月"));
        String d = s.substring(s.indexOf("月") + 1, s.indexOf("日"));

        mBirDate = new BirthdayDate(Integer.parseInt(y), Integer.parseInt(m) - 1, Integer.parseInt(d));

        DatePicker datePicker = (DatePicker) findViewById(R.id.dp);

        //初始化
        datePicker.init(year, month , day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mBirDate.setDay(dayOfMonth);
                mBirDate.setMonth(monthOfYear);
                mBirDate.setYear(year);
                isChangeBirthday = true;
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
                if (mOnDialogBtClick!=null){
                      mOnDialogBtClick.onClickComplete(mBirDate,isChangeBirthday);
                }

                dismiss();
            }
        });
        //设置分割线颜色
        CommonUtils.setDatePickerDividerColor(getContext(),datePicker);
    }

    public void initDate(int year,int month ,int day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }




    public interface onDialogBtClick{
        void onClickComplete(BirthdayDate date,boolean isChange);
    }

    public void setOnDialogBtClick(onDialogBtClick onDialogBtClick) {
        mOnDialogBtClick = onDialogBtClick;
    }

    private onDialogBtClick mOnDialogBtClick;
}
