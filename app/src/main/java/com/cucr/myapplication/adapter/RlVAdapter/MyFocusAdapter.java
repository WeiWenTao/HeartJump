package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by cucr on 2018/2/10.
 */

public class MyFocusAdapter extends RecyclerView.Adapter<MyFocusAdapter.FocusHolder> {

    private List<FocusInfo.RowsBean> rows;

    public void setData(List<FocusInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public FocusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_my_focus, parent, false);
        return new FocusHolder(inflate);
    }

    @Override
    public void onBindViewHolder(FocusHolder holder, int position) {
//        FocusInfo.RowsBean rowsBean = rows.get(position);
//        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUser().getUserHeadPortrait(), holder.iv_user_icon_all_focus, MyApplication.getImageLoaderOptions());
//        holder.tv_name.setText(rowsBean.getStart().);
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    class FocusHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_user_icon_all_focus)
        private ImageView iv_user_icon_all_focus;

        @ViewInject(R.id.tv_name)
        private TextView tv_name;

        @ViewInject(R.id.tv_sign)
        private TextView tv_sign;

        @ViewInject(R.id.tv_to_focus)
        private TextView tv_to_focus;

        public FocusHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
