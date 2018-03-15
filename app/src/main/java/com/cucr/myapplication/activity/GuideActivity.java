package com.cucr.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.GuidePagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 向导界面  第一次启动app的时候启动
 */
public class GuideActivity extends Activity {

    private List<Integer> mList;
    private long firstTime;
    private long secondTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        SharedPreferences.Editor edit = SpUtil.getNewSp().edit();
        edit.putBoolean(SpConstant.IS_FIRST_RUN, true).commit();
        mList = new ArrayList<>();
        mList.add(R.drawable.sp1);
        mList.add(R.drawable.sp2);
        mList.add(R.drawable.sp3);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new GuidePagerAdapter(mList));

    }

    //双击退出程序
    @Override
    public void onBackPressed() {
        secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showToast("再按一次就要退出啦");
            firstTime = secondTime;
        } else {
            Intent intent = new Intent(MyApplication.getInstance(), SplishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            EventBus.getDefault().postSticky(new EventChageAccount());
        }
    }
}
