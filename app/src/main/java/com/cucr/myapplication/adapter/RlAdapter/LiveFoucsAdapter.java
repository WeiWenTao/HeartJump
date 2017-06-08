package com.cucr.myapplication.adapter.RlAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/6.
 */



public class LiveFoucsAdapter extends RecyclerView.Adapter<LiveFoucsAdapter.MyHolder> {
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_my_focus_lv, parent, false);
        MyHolder vh = new MyHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }


//        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView,parent.getContext(), R.layout.item_live_my_focus_lv,null);


    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}

