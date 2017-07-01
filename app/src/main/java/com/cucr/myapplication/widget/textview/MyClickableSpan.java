package com.cucr.myapplication.widget.textview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.cucr.myapplication.utils.ToastUtils;

/**
 * Created by 911 on 2017/6/28.
 */

public class MyClickableSpan extends ClickableSpan {
    private String content;

    public MyClickableSpan(String content) {
        this.content = content;
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
        ToastUtils.showToast(widget.getContext(),content);
    }
}
