package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.comment.FenTuanCatgoryActiviry;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.MsgBean.MsgInfo;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vanniktech.emoji.EmojiTextView;

import java.util.List;
import java.util.Map;

/**
 * Created by cucr on 2018/3/16.
 */

public class MsgGoodAdapter extends RecyclerView.Adapter<MsgGoodAdapter.MyHolder> {

    private List<MsgInfo.RowsBean> rows;
    private Gson mGson;
    private Context mContext;

    public MsgGoodAdapter() {
        mContext = MyApplication.getInstance();
        mGson = MyApplication.getGson();
    }

    public void setDate(List<MsgInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addDate(List<MsgInfo.RowsBean> rows) {
        if (this.rows != null && rows != null) {
            notifyItemInserted(this.rows.size() + 1);
            this.rows.addAll(rows);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_msg_good, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MsgInfo.RowsBean rowsBean = rows.get(position);
        MsgInfo.RowsBean.ReceiverUserBean receiverUser = rowsBean.getReceiverUser();    //原文作者
        MsgInfo.RowsBean.SendUserBean sendUser = rowsBean.getSendUser();                //评论人

        if (TextUtils.isEmpty(rowsBean.getPhotoUrl())) {
            holder.iv_cover.setVisibility(View.GONE);
        } else {
            holder.iv_cover.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(rowsBean.getPhotoUrl(), //原文封面
                    holder.iv_cover, MyApplication.getImageLoaderOptions());
        }

        ImageLoader.getInstance().displayImage(sendUser.getUserHeadPortrait(), //评论人头像
                holder.iv_pic, MyApplication.getImageLoaderOptions());

        Map map = mGson.fromJson(rowsBean.getExtras(), Map.class);
        final String DateId = (String) map.get("dataId");
        holder.tv_name.setText(receiverUser.getName());             //原文作者姓名
        holder.tv_neckname.setText(sendUser.getName() + " 赞了你");     //点赞人姓名
        holder.tv_time.setText(rowsBean.getCreateTime());       //创建时间
        holder.tv_content.setText(((String) map.get("title")));         //原文内容

        //跳转粉团详情界面
        holder.ll_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FenTuanCatgoryActiviry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dataId", DateId);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        //点击跳转
        @ViewInject(R.id.ll_dynamic)
        private LinearLayout ll_dynamic;

        //用户头像
        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;

        //评论人
        @ViewInject(R.id.tv_neckname)
        private TextView tv_neckname;

        //评论时间
        @ViewInject(R.id.tv_time)
        private TextView tv_time;

        //图片
        @ViewInject(R.id.iv_cover)
        private ImageView iv_cover;

        //原文昵称
        @ViewInject(R.id.tv_name)
        private TextView tv_name;

        //原文内容
        @ViewInject(R.id.tv_content)
        private EmojiTextView tv_content;

        public MyHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
