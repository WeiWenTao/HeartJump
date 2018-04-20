package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/27.
 */

public class DialogSearchEmpty extends Dialog implements View.OnClickListener {


    private TextView tv_confirm;
    private TextView tv_cancle;
    private TextView tv_who;
    private String name;

    public DialogSearchEmpty(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_empty);

        initView();
    }

    public void setName(String name) {
        this.name = name;
        SpannableString sp = new SpannableString("是否将“" + name + "”推荐给心跳互娱？");
        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4f49")), 4, 4 + name.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        sp.setSpan(new AbsoluteSizeSpan(15, true), 4, 4 + name.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 4, 4 + name.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //SpannableString对象设置给TextView
        tv_who.setText(sp);
    }

    private void initView() {
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        tv_who = (TextView) findViewById(R.id.tv_who);
        tv_confirm.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_cancle:
                dismiss();
                break;

            case R.id.tv_confirm:
                if (mClickListener != null) {
                    mClickListener.onClickConfirm(name);
                }
                dismiss();
                break;
        }
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onClickConfirm(String who);
    }
}
