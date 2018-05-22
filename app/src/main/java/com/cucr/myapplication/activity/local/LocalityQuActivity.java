package com.cucr.myapplication.activity.local;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.journey.AddJourneyActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalityQuActivity extends BaseActivity {

    //地区列表
    @ViewInject(R.id.lv_qu)
    ListView lv_qu;

    //要跳转的所有activity
    private Map<String, Class> actives;

    //把传过来的字符串当作键
    private String mWhich;

    private List<LocationData> mLocationDatas;


    @Override
    protected void initChild() {
        initActivitys();

        initData();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_locality_qu;
    }

    private void initActivitys() {
        actives = new HashMap<>();
       /* //发布福利
        actives.put("FaBuHuoDongActivity", FaBuHuoDongActivity.class);*/
        //预约详情
        actives.put("YuYueCatgoryActivity", YuYueCatgoryActivity.class);
          //添加行程
        actives.put("AddJourneyActivity", AddJourneyActivity.class);
    }

    private void initData() {
        //市
        Intent intent = getIntent();
        LocationData location = (LocationData) intent.getSerializableExtra("qu");
        mWhich = intent.getStringExtra("className");

        mLocationDatas = CityDao.queryDistrictByPcode(location.getCode());


        lv_qu.addHeaderView(View.inflate(this, R.layout.header_item_location, null));
        lv_qu.setAdapter(new LocationAdapter(mLocationDatas, false));

        lv_qu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;

                if (position == 0) {
                    intent = new Intent(LocalityQuActivity.this, actives.get(mWhich));
                    //点头携带定位数据，这里用字符串模拟定位
                    intent.putExtra("finalData", new LocationData(1688, "420111", "洪山区", "420100"));
                } else {
                    intent = new Intent(LocalityQuActivity.this, actives.get(mWhich));
                    intent.putExtra("finalData", mLocationDatas.get(position - 1));
                    MyLogger.jLog().i("mLocationDatas" + mLocationDatas.get(position - 1).toString());
                }
                startActivity(intent);
            }
        });
    }

}
