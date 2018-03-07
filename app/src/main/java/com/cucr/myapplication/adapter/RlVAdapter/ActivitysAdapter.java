package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2017/12/7.
 */

public class ActivitysAdapter extends RecyclerView.Adapter<ActivitysAdapter.ActivesHolder> {

    private List<QiYeHuoDongInfo.RowsBean> rows;
    private Context mContext;

    public void setData(List<QiYeHuoDongInfo.RowsBean> rows) {
        this.rows = rows;
        mContext = MyApplication.getInstance();
        notifyDataSetChanged();
    }

    public void addData(List<QiYeHuoDongInfo.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    @Override
    public ActivesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rlv_actives, parent, false);
        return new ActivesHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivesHolder holder, final int position) {
        final QiYeHuoDongInfo.RowsBean rowsBean = rows.get(position);
        MyLogger.jLog().i("activePosition:" + position + ",rowsBean:" + rowsBean);

        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getPicurl(), holder.iv_pic, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getApplyUser().getUserHeadPortrait(), holder.iv_head, MyApplication.getImageLoaderOptions());
        holder.tv_title.setText(rowsBean.getActiveName());
        holder.tv_name.setText(rowsBean.getApplyUser().getRealName());
        holder.tv_commends.setText(rowsBean.getCommentCount() + "");
        holder.tv_goods.setText(rowsBean.getGiveUpCount() + "");
        if (rowsBean.getIsSignUp() == 1) {
            holder.iv_zan.setImageResource(R.drawable.icon_good_sel);
        }else {
            holder.iv_zan.setImageResource(R.drawable.icon_good_nor);
        }


        holder.item_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickCommends(position, rowsBean,false);
                }
            }
        });

        holder.ll_commends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickCommends(position, rowsBean,true);
                }
            }
        });

        holder.ll_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickGoods(position, rowsBean);
                }
                notifyDataSetChanged();
            }
        });

        holder.ll_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickPerson(rowsBean.getApplyUser().getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class ActivesHolder extends RecyclerView.ViewHolder {

        //条目
        @ViewInject(R.id.item_active)
        private LinearLayout item_active;

        //封面
        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;

        //头像
        @ViewInject(R.id.iv_head)
        private ImageView iv_head;

        //点赞图标
        @ViewInject(R.id.iv_zan)
        private ImageView iv_zan;

        //标题
        @ViewInject(R.id.tv_title)
        private TextView tv_title;

        //名称
        @ViewInject(R.id.tv_name)
        private TextView tv_name;

        //评论数量
        @ViewInject(R.id.tv_commends)
        private TextView tv_commends;

        //点赞数量
        @ViewInject(R.id.tv_goods)
        private TextView tv_goods;

        //点击评论
        @ViewInject(R.id.ll_commends)
        private LinearLayout ll_commends;

        //点击点赞
        @ViewInject(R.id.ll_goods)
        private LinearLayout ll_goods;

        //点击发布人
        @ViewInject(R.id.ll_persona)
        private LinearLayout ll_persona;


        public ActivesHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private ClickListener onClickListener;

    public void setOnClickListener(ClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface ClickListener {
        void onClickGoods(int position, QiYeHuoDongInfo.RowsBean rowsBean);

        void onClickCommends(int position, QiYeHuoDongInfo.RowsBean rowsBean,boolean isFromComment);

        void onClickPerson(int personId);
    }
}
