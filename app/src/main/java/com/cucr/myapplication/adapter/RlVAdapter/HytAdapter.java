package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytListInfos;
import com.cucr.myapplication.constants.HttpContans;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucrx on 2018/1/16.
 */

public class HytAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HytListInfos.RowsBean> rows;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_hyt, parent, false);
        return new HytItemHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HytListInfos.RowsBean rowsBean = rows.get(position);
        ((HytItemHolder) holder).tv_hyt_name.setText(rowsBean.getName());
        ((HytItemHolder) holder).tv_hyt_peoples.setText(rowsBean.getGroupOfNumber() + "人热聊中");
        //0表示未加入
        if (rowsBean.getIsJoin() == 0) {
            ((HytItemHolder) holder).tv_join.setText("加入");
            ((HytItemHolder) holder).tv_join.setBackgroundResource(R.drawable.corner_13);
        } else {
            ((HytItemHolder) holder).tv_join.setText("已加入");
            ((HytItemHolder) holder).tv_join.setBackgroundResource(R.drawable.corner_13_gray);
        }

        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getPicUrl(),
                ((HytItemHolder) holder).iv_pic, MyApplication.getImageLoaderOptions());

        ((HytItemHolder) holder).tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItems != null) {
                }
                mOnClickItems.onClickJoin(rowsBean.getId(), rowsBean.getName(), rowsBean.getIsJoin() != 0, rowsBean.getCreateUser().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public void setData(List<HytListInfos.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<HytListInfos.RowsBean> rows) {
        if (this.rows != null) {
            this.rows.addAll(rows);
            notifyDataSetChanged();
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
        void onClickJoin(int hytId, String name, boolean isjoin, int cretaId);
    }
}
