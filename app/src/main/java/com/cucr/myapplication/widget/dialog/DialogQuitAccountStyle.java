package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;

import static com.cucr.myapplication.R.id.tv_quit_confirm;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogQuitAccountStyle extends Dialog implements View.OnClickListener {

    public DialogQuitAccountStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quit_account);

        TextView tv_quit_cancle = (TextView) findViewById(R.id.tv_quit_cancle);
        TextView tv_quit_confirm = (TextView) findViewById(R.id.tv_quit_confirm);

        tv_quit_cancle.setOnClickListener(this);
        tv_quit_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quit_cancle:
                dismiss();
                break;

            case tv_quit_confirm:
                if (mOnClickConfirmQuit != null) {
                    mOnClickConfirmQuit.clickConfirmQuit();
                }
                dismiss();
                break;
        }
    }

    //点击确定退出的接口回调
    public interface OnClickConfirmQuit {
        void clickConfirmQuit();
    }

    public void setOnClickConfirmQuit(OnClickConfirmQuit onClickConfirmQuit) {
        mOnClickConfirmQuit = onClickConfirmQuit;
    }

    private OnClickConfirmQuit mOnClickConfirmQuit;
}
