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

public class DialogGenderStyle extends Dialog implements View.OnClickListener {

    public DialogGenderStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gender);

        TextView tv_boy = (TextView) findViewById(R.id.tv_boy);
        TextView tv_girl = (TextView) findViewById(R.id.tv_girl);

        tv_boy.setOnClickListener(this);
        tv_girl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_boy:
                if (mOnClickGender != null){
                    mOnClickGender.onClickBoy();
                    dismiss();
                }
                break;

            case R.id.tv_girl:
                if (mOnClickGender != null){
                    mOnClickGender.onClickGirl();
                    dismiss();
                }
                break;
        }
    }

    public interface OnClickGender{
        void onClickBoy();
        void onClickGirl();
    }

    private OnClickGender mOnClickGender;

    public void setOnClickGender(OnClickGender onClickGender) {
        mOnClickGender = onClickGender;
    }
}
