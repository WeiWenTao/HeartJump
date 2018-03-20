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
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        findViewById(R.id.iv_base_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
