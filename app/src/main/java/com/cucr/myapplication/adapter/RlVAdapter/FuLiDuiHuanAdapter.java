package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fuli.DuiHuanGoosInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/6/23.
 */
public class FuLiDuiHuanAdapter extends RecyclerView.Adapter<FuLiDuiHuanAdapter.FiLiDuiHuanHolder> {

    private Context mContext;
    private List<DuiHuanGoosInfo.RowsBean> list;

    public void setDate(List<DuiHuanGoosInfo.RowsBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public FuLiDuiHuanAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public FiLiDuiHuanHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_fuli_, parent, false);

        FiLiDuiHuanHolder holder = new FiLiDuiHuanHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FiLiDuiHuanHolder holder, final int position) {
        //设置图片
        if (list!= null){
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + list.get(position).getShopPicUrl(), holder.iv_duihuan_icon, MyApplication.getImageLoaderOptions());
            holder.tv_goods_price.setText(list.get(position).getShopPrice()+"星币");
        }

        holder.ll_duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.OnItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class FiLiDuiHuanHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_duihuan;
        private ImageView iv_duihuan_icon;
        private TextView tv_goods_price;


        public FiLiDuiHuanHolder(View itemView) {
            super(itemView);
            ll_duihuan = (LinearLayout) itemView.findViewById(R.id.ll_duihuan);
            iv_duihuan_icon = (ImageView) itemView.findViewById(R.id.iv_duihuan_icon);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
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
