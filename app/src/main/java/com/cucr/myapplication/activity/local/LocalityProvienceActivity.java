package com.cucr.myapplication.activity.local;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.huodong.FaBuHuoDongActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.model.setting.LocationData;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalityProvienceActivity extends Activity {

    private ListView mLv_provience;
    private List<LocationData> mProvinces;
    private boolean mNeedShow;

    private Map<String,Class> actives;

    //沉浸栏
    @ViewInject(R.id.head)
    RelativeLayout head;

    private String mWhich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_provience);
        ViewUtils.inject(this);

        //沉浸栏
        initHead();

        actives = new HashMap<>();
        //发布福利
        actives.put("FaBuHuoDongActivity", FaBuHuoDongActivity.class);
        //预约详情
        actives.put("YuYueCatgoryActivity",YuYueCatgoryActivity.class);

        //是否需要跳转三级地区界面
        Intent intent = getIntent();
        mNeedShow = intent.getBooleanExtra("needShow", false);
        mWhich = intent.getStringExtra("className");

        mLv_provience = (ListView) findViewById(R.id.lv_provience);
        initData();
    }

    private void initData() {

        mProvinces = CityDao.queryProvience();

        //添加头
        mLv_provience.addHeaderView(View.inflate(this,R.layout.header_item_location,null));
//        mLv_provience.setHeaderDividersEnabled(false);
        mLv_provience.setAdapter(new LocationAdapter(mProvinces,true));

        mLv_provience.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //点头直接返回个人信息页
                if (position == 0){
                    Intent intent = new Intent(LocalityProvienceActivity.this,mNeedShow ? actives.get(mWhich) : PersonalInfoActivity.class);
                    if (mNeedShow){
                        intent.putExtra("finalData",new LocationData(1688,"420111","洪山区","420100"));
                    }else {
                        intent.putExtra("finalData",new LocationData(172,"420100","武汉市","420000"));
                    }
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LocalityProvienceActivity.this,LocalityCityActivity.class);
                    intent.putExtra("data",mProvinces.get(position-1));
                    intent.putExtra("mNeedShow",mNeedShow);
                    intent.putExtra("className",mWhich);
                    startActivity(intent);
                }
            }
        });
    }
    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this,73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //返回
    @OnClick(R.id.iv_back)
    public void back(View view){
        finish();
    }
}
