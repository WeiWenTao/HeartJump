package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.DataUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by cucrx on 2018/1/16.
 */

public class YyhdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<YyhdInfos.RowsBean> rows;
    private double mAmount;
    private int mProgress;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_yyhd, parent, false);
        return new YyhdItemHolder(itemView);
    }

    public void setData(List<YyhdInfos.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<YyhdInfos.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final YyhdInfos.RowsBean rowsBean = rows.get(position);
        //头像
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        rowsBean.getCreateUser().getUserHeadPortrait(),
                ((YyhdItemHolder) holder).iv_headpic, MyApplication.getImageLoaderOptions());
        //封面
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        rowsBean.getPicUrl(),
                ((YyhdItemHolder) holder).iv_yyhd_cover, MyApplication.getImageLoaderOptions());
        //后援团名称
        ((YyhdItemHolder) holder).tv_hyt_name.setText(rowsBean.getCreateUser().getName());
        //活动名称
        ((YyhdItemHolder) holder).tv_yyhd_name.setText(rowsBean.getActiveName());
        //剩余天数

        //状态  0:进行中
        if (rowsBean.getDoingStatu() == 0) {
            ((YyhdItemHolder) holder).tv_yyhd_status.setText("进行中");
            int shengYu = 0;
            try {
                String endTime = rowsBean.getEndTime();
                Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
                Date date1 = new Date();
                shengYu = DataUtils.differentDays(date1, parse);
            } catch (ParseException e) {
                MyLogger.jLog().i("日期解析错误");
            }
            ((YyhdItemHolder) holder).tv_residue.setText("距离活动结束" + shengYu + "天");
            ((YyhdItemHolder) holder).tv_yyhd_status.setBackgroundResource(R.drawable.corner_left_top_sel);

        } else {
            ((YyhdItemHolder) holder).tv_yyhd_status.setText("已结束");
            ((YyhdItemHolder) holder).tv_residue.setText("活动已结束");
            ((YyhdItemHolder) holder).tv_yyhd_status.setBackgroundResource(R.drawable.corner_left_top_nor);
        }
        switch (rowsBean.getActiveType()) {

            case Constans.TYPE_ONE:
                YyhdInfos.RowsBean.SysHytActiveOpenscreenBean active_1 = rowsBean.getSysHytActiveOpenscreen();
                mAmount = active_1.getAmount();
                break;

            case Constans.TYPE_TWO:
                YyhdInfos.RowsBean.SysHytActiveBigpadBean active_2 = rowsBean.getSysHytActiveBigpad();
                mAmount = active_2.getYyje();
                break;

            case Constans.TYPE_THREE:
                YyhdInfos.RowsBean.SysHytActiveZcBean active_3 = rowsBean.getSysHytActiveZc();
                mAmount = active_3.getAmount();
                break;

        }
        double signUpAmount = rowsBean.getSignUpAmount();//当前金额
        if (signUpAmount == 0) {
            mProgress = 0;
        } else {
            mProgress = (int) (signUpAmount / mAmount * 100);
        }

        ((YyhdItemHolder) holder).pb_yhhd_progress.setProgress(mProgress);
        ((YyhdItemHolder) holder).tv_progress.setText(mProgress + "%");

        ((YyhdItemHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItems != null) {
                    mOnClickItems.onClickItem(rowsBean);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class YyhdItemHolder extends RecyclerView.ViewHolder {
        private ImageView iv_headpic;           //头像
        private ImageView iv_yyhd_cover;        //封面
        private TextView tv_hyt_name;           //后援团名称
        private TextView tv_yyhd_name;          //应援活动名称
        private TextView tv_yyhd_status;        //状态(进行中)
        private TextView tv_residue;            //剩余天数
        private TextView tv_progress;           //进度条
        private ProgressBar pb_yhhd_progress;   //进度百分比
        private LinearLayout ll_item;           //item

        public YyhdItemHolder(View itemView) {
            super(itemView);
//            ViewUtils.inject(this, itemView);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            iv_headpic = (ImageView) itemView.findViewById(R.id.iv_headpic);
            iv_yyhd_cover = (ImageView) itemView.findViewById(R.id.iv_yyhd_cover);
            tv_hyt_name = (TextView) itemView.findViewById(R.id.tv_hyt_name);
            tv_yyhd_name = (TextView) itemView.findViewById(R.id.tv_yyhd_name);
            tv_yyhd_status = (TextView) itemView.findViewById(R.id.tv_yyhd_status);
            tv_residue = (TextView) itemView.findViewById(R.id.tv_residue);
            tv_progress = (TextView) itemView.findViewById(R.id.tv_progress);
            pb_yhhd_progress = (ProgressBar) itemView.findViewById(R.id.pb_yhhd_progress);
        }
    }

    private OnClickItems mOnClickItems;

    public void setOnClickItems(OnClickItems onClickItems) {
        mOnClickItems = onClickItems;
    }

    public interface OnClickItems {
        void onClickItem(YyhdInfos.RowsBean rowsBean);
    }
}
