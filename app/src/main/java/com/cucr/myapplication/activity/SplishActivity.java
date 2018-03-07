package com.cucr.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ZipUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SplishActivity extends Activity {

//    private TextView mTimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);

        EventBus.getDefault().register(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

        initData();

        timmer();
    }

    private void timmer() {
//        mTimmer = (TextView) findViewById(R.id.tv_timmer);
        downTimer.start();

    }

    //验证码倒计时
    private CountDownTimer downTimer = new CountDownTimer(4 * 500, 500) {
        @Override
        public void onTick(long l) {
//            if (l >= 1000) {
//                    mTimmer.setText((l / 1000) +1 + "s");
//                } else {
//                    mTimmer.setText(1 + "s");
//            }
            MyLogger.jLog().i("timmer:"+l);
        }


        @Override
        public void onFinish() {
            MyLogger.jLog().i("timmer:finish");
            startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
        }
    };

    private void initData() {
        //实例化文件对象 判断文件是否存在
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase");
        file.mkdir();
        if (!file.exists()) {
            //解压文件
            initZip();
        }
    }

    private void initZip() {
        ThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                MyLogger.jLog().i("解压文件");
                try {
                    InputStream is = getAssets().open("citys.zip");
                    FileOutputStream os = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase", "city.db"));
                    ZipUtil.unzip(is, os);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        downTimer.cancel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getevent(EventChageAccount event) {
        finish();
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        downTimer.cancel();
    }
}
