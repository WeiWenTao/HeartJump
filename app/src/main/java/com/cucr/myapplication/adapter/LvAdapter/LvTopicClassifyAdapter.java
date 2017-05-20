package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/5/9.
 */
public class LvTopicClassifyAdapter extends BaseAdapter {
    private int position;
    private Context context;
    public LvTopicClassifyAdapter(int position, Context context) {
        this.position = position;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 15;
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

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, context, R.layout.item_lv_topicclassify_hot, null);

        return cvh.convertView;
    }
}
