package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.ToastUtils;

/**
 * Created by 911 on 2017/4/27.
 */

public class DialogChangePswStyle extends Dialog implements View.OnClickListener {

    private boolean needShowNewPsw;
    private boolean needShowConfirmPsw;
    private ImageView mIv_new_psw;
    private ImageView mIv_confirm_psw;
    private EditText et_old_psw;
    private EditText mEt_new_psw;
    private EditText mEt_confirm_psw;
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
        mIv_new_psw = (ImageView) findViewById(R.id.iv_new_psw);
        mIv_confirm_psw = (ImageView) findViewById(R.id.iv_confirm_psw);
        et_old_psw = (EditText) findViewById(R.id.et_old_psw);
        mEt_new_psw = (EditText) findViewById(R.id.et_new_psw);
        mEt_confirm_psw = (EditText) findViewById(R.id.et_confirm_psw);
        btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        btn_save = (TextView) findViewById(R.id.btn_save);

        mIv_new_psw.setOnClickListener(this);
        mIv_confirm_psw.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_new_psw:
                needShowNewPsw = !needShowNewPsw;
                if (needShowNewPsw) {
                    //显示密码
                    mIv_new_psw.setImageResource(R.drawable.eye);
                    mEt_new_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {

                    //隐藏密码
                    mIv_new_psw.setImageResource(R.drawable.eye_no);
                    mEt_new_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                //将光标移至末尾
                mEt_new_psw.setSelection(mEt_new_psw.getText().length());
                break;

            case R.id.iv_confirm_psw:
                needShowConfirmPsw = !needShowConfirmPsw;

                if (needShowConfirmPsw) {
                    mIv_confirm_psw.setImageResource(R.drawable.eye);
                    mEt_confirm_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    mIv_confirm_psw.setImageResource(R.drawable.eye_no);
                    mEt_confirm_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                //将光标移至末尾
                mEt_confirm_psw.setSelection(mEt_confirm_psw.getText().length());
                break;

            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_save:
                if (!mEt_new_psw.getText().toString().equals(mEt_confirm_psw.getText().toString())) {
                    ToastUtils.showToast("两次输入密码不一致哦");
                } else {
                    if (mClickListener != null) {
                        mClickListener.onClickSave(et_old_psw.getText().toString(),mEt_new_psw.getText().toString());
                        et_old_psw.setText("");
                        mEt_new_psw.setText("");
                        mEt_confirm_psw.setText("");
                        et_old_psw.requestFocus();
                    }
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
