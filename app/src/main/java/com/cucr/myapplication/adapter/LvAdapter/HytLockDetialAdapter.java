package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.utils.DataUtils;

import java.util.List;

/**
 * Created by cucr on 2018/3/1.
 */

public class HytLockDetialAdapter extends BaseAdapter {
    private List<Integer> times;
    private int selPosi;

    public HytLockDetialAdapter(List<Integer> times) {
        this.times = times;
    }

    public void setSel(int posi) {
        this.selPosi = posi;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return times == null ? 0 : times.size();
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
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, MyApplication.getInstance(), R.layout.item_lock_detial, null);
        ImageView iv_sel = cvh.getIv(R.id.iv_sel);
        TextView tv = cvh.getTv(R.id.tv_time);
        tv.setText(DataUtils.getDateByMinutes(times.get(position)));
        if (position == selPosi) {
            iv_sel.setVisibility(View.VISIBLE);
        } else {
            iv_sel.setVisibility(View.INVISIBLE);
        }
        return cvh.convertView;
    }
}
