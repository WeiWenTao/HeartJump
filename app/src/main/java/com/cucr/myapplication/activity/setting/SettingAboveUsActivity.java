package com.cucr.myapplication.activity.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.AppInfo;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.AppUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.utils.upDataUtils.DownLoadApk;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class SettingAboveUsActivity extends BaseActivity implements RequersCallBackListener {

    @ViewInject(R.id.tv_code)
    private TextView tv_code;

    private AppCore mCore;

    @Override
    protected void initChild() {
        mCore = new AppCore();
        initTitle("关于我们");
        initData();
    }

    private void initData() {
        String verName = AppUtils.getVerName(MyApplication.getInstance());
        tv_code.setText("版本 " + verName);
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

    //意见反馈
    @OnClick(R.id.rlv_upData)
    public void checkVersion(View view) {
        mCore.queryCode(this);
    }

    //去应用市场评分
    @OnClick(R.id.rlv_goMarket)
    public void goMarket(View view) {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showToast("您的手机没有安装Android应用市场");
            e.printStackTrace();
        }
    }

    private void normalUpdate(String text, final AppInfo appInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(text + appInfo.getKeyFild());
        builder.setMessage(appInfo.getRemark());
        AlertDialog.Builder builder1 = builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!AppUtils.canDownloadState()) {
                    showDownloadSetting();
                    return;
                }
                ToastUtils.showToast("已开始后台下载");
                DownLoadApk.download(SettingAboveUsActivity.this, appInfo.getValueFild(), "正在下载", "心跳互娱");
            }
        }).setCancelable(appInfo.getGroupFild() == 0);
        if (appInfo.getGroupFild() == 0) {
            //非强制更新
            builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder1.create().show();
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        AppInfo appInfo = MyApplication.getGson().fromJson(response.get(), AppInfo.class);
        if (AppUtils.getVersionCode(MyApplication.getInstance()) < appInfo.getExtra1()) {
            normalUpdate("心跳互娱有新版本了 V", appInfo);
        } else {
            ToastUtils.showToast("已经是最新版本啦");
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
