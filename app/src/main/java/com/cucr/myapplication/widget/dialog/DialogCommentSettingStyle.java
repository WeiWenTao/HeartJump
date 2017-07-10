package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogCommentSettingStyle extends Dialog implements View.OnClickListener {

    private ImageView mIv_everyone;
    private ImageView mIv_my_focus;
    private ImageView mIv_close_comment;
    private ImageView temp;

    public DialogCommentSettingStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting_at_me);

        RelativeLayout rl_everyone = (RelativeLayout) findViewById(R.id.rl_everyone);
        RelativeLayout rl_my_focus = (RelativeLayout) findViewById(R.id.rl_my_focus);
        RelativeLayout rl_close_comment = (RelativeLayout) findViewById(R.id.rl_close_comment);

        mIv_everyone = (ImageView) findViewById(R.id.iv_everyone);
        temp = mIv_everyone;
        mIv_my_focus = (ImageView) findViewById(R.id.iv_my_focus);
        mIv_close_comment = (ImageView) findViewById(R.id.iv_close_at);

        rl_everyone.setOnClickListener(this);
        rl_my_focus.setOnClickListener(this);
        rl_close_comment.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_everyone:
                temp.setVisibility(View.GONE);
                mIv_everyone.setVisibility(View.VISIBLE);
                temp = mIv_everyone;
                break;

            case R.id.rl_my_focus:
                temp.setVisibility(View.GONE);
                mIv_my_focus.setVisibility(View.VISIBLE);
                temp = mIv_my_focus;
                break;

            case R.id.rl_close_comment:
                temp.setVisibility(View.GONE);
                mIv_close_comment.setVisibility(View.VISIBLE);
                temp = mIv_close_comment;
                break;
        }
    }
}
