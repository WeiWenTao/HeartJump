package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/23.
 */
public class FuLiDuiHuanAdapter extends RecyclerView.Adapter<FuLiDuiHuanAdapter.FiLiDuiHuanHolder> {

    private Context mContext;

    public FuLiDuiHuanAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FiLiDuiHuanHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_fuli_,parent,false);

        FiLiDuiHuanHolder holder = new FiLiDuiHuanHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FiLiDuiHuanHolder holder, final int position) {
        holder.ll_duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null){
                    onItemListener.OnItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class FiLiDuiHuanHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_duihuan;


        public FiLiDuiHuanHolder(View itemView) {
            super(itemView);
            ll_duihuan  = (LinearLayout) itemView.findViewById(R.id.ll_duihuan);
        }
    }

    private OnItemListener onItemListener;

    public interface OnItemListener {
        void OnItemClick(View view, int position);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

}
