package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;

import org.zackratos.ultimatebar.UltimateBar;

public class InvateActivity extends Activity {

    private DialogShareStyle mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invate);

        //沉浸栏
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        mDialog = new DialogShareStyle(this, R.style.ShowAddressStyleTheme);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.mystyle);  //添加动画

    }

    public void invate(View view) {
        mDialog.show();

    }
}
