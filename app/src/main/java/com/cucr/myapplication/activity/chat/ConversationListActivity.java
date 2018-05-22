package com.cucr.myapplication.activity.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.cucr.myapplication.R;

import org.zackratos.ultimatebar.UltimateBar;

/**
 * Created by cucr on 2018/3/15.
 */

public class ConversationListActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversationlist);
        //设置状态栏字体颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.white), 0);
        findViewById(R.id.iv_base_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
