package com.cucr.myapplication.activity.local;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.hyt.CreatHytActivity;
import com.cucr.myapplication.activity.hyt.YyhdActivity_3;
import com.cucr.myapplication.activity.journey.AddJourneyActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.dao.CityDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalityProvienceActivity extends BaseActivity {

    private ListView mLv_provience;
    private List<LocationData> mProvinces;
    private boolean mNeedShow;
    private Map<String, Class> actives;
    private String mWhich;

    @Override
    protected void initChild() {
        initTitle("所在地");
        actives = new HashMap<>();
        //个人中心
        actives.put("PersonalInfoActivity", PersonalInfoActivity.class);
       /* //发布福利
        actives.put("FaBuHuoDongActivity", FaBuHuoDongActivity.class);*/
        //预约详情
        actives.put("YuYueCatgoryActivity", YuYueCatgoryActivity.class);
         //添加行程
        actives.put("AddJourneyActivity", AddJourneyActivity.class);
        //创建后援团
        actives.put("CreatHytActivity", CreatHytActivity.class);
        //后援活动3
        actives.put("YyhdActivity_3", YyhdActivity_3.class);

        //是否需要跳转三级地区界面
        Intent intent = getIntent();
        mNeedShow = intent.getBooleanExtra("needShow", false);
        mWhich = intent.getStringExtra("className");

        mLv_provience = (ListView) findViewById(R.id.lv_provience);
        initData();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_location_provience;
    }

    private void initData() {

        mProvinces = CityDao.queryProvience();

        //添加头
        mLv_provience.addHeaderView(View.inflate(this, R.layout.header_item_location, null));
//        mLv_provience.setHeaderDividersEnabled(false);
        mLv_provience.setAdapter(new LocationAdapter(mProvinces, mNeedShow));

        mLv_provience.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //点头直接返回个人信息页
                if (position == 0) {
                    Intent intent = new Intent(LocalityProvienceActivity.this, actives.get(mWhich));
                    if (mNeedShow) {
                        intent.putExtra("finalData", new LocationData(1688, "420111", "洪山区", "420100"));
                    } else {
                        intent.putExtra("finalData", new LocationData(172, "420100", "武汉市", "420000"));
                    }
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LocalityProvienceActivity.this, LocalityCityActivity.class);
                    intent.putExtra("data", mProvinces.get(position - 1));
                    intent.putExtra("mNeedShow", mNeedShow);
                    intent.putExtra("className", mWhich);
                    startActivity(intent);
                }
            }
        });
    }
}
