package com.cucr.myapplication.adapter.RlVAdapter;

import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cucr on 2018/1/3.
 */

public class PicWallAdapter extends RecyclerView.Adapter<PicWallAdapter.PicWallHolder> {

    public PicWallAdapter() {
        map = new HashMap<>();
    }

    private List<PicWallInfo.RowsBean> rows;
    private Map<Integer, Integer> map;

    public void setData(List<PicWallInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<PicWallInfo.RowsBean> rows) {
        if (this.rows == null || rows == null || rows.size() == 0) {
            return;
        }
        notifyItemInserted(this.rows.size() + 1);
        this.rows.addAll(rows);
//        notifyDataSetChanged();
//        notifyItemRangeChanged(this.rows.size(), this.rows.size() + rows.size());
    }

    @Override
    public PicWallHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_rlv_picwall, parent, false);
        return new PicWallHolder(view);
    }

    @Override
    public void onBindViewHolder(final PicWallHolder holder, final int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.iv_pic.setTransitionName("aaa");
        }
        final PicWallInfo.RowsBean rowsBean = rows.get(position);
//======================================================================
//        Integer viewHeight = map.get(position);
//        if (viewHeight != null) {
//            ViewGroup.LayoutParams layoutParams = holder.iv_pic.getLayoutParams();
//            layoutParams.height = viewHeight;
//            holder.iv_pic.setLayoutParams(layoutParams);
//        } else {
//            //网络请求获取到图片的Drawable或者bitmap,得到图片宽高比例，并得到View高度viewHeight
//            ViewGroup.LayoutParams layoutParams = holder.iv_pic.getLayoutParams();
//            viewHeight = layoutParams.height;
//            holder.iv_pic.setLayoutParams(layoutParams);
//            map.put(position, viewHeight);
//
//        }
//======================================================================
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getPicUrl(), holder.iv_pic, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUser().getUserHeadPortrait(), holder.iv_user_head, MyApplication.getImageLoaderOptions());
        holder.tv_goods_num.setText(rowsBean.getGiveUpCount() + "");
        holder.tv_user_name.setText(rowsBean.getUser().getName());
        holder.ll_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.clickUser(rowsBean.getUser().getId());
                }
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.clickItem(position, rowsBean, holder.iv_pic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class PicWallHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.item)
        private CardView item;

        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;

        @ViewInject(R.id.iv_user_head)
        private ImageView iv_user_head;

        @ViewInject(R.id.tv_user_name)
        private TextView tv_user_name;

        @ViewInject(R.id.tv_goods_num)
        private TextView tv_goods_num;

        @ViewInject(R.id.ll_personal)
        private LinearLayout ll_personal;


        public PicWallHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void clickUser(int userId);

        void clickItem(int position, PicWallInfo.RowsBean rowsBean, ImageView imageView);
    }
}
