package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.LvTopicClassifyAdapter;

import java.util.List;

/**
 * Created by 911 on 2017/5/9.
 */

public class TopicClassifyAdapter extends PagerAdapter {

    private List<String> dataList;
    private Context context;

    public TopicClassifyAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = null;

        if (itemView == null){
            itemView = View.inflate(context, R.layout.item_pager_topic_hot,null);
            ListView lv_topic_hot = (ListView) itemView.findViewById(R.id.lv_hot);
            lv_topic_hot.addHeaderView(View.inflate(context,R.layout.header_gray_space,null),null,true);
            lv_topic_hot.setHeaderDividersEnabled(false);
            lv_topic_hot.setAdapter(new LvTopicClassifyAdapter(position,context));
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
