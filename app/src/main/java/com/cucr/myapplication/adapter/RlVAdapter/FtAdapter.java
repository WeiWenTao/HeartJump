package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.FenTuanCatgoryActiviry;
import com.cucr.myapplication.activity.fenTuan.ImagePagerActivity;
import com.cucr.myapplication.activity.video.VideoActivity;
import com.cucr.myapplication.adapter.GvAdapter.GridAdapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.gridView.NoScrollGridView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 911 on 2017/6/27.
 */
public class FtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final LayoutInflater mLayoutInflater;
    private QueryFtInfos mQueryFtInfos;

    public FtAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(QueryFtInfos mQueryFtInfos) {
        this.mQueryFtInfos = mQueryFtInfos;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            //视频
            case Constans.TYPE_VIDEO:
                return new Tp1_Holder(mLayoutInflater.inflate(R.layout.item_ft_list_type1, parent, false));

            //图片
            case Constans.TYPE_PICTURE:
                return new Tp2_Holder(mLayoutInflater.inflate(R.layout.item_ft_list_type2, parent, false));

            //纯文本
            case Constans.TYPE_TEXT:
                return new Tp3_Holder(mLayoutInflater.inflate(R.layout.item_ft_list_type3, parent, false));

        }
        return null;
    }

    //获取条目类型
    @Override
    public int getItemViewType(int position) {
        QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(position);
        return rowsBean.getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(position);
// -------------------------------------------------------------------------------------------------
        if (holder instanceof Tp1_Holder) {  //视频
            //头像
            ImageLoader.getInstance().displayImage(rowsBean.getUserHeadPortrait(), ((Tp1_Holder) holder).iv_pic);
            //视频封面
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(0).getVideoPagePic(), ((Tp1_Holder) holder).iv_video_pic);

            ((Tp1_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp1_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp1_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            ((Tp1_Holder) holder).tv_content.setText(rowsBean.getContent());    //文字内容
            if (TextUtils.isEmpty(rowsBean.getContent())) {
                ((Tp1_Holder) holder).tv_content.setVisibility(View.GONE);
            }
            ((Tp1_Holder) holder).tv_time.setText(CommonUtils.secToTime(rowsBean.getAttrFileList().get(0).getTimeCount()));    //时长
            ((Tp1_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp1_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量

            //点击跳转视频界面
            ((Tp1_Holder) holder).rl_goto_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("path", rowsBean.getAttrFileList().get(0).getFileUrl());
                    context.startActivity(intent);
                }
            });

            //点击分享
            ((Tp1_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //点击评论
            ((Tp1_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toComment(position);
                }
            });

            //点赞
            ((Tp1_Holder) holder).iv_favorite3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

// -------------------------------------------------------------------------------------------------
        } else if (holder instanceof Tp2_Holder) {    //图片
            ImageLoader.getInstance().displayImage(rowsBean.getUserHeadPortrait(), ((Tp2_Holder) holder).iv_pic);     //头像
            ((Tp2_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp2_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp2_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            ((Tp2_Holder) holder).gridview.setAdapter(new GridAdapter(context, rowsBean.getAttrFileList()));  //图片列表
            ((Tp2_Holder) holder).tv_content.setText(rowsBean.getContent());    //文字内容
            if (TextUtils.isEmpty(rowsBean.getContent())) {
                ((Tp2_Holder) holder).tv_content.setVisibility(View.GONE);
            }
            ((Tp2_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp2_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量


            ((Tp2_Holder) holder).gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, ImagePagerActivity.class);
                    // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                    List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList = rowsBean.getAttrFileList();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("imgs", (Serializable) attrFileList);//序列化,要注意转化(Serializable)
                    intent.putExtras(bundle);//发送数据
                    intent.putExtra("position", position);
                    context.startActivity(intent);

                }
            });
            //点击分享
            ((Tp2_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //点击评论
            ((Tp2_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toComment(position);
                }
            });

            //点赞
            ((Tp2_Holder) holder).iv_favorite3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

// -------------------------------------------------------------------------------------------------
        } else { //文字
            ImageLoader.getInstance().displayImage(rowsBean.getUserHeadPortrait(), ((Tp3_Holder) holder).iv_pic);     //头像
            ((Tp3_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp3_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp3_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            ((Tp3_Holder) holder).tv_content.setText(rowsBean.getContent());    //文字内容
            ((Tp3_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp3_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量

            //点击分享
            ((Tp3_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //点击评论
            ((Tp3_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toComment(position);
                }
            });

            //点赞
            ((Tp3_Holder) holder).iv_favorite3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        if (mQueryFtInfos == null || mQueryFtInfos.getRows() == null || mQueryFtInfos.getRows().size() == 0) {
            return 0;
        }
        return mQueryFtInfos.getRows().size();
    }


    //Video
    class Tp1_Holder extends RecyclerView.ViewHolder {
        //用户头像
        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;

        //视频封面
        @ViewInject(R.id.iv_video_pic)
        private ImageView iv_video_pic;

        //用户昵称
        @ViewInject(R.id.tv_neckname)
        private TextView tv_neckname;

        //时间和来源
        @ViewInject(R.id.tv_forminfo)
        private TextView tv_forminfo;

        //阅读量
        @ViewInject(R.id.tv_read)
        private TextView tv_read;

        //标题
        @ViewInject(R.id.tv_content)
        private TextView tv_content;

        //视频时长
        @ViewInject(R.id.tv_time)
        private TextView tv_time;

        //分享
        @ViewInject(R.id.rl_share)
        private RelativeLayout rl_share;

        //点击评论
        @ViewInject(R.id.rl_comment)
        private RelativeLayout rl_comment;

        //评论数量
        @ViewInject(R.id.tv_session)
        private TextView tv_session;

        //点赞
        @ViewInject(R.id.iv_favorite3)
        private ImageView iv_favorite3;

        //点赞数量
        @ViewInject(R.id.tv_favorite)
        private TextView tv_favorite;

        //点击播放视频
        @ViewInject(R.id.rl_goto_video)
        private RelativeLayout rl_goto_video;

        public Tp1_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    //Picture
    class Tp2_Holder extends RecyclerView.ViewHolder {
        //用户头像
        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;

        //图片列表
        @ViewInject(R.id.gridview)
        private NoScrollGridView gridview;

        //用户昵称
        @ViewInject(R.id.tv_neckname)
        private TextView tv_neckname;

        //时间和来源
        @ViewInject(R.id.tv_forminfo)
        private TextView tv_forminfo;

        //阅读量
        @ViewInject(R.id.tv_read)
        private TextView tv_read;

        //标题
        @ViewInject(R.id.tv_content)
        private TextView tv_content;

        //分享
        @ViewInject(R.id.rl_share)
        private RelativeLayout rl_share;

        //点击评论
        @ViewInject(R.id.rl_comment)
        private RelativeLayout rl_comment;

        //评论数量
        @ViewInject(R.id.tv_session)
        private TextView tv_session;

        //点赞
        @ViewInject(R.id.iv_favorite3)
        private ImageView iv_favorite3;

        //点赞数量
        @ViewInject(R.id.tv_favorite)
        private TextView tv_favorite;

        public Tp2_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    //Text
    class Tp3_Holder extends RecyclerView.ViewHolder {
        //用户头像
        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;


        //用户昵称
        @ViewInject(R.id.tv_neckname)
        private TextView tv_neckname;

        //时间和来源
        @ViewInject(R.id.tv_forminfo)
        private TextView tv_forminfo;

        //阅读量
        @ViewInject(R.id.tv_read)
        private TextView tv_read;

        //标题
        @ViewInject(R.id.tv_content)
        private TextView tv_content;

        //分享
        @ViewInject(R.id.rl_share)
        private RelativeLayout rl_share;

        //点击评论
        @ViewInject(R.id.rl_comment)
        private RelativeLayout rl_comment;

        //评论数量
        @ViewInject(R.id.tv_session)
        private TextView tv_session;

        //点赞
        @ViewInject(R.id.iv_favorite3)
        private ImageView iv_favorite3;

        //点赞数量
        @ViewInject(R.id.tv_favorite)
        private TextView tv_favorite;


        public Tp3_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
