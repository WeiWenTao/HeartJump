package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogPayStyle extends Dialog implements View.OnClickListener {

    public DialogPayStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay);

        findViewById(R.id.iv_pay).setOnClickListener(this);
        findViewById(R.id.iv_wei_xin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //支付宝支付
            case R.id.iv_pay:
                if (mOnClickPay!=null){
                    mOnClickPay.OnCLickAliPay();
                    dismiss();
                }
                break;

            //微信支付
            case R.id.iv_wei_xin:
                if (mOnClickPay!=null){
                    mOnClickPay.OnClickWxPay();
                    dismiss();
                }
                break;

        }
    }

    private OnClickPay mOnClickPay;

    public void setOnClickPay(OnClickPay onClickPay) {
        mOnClickPay = onClickPay;
    }

    public interface OnClickPay{
        void OnCLickAliPay();

        void OnClickWxPay();
    }
}
