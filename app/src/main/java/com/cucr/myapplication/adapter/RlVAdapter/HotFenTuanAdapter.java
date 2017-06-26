package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/6/24.
 */
public class HotFenTuanAdapter extends BaseAdapter {

    private Context mContext;

    public HotFenTuanAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
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

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_gv_hot_fentuan, null);

        return cvh.convertView;
    }
}
