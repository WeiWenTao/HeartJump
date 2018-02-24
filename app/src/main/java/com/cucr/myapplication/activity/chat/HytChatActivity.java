package com.cucr.myapplication.activity.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cucr.myapplication.R;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class HytChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyt_chat);

        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, "xthy003");
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}
