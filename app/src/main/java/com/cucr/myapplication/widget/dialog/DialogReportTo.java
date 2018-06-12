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

public class DialogReportTo extends Dialog implements View.OnClickListener {

    public DialogReportTo(Context context, int themeResId) {
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
        setContentView(R.layout.dialog_report);

        initView();

    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(true);

        TextView cancel = (TextView) findViewById(R.id.cancel);
        TextView tv_report = (TextView) findViewById(R.id.tv_report);

        cancel.setOnClickListener(this);
        tv_report.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_report:
                if (mOnClickBt != null) {
                    mOnClickBt.clickReportTo();
                }
                dismiss();
                break;

            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private OnClickShareTo mOnClickBt;

    public void setOnClickBt(OnClickShareTo onClickBt) {
        mOnClickBt = onClickBt;
    }

    public interface OnClickShareTo {

        void clickReportTo();
    }
}
