package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogLoad extends Dialog {

    private TextView mTv_title;
    private TextView mTv_progress;

    public DialogLoad(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);

        initView();

    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_progress = (TextView) findViewById(R.id.tv_progress);

    }

    public void setTitle(String title){
        mTv_title.setText(title);
    }


}
