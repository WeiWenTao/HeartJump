package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytMembers;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/2/28.
 */

public class HytMembersAdapter extends RecyclerView.Adapter<HytMembersAdapter.MyHolder> {

    private List<HytMembers.RowsBean> rows;

    public void setData(List<HytMembers.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hyt_members, parent, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        HytMembers.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUser().getUserHeadPortrait(), holder.iv_pic, MyApplication.getImageLoaderOptions());
        holder.tv_name.setText(rowsBean.getUser().getName());
        holder.tv_tip.setText(CommonUtils.getTip(rowsBean.getIntegral()));
        if (position == 0) {
            holder.tv_captain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv_pic;
        private TextView tv_name;
        private TextView tv_tip;
        private TextView tv_captain;

        public MyHolder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_tip = (TextView) itemView.findViewById(R.id.tv_tip);
            tv_captain = (TextView) itemView.findViewById(R.id.tv_captain);
        }
    }

}
