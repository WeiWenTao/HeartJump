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

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.yuyue.YuYueCore;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YuYueCatgoryActivity extends FragmentActivity {

    //报价
    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    //地区
    @ViewInject(R.id.tv_active_local)
    private TextView tv_active_local;

    //明星姓名
    @ViewInject(R.id.tv_star_name)
    private TextView tv_star_name;

    //企业名称
    @ViewInject(R.id.et_qiyename)
    private TextView et_qiyename;

    //企业联系方式
    @ViewInject(R.id.et_qiye_contact)
    private TextView et_qiye_contact;

    //详细地区
    @ViewInject(R.id.et_local_catgory)
    private EditText et_local_catgory;

    //活动名称
    @ViewInject(R.id.et_activename)
    private EditText et_activename;

    //活动内容
    @ViewInject(R.id.et_active_content)
    private EditText et_active_content;

    //活动人数
    @ViewInject(R.id.et_people)
    private EditText et_people;

    //室内iv
    @ViewInject(R.id.iv_shi_nei)
    private ImageView iv_shi_nei;

    //室外iv
    @ViewInject(R.id.iv_shi_wai)
    private ImageView iv_shi_wai;

    //头像
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;

    //头部
    @ViewInject(R.id.head)
    private RelativeLayout head;

    //开始时间
    @ViewInject(R.id.tv_time_star)
    private TextView tv_time_star;

    //结束时间
    @ViewInject(R.id.tv_time_end)
    private TextView tv_time_end;

    private StarListInfos.RowsBean mData;
    //定义变量记录是开始时间还是结束时间
    private boolean isEndTime;
    private int scene;//活动场景
    private Gson mGson;

    //日期格式
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private YuYueCore mCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_yue_catgory);

        ViewUtils.inject(this);

        initHead();

        initViews();

    }

    private void initViews() {
        //回显数据
        //-----------------------------------------价格-------------------------------------
        String price = mData.getStartCost() + " 万";
        SpannableString sp = new SpannableString("参考费用 " + price + " /场");
        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15, true), 4, 4 + price.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //SpannableString对象设置给TextView
        tv_price.setText(sp);
        //设置TextView可点击
        tv_price.setMovementMethod(LinkMovementMethod.getInstance());
        //-----------------------------------------价格-------------------------------------
        tv_star_name.setText(mData.getRealName());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mData.getStartShowPic(), iv_head, MyApplication.getImageLoaderOptions());
        scene = 1; //默认场景为室外

        et_qiyename.setText((String) SpUtil.getParam(SpConstant.SP_QIYE_NAME, ""));
        et_qiye_contact.setText((String) SpUtil.getParam(SpConstant.SP_QIYE_CONTACT, ""));
    }

    //活动地区
    @OnClick(R.id.tv_active_local)
    public void selLocal(View view) {
        Intent intent = new Intent(MyApplication.getInstance(), LocalityProvienceActivity.class);
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
        iv_shi_nei.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pc_sel));
        iv_shi_wai.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pc_nor));
        scene = 0;
    }

    //室外
    @OnClick(R.id.ll_shiwai)
    public void shiWai(View view) {
        iv_shi_nei.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pc_nor));
        iv_shi_wai.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pc_sel));
        scene = 1;
    }

    //返回
    @OnClick(R.id.iv_myyuyue_back)
    public void back(View view) {
        finish();
    }

    //沉浸栏
    private void initHead() {
        //获取数据
        mData = (StarListInfos.RowsBean) getIntent().getSerializableExtra("data");
        mCore = new YuYueCore();
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
        mGson = new Gson();
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
                .setIndicatorColor(getResources().getColor(R.color.xtred))
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
    public void yuYue(View view) {

        String activityName = et_activename.getText().toString().trim();
        String activeLocal = tv_active_local.getText().toString().trim();
        String localCatgory = et_local_catgory.getText().toString().trim();
        String starTime = tv_time_star.getText().toString().trim();
        String endTime = tv_time_end.getText().toString().trim();
        String activeContent = et_active_content.getText().toString().trim();
        String peoples = et_people.getText().toString().trim();
        String qiYeName = et_qiyename.getText().toString().trim();
        String qiYeContact = et_qiye_contact.getText().toString().trim();

        if (CommonUtils.isEmpty(activityName, activeLocal, localCatgory, starTime, endTime,
                activeContent, peoples, qiYeName, qiYeContact)) {
            ToastUtils.showToast("请先完善预约信息哟!");
            return;
        }

        MyLogger.jLog().i(starTime);
        mCore.yuYueStar(mData.getId(), activityName, activeLocal, localCatgory, starTime + ":00", endTime + ":00", scene
                , activeContent, Integer.parseInt(peoples), new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                        if (msg.isSuccess()) {
                            ToastUtils.showToast(MyApplication.getInstance(), "已提交预约");
                            finish();
                        } else {
                            ToastUtils.showToast(msg.getMsg());
                        }
                    }
                });
    }

    @OnClick(R.id.ll_peoples)
    public void clickPeoples(View view) {
        et_people.setFocusable(true);
        et_people.setFocusableInTouchMode(true);
        et_people.requestFocus();
        CommonUtils.hideKeyBorad(MyApplication.getInstance(), et_people, false);
    }

}
