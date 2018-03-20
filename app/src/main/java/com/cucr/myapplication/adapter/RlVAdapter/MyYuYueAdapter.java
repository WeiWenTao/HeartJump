package com.cucr.myapplication.adapter.RlVAdapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/7/3.
 */
public class MyYuYueAdapter extends RecyclerView.Adapter<MyYuYueAdapter.MyHolder> {

    private List<QiYeHuoDongInfo.RowsBean> rows;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_rlv_myyuyue, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final QiYeHuoDongInfo.RowsBean rowsBean = rows.get(position);
        initTV(holder.mTv_price, rowsBean.getStartCost());
        holder.mTv_result.setText(rowsBean.getResult() == 0 ? "预约中" : "已完成");
        holder.mTv_result.setTextColor(rowsBean.getResult() == 0 ? Color.parseColor("#ff4f49") : Color.parseColor("#999999"));
        holder.mTv_star_name.setText(rowsBean.getAppStartUser().getRealName());
        holder.mTv_time.setText(rowsBean.getActiveStartTime());
        holder.mTv_title.setText(rowsBean.getActiveName());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        rowsBean.getAppStartUser().getUserHeadPortrait(), holder.mIv_head,
                MyApplication.getImageLoaderOptions());
        holder.rlv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem != null) {
                    mItem.onItemClick(rowsBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public void setData(List<QiYeHuoDongInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<QiYeHuoDongInfo.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView mTv_price;
        private TextView mTv_star_name;
        private TextView mTv_result;
        private TextView mTv_title;
        private TextView mTv_time;
        private ImageView mIv_head;
        private RelativeLayout rlv_item;

        public MyHolder(View itemView) {
            super(itemView);
            mTv_price = (TextView) itemView.findViewById(R.id.tv_price);
            mTv_star_name = (TextView) itemView.findViewById(R.id.tv_star_name);
            mTv_result = (TextView) itemView.findViewById(R.id.tv_result);
            mTv_title = (TextView) itemView.findViewById(R.id.tv_title);
            mTv_time = (TextView) itemView.findViewById(R.id.tv_time);
            mIv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            rlv_item = (RelativeLayout) itemView.findViewById(R.id.rlv_item);
        }
    }

    private void initTV(TextView tv_price, int prices) {
        //模拟获取数据
        String price = prices + " 万";

        SpannableString sp = new SpannableString("商业出演 " + price + " /场");

        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#ff4f49")), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //字体
        sp.setSpan(new AbsoluteSizeSpan(15, true), 4, 4 + price.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //加粗
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 4, 4 + price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //SpannableString对象设置给TextView
        tv_price.setText(sp);
        //设置TextView可点击
        tv_price.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private OnClickItem mItem;

    public void setItem(OnClickItem item) {
        mItem = item;
    }

    public interface OnClickItem {
        void onItemClick(QiYeHuoDongInfo.RowsBean rowsBean);
    }

}
