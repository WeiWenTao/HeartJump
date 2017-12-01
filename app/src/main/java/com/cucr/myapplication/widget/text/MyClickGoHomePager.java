package com.cucr.myapplication.widget.text;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.utils.ToastUtils;

/**
 * Created by 911 on 2017/6/28.
 */

public class MyClickGoHomePager extends ClickableSpan {
    private int userId;
    private Context context;

    public MyClickGoHomePager(int userId, Context context) {
        this.userId = userId;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //是否显示下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
//        Intent intent = new Intent(widget.getContext(), OtherActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("content", content);
//        intent.putExtra("bundle", bundle);
//        startActivity(intent);
        Intent intent = new Intent(context, PersonalMainPagerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId",userId);
        context.startActivity(intent);
    }
}
