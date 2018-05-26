package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.star.StarProduction;

import java.util.List;

/**
 * Created by cucr on 2018/5/25.
 */

public class MultiFeedAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MultiFeedAdapter";
    public static final int TYPE_TIME = 0;
    private List<StarProduction> allWorks;

    @Override
    public int getItemViewType(int position) {
        return allWorks.get(position).getType();
    }

    public void setData(List<StarProduction> allWorks) {
        this.allWorks = allWorks;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
            return new ItemHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_star_pro, parent, false);
            return new FeedHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StarProduction starProduction = allWorks.get(position);
        if (holder instanceof FeedHolder) {
            ((FeedHolder) holder).mTvTime.setText(getTitle(position));
        } else if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).tv_works_name.setText("《" + starProduction.getName() + "》");
            ((ItemHolder) holder).tv_works_time.setText(starProduction.getDate());
            String actors = starProduction.getActors();
            ((ItemHolder) holder).tv_works_detial.setText(actors);
        }
    }

    private String getTitle(int position) {

        switch (allWorks.get(position).getType()) {
            case 1:

                return "单曲";

            case 2:

                return "电影";

            default:

                return "电视";
        }
    }

    @Override
    public int getItemCount() {
        return allWorks == null ? 0 : allWorks.size();
    }

    public static class FeedHolder extends RecyclerView.ViewHolder {
        private TextView mTvTime;

        public FeedHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tv_works_name;
        private TextView tv_works_time;
        private TextView tv_works_detial;

        public ItemHolder(View itemView) {
            super(itemView);
            tv_works_name = (TextView) itemView.findViewById(R.id.tv_works_name);
            tv_works_detial = (TextView) itemView.findViewById(R.id.tv_works_detial);
            tv_works_time = (TextView) itemView.findViewById(R.id.tv_works_time);
        }
    }
}
