package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.invate.InvateSerchStar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/6/19.
 */

public class InvateStarAdapter extends RecyclerView.Adapter<InvateStarAdapter.MyHolder> {

    private List<InvateSerchStar.RowsBean> rows;

    public void setData(List<InvateSerchStar.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<InvateSerchStar.RowsBean> rows) {
        if (this.rows != null) {
            notifyItemInserted(this.rows.size() + 1);
            this.rows.addAll(rows);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invate_stars, parent, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        InvateSerchStar.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(rowsBean.getUserHeadPortrait(), holder.iv, MyApplication.getImageLoaderOptions());
        holder.tv.setText(rowsBean.getRealName());
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.clickItem(rowsBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv)
        private ImageView iv;

        @ViewInject(R.id.tv)
        private TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    public interface OnItemClickListener{
        void clickItem(InvateSerchStar.RowsBean rowsBean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
