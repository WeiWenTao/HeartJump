package com.cucr.myapplication.adapter.SpinnerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.model.starList.StarListKey;
import com.cucr.myapplication.utils.CommonViewHolder;

import java.util.List;

/**
 * Created by cucr on 2017/9/18.
 */

public class MySp1Adapter extends BaseAdapter {

    private List<StarListKey.RowsBean> rows;

    public void setData(List<StarListKey.RowsBean> rows){
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rows == null ? 0 : rows.size();
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
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, MyApplication.getInstance(), R.layout.spinner1_item, null);
        TextView tv = cvh.getTv(R.id.tv);
        tv.setText(rows.get(position).getValueFild());
        return cvh.convertView;
    }
}
