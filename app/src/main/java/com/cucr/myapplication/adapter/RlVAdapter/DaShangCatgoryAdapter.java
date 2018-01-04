package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.DaShangListInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2017/11/9.
 */

public class DaShangCatgoryAdapter extends RecyclerView.Adapter<DaShangCatgoryAdapter.DSCatgoryHolder> {

    private List<DaShangListInfo.RowsBean> rows;

    public void setData(List<DaShangListInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public DSCatgoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashang_list, parent, false);
        return new DSCatgoryHolder(view);
    }

    @Override
    public void onBindViewHolder(DSCatgoryHolder holder, int position) {
        final DaShangListInfo.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST +
                        rowsBean.getRewardUser().getUserHeadPortrait(), holder.iv_ds_userhead,
                MyApplication.getImageLoaderOptions());
        holder.tv_ds_username.setText(rowsBean.getRewardUser().getName());
        holder.tv_ds_time.setText(rowsBean.getRewardTime());
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST +
                        rowsBean.getRewardType().getPicUrl(), holder.iv_ds_img,
                MyApplication.getImageLoaderOptions());
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class DSCatgoryHolder extends RecyclerView.ViewHolder {
        //用户头像
        @ViewInject(R.id.iv_ds_userhead)
        private ImageView iv_ds_userhead;
        //用户昵称
        @ViewInject(R.id.tv_ds_username)
        private TextView tv_ds_username;
        //打赏时间
        @ViewInject(R.id.tv_ds_time)
        private TextView tv_ds_time;
        //道具图片
        @ViewInject(R.id.iv_ds_img)
        private ImageView iv_ds_img;


        public DSCatgoryHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
