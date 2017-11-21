package com.cucr.myapplication.activity.journey;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.core.starListAndJourney.StarJourney;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.setting.BirthdayDate;
import com.cucr.myapplication.model.setting.LocationData;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Calendar;
import java.util.Date;

public class AddJourneyActivity extends BaseActivity {

    //日期
    @ViewInject(R.id.date_text)
    private TextView tv_date;

    //地区
    @ViewInject(R.id.local_text)
    private TextView local_text;

    //内容
    @ViewInject(R.id.et_journey_content)
    private EditText et_journey_content;


    private StarJourney mCore;
    private Intent mIntent;

    private String mProvince = "";
    private String mCity = "";
    private String mQu = "";

    //日期对话框
    private DialogBirthdayStyle mBirthdayStyle;

    @Override
    protected void initChild() {
        initTitle("添加行程");
        initView();
    }

    //初始化控件
    private void initView() {
        mCore = new StarJourney();
        mBirthdayStyle = new DialogBirthdayStyle(this, R.style.BirthdayStyleTheme, false);
        String dateText = CommonUtils.getCurrentDate();
        String year = dateText.substring(0, 4).trim();
        String mon = dateText.substring(5, 7).trim();
        String day = dateText.substring(8, 10).trim();
        //初始化日期数据
        mBirthdayStyle.initDate(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day));


    }

    //显示日期对话框
    @OnClick(R.id.date_text)
    public void showDateDialog(View view) {
        mBirthdayStyle.show();
        mBirthdayStyle.setOnDialogBtClick(new DialogBirthdayStyle.onDialogBtClick() {
            @Override
            public void onClickComplete(BirthdayDate date, boolean isChange) {
                if (isChange) {
                    int day = date.getDay();
                    int month = date.getMonth();
                    int year = date.getYear();
                    String week = date.getWeek();
                    tv_date.setText(year + "-" + (month + 1) + "-" + day + " " + week);
                } else {
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(new Date());
                    tv_date.setText(CommonUtils.getCurrentDate() + " " + CommonUtils.getWeek(instance));
                }
            }
        });
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

            local_text.setText(province + "-" + district + "-" + qu);
            et_journey_content.requestFocus();
        }

    }

    //跳转地址选择界面
    @OnClick(R.id.local_text)
    public void goLocalActivity(View view) {
        Intent intent = new Intent(this, LocalityProvienceActivity.class);
        intent.putExtra("needShow", true);
        intent.putExtra("className", "AddJourneyActivity");
        startActivity(intent);
    }

    @OnClick(R.id.tv_save)
    public void addJourney(View view) {

        if (TextUtils.isEmpty(tv_date.getText())) {
            ToastUtils.showToast(this, "请选择行程择日期哦");
            return;
        }

        if (TextUtils.isEmpty(local_text.getText())) {
            ToastUtils.showToast(this, "请选择行程地区哦");
            return;
        }

        if (TextUtils.isEmpty(et_journey_content.getText())) {
            ToastUtils.showToast(this, "请输入行程内容哦");
            return;
        }

        mCore.addJourney(local_text.getText().toString(), et_journey_content.getText().toString(),
                tv_date.getText().toString().substring(0, 10), new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        ToastUtils.showToast(AddJourneyActivity.this,"添加行程成功");
                        finish();
                    }
                });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_add_journey;
    }
}
