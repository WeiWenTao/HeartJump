package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/6/27.
 */
public class XingWenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final LayoutInflater mLayoutInflater;
    private QueryFtInfos mQueryFtInfos;
    private List<QueryFtInfos.RowsBean> rows;
    private Intent mIntent;

    public XingWenAdapter() {
        this.context = MyApplication.getInstance();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(QueryFtInfos mQueryFtInfos) {
        this.mQueryFtInfos = mQueryFtInfos;
        rows = mQueryFtInfos.getRows();
        notifyDataSetChanged();
    }

    public void addData(List<QueryFtInfos.RowsBean> newData) {
        rows.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            //视频
            case Constans.TYPE_VIDEO:
                return new Tp1_Holder(mLayoutInflater.inflate(R.layout.item_xw_type02, parent, false));

            //图片
            case Constans.TYPE_PICTURE:
                return new Tp2_Holder(mLayoutInflater.inflate(R.layout.item_xw_type01, parent, false));

            //纯文本
            case Constans.TYPE_TEXT:
                return new Tp3_Holder(mLayoutInflater.inflate(R.layout.item_xw_type00, parent, false));

        }
        return null;
    }

    //获取条目类型
    @Override
    public int getItemViewType(int position) {
        QueryFtInfos.RowsBean rowsBean = rows.get(position);
        return rowsBean.getType();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final QueryFtInfos.RowsBean rowsBean = rows.get(position);
        MyLogger.jLog().i("position:" + position + ",rowsBean:" + rowsBean);
        mIntent = new Intent(context, TestWebViewActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

// -------------------------------------------------------------------------------------------------
        if (holder instanceof Tp1_Holder) {  //视频
            ((Tp1_Holder) holder).tv_title.setText(rowsBean.getTitle());            //标题
            ((Tp1_Holder) holder).tv_from.setText(rowsBean.getCreateUserName());    //来自
            ((Tp1_Holder) holder).tv_reads.setText(rowsBean.getReadCount() + "");     //阅读量

            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(0).getVideoPagePic(), ((Tp1_Holder) holder).iv_video, MyApplication.getImageLoaderOptions());

            ((Tp1_Holder) holder).ll_item_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.putExtra("url", rowsBean.getLocationUrl());
                    context.startActivity(mIntent);
                }
            });
// -------------------------------------------------------------------------------------------------
        } else if (holder instanceof Tp2_Holder) {//图片
            ((Tp2_Holder) holder).tv_title.setText(rowsBean.getTitle());            //标题
            ((Tp2_Holder) holder).tv_from.setText(rowsBean.getCreateUserName());    //来自
            ((Tp2_Holder) holder).tv_reads.setText(rowsBean.getReadCount() + "");     //阅读量

            try {
                ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(0).getFileUrl(), ((Tp2_Holder) holder).iv_pic1, MyApplication.getImageLoaderOptions());
                ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(1).getFileUrl(), ((Tp2_Holder) holder).iv_pic2, MyApplication.getImageLoaderOptions());
                ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(2).getFileUrl(), ((Tp2_Holder) holder).iv_pic3, MyApplication.getImageLoaderOptions());
            } catch (Exception e) {
                MyLogger.jLog().i("跳过");
                notifyDataSetChanged();
            }

            ((Tp2_Holder) holder).ll_item_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.putExtra("url", rowsBean.getLocationUrl());
                    context.startActivity(mIntent);
                }
            });
// -------------------------------------------------------------------------------------------------
        } else { //文字
            ((Tp3_Holder) holder).tv_title.setText(rowsBean.getTitle());            //标题
            ((Tp3_Holder) holder).tv_from.setText(rowsBean.getCreateUserName());    //来自
            ((Tp3_Holder) holder).tv_reads.setText(rowsBean.getReadCount() + "");     //阅读量

            ((Tp3_Holder) holder).tv_content.setText(rowsBean.getContent());     //内容

            ((Tp3_Holder) holder).ll_item_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.putExtra("url", rowsBean.getLocationUrl());
                    context.startActivity(mIntent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mQueryFtInfos == null || rows == null) {
            MyLogger.jLog().i("mQueryFtInfos:" + mQueryFtInfos + ",rows:" + rows);
            return 0;
        }
        MyLogger.jLog().i("size:" + rows.size());
        return rows.size();
    }

    //Video
    class Tp1_Holder extends RecyclerView.ViewHolder {
        //标题
        @ViewInject(R.id.tv_title)
        TextView tv_title;

        //视频封面
        @ViewInject(R.id.iv_video)
        ImageView iv_video;

        //视频时长
        @ViewInject(R.id.tv_time)
        TextView tv_time;

        //来自
        @ViewInject(R.id.tv_from)
        TextView tv_from;

        //阅读量
        @ViewInject(R.id.tv_reads)
        TextView tv_reads;

        //条目
        @ViewInject(R.id.ll_item_video)
        LinearLayout ll_item_video;

        public Tp1_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    //Picture
    class Tp2_Holder extends RecyclerView.ViewHolder {
        //标题
        @ViewInject(R.id.tv_title)
        TextView tv_title;

        //图片1
        @ViewInject(R.id.iv_pic1)
        ImageView iv_pic1;

        //图片2
        @ViewInject(R.id.iv_pic2)
        ImageView iv_pic2;

        //图片3
        @ViewInject(R.id.iv_pic3)
        ImageView iv_pic3;

        //来自
        @ViewInject(R.id.tv_from)
        TextView tv_from;

        //阅读量
        @ViewInject(R.id.tv_reads)
        TextView tv_reads;

        //条目
        @ViewInject(R.id.ll_item_pic)
        LinearLayout ll_item_pic;

        public Tp2_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    //Text
    class Tp3_Holder extends RecyclerView.ViewHolder {

        //标题
        @ViewInject(R.id.tv_title)
        TextView tv_title;

        //内容
        @ViewInject(R.id.tv_content)
        TextView tv_content;

        //来自
        @ViewInject(R.id.tv_from)
        TextView tv_from;

        //阅读量
        @ViewInject(R.id.tv_reads)
        TextView tv_reads;

        //条目
        @ViewInject(R.id.ll_item_text)
        LinearLayout ll_item_text;

        public Tp3_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

}
