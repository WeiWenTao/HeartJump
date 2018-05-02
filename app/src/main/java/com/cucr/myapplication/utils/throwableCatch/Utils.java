package com.cucr.myapplication.utils.throwableCatch;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.cucr.myapplication.app.MyApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cucr on 2018/3/7.
 */

public class Utils {
    //处理异常
    public static void handleT(String path, String throwable) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(path + File.separator + "carsh");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            long currTime = System.currentTimeMillis();
            File file = new File(dir, currTime + ".log");
            Log.i("path", file.getAbsolutePath());
            if (!file.exists()) {
                try {
                    PackageManager pm = MyApplication.getInstance().getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(MyApplication.getInstance().getPackageName(),
                            PackageManager.GET_ACTIVITIES);

                    file.createNewFile();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));   //保存异常信息到文件中，可上传到服务器。在开发本人通过Okhttp上传文件。
                    String phoneType = "手机型号=" + Build.MANUFACTURER + " " + Build.MODEL;
                    writer.write(phoneType + "\n");
                    writer.write("versionName:" + (pi.versionName == null ? "null" : pi.versionName + "\n"));
                    writer.write("versionCode:" + pi.versionCode + "" + "\n");
                    writer.write("sdkVersion:" + Build.VERSION.RELEASE);
                    writer.write("\r\n");
                    writer.write(throwable);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            boolean exists = file.exists();
            if (exists) {
                String absolutePath = file.getAbsolutePath();
                Log.i("absolutePath", absolutePath);
            } else {
                Log.i("absolutePath", "NoFound");
            }

            /*Intent intent = new Intent(MyApplication.getInstance(), SplishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MyApplication.getInstance().startActivity(intent);
            EventBus.getDefault().postSticky(new EventChageAccount());*/

//            android.os.Process.killProcess(android.os.Process.myPid());   //关闭应用
        }
    }

    //读出异常文件
    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}