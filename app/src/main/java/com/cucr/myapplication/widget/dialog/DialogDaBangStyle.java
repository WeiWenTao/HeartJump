package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogDaBangStyle extends Dialog implements View.OnClickListener {

    private EditText et_other;
    private RadioGroup mRg_xingbi;
    private InputMethodManager imm;
    private String text;

    public DialogDaBangStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_da_bang);
        initView();
    }

    private void initView() {
        mRg_xingbi = (RadioGroup) findViewById(R.id.rg_xingbi);
        TextView et_tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        TextView tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        TextView tv_dashang_title = (TextView) findViewById(R.id.tv_dashang_title);
        tv_dashang_title.setText(text);
        et_tv_cancle.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }


    public void setData(String text) {
        this.text = "给" + text + "打榜";
    }

    //获取星币数量
    public int getChildNum(int child) {

        switch (child) {
            case R.id.rb1:

                return 10;

            case R.id.rb2:

                return 20;

            case R.id.rb3:

                return 100;

            default:

                return -1;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                dismiss();
                break;

            case R.id.tv_confirm:
                if (confirmListener != null) {
                    int childNum = getChildNum(mRg_xingbi.getCheckedRadioButtonId());
                    confirmListener.onClickConfirm(childNum);
                    dismiss();
                }
                break;

        }
    }


    //接口回调
    private ClickconfirmListener confirmListener;

    public void setConfirmListener(ClickconfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public interface ClickconfirmListener {
        void onClickConfirm(int howMuch);
    }
}
