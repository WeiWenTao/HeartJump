package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.AppInfo;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class SettingAboveUsActivity extends BaseActivity {

    @ViewInject(R.id.tv_code)
    private TextView tv_code;

    private AppCore mCore;
    private Gson mGson;

    @Override
    protected void initChild() {
        mGson = MyApplication.getGson();
        mCore = new AppCore();
        initTitle("关于我们");
        initData();
    }

    private void initData() {
        mCore.queryCode(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                AppInfo appInfo = mGson.fromJson(response.get(), AppInfo.class);
                String keyFild = appInfo.getKeyFild();      //版本号
                String valueFild = appInfo.getValueFild();  //下载地址
                tv_code.setText(keyFild);
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_above_us;
    }

    //意见反馈
    @OnClick(R.id.rl_feedback)
    public void feedback(View view) {
        startActivity(new Intent(MyApplication.getInstance(), FeedbackActivity.class));
    }
}
