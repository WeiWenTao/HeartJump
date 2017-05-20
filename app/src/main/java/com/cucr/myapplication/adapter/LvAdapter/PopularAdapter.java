package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 911 on 2017/4/19.
 */
public class PopularAdapter extends BaseAdapter {

    Map<Integer, Boolean> isFocus = new HashMap<>();

    {
        for (int i = 0; i <= 20; i++) {
            isFocus.put(i, true);
            if (i == 3) {
                isFocus.put(i, false);
            }
        }
    }


    private TextView mTv_focus;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_lv_popularity, null);
        TextView tv_num = cvh.getView(R.id.tv_num, TextView.class);
        tv_num.setText("第" + (position + 4) + "名");

        mTv_focus = cvh.getView(R.id.tv_focus_popularity, TextView.class);
        mTv_focus.setText(isFocus.get(position)?"已关注":"加关注");
        mTv_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

        return cvh.convertView;
    }


}
