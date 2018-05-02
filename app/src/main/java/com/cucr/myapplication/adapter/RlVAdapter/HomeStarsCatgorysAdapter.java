package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.StarListKey;

import java.util.List;

/**
 * Created by cucr on 2018/4/19.
 */

public class HomeStarsCatgorysAdapter extends RecyclerView.Adapter<HomeStarsCatgorysAdapter.MyHolder> {

    private List<StarListKey.RowsBean> list;
    private int selPosition;
    private Context mContext;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = MyApplication.getInstance();
        View inflate = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_home_news_catgory, parent, false);
        return new MyHolder(inflate);
    }

    public void setData(List<StarListKey.RowsBean> list) {
        selPosition = list.size() - 1;
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final StarListKey.RowsBean rowsBean = list.get(position);
        if (selPosition == position) {
            holder.mTv_catgory.setTextColor(mContext.getResources().getColor(R.color.xtred));
        } else {
            holder.mTv_catgory.setTextColor(mContext.getResources().getColor(R.color.color_333));
        }
        holder.mTv_catgory.setText(rowsBean.getValueFild());
        holder.mTv_catgory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItem != null) {
                    if (position == list.size() - 1) {
                        mOnClickItem.clickItem(null, rowsBean.getValueFild());
                    } else {
                        mOnClickItem.clickItem(rowsBean.getKeyFild(), rowsBean.getValueFild());
                    }
                    selPosition = position;
                    HomeStarsCatgorysAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView mTv_catgory;

        public MyHolder(View itemView) {
            super(itemView);
            mTv_catgory = (TextView) itemView.findViewById(R.id.tv_catgory);
        }
    }

    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public interface OnClickItem {
        void clickItem(String tpye, String name);
    }
}
