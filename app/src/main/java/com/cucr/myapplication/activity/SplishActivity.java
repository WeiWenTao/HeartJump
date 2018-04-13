package com.cucr.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.SplishInfo;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ThreadUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.utils.ZipUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

public class SplishActivity extends Activity implements RequersCallBackListener {

    private ImageView mIv_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish);
        EventBus.getDefault().register(this);
//        UltimateBar ultimateBar = new UltimateBar(this);
//        ultimateBar.setImmersionBar();


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
        initPermission();
        initData();

    }

    private void initViews() {

        Date now = new Date();
        DateFormat df1 = DateFormat.getDateInstance(); //格式化后的时间格式：2016-2-19
        String str1 = df1.format(now);

        mIv_bg = (ImageView) findViewById(R.id.iv_bg);
        AppCore core = new AppCore();
        core.querySplish(str1, this);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            downTimer.start();
        }
    }

    //隐藏状态栏和导航栏
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            UltimateBar ultimateBar = new UltimateBar(this);
//            ultimateBar.setHintBar();
//        }
//    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
            MyLogger.jLog().i("timmer:" + l);
        }


        @Override
        public void onFinish() {
            if (SpUtil.getNewSp().getBoolean(SpConstant.IS_FIRST_RUN, false)) {
                startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
            } else {
                startActivity(new Intent(MyApplication.getInstance(), GuideActivity.class));
            }
        }
    };

    private void initData() {
        //实例化文件对象 判断文件是否存在
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase");
        file.mkdir();
        if (!file.exists() || file.listFiles() == null || file.listFiles().length == 0) {
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

    private long firstTime;
    private long secondTime;

    @Override
    public void onBackPressed() {
        secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showToast("再按一次就要退出啦");
            firstTime = secondTime;
        } else {
            downTimer.cancel();
            Intent intent = new Intent(MyApplication.getInstance(), SplishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            EventBus.getDefault().postSticky(new EventChageAccount());
        }
    }


    @Override
    public void onRequestSuccess(int what, Response<String> response) {
//        boolean fromCache = response.isFromCache();
        SplishInfo splishInfo = MyApplication.getGson().fromJson(response.get(), SplishInfo.class);
        if (splishInfo.isSuccess()) {
            ImageLoader.getInstance().displayImage(splishInfo.getObj().getFileUrl(), mIv_bg);
//            ToastUtils.showToast("获取到了图片:" + fromCache);
        } else {
            ToastUtils.showToast(splishInfo.getMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
//        ToastUtils.showToast("请求开始啦");
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
