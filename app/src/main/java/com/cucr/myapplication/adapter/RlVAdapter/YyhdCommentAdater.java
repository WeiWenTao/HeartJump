package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdCommentInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2018/1/31.
 */

public class YyhdCommentAdater extends RecyclerView.Adapter<YyhdCommentAdater.MyHolder> {

    private List<YyhdCommentInfo.RowsBean> rows;

    public void setData(List<YyhdCommentInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd_comment, parent, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final YyhdCommentInfo.RowsBean rowsBean = rows.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST +
                        rowsBean.getUser().getUserHeadPortrait(), holder.iv_userhead,
                MyApplication.getImageLoaderOptions());
        holder.tv_comment_content.setText(CommonUtils.unicode2String(rowsBean.getComment()));
        holder.tv_comment_time.setText(rowsBean.getReleaseTime());
        holder.tv_username.setText(rowsBean.getUser().getName());
        holder.tv_good_value.setText(rowsBean.getGiveUpCount() + "");
        holder.iv_good.setImageResource(
                rowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);

        holder.iv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickGood != null) {
                    mOnClickGood.clickGood(position, rowsBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_userhead)
        private ImageView iv_userhead;

        @ViewInject(R.id.tv_username)
        private TextView tv_username;

        @ViewInject(R.id.tv_comment_time)
        private TextView tv_comment_time;

        @ViewInject(R.id.tv_comment_content)
        private TextView tv_comment_content;

        @ViewInject(R.id.iv_good)
        private ImageView iv_good;

        @ViewInject(R.id.tv_good_value)
        private TextView tv_good_value;

        public MyHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnClickGood mOnClickGood;

    public void setOnClickGood(OnClickGood onClickGood) {
        mOnClickGood = onClickGood;
    }

    public interface OnClickGood {
        void clickGood(int position, YyhdCommentInfo.RowsBean rowsBean);
    }

}
