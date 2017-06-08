package com.cucr.myapplication.adapter.LvAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/19.
 */
public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_popularity,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_num.setText("第 "+position+" 名");

    }

    @Override
    public int getItemCount() {
        return 20;
    }



    class  MyViewHolder extends RecyclerView.ViewHolder{
        public  TextView tv_focus_popularity;
        public  TextView tv_num;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_focus_popularity = (TextView) itemView.findViewById(R.id.tv_focus_popularity);
        }
    }

}
