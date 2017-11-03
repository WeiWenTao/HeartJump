package com.cucr.myapplication.activity.yuyue;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.core.yuyue.YuYueCore;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.model.setting.LocationData;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YuYueCatgoryActivity extends FragmentActivity {

    //报价
    @ViewInject(R.id.tv_price)
    TextView tv_price;

    //地区
    @ViewInject(R.id.tv_active_local)
    TextView tv_active_local;

    //详细地区
    @ViewInject(R.id.et_local_catgory)
    EditText et_local_catgory;

    //室内iv
    @ViewInject(R.id.iv_shi_nei)
    ImageView iv_shi_nei;

    //室外iv
    @ViewInject(R.id.iv_shi_wai)
    ImageView iv_shi_wai;

    //头部
    @ViewInject(R.id.head)
    RelativeLayout head;

    //开始时间
    @ViewInject(R.id.tv_time_star)
    TextView tv_time_star;

    //结束时间
    @ViewInject(R.id.tv_time_end)
    TextView tv_time_end;

    //定义变量记录是开始时间还是结束时间
    private boolean isEndTime;


    //日期格式
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private YuYueCore mCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_yue_catgory);

        ViewUtils.inject(this);

        initHead();

        initTv();

        initTime();
    }

    private void initTime() {

    }


    private void initTv() {

        //模拟获取数据
        String price = " 56万";

        SpannableString sp = new SpannableString("商业出演" + price + " /场");

        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15, true), 4, 4 + price.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //SpannableString对象设置给TextView
        tv_price.setText(sp);
        //设置TextView可点击
        tv_price.setMovementMethod(LinkMovementMethod.getInstance());

    }

    //活动地区
    @OnClick(R.id.tv_active_local)
    public void selLocal(View view) {
        Intent intent = new Intent(this, LocalityProvienceActivity.class);
        intent.putExtra("needShow", true);
        intent.putExtra("className", "YuYueCatgoryActivity");
        startActivity(intent);
    }

    //这个界面配置了signTask启动模式  用getIntent获取数据会为null  用onNewIntent + setIntent()
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LocationData location = (LocationData) getIntent().getSerializableExtra("finalData");

        if (location != null) {

            String qu = location.getName();

            LocationData shi = CityDao.queryCityBycode(location.getpCode());

            LocationData sheng = CityDao.queryPrivnceBycode(shi.getpCode());

            String district = shi.getName();
            String province = sheng.getName();

            tv_active_local.setText(province + " " + district + " " + qu);
            et_local_catgory.requestFocus();
        }

    }

    //室内
    @OnClick(R.id.ll_shinei)
    public void shiNei(View view) {
        iv_shi_nei.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dot_sel));
        iv_shi_wai.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dot_nor));
    }

    //室外
    @OnClick(R.id.ll_shiwai)
    public void shiWai(View view) {
        iv_shi_nei.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dot_nor));
        iv_shi_wai.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dot_sel));
    }

    //返回
    @OnClick(R.id.iv_myyuyue_back)
    public void back(View view) {
        finish();
    }

    //沉浸栏
    private void initHead() {
        mCore = new YuYueCore();
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
    }

    //活动开始时间
    @OnClick(R.id.tv_time_star)
    public void starData(View view) {
        isEndTime = false;
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .setMinDate(new Date(System.currentTimeMillis()))
                //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_LIGHT)
                .setIndicatorColor(getResources().getColor(R.color.xtred))
                .build()
                .show();
    }

    //活动结束时间
    @OnClick(R.id.tv_time_end)
    public void endData(View view) {
        isEndTime = true;
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .setMinDate(new Date(System.currentTimeMillis()))
                //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                .setIndicatorColor(Color.parseColor("#f68d89"))
                .build()
                .show();
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {

            String timeDate = mFormatter.format(date);

            if (isEndTime) {
                tv_time_end.setText(timeDate);
            } else {
                tv_time_star.setText(timeDate);
            }

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
//            Toast.makeText(YuYueCatgoryActivity.this,
//                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @OnClick(R.id.tv_commit_yuyue)
    public void yuYue(View view){
//        mCore.yuYueStar();
    }

}
