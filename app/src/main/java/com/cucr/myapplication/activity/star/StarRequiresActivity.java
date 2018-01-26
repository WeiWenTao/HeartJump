package com.cucr.myapplication.activity.star;

import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.core.starListAndJourney.StarRequireCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.starList.StarRequires;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.signcalendar.SignCalendar;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class StarRequiresActivity extends BaseActivity {

    //月份
    @ViewInject(R.id.tv_month)
    TextView tv_month;

    //年份
    @ViewInject(R.id.tv_year)
    TextView tv_year;

    //随行人数
    @ViewInject(R.id.tv_zhuli)
    TextView tv_zhuli;

    //头等舱 and 经济舱
    @ViewInject(R.id.tv_jipiao)
    TextView tv_jipiao;

    //别克商务
    @ViewInject(R.id.tv_car)
    TextView tv_car;

    //出演场地
    @ViewInject(R.id.tv_grand)
    TextView tv_grand;

    //住宿要求
    @ViewInject(R.id.tv_bed)
    TextView tv_bed;

    //化妆师
    @ViewInject(R.id.tv_hzs)
    TextView tv_hzs;

    //粉丝接机
    @ViewInject(R.id.tv_fans_welcome)
    TextView tv_fans_welcome;

    //其他要求
    @ViewInject(R.id.tv_other)
    TextView tv_other;

    private SignCalendar calendar;
    private StarRequireCore mCore;
    private Gson mGson;
    private int mStarId;

    @Override
    protected void initChild() {
        initTitle("出演要求");
        ViewUtils.inject(this);
        mCore = new StarRequireCore();
        mGson = new Gson();
        mStarId = getIntent().getIntExtra("starId", -1);
        //初始化标题日期
        calendar = (SignCalendar) findViewById(R.id.sc_date);
        tv_year.setText(calendar.getCalendarYear() + "年");
        tv_month.setText(calendar.getCalendarMonth() + "月");
        initDatas();
        //日历改变时
        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                tv_year.setText(year + "年");
                tv_month.setText(month + "月");
            }
        });
    }

    private void initDatas() {
        mCore.queryStarRequire(mStarId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarRequires starRequires = mGson.fromJson(response.get(), StarRequires.class);
                if (starRequires.isSuccess()) {
                    StarRequires.MsgBean obj = starRequires.getObj();
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
            return;
        }
        //随行人数
        tv_zhuli.setText(msg.getAssistantNum() + "名");
        //活动场地
        switch (msg.getActiveScene()) {
            case 0:
                tv_grand.setText("室内");
                break;
            case 1:
                tv_grand.setText("室外");
                break;
            default:
                tv_grand.setText("室内室外都行");
                break;


        }

        //头等舱数量
        String tdc = "头等舱" + msg.getFirstClass() + "张 ";
        //经济舱数量
        String jjc = "经济舱" + msg.getEconomyClass() + "张";
        tv_jipiao.setText(tdc + jjc);
        //用车数量
        tv_car.setText("别克商务" + msg.getCarNum() + "辆");
        //住宿
        switch (msg.getBed()) {
            case 0:
                tv_bed.setText("四星级");
                break;
            case 1:
                tv_bed.setText("五星级");
                break;
            default:
                tv_bed.setText("暂无");
                break;
        }
        //化妆师
        switch (msg.getBed()) {
            case 0:
                tv_hzs.setText("自带");
                break;
            case 1:
                tv_hzs.setText("活动安排");
                break;
            default:
                tv_hzs.setText("暂无");
                break;
        }
        //粉丝接机
        switch (msg.getBed()) {
            case 0:
                tv_fans_welcome.setText("自带");
                break;
            case 1:
                tv_fans_welcome.setText("不安排");
                break;
            default:
                tv_fans_welcome.setText("暂无");
                break;
        }
        //其他要求
        tv_other.setText(msg.getQtyq());

        for (StarRequires.MsgBean.StartTimeListBean startTimeListBean : msg.getStartTimeList()) {
            calendar.addMark(startTimeListBean.getTime().substring(0, 10), 0);
        }
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_star_requires;
    }

    //绑定声明周期停止请求
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCore.stopRequire();
    }
}
