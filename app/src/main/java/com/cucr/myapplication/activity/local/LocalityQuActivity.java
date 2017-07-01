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
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class LocalityQuActivity extends Activity {

    //地区列表
    @ViewInject(R.id.lv_qu)
    ListView lv_qu;
    //沉浸栏
    @ViewInject(R.id.head)
    RelativeLayout head;


    private List<LocationData> mLocationDatas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality_qu);

        ViewUtils.inject(this);

        //沉浸栏
        initHead();

        initData();
    }

    private void initData() {
        //市
        LocationData location = (LocationData) getIntent().getSerializableExtra("qu");
        mLocationDatas = CityDao.queryDistrictByPcode(location.getCode());


        lv_qu.addHeaderView(View.inflate(this, R.layout.header_item_location, null));
        lv_qu.setAdapter(new LocationAdapter(mLocationDatas, false));

        lv_qu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;

                if (position == 0) {
                    intent = new Intent(LocalityQuActivity.this, YuYueCatgoryActivity.class);
                    //点头携带定位数据，这里用字符串模拟定位
                    intent.putExtra("finalData", new LocationData(1688,"420111","洪山区","420100"));
                } else {
                    intent = new Intent(LocalityQuActivity.this, YuYueCatgoryActivity.class);
                    intent.putExtra("finalData", mLocationDatas.get(position - 1));
                    MyLogger.jLog().i("mLocationDatas"+mLocationDatas.get(position - 1).toString());
                }
                startActivity(intent);
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

    @OnClick(R.id.iv_back)
    public void back(View view){
        finish();
    }
}
