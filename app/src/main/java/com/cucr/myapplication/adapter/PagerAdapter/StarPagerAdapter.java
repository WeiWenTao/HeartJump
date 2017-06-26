package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.JourneyAndTopic.JourneyCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.HomeAdapter;
import com.cucr.myapplication.adapter.LvAdapter.LvPersonalJourneyAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lantouzi.wheelview.WheelView;

import java.util.List;

/**
 * 明星主页的pagerAdapter
 */

public class StarPagerAdapter extends PagerAdapter {
    private List<String> mDataList;
    private WheelView mWheelview;
    private ListView mLv_journey;
    private Context mContext;

    public StarPagerAdapter(List<String> dataList, Context context) {
        mDataList = dataList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = null;

        if (itemView == null){
               switch (position){
                   case 0:
                       //星闻
                       itemView = View.inflate(mContext, R.layout.item_personal_pager_xingwen, null);
                       ListView lv_dongtai = (ListView) itemView.findViewById(R.id.lv_xingwen);
                       lv_dongtai.setAdapter(new HomeAdapter(mContext));
                       break;

                   case 1:
                       //行程
                       itemView = View.inflate(mContext,R.layout.item_personal_pager_journey,null);
                       mLv_journey = (ListView) itemView.findViewById(R.id.lv_journey);
                       mWheelview = (WheelView) itemView.findViewById(R.id.wheelview);
                       //设置单位（没啥用，设置属性的时候才有用 ）
                       mWheelview.setAdditionCenterMark(" ");
                       initWheelView();
                       break;

               }

        }
        container.addView(itemView);
        return itemView;
    }

    //初始化日期滚轮控件 和 listView
    private void initWheelView() {
        List<String> dateList = CommonUtils.getDateList();
        mWheelview.setItems(dateList);
        mWheelview.selectIndex(4);


        mLv_journey.addHeaderView(View.inflate(mContext,R.layout.header_gray_space,null));
        mLv_journey.setAdapter(new LvPersonalJourneyAdapter());
        mLv_journey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mContext.startActivity(new Intent(mContext, JourneyCatgoryActivity.class));
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
