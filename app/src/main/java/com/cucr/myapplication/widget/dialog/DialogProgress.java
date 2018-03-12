package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.MyLogger;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogProgress extends Dialog {


    private ProgressBar mPb;
    private TextView mTv_title;
    private TextView mTv_progress;

    public DialogProgress(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        initView();
    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mPb = (ProgressBar) findViewById(R.id.pb);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_progress = (TextView) findViewById(R.id.tv_progress);
    }

    public void setTitle(String title){
        mTv_title.setText(title);
    }

    public void setProgress(int progress){
        if (progress == 0){
            progress = 1;
        }
        MyLogger.jLog().i("我被调用了,proregss:"+progress);
        mPb.setProgress(progress);
        mTv_progress.setText("已上传: "+progress +" / 100");

    }

}
