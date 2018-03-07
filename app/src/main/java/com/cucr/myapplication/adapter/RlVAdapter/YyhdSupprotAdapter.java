package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdSupports;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/1/30.
 */

public class YyhdSupprotAdapter extends RecyclerView.Adapter {

    private List<YyhdSupports.RowsBean> rows;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        if (viewType == Constans.TYPE_FOOTER) {
            View more = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd_support_more, parent, false);
            ViewGroup.LayoutParams layoutParams = more.getLayoutParams();
            layoutParams.width = wm.getDefaultDisplay().getWidth() / 5;
            more.setLayoutParams(layoutParams);
            more.requestLayout();
            return new MyMoreHolder(more);
        } else {
            View item = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd_support, parent, false);
            ViewGroup.LayoutParams layoutParams = item.getLayoutParams();
            layoutParams.width = wm.getDefaultDisplay().getWidth() / 5;
            item.setLayoutParams(layoutParams);
            item.requestLayout();
            return new MySupportHolder(item);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MySupportHolder) {
            final YyhdSupports.RowsBean rowsBean = rows.get(position);
            ((MySupportHolder) holder).iv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClick != null) {
                        mOnItemClick.onClickItem(rowsBean.getUser().getId());
                    }
                }
            });

            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUser()
                    .getUserHeadPortrait(), ((MySupportHolder) holder).iv_item, MyApplication.
                    getImageLoaderOptions());
        } else if (holder instanceof MyMoreHolder) {
            ((MyMoreHolder) holder).iv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClick != null) {
                        mOnItemClick.onClickMore();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return rows == null ? 0 : rows.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        return position == rows.size() ? Constans.TYPE_FOOTER : Constans.TYPE_ITEM;
    }

    public void setData(List<YyhdSupports.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public class MySupportHolder extends RecyclerView.ViewHolder {
        private ImageView iv_item;

        public MySupportHolder(View itemView) {
            super(itemView);
            iv_item = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }

    public class MyMoreHolder extends RecyclerView.ViewHolder {
        private ImageView iv_more;

        public MyMoreHolder(View itemView) {
            super(itemView);
            iv_more = (ImageView) itemView.findViewById(R.id.iv_item_more);
        }
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onClickItem(int userId);

        void onClickMore();
    }
}
