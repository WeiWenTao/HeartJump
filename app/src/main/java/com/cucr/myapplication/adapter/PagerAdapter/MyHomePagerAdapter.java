package com.cucr.myapplication.adapter.PagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.HomeAdapter;

import java.util.List;

/**
 * 明星主页的pagerAdapter
 */

public class MyHomePagerAdapter extends PagerAdapter {
    private List<String> mDataList;

    public MyHomePagerAdapter(List<String> dataList) {
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

        if (itemView == null){


            itemView = View.inflate(container.getContext(), R.layout.item_my_home_pager, null);
            ListView lv_my_home_pager = (ListView) itemView.findViewById(R.id.lv_my_home_pager);
            lv_my_home_pager.setAdapter(new HomeAdapter(container.getContext()));
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
