package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdSupports;
import com.cucr.myapplication.constants.HttpContans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/1/31.
 */

public class YyhdSupportRecord extends RecyclerView.Adapter<YyhdSupportRecord.RecordHolder> {


    private List<YyhdSupports.RowsBean> rows;

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_support_record, parent, false);
        return new RecordHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecordHolder holder, int position) {
        YyhdSupports.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        rowsBean.getUser().getUserHeadPortrait(), holder.iv_headpic,
                MyApplication.getImageLoaderOptions());
        holder.tv_je.setText("¥" + rowsBean.getAmount());
        holder.tv_name.setText(rowsBean.getUser().getName());
        holder.tv_time.setText(rowsBean.getCreateTime());

    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public void setData(List<YyhdSupports.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public class RecordHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_headpic)
        private ImageView iv_headpic;

        @ViewInject(R.id.tv_name)
        private TextView tv_name;

        @ViewInject(R.id.tv_time)
        private TextView tv_time;

        @ViewInject(R.id.tv_je)
        private TextView tv_je;

        public RecordHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
