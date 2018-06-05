package com.cucr.myapplication.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by cucr on 2018/6/1.
 */

public class PromissUtils {

    public static void showDialogTipUserGoToAppSettting(Activity activity, String title) {
        new AlertDialog.Builder(activity)
                .setTitle(title + "权限不可用")
                .setMessage("请在-应用设置-权限-中，允许心跳互娱使用" + title + "权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, 123);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).show();
    }
}
