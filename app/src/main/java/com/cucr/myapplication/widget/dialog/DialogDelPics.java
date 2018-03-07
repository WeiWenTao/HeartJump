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

public class DialogDelPics extends Dialog implements View.OnClickListener {

    public DialogDelPics(Context context, int themeResId) {
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
        setContentView(R.layout.dialog_del);

        initView();

    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(true);

        TextView tv_del = (TextView) findViewById(R.id.tv_del);
        TextView cancel = (TextView) findViewById(R.id.cancel);

        tv_del.setOnClickListener(this);
        cancel.setOnClickListener(this);

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
    }
}
