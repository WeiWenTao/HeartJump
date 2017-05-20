package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.JourneyCatgoryAdapter;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class TopicCatgoryPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mDataList;

    public TopicCatgoryPagerAdapter(List<String> dataList, Context context) {
        mDataList = dataList;
        this.mContext = context;
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

            itemView = View.inflate(mContext, R.layout.item_pager_topic_catgory,null);
            ListView lv_topic_hot = (ListView) itemView.findViewById(R.id.lv_topic_hot);
            lv_topic_hot.addHeaderView(View.inflate(mContext,R.layout.header_gray_space,null),null,true);
            lv_topic_hot.setHeaderDividersEnabled(false);
            lv_topic_hot.setAdapter(new JourneyCatgoryAdapter(container.getContext()));
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
