package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/27.
 */

public class DialogChangePswStyle extends Dialog implements View.OnClickListener {

    private boolean needShowNewPsw;
    private boolean needShowConfirmPsw;
    private ImageView mIv_new_psw;
    private ImageView mIv_confirm_psw;
    private EditText mEt_new_psw;
    private EditText mEt_confirm_psw;

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
        mIv_new_psw = (ImageView) findViewById(R.id.iv_new_psw);
        mIv_confirm_psw = (ImageView) findViewById(R.id.iv_confirm_psw);
        mEt_new_psw = (EditText) findViewById(R.id.et_new_psw);
        mEt_confirm_psw = (EditText) findViewById(R.id.et_confirm_psw);

        mIv_new_psw.setOnClickListener(this);
        mIv_confirm_psw.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_new_psw:
                needShowNewPsw = !needShowNewPsw;
                if (needShowNewPsw){
                    //显示密码
                    mIv_new_psw.setImageResource(R.drawable.eye);
                    mEt_new_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else {

                    //隐藏密码
                    mIv_new_psw.setImageResource(R.drawable.eye_no);
                    mEt_new_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                //将光标移至末尾
                mEt_new_psw.setSelection(mEt_new_psw.getText().length());
                break;

            case R.id.iv_confirm_psw:
                needShowConfirmPsw = !needShowConfirmPsw;

                if (needShowConfirmPsw){
                    mIv_confirm_psw.setImageResource(R.drawable.eye);
                    mEt_confirm_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else {
                    mIv_confirm_psw.setImageResource(R.drawable.eye_no);
                    mEt_confirm_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                //将光标移至末尾
                mEt_confirm_psw.postInvalidate();
                break;
        }
    }
}
