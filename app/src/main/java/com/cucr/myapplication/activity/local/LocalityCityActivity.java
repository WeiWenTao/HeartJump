package com.cucr.myapplication.activity.local;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.fuli.DingDanActivity;
import com.cucr.myapplication.activity.huodong.FaBuHuoDongActivity;
import com.cucr.myapplication.activity.hyt.CreatHytActivity;
import com.cucr.myapplication.activity.hyt.YyhdActivity_3;
import com.cucr.myapplication.activity.journey.AddJourneyActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.LocationAdapter;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.bean.setting.LocationData;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalityCityActivity extends BaseActivity {

    //市区列表
    @ViewInject(R.id.lv_city)
    ListView lv_city;
    private List<LocationData> mLocationDatas;

    //要跳转的所有activity
    private Map<String,Class> actives;

    //把传过来的字符串当作键
    private String mWhich;

    //是否需要显示小箭头   显示则可跳转下一级地区  不显示则跳转回个人信息界面
    private boolean needShowArrow;

    @Override
    protected void initChild() {
        initTitle("所在地");
        initActivitys();
        initData();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_locality_city;
    }

    private void initActivitys() {
        actives = new HashMap<>();
        //个人中心
        actives.put("PersonalInfoActivity", PersonalInfoActivity.class);
        //发布福利
        actives.put("FaBuHuoDongActivity", FaBuHuoDongActivity.class);
        //预约详情
        actives.put("YuYueCatgoryActivity",YuYueCatgoryActivity.class);
        //预约详情
        actives.put("DingDanActivity",DingDanActivity.class);
        //添加行程
        actives.put("AddJourneyActivity", AddJourneyActivity.class);
        //创建后援团
        actives.put("CreatHytActivity", CreatHytActivity.class);
        //后援活动3
        actives.put("YyhdActivity_3", YyhdActivity_3.class);
    }


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
                    intent = new Intent(LocalityCityActivity.this, actives.get(mWhich));
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
                        intent = new Intent(LocalityCityActivity.this,actives.get(mWhich));
                        intent.putExtra("finalData", mLocationDatas.get(position - 1));
                    }
                }
                startActivity(intent);
            }
        });
    }


}
