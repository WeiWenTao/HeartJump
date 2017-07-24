package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    public void onBindViewHolder(FiLiHolder holder, final int position) {
        holder.rl_fuli.setOnClickListener(new View.OnClickListener() {
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

    class FiLiHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rl_fuli;
        private TextView tv_isopen;

        public FiLiHolder(View itemView) {
            super(itemView);
            rl_fuli  = (RelativeLayout) itemView.findViewById(R.id.rl_fuli);
            tv_isopen  = (TextView) itemView.findViewById(R.id.tv_isopen);
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
