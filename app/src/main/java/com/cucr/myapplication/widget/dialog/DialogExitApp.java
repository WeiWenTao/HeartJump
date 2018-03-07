package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.SplishActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogExitApp extends Dialog implements View.OnClickListener {

    public DialogExitApp(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);

        TextView confirm = (TextView) findViewById(R.id.tv_clean_confirm);
        TextView cancle = (TextView) findViewById(R.id.tv_clean_cancle);
        confirm.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clean_cancle:
                dismiss();
                break;

            case R.id.tv_clean_confirm:
                dismiss();
                Intent intent = new Intent(MyApplication.getInstance(), SplishActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MyApplication.getInstance().startActivity(intent);
                EventBus.getDefault().postSticky(new EventChageAccount());
                break;
        }
    }
}
