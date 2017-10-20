package com.cucr.myapplication.widget.text;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cucr.myapplication.activity.dongtai.PersonalMainPagerActivity;
import com.cucr.myapplication.utils.ToastUtils;

/**
 * Created by 911 on 2017/6/28.
 */

public class MyClickGoHomePager extends ClickableSpan {
    private String content;
    private Context context;

    public MyClickGoHomePager(String content, Context context) {
        this.content = content;
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
        ToastUtils.showToast(widget.getContext(), "跳转到用户主页");
        context.startActivity(new Intent(context, PersonalMainPagerActivity.class));
    }
}
