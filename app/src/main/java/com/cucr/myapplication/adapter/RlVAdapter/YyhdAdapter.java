package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
            return new YyhdHeaderHolder(headerView);
        } else {
            View itemView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd, parent, false);
            return new YyhdItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof YyhdHeaderHolder) {
            ((YyhdHeaderHolder) holder).ll_creat_yyhd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickItems != null) {
                        mOnClickItems.onClickItem(position);
                    }
                }
            });
        } else if (holder instanceof YyhdItemHolder) {
            ((YyhdItemHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickItems != null) {
                        mOnClickItems.onClickItem(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constans.TYPE_HEADER : Constans.TYPE_ITEM;
    }

    public class YyhdItemHolder extends RecyclerView.ViewHolder {
        private ImageView iv_headpic;           //头像
        private ImageView iv_yyhd_cover;        //封面
        private TextView tv_hyt_name;           //后援团名称
        private TextView tv_yyhd_name;          //应援活动名称
        private TextView tv_yyhd_status;        //状态(进行中)
        private TextView tv_residue;            //剩余天数
        private TextView tv_progress;           //进度条
        private ProgressBar pb_yhhd_progress;   //进度百分比
        private LinearLayout ll_item;           //item

        public YyhdItemHolder(View itemView) {
            super(itemView);
//            ViewUtils.inject(this, itemView);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    public class YyhdHeaderHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_creat_yyhd;

        public YyhdHeaderHolder(View itemView) {
            super(itemView);
            ll_creat_yyhd = (LinearLayout) itemView.findViewById(R.id.ll_creat_yyhd);
        }
    }

    private OnClickItems mOnClickItems;

    public void setOnClickItems(OnClickItems onClickItems) {
        mOnClickItems = onClickItems;
    }

    public interface OnClickItems {
        void onClickItem(int position);
    }
}
