package com.cucr.myapplication.widget.text;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cucr.myapplication.activity.regist.RegistActivity;

/**
 * Created by 911 on 2017/6/28.
 */

public class MyClickRegist extends ClickableSpan {

    public MyClickRegist() {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //是否显示下划线
        ds.setUnderlineText(true);
    }

    @Override
    public void onClick(View widget) {
        Context context = widget.getContext();
        Intent intent = new Intent(context, RegistActivity.class);

        context.startActivity(intent);

    }
}
