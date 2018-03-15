package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/3/13.
 */

public class SearchStarAdapter extends RecyclerView.Adapter<SearchStarAdapter.MyHolder> {

    private List<StarListInfos.RowsBean> rows;
    private Context mContext;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = MyApplication.getInstance();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_star_search, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final StarListInfos.RowsBean rowsBean = rows.get(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick(rowsBean.getId());
                }
            }
        });
        ImageLoader.getInstance().displayImage(rowsBean.getStartShowPic(), holder.iv_headpic, MyApplication.getImageLoaderOptions());
        holder.tv_star_name.setText(rowsBean.getRealName());
        //是否关注  0：未关注      1：已关注
        Resources resources = mContext.getResources();
        if (rowsBean.getIsfollow() == 0) {
            holder.tv_focus.setText("加关注");
            holder.tv_focus.setTextColor(Color.WHITE);
            holder.tv_focus.setBackgroundDrawable(resources.getDrawable(R.drawable.care_nor));
        } else {
            holder.tv_focus.setText("已关注");
            holder.tv_focus.setTextColor(resources.getColor(R.color.pink));
            holder.tv_focus.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_focud_bg));
        }
        holder.tv_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onFocusClick(rowsBean);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public void setData(List<StarListInfos.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<StarListInfos.RowsBean> rows) {
        if (this.rows != null) {
            notifyItemInserted(this.rows.size() + 1);
            this.rows.addAll(rows);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private View item;
        private ImageView iv_headpic;
        private TextView tv_star_name;
        private TextView tv_focus;


        public MyHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.ll_item);
            iv_headpic = (ImageView) itemView.findViewById(R.id.iv_headpic);
            tv_star_name = (TextView) itemView.findViewById(R.id.tv_star_name);
            tv_focus = (TextView) itemView.findViewById(R.id.tv_focus);
        }
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int id);

        void onFocusClick(StarListInfos.RowsBean rowsBean);
    }
}
