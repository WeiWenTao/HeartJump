package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogCanaleFocusStyle extends Dialog implements View.OnClickListener {

    private TextView mTv_title;

    public DialogCanaleFocusStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancle_focus);

        initView();
//        initTitle("黄晓明");
    }

    public void initTitle(String text) {

        mTv_title.setText("取消关注 "+text +" 吗？");


    }


    @Override
    public void show() {
        super.show();
    }

    private void initView() {

        //设置点击外部消失
        setCanceledOnTouchOutside(true);
        mTv_title = (TextView) findViewById(R.id.tv_cancle_title);
        TextView tv_confirm = (TextView) findViewById(R.id.tv_cancle_focus_confirm);
        TextView tv_cancle = (TextView) findViewById(R.id.tv_cancle_focus_cancle);
        tv_confirm.setText("确定");
        tv_cancle.setText("取消");

        tv_confirm.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle_focus_confirm:
                if (mOnClickBtListener != null) {
                    mOnClickBtListener.clickConfirm();
                }
                break;

            case R.id.tv_cancle_focus_cancle:
                if (mOnClickBtListener != null) {
                    mOnClickBtListener.clickCancle();
                }
                break;
        }
    }


    private OnClickBtListener mOnClickBtListener;

    public void setOnClickBtListener(OnClickBtListener onClickBtListener) {
        mOnClickBtListener = onClickBtListener;
    }

    public interface OnClickBtListener {
        void clickConfirm();

        void clickCancle();
    }
}
