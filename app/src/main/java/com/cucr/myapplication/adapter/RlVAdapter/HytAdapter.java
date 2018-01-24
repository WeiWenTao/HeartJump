package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.Hyt.HytListInfos;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucrx on 2018/1/16.
 */

public class HytAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HytListInfos.RowsBean> rows;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constans.TYPE_HEADER) {
            View headerView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_hyt_head, parent, false);
            return new HytHeaderHolder(headerView);
        } else {
            View itemView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_hyt, parent, false);
            return new HytItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof HytHeaderHolder) {
            ((HytHeaderHolder) holder).ll_creat_hyt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickItems != null) {
                    }
                    mOnClickItems.onClickItem(position);
                }
            });

//-----------------------------------------------------------------------------
        } else if (holder instanceof HytItemHolder) {
//-----------------------------------------------------------------------------
            final HytListInfos.RowsBean rowsBean = rows.get(position - 1);
            ((HytItemHolder) holder).tv_hyt_name.setText(rowsBean.getName());
            ((HytItemHolder) holder).tv_hyt_peoples.setText(rowsBean.getUserContact() + "人热聊中");
            //加入用及时通讯
//            ((HytItemHolder) holder).tv_join.setText(rowsBean.getCreateUser().);

            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getPicUrl(),
                    ((HytItemHolder) holder).iv_pic, MyApplication.getImageLoaderOptions());

            ((HytItemHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickItems != null) {
                    }
                    mOnClickItems.onClickItem(position);
                }
            });

            ((HytItemHolder) holder).tv_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickItems != null) {
                    }
                    mOnClickItems.onClickJoin(rowsBean.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rows == null ? 1 : rows.size() + 1;
    }


    public void setData(List<HytListInfos.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }


    public void addData(List<HytListInfos.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constans.TYPE_HEADER : Constans.TYPE_ITEM;
    }

    public class HytHeaderHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_creat_hyt;

        public HytHeaderHolder(View itemView) {
            super(itemView);
            ll_creat_hyt = (LinearLayout) itemView.findViewById(R.id.ll_creat_hyt);
        }
    }

    public class HytItemHolder extends RecyclerView.ViewHolder {
        private ImageView iv_pic;
        private TextView tv_hyt_name;
        private TextView tv_hyt_peoples;
        private TextView tv_join;
        private RelativeLayout rl_item;

        public HytItemHolder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_hyt_name = (TextView) itemView.findViewById(R.id.tv_hyt_name);
            tv_hyt_peoples = (TextView) itemView.findViewById(R.id.tv_hyt_peoples);
            tv_join = (TextView) itemView.findViewById(R.id.tv_join);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    private OnClickItems mOnClickItems;

    public void setOnClickItems(OnClickItems onClickItems) {
        mOnClickItems = onClickItems;
    }

    public interface OnClickItems {
        void onClickItem(int position);

        void onClickJoin(int hytId);
    }
}