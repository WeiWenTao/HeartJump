package com.cucr.myapplication.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by 911 on 2017/8/18.
 */

public class WaitDialog extends ProgressDialog {

    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage("请稍后。。。");
    }

}