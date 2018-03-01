package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.SpUtil;
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
        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
    }

    public void invate(View view) {
        mDialog.setData2(new ShareEntity("this is title", " this is describe",
                HttpContans.ADDRESS_INVATE_REGIST + SpUtil.getParam(SpConstant.USER_ID,
                        -1), ""));
        mDialog.show();
    }
}
