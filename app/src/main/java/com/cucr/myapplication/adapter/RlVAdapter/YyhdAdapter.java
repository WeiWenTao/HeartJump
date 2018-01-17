package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;

/**
 * Created by cucrx on 2018/1/16.
 */

public class YyhdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constans.TYPE_HEADER) {
            View headerView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd_head, parent, false);
            return new HytHeaderHolder(headerView);
        } else {
            View itemView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd, parent, false);
            return new HytItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constans.TYPE_HEADER : Constans.TYPE_ITEM;
    }

    public class HytItemHolder extends RecyclerView.ViewHolder {
        public HytItemHolder(View itemView) {
            super(itemView);
        }
    }

    public class HytHeaderHolder extends RecyclerView.ViewHolder {
        public HytHeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
