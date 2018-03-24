package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogPerfirmPayResult extends Dialog implements View.OnClickListener {

    private ProgressBar mPb;
    private TextView mTv_title;
    private TextView tv_yes;

    public DialogPerfirmPayResult(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_perfirm_pay_result);
        setCanceledOnTouchOutside(false);
        setCancelable(false);       //点其他地方不起作用
        initView();
    }

    private void initView() {

        mPb = (ProgressBar) findViewById(R.id.pb);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        tv_yes = (TextView) findViewById(R.id.tv_yes);
        tv_yes.setOnClickListener(this);
    }

    public void setDialog(String title, boolean isShow) {
        mTv_title.setText(title);
        mPb.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tv_yes.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (mOnClickYes != null) {
            mOnClickYes.clickYes();
        }
    }

    private OnClickYes mOnClickYes;

    public interface OnClickYes {
        void clickYes();
    }

    public void setOnClickYes(OnClickYes onClickYes) {
        mOnClickYes = onClickYes;
    }
}
