package com.cucr.myapplication.activity.yuyue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class YuYueCatgoryActivity extends Activity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_yue_catgory);

        ViewUtils.inject(this);

        initHead();

        initTv();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


}
