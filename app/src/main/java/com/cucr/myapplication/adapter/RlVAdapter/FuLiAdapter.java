package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/23.
 */
public class FuLiAdapter extends RecyclerView.Adapter<FuLiAdapter.FiLiHolder> {

    private Context mContext;

    public FuLiAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FiLiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_fuli,parent,false);


        FiLiHolder holder = new FiLiHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FiLiHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class FiLiHolder extends RecyclerView.ViewHolder{

        public FiLiHolder(View itemView) {
            super(itemView);
        }
    }
}
