package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogYyhd extends Dialog implements View.OnClickListener {


    public DialogYyhd(Context context, int themeResId) {
        super(context, themeResId);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_yyhd);

        initView();

    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(true);

        TextView tv_yyhd_1 = (TextView) findViewById(R.id.tv_yyhd_1);
        TextView tv_yyhd_2 = (TextView) findViewById(R.id.tv_yyhd_2);
        TextView tv_yyhd_3 = (TextView) findViewById(R.id.tv_yyhd_3);
        LinearLayout cancel = (LinearLayout) findViewById(R.id.cancel);

        tv_yyhd_1.setOnClickListener(this);
        tv_yyhd_2.setOnClickListener(this);
        tv_yyhd_3.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yyhd_1:
                if (mOnClickBt != null) {
                    mOnClickBt.clickYyhd1();
                }
                dismiss();
                break;

            case R.id.tv_yyhd_2:
                if (mOnClickBt != null) {
                    mOnClickBt.clickYyhd2();
                }
                dismiss();
                break;

            case R.id.tv_yyhd_3:
                if (mOnClickBt != null) {
                    mOnClickBt.clickYyhd3();
                }
                dismiss();
                break;

            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private OnClickBt mOnClickBt;

    public void setOnClickBt(OnClickBt onClickBt) {
        mOnClickBt = onClickBt;
    }

    public interface OnClickBt {
        void clickYyhd1();

        void clickYyhd2();

        void clickYyhd3();
    }
}
