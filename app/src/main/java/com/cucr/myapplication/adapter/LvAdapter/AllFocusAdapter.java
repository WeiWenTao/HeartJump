package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/4/24.
 */
public class AllFocusAdapter extends BaseAdapter {

    private int i;
    public AllFocusAdapter(int i) {
        this.i = i;
    }


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
        if (i == 0){
            CommonViewHolder cvh = null;
            cvh=CommonViewHolder.createCVH(convertView,parent.getContext(), R.layout.item_all_focus,null);
            return cvh.convertView;
        }else {
            CommonViewHolder cvh = null;
            cvh=CommonViewHolder.createCVH(convertView,parent.getContext(), R.layout.item_recommend_focus,null);
            return cvh.convertView;
        }

    }
}
