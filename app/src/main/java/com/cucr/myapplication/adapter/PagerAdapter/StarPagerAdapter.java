package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.RlvPersonalJourneyAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lantouzi.wheelview.WheelView;

import java.util.List;

/**
 * 明星主页的pagerAdapter
 */

public class StarPagerAdapter extends PagerAdapter {
    private List<String> mDataList;
    private WheelView mWheelview;
    private RecyclerView mRlv_journey;
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
                       RecyclerView rlv_dongtai = (RecyclerView) itemView.findViewById(R.id.rlv_xingwen);
                       rlv_dongtai.setLayoutManager(new LinearLayoutManager(mContext));
                       rlv_dongtai.setAdapter(new XingWenAdapter(mContext));
                       break;

                   case 1:
                       //行程
                       itemView = View.inflate(mContext,R.layout.item_personal_pager_journey,null);
                       mRlv_journey = (RecyclerView) itemView.findViewById(R.id.rlv_journey);
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
        mRlv_journey.setLayoutManager(new LinearLayoutManager(mContext));
        mRlv_journey.setAdapter(new RlvPersonalJourneyAdapter(mContext));
//        mRlv_journey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mContext.startActivity(new Intent(mContext, JourneyCatgoryActivity.class));
//            }
//        });
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
