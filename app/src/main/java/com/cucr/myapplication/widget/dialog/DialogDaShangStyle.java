package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogDaShangStyle extends Dialog implements View.OnClickListener {

    private EditText et_other;
    private RadioGroup mRg_xingbi;
    private InputMethodManager imm;

    public DialogDaShangStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_da_shang);

        initView();

    }


    private int preChild;

    private void initView() {
        mRg_xingbi = (RadioGroup) findViewById(R.id.rg_xingbi);
        et_other = (EditText) findViewById(R.id.et_other);
        TextView et_tv_cancleother = (TextView) findViewById(R.id.tv_cancle);
        TextView tv_confirm = (TextView) findViewById(R.id.tv_confirm);

        et_tv_cancleother.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        et_other.setOnClickListener(this);

        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);


        mRg_xingbi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                et_other.setHint("其他数量");

                //不知为何  点击editText会调用VG的监听 在这里做个判断
                if (checkedId != preChild) {
                    et_other.setText("");
                }

                et_other.setCursorVisible(false);
                //关闭软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                preChild = checkedId;
            }
        });

    }

    public int getChildNum(int child) {

        switch (child) {
            case R.id.rb1:

                return 1;

            case R.id.rb2:

                return 5;

            case R.id.rb3:

                return 10;

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
                    String etNum = null;
                    int childNum = getChildNum(mRg_xingbi.getCheckedRadioButtonId());
                    String text = et_other.getText().toString().trim();

                    if (!TextUtils.isEmpty(text)){
                        etNum = text;
                    }else {
                        etNum = "0";
                    }

                    int howMuch = childNum == -1 ? Integer.parseInt(etNum) : childNum;
                    confirmListener.onClickConfirm(howMuch);
                }
                break;

            case R.id.et_other:
                mRg_xingbi.clearCheck();
                et_other.setHint("");
                et_other.setCursorVisible(true);
                //显示软键盘
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
