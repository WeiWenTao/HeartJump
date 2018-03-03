package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.fuli.ErWeiMaInfo;
import com.cucr.myapplication.utils.QRCodeUtil;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogErWeiMa extends Dialog {

    private ErWeiMaInfo info;

    public DialogErWeiMa(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setDate(ErWeiMaInfo info) {
        this.info = info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_er_wei_ma);
        //设置点击外部消失
        setCanceledOnTouchOutside(true);
        TextView name = (TextView) findViewById(R.id.tv_active_name);
        ImageView iv_code = (ImageView) findViewById(R.id.iv_code);
        TextView number = (TextView) findViewById(R.id.tv_number);

        name.setText(info.getName());
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(info.getNumber(), 480, 480);
        iv_code.setImageBitmap(mBitmap);
        number.setText("券号: " + info.getNumber());
    }

    @Override
    public void show() {
        super.show();
    }
}
