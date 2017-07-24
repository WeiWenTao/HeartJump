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

public class LocalityCityActivity extends Activity {

    //市区列表
    @ViewInject(R.id.lv_city)
    ListView lv_city;
    private List<LocationData> mLocationDatas;

    //沉浸栏
    @ViewInject(R.id.head)
    RelativeLayout head;

    //要跳转的所有activity
    private Map<String,Class> actives;

    //把传过来的字符串当作键
    private String mWhich;

    //是否需要显示小箭头   显示则可跳转下一级地区  不显示则跳转回个人信息界面
    private boolean needShowArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality_city);
        ViewUtils.inject(this);

        initActivitys();
        //沉浸栏
        initHead();

        initData();

    }

    private void initActivitys() {
        actives = new HashMap<>();
        //发布福利
        actives.put("FaBuHuoDongActivity", FaBuHuoDongActivity.class);
        //预约详情
        actives.put("YuYueCatgoryActivity",YuYueCatgoryActivity.class);
    }

    //沉浸栏
    private void initData() {
        Intent intent = getIntent();
        LocationData data = (LocationData) intent.getSerializableExtra("data");
        mWhich = intent.getStringExtra("className");
        //是否需要跳转三级地区界面
        needShowArrow = intent.getBooleanExtra("mNeedShow", false);

        mLocationDatas = CityDao.queryCityByPcode(data.getCode());
        lv_city.addHeaderView(View.inflate(this, R.layout.header_item_location, null));
        lv_city.setAdapter(new LocationAdapter(mLocationDatas, needShowArrow));

        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = null;

                if (position == 0) {
                    intent = new Intent(LocalityCityActivity.this, needShowArrow ? actives.get(mWhich) : PersonalInfoActivity.class);
                    //点头携带定位数据，这里用字符串模拟定位
                    if (needShowArrow) {
                        intent.putExtra("finalData", new LocationData(1688, "420111", "洪山区", "420100"));
                    } else {
                        intent.putExtra("finalData", new LocationData(172, "420100", "武汉市", "420000"));
                    }
                } else {
                    if (needShowArrow) {
                        //跳转到三级地区界面
                        intent = new Intent(LocalityCityActivity.this, LocalityQuActivity.class);
                        intent.putExtra("qu", mLocationDatas.get(position - 1));
                        intent.putExtra("className",mWhich);
                    } else {
                        //跳转回个人信息界面
                        intent = new Intent(LocalityCityActivity.this, PersonalInfoActivity.class);
                        intent.putExtra("finalData", mLocationDatas.get(position - 1));
                    }
                }
                startActivity(intent);
            }
        });
    }

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

    //返回
    @OnClick(R.id.iv_back)
    public void back(View view) {
        finish();
    }

}
