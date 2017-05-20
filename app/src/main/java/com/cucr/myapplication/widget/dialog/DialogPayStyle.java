package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogPayStyle extends Dialog {

    public DialogPayStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay);
    }
}
