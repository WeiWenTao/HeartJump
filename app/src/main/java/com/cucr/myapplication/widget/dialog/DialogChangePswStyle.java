package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/27.
 */

public class DialogChangePswStyle extends Dialog implements View.OnClickListener {

    private EditText et_old_psw;
    private EditText mEt_new_psw;
    private TextView btn_save;
    private TextView btn_cancel;

    public DialogChangePswStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting_psw);

        initView();
    }

    private void initView() {
        et_old_psw = (EditText) findViewById(R.id.et_old_psw);
        mEt_new_psw = (EditText) findViewById(R.id.et_new_psw);
        btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        btn_save = (TextView) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_save:
                if (mClickListener != null) {
                    mClickListener.onClickSave(et_old_psw.getText().toString(), mEt_new_psw.getText().toString());
                    et_old_psw.setText("");
                    mEt_new_psw.setText("");
                    et_old_psw.requestFocus();
                }
                break;
        }
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onClickSave(String oldPsw, String newPsw);
    }
}
