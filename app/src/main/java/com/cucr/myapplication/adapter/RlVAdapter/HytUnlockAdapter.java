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
import com.cucr.myapplication.bean.Hyt.HytLockList;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.DataUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/2/28.
 */

public class HytUnlockAdapter extends RecyclerView.Adapter<HytUnlockAdapter.MyHolder> {

    private List<HytLockList.RowsBean> rows;
    private String lockIds;

    public void setData(List<HytLockList.RowsBean> rows) {
        this.rows = rows;
        lockIds = "";
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hyt_unlock, parent, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final HytLockList.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUserHeadPortrait(), holder.iv_pic, MyApplication.getImageLoaderOptions());
        holder.tv_name.setText(rowsBean.getName());
        String difValue = DataUtils.getDifValue(rowsBean.getTime());
        holder.tv_tip.setText("被禁言" + difValue.replace("-"," "));

        if (rowsBean.isSel()) {
            holder.iv_sel.setImageResource(R.drawable.remove_sel);
        } else {
            holder.iv_sel.setImageResource(R.drawable.circle_r_6_nor);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rowsBean.isSel()) {
                    //清除选项
                    lockIds.replace("," + rowsBean.getUserId(), "");
                } else {
                    lockIds = lockIds + "," + rowsBean.getUserId();
                }
                rowsBean.setSel(!rowsBean.isSel());
                notifyDataSetChanged();
            }
        });
    }

    public String getLockId() {
        return lockIds;
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv_pic;
        private ImageView iv_sel;
        private TextView tv_name;
        private TextView tv_tip;
        private RelativeLayout item;

        public MyHolder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            iv_sel = (ImageView) itemView.findViewById(R.id.iv_sel);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_tip = (TextView) itemView.findViewById(R.id.tv_tip);
            item = (RelativeLayout) itemView.findViewById(R.id.item);
        }
    }

}
