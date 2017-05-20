package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/5/8.
 */

public class LvPersonalTopicAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_lv_topic, null);
        ImageView iv_topic_pic = cvh.getIv(R.id.iv_topic_pic);
        if (position%2==0){
            iv_topic_pic.setImageResource(R.drawable.topic1);
        }else {
            iv_topic_pic.setImageResource(R.drawable.topic2);
        }
        return cvh.convertView;
    }
}
