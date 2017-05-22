package com.cucr.myapplication.adapter.GvAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/5/22.
 */

public class StarClassifyAdapter extends BaseAdapter {
    Context mContext;

    int checked = -1;

    public void setCheck(int position){
        checked = position;
        notifyDataSetChanged();
    }

    public StarClassifyAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 30;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_gv_star_classify, null);
        FrameLayout fl = cvh.getView(R.id.fl_star_bg,FrameLayout.class);
        RelativeLayout rl_goto_starpager = cvh.getView(R.id.rl_goto_starpager, RelativeLayout.class);
        rl_goto_starpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),position+"",Toast.LENGTH_SHORT).show();
            }
        });
        if (position == checked){
            //条目复用导致的混乱 可用对象存储字段解决 TODO
            fl.setVisibility( fl.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }

        return cvh.convertView;
    }
}
