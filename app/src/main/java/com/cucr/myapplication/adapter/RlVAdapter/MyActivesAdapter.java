package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fuli.MyActives;
import com.cucr.myapplication.constants.HttpContans;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/3/1.
 */

public class MyActivesAdapter extends RecyclerView.Adapter<MyActivesAdapter.MyHoder> {

    private List<MyActives.RowsBean> rows;

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_myactives, parent, false);
        return new MyHoder(inflate);
    }

    public void setData(List<MyActives.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        MyActives.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getActive().getPicUrl(), holder.iv_pic, MyApplication.getImageLoaderOptions());
        holder.tv_time.setText("有效期至" + rowsBean.getActive().getEndTime().substring(0, 10));
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class MyHoder extends RecyclerView.ViewHolder {

        private ImageView iv_pic;
        private LinearLayout ll_detial;
        private TextView tv_time;

        public MyHoder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            ll_detial = (LinearLayout) itemView.findViewById(R.id.ll_detial);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
