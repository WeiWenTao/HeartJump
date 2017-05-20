package com.cucr.myapplication.adapter.GvAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

import java.util.List;

/**
 * Created by 911 on 2017/5/16.
 */

public class TopicClassifyGVAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    //用户点击的gv条目
    private int checkedPosition;

    //用于设置点击条目的背景
    public void setCheckPosition(int position){
        this.checkedPosition = position;
        //刷新适配器
        notifyDataSetChanged();
    }

    public TopicClassifyGVAdapter(Context context , List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 :list.size();
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
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, context, R.layout.item_gv_topic_classify, null);
        TextView tv = cvh.getTv(R.id.tv_gv_item);
        tv.setText(list.get(position));

        if (checkedPosition == position){
            //设置选中状态
            tv.setBackgroundResource(R.drawable.shape_topic_classify_sel);
            tv.setTextColor(Color.WHITE);
        }else {
            //设置未选中状态
            tv.setBackgroundResource(R.drawable.shape_topic_classify_nor);
            tv.setTextColor(Color.parseColor("#666666"));
        }

        return cvh.convertView;
    }
}
