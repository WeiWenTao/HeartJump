package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.dao.CityDao;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class LocalityProvienceActivity extends Activity {

    private ListView mLv_provience;
    private List<LocationData> mProvinces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_provience);
        ViewUtils.inject(this);

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
                    Intent intent = new Intent(LocalityProvienceActivity.this,PersonalInfoActivity.class);
                    intent.putExtra("finalData",new LocationData(172,"420100","武汉市","420000"));
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LocalityProvienceActivity.this,LocalityCityActivity.class);
                    intent.putExtra("data",mProvinces.get(position-1));
                    startActivity(intent);
                }
            }
        });
    }

    //返回
    @OnClick(R.id.iv_location_back)
    public void back(View view){
        finish();
    }
}
