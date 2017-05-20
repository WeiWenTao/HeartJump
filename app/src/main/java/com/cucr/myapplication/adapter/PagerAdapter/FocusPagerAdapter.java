package com.cucr.myapplication.adapter.PagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.AllFocusAdapter;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class FocusPagerAdapter extends PagerAdapter {
    private List<String> mDataList;

    public FocusPagerAdapter(List<String> dataList) {
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


            if (position == 0) {
                itemView = View.inflate(container.getContext(), R.layout.my_focus_lv, null);
                ListView lv_all_focus = (ListView) itemView.findViewById(R.id.lv_all_focus);
                //共用一个adapter 传0表示全部关注 传1表示推荐关注
                lv_all_focus.setAdapter(new AllFocusAdapter(0));

            } else {
                itemView = View.inflate(container.getContext(), R.layout.recommend_focus, null);
                ListView lv_recommend_focus = (ListView) itemView.findViewById(R.id.lv_recommend_focus);
                lv_recommend_focus.setAdapter(new AllFocusAdapter(1));
            }
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
