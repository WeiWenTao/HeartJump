package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/4/18.
 */
public class LVPersonalDongtaiAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 3;
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
        CommonViewHolder cvh1 = null;
        CommonViewHolder cvh2 = null;
        CommonViewHolder cvh3 = null;
        switch (getItemViewType(position)){
            case Constans.TYPE_ONE:
                cvh1 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_personal_journey_type_one,null);

                return cvh1.convertView;

            case Constans.TYPE_TWO:
                cvh2 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_personal_journey_type_two,null);

                return cvh2.convertView;


        }

        return cvh1.convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return Constans.TYPE_ONE;
        }else {
            return Constans.TYPE_TWO;
        }


    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}
