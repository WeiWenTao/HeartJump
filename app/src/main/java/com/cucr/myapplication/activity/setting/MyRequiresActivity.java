package com.cucr.myapplication.activity.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.starListAndJourney.StarRequireCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.starList.StarRequires;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.signcalendar.SignCalendar;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class MyRequiresActivity extends BaseActivity {

    //月份
    @ViewInject(R.id.tv_month)
    TextView tv_month;

    //年份
    @ViewInject(R.id.tv_year)
    TextView tv_year;

    //随行人数
    @ViewInject(R.id.et_peoples)
    EditText et_peoples;

    //头等舱
    @ViewInject(R.id.et_fly)
    EditText et_fly;

    //经济舱
    @ViewInject(R.id.et_fly1)
    EditText et_fly1;

    //别克商务
    @ViewInject(R.id.et_car)
    EditText et_car;

    //出演场地
    @ViewInject(R.id.rg_place)
    RadioGroup rg_place;

    //住宿要求
    @ViewInject(R.id.rg_live)
    RadioGroup rg_live;

    //化妆师
    @ViewInject(R.id.rg_dresser)
    RadioGroup rg_dresser;

    //粉丝接机
    @ViewInject(R.id.rg_welcome)
    RadioGroup rg_welcome;

    //其他要求
    @ViewInject(R.id.et_other_require)
    EditText et_other_require;

    //保存
    @ViewInject(R.id.tv_save_requires)
    TextView tv_save_requires;


    private SignCalendar calendar;
    private List<String> markDates;
    private StarRequireCore mCore;
    private Gson mGson;
    private int saveOrChange;

    @Override
    protected void initChild() {
        initTitle("出演要求");
        ViewUtils.inject(this);
        markDates = new ArrayList<>();
        mCore = new StarRequireCore();
        mGson = new Gson();
        //初始化标题日期
        calendar = (SignCalendar) findViewById(R.id.sc_date);
        tv_year.setText(calendar.getCalendarYear() + "年");
        tv_month.setText(calendar.getCalendarMonth() + "月");
        backShow(); //回显数据

        //日历改变时
        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                tv_year.setText(year + "年");
                tv_month.setText(month + "月");
            }
        });

        //点击日历控件时
        calendar.setOnCalendarClickListener(new SignCalendar.OnCalendarClickListener() {
            @Override
            public void onCalendarClick(int row, int col, String dateFormat) {
                if (calendar.hasMarked(dateFormat)) {
                    calendar.removeMark(dateFormat);
                    markDates.remove(dateFormat);
                    MyLogger.jLog().i("date_" + dateFormat);
                } else {
                    calendar.addMark(dateFormat, 0);
                    markDates.add(dateFormat);
                }
            }
        });
    }

    //回显数据
    private void backShow() {
        mCore.queryStarRequire(((int) SpUtil.getParam(SpConstant.USER_ID, -1)), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyLogger.jLog().i("reback:" + response.get());
                StarRequires starRequires = mGson.fromJson(response.get(), StarRequires.class);
                if (starRequires.isSuccess()) {
                    StarRequires.MsgBean obj = starRequires.getObj();
                    MyLogger.jLog().i("backshow:" + obj);
                    initViews(obj);
                } else {
                    //这里obj和msg写反了
                    ToastUtils.showToast(starRequires.getMsg());
                }
            }
        });
    }

    private void initViews(StarRequires.MsgBean msg) {
        if (msg == null) {
            //表示没有保存过数据 是第一次保存
            saveOrChange = -1;
            return;
        }
        saveOrChange = msg.getId();
        //随行人数
        et_peoples.setText(msg.getAssistantNum() + "");
        //活动场地
        ((RadioButton) rg_place.getChildAt(msg.getActiveScene())).setChecked(true);
        //头等舱数量
        et_fly.setText(msg.getFirstClass() + "");
        //经济舱数量
        et_fly1.setText(msg.getEconomyClass() + "");
        //用车数量
        et_car.setText(msg.getCarNum() + "");
        //住宿
        ((RadioButton) rg_live.getChildAt(msg.getBed())).setChecked(true);
        //化妆师
        ((RadioButton) rg_dresser.getChildAt(msg.getHzs())).setChecked(true);
        //粉丝接机
        ((RadioButton) rg_welcome.getChildAt(msg.getFsjj())).setChecked(true);
        //其他要求
        et_other_require.setText(msg.getQtyq().isEmpty() ? "暂无" : msg.getQtyq());

        for (StarRequires.MsgBean.StartTimeListBean startTimeListBean : msg.getStartTimeList()) {
            calendar.addMark(startTimeListBean.getTime().substring(0, 10), 0);
            markDates.add(startTimeListBean.getTime().substring(0, 10));
        }
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_my_requires;
    }

    //保存
    @OnClick(R.id.tv_save_requires)
    public void saveInfo(View view) {
        //随行人数
        int assistantNum = TextUtils.isEmpty(et_peoples.getText()) ? 0 : Integer.parseInt(et_peoples.getText().toString());
        //活动场地
        int activeScene = rg_place.indexOfChild(findViewById(rg_place.getCheckedRadioButtonId()));
        //头等舱数量
        int firstClass = TextUtils.isEmpty(et_fly.getText()) ? 0 : Integer.parseInt(et_fly.getText().toString());
        //经济舱数量
        int economyClass = TextUtils.isEmpty(et_fly1.getText()) ? 0 : Integer.parseInt(et_fly1.getText().toString());
        //用车数量
        int carNum = TextUtils.isEmpty(et_car.getText()) ? 0 : Integer.parseInt(et_car.getText().toString());
        //住宿
        int bed = rg_live.indexOfChild(findViewById(rg_live.getCheckedRadioButtonId()));
        //化妆师
        int hzs = rg_dresser.indexOfChild(findViewById(rg_dresser.getCheckedRadioButtonId()));
        //粉丝接机
        int fsjj = rg_welcome.indexOfChild(findViewById(rg_welcome.getCheckedRadioButtonId()));
        //其他要求
        String qtyq = et_other_require.getText().toString();

        //id < 0 表示不用传 saveOrChange
        MyLogger.jLog().i("saveOrChange:" + saveOrChange);
        mCore.addRequires(saveOrChange, assistantNum, activeScene, firstClass, economyClass, carNum, bed,
                hzs, fsjj, qtyq, markDates, new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                        if (msg.isSuccess()) {
                            ToastUtils.showToast("保存成功");
                            finish();
                        } else {
                            ToastUtils.showToast(msg.getMsg());
                        }
                    }
                });
    }

    //绑定声明周期停止请求
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCore.stopRequire();
    }
}
