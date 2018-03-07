package com.cucr.myapplication.adapter.RlVAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/1/3.
 */

public class MinePicWallAdapter extends RecyclerView.Adapter<MinePicWallAdapter.PicWallHolder> {

    private List<PicWallInfo.RowsBean> rows;
    private Context mContext;

    public MinePicWallAdapter() {
        mContext = MyApplication.getInstance();
    }

    public void setData(List<PicWallInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void delData(List<PicWallInfo.RowsBean> rows, int position) {
        this.rows = rows;
        notifyDataSetChanged();
//        notifyItemRemoved(position);
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
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_rlv_mine_picwall, parent, false);
        return new PicWallHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final PicWallHolder holder, final int position) {
        final PicWallInfo.RowsBean rowsBean = rows.get(position);
        if (rowsBean == null){
            return;
        }
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getPicUrl(), holder.iv_pic, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUser().getUserHeadPortrait(), holder.iv_user_head, MyApplication.getImageLoaderOptions());
        holder.tv_goods_num.setText(rowsBean.getGiveUpCount() + "");
        holder.tv_user_name.setText(rowsBean.getUser().getName());

        if (rowsBean.getStatu() == 0) {       //待审核
            holder.ll_personal.setVisibility(View.GONE);
            holder.tv_goods_num.setVisibility(View.GONE);
            holder.iv_good.setVisibility(View.GONE);
            holder.tv_result.setText("待审核");
            holder.tv_result.setVisibility(View.VISIBLE);
            holder.rl_foot.setBackgroundColor(Color.parseColor("#00000000"));
            holder.rl_item.setForeground(mContext.getResources().getDrawable(R.drawable.shape_alpha_forgrand_sel));

        } else if (rowsBean.getStatu() == 1) { //通过
            holder.ll_personal.setVisibility(View.VISIBLE);
            holder.tv_goods_num.setVisibility(View.VISIBLE);
            holder.iv_good.setVisibility(View.VISIBLE);
            holder.tv_result.setVisibility(View.GONE);
            holder.rl_foot.setBackgroundColor(Color.parseColor("#50000000"));
            holder.rl_item.setForeground(mContext.getResources().getDrawable(R.drawable.shape_alpha_forgrand_nor));

        } else if (rowsBean.getStatu() == 2) { //拒绝
            holder.ll_personal.setVisibility(View.VISIBLE);
            holder.tv_goods_num.setVisibility(View.GONE);
            holder.iv_good.setVisibility(View.GONE);
            holder.tv_result.setText("审核未通过");
            holder.tv_result.setVisibility(View.VISIBLE);
            holder.rl_foot.setBackgroundColor(Color.parseColor("#00000000"));
            holder.rl_item.setForeground(mContext.getResources().getDrawable(R.drawable.shape_alpha_forgrand_sel));
        }

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

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.longClickItem(rowsBean.getId(),position);
                }
                return true;
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

        @ViewInject(R.id.rl_item)
        private RelativeLayout rl_item;

        @ViewInject(R.id.tv_result)
        private TextView tv_result;

        @ViewInject(R.id.iv_good)
        private ImageView iv_good;

        @ViewInject(R.id.rl_foot)
        private RelativeLayout rl_foot;


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

        void longClickItem(int dataId,int position);
    }
}
