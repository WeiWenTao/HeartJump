package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogShareDelPics extends Dialog implements View.OnClickListener {

    public DialogShareDelPics(Context context, int themeResId) {
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
        setContentView(R.layout.dialog_share_del);
        initView();
    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(true);

        TextView tv_del = (TextView) findViewById(R.id.tv_del);
        TextView tv_share = (TextView) findViewById(R.id.tv_share);
        TextView cancel = (TextView) findViewById(R.id.cancel);
        TextView tv_report = (TextView) findViewById(R.id.tv_report);

        tv_del.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        cancel.setOnClickListener(this);
        tv_report.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_del:
                if (mOnClickBt != null) {
                    mOnClickBt.clickDel();
                }
                dismiss();
                break;

            case R.id.tv_report:
                if (mOnClickBt != null) {
                    mOnClickBt.clickReport();
                }
                dismiss();
                break;

            case R.id.tv_share:
                if (mOnClickBt != null) {
                    mOnClickBt.clickShare();
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
        void clickDel();

        void clickShare();

        void clickReport();
    }
}
