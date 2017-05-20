package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.bean.LocationData;
import com.cucr.myapplication.dao.CityDao;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

public class LocalityCityActivity extends Activity {


    @ViewInject(R.id.lv_city)
    ListView lv_city;
    private List<LocationData> mLocationDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality_city);
        ViewUtils.inject(this);

        initData();

    }

    private void initData() {
        LocationData data = (LocationData) getIntent().getSerializableExtra("data");
        mLocationDatas = CityDao.queryCityByPcode(data.getCode());
        lv_city.addHeaderView(View.inflate(this,R.layout.header_item_location,null));
        lv_city.setAdapter(new LocationAdapter(mLocationDatas,false));

        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocalityCityActivity.this,PersonalInfoActivity.class);
                if (position == 0){
                    //点头携带定位数据，这里用字符串模拟定位
                    intent.putExtra("finalData",new LocationData(172,"420100","武汉市","420000"));
                }else {
                    intent.putExtra("finalData", mLocationDatas.get(position-1));
                }
                startActivity(intent);
            }
        });
    }

}
