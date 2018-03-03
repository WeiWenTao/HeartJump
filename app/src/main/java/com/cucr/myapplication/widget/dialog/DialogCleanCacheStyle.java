package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.utils.CacheUtils;
import com.cucr.myapplication.utils.ToastUtils;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogCleanCacheStyle extends Dialog implements View.OnClickListener {

    public DialogCleanCacheStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_clean_cache);

        TextView confirm = (TextView) findViewById(R.id.tv_clean_confirm);
        TextView cancle = (TextView) findViewById(R.id.tv_clean_cancle);
        confirm.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clean_cancle:
                dismiss();
                break;

            case R.id.tv_clean_confirm:
                dismiss();
                try {
                    if (CacheUtils.getTotalCacheSize(MyApplication.getInstance()).equals("0K")) {
                        ToastUtils.showToast("已经很干净了哦,过会再来清理吧");
                    } else {
                        ToastUtils.showToast("成功清除" + CacheUtils.getTotalCacheSize(MyApplication.getInstance()) + "缓存");
                        CacheUtils.clearAllCache(MyApplication.getInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
