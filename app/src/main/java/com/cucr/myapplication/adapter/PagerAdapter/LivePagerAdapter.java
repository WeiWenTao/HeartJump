package com.cucr.myapplication.adapter.PagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.MyfocusAdapter;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class LivePagerAdapter extends PagerAdapter {
    private List<String> mDataList;

    public LivePagerAdapter(List<String> dataList) {
        mDataList = dataList;
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
        if (position == 2) {
            itemView = View.inflate(container.getContext(), R.layout.item_personal_pager_popularity, null);
            ListView lv_popularity = (ListView) itemView.findViewById(R.id.lv_popularity);
            //添加热门主播的listView的头
            lv_popularity.addHeaderView(View.inflate(container.getContext(),R.layout.header_popularity_lv,null));
//            lv_popularity.setAdapter(new PopularAdapter());

        } else {
            itemView = View.inflate(container.getContext(), R.layout.pager_my_focus, null);
            ListView lv_my_focus = (ListView) itemView.findViewById(R.id.lv_my_focus);
            lv_my_focus.setDividerHeight(0);
            lv_my_focus.setAdapter(new MyfocusAdapter());
        }


        container.addView(itemView);
        return itemView;
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
