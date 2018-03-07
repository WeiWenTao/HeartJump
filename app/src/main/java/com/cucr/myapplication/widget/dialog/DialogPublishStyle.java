package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/14.
 */

public class DialogPublishStyle extends Dialog {

    public DialogPublishStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_publish);

        findViewById(R.id.tv_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        findViewById(R.id.tv_dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickConfirm!=null){
                    onClickConfirm.OnConfirm();
                }
            }
        });
    }

    //点击确定按钮的接口回调
    public interface OnClickConfirm{
        void OnConfirm();
    }

    public void setOnClickConfirm(OnClickConfirm onClickConfirm) {
        this.onClickConfirm = onClickConfirm;
    }

    private OnClickConfirm onClickConfirm;


}
