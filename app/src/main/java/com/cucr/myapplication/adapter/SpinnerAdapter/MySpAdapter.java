package com.cucr.myapplication.adapter.SpinnerAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

import java.util.List;

/**
 * Created by cucr on 2017/9/18.
 */

public class MySpAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public MySpAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, context, R.layout.spinner_item, null);
        TextView tv = cvh.getTv(R.id.tv);
        tv.setText(list.get(position));
        return cvh.convertView;
    }
}
