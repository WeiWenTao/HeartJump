package com.cucr.myapplication.adapter.RlVAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.ImagePagerActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
import com.cucr.myapplication.widget.picture.FlowImageLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by 911 on 2017/6/27.
 */
public class FtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final LayoutInflater mLayoutInflater;
    private QueryFtInfos mQueryFtInfos;
    private List<QueryFtInfos.RowsBean> rows;
    private Intent mIntent;
    private Activity activity;

    public FtAdapter(Activity activity) {
        this.activity = activity;
        this.context = MyApplication.getInstance();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(QueryFtInfos mQueryFtInfos) {
        this.mQueryFtInfos = mQueryFtInfos;
        rows = mQueryFtInfos.getRows();
        notifyDataSetChanged();
    }

    public void addData(List<QueryFtInfos.RowsBean> newData) {
        if (newData != null && newData.size() > 0) {
            notifyItemInserted(this.rows.size() + 1);
            this.rows.addAll(newData);
        }
    }

    public void delData(int position) {
        this.rows.remove(position);
        notifyItemRemoved(position);
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
        QueryFtInfos.RowsBean rowsBean = rows.get(position);
        return rowsBean.getType();
    }

    private Integer giveNum;

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final QueryFtInfos.RowsBean rowsBean = rows.get(position);
        boolean isStar = rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR;
// -------------------------------------------------------------------------------------------------
        if (holder instanceof Tp1_Holder) {  //视频

            //头像
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUserHeadPortrait(), ((Tp1_Holder) holder).iv_pic, MyApplication.getImageLoaderOptions());
            //视频封面
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(0).getVideoPagePic(), ((Tp1_Holder) holder).iv_video_pic);
            ((Tp1_Holder) holder).iv_tag.setVisibility(isStar ? View.VISIBLE : View.INVISIBLE);
            ((Tp1_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp1_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp1_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            if (rowsBean.isIsGiveUp()) {
                ((Tp1_Holder) holder).iv_favorite3.setChecked(true, false);
            } else {
                ((Tp1_Holder) holder).iv_favorite3.setChecked(false, false);
                ((Tp1_Holder) holder).iv_favorite3.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_above));
            }

            if (TextUtils.isEmpty(rowsBean.getContent())) {                 //文字内容
                ((Tp1_Holder) holder).tv_content.setVisibility(View.GONE);
            } else {
                // TODO: 2018/6/13 编码
                ((Tp1_Holder) holder).tv_content.setText(CommonUtils.unicode2String(rowsBean.getContent()));
            }

            ((Tp1_Holder) holder).tv_dashang.setText(rowsBean.getDssl() + "人打赏了道具");
            if (TextUtils.isEmpty(rowsBean.getContent())) {
                ((Tp1_Holder) holder).tv_content.setVisibility(View.GONE);
            } else {
                ((Tp1_Holder) holder).tv_content.setVisibility(View.VISIBLE);
            }
            ((Tp1_Holder) holder).tv_time.setText(CommonUtils.secToTime(rowsBean.getAttrFileList().get(0).getTimeCount()));    //时长
            ((Tp1_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp1_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量

            //点击跳转视频界面
            ((Tp1_Holder) holder).rl_goto_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickVideoCommends(position, rowsBean, false, false);
                    }
                }
            });

            //点击分享
            ((Tp1_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickshare(rowsBean, rowsBean.getAttrFileList().get(0).getVideoPagePic());
                    }
                }
            });

            //点击评论
            ((Tp1_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickVideoCommends(position, rowsBean, false, true);
                    }
                }
            });

            //点赞
            ((Tp1_Holder) holder).ll_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickGoods(position, rowsBean, ((Tp1_Holder) holder).iv_favorite3);
                    }
                }
            });

            //点赞爱心
            ((Tp1_Holder) holder).iv_favorite3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickGoods(position, rowsBean, ((Tp1_Holder) holder).iv_favorite3);
                    }
                }
            });

            //点击条目
            ((Tp1_Holder) holder).ll_type1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickVideoCommends(position, rowsBean, false, false);
                    }
                }
            });

            //点击打赏
            ((Tp1_Holder) holder).iv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDaShang(rowsBean.getId(), position);
                    }
                }
            });

            //打赏列表
            ((Tp1_Holder) holder).tv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDsRecored(rowsBean.getId());
                    }
                }
            });

            //点击头像
            ((Tp1_Holder) holder).iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId(), rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR);
                    }
                }
            });
            //点击昵称
            ((Tp1_Holder) holder).tv_neckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId(), rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR);
                    }
                }
            });

            ((Tp1_Holder) holder).iv_ds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDsRecored(rowsBean.getId());
                    }
                }
            });
// -------------------------------------------------------------------------------------------------
        } else if (holder instanceof Tp2_Holder) {
            //图片
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUserHeadPortrait(), ((Tp2_Holder) holder).iv_pic, MyApplication.getImageLoaderOptions());     //头像
            ((Tp2_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp2_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp2_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量

            if (rowsBean.isIsGiveUp()) {
                ((Tp2_Holder) holder).iv_favorite3.setChecked(true, false);
            } else {
                ((Tp2_Holder) holder).iv_favorite3.setChecked(false, false);
                ((Tp2_Holder) holder).iv_favorite3.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_above));
            }

            ((Tp2_Holder) holder).iv_tag.setVisibility(isStar ? View.VISIBLE : View.INVISIBLE);
            ((Tp2_Holder) holder).image_layout.loadImage(rowsBean.getAttrFileList().size(), new FlowImageLayout.OnImageLayoutFinishListener() {
                @Override
                public void layoutFinish(List<GifImageView> images) {
                    for (int i = 0; i < rowsBean.getAttrFileList().size(); i++) {
                        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(i).getFileUrl(), images.get(i), MyApplication.getImageLoaderOptions());
                    }
                }
            });

            ((Tp2_Holder) holder).image_layout.setHorizontalSpacing(3);
            ((Tp2_Holder) holder).image_layout.setVerticalSpacing(3);
            ((Tp2_Holder) holder).image_layout.setSingleImageSize(640, 400);

            ((Tp2_Holder) holder).tv_content.setText(CommonUtils.unicode2String(rowsBean.getContent()));    //文字内容
            ((Tp2_Holder) holder).tv_dashang.setText(rowsBean.getDssl() + "人打赏了道具");
            MyLogger.jLog().i("position:" + position + "ISGIVEUP_GETVIEW:" + rowsBean.isIsGiveUp());
            if (TextUtils.isEmpty(rowsBean.getContent())) {
                ((Tp2_Holder) holder).tv_content.setVisibility(View.GONE);
            } else {
                ((Tp2_Holder) holder).tv_content.setVisibility(View.VISIBLE);
            }
            ((Tp2_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp2_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量

            ((Tp2_Holder) holder).image_layout.setOnItemClick(new FlowImageLayout.OnItemClick() {
                @Override
                public void onItemClickListener(View view, int position) {
                    mIntent = new Intent(context, ImagePagerActivity.class);
                    // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                    List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList = rowsBean.getAttrFileList();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("imgs", (Serializable) attrFileList);//序列化,要注意转化(Serializable)
                    bundle.putBoolean("formCatgory", false);//序列化,要注意转化(Serializable)
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mIntent.putExtras(bundle);//发送数据
                    mIntent.putExtra("position", position);
                    context.startActivity(mIntent);
                }
            });
            //点击分享
            ((Tp2_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickshare(rowsBean, rowsBean.getAttrFileList().get(0).getFileUrl());
                    }
                }
            });

            //点击评论
            ((Tp2_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickCommends(position, rowsBean, true, true);
                    }
                }
            });

            //点赞
            ((Tp2_Holder) holder).ll_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickGoods(position, rowsBean, ((Tp2_Holder) holder).iv_favorite3);
                    }
                }
            });

            ((Tp2_Holder) holder).iv_favorite3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
//                        if (rowsBean.isIsGiveUp()) {
//                            ((Tp2_Holder) holder).iv_favorite3.setChecked(false, true);
//                            ((Tp2_Holder) holder).iv_favorite3.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));
//                        } else {
//                            ((Tp2_Holder) holder).iv_favorite3.setChecked(true, true);
//                        }
//
//                        rowsBean.setIsGiveUp(!rowsBean.isIsGiveUp());
//
//                        if (rowsBean.isIsGiveUp()) {
//                            giveNum = rowsBean.getGiveUpCount() - 1;
//                            rowsBean.setGiveUpCount(giveNum);
//                        } else {
//                            giveNum = rowsBean.getGiveUpCount() + 1;
//                            rowsBean.setGiveUpCount(giveNum);
//                        }
//                        ((Tp2_Holder) holder).tv_favorite.setText(giveNum + "");
                        mOnClickBt.onClickGoods(position, rowsBean, ((Tp2_Holder) holder).iv_favorite3);
                    }
                }
            });

            //点击条目
            ((Tp2_Holder) holder).ll_type2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickCommends(position, rowsBean, true, false);
                    }
                }
            });

            //点击打赏
            ((Tp2_Holder) holder).iv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDaShang(rowsBean.getId(), position);
                    }
                }
            });

            //打赏列表
            ((Tp2_Holder) holder).tv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDsRecored(rowsBean.getId());
                    }
                }
            });

            //点击头像
            ((Tp2_Holder) holder).iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId(), rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR);
                    }
                }
            });
            //点击昵称
            ((Tp2_Holder) holder).tv_neckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId(), rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR);
                    }
                }
            });

            ((Tp2_Holder) holder).iv_ds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDsRecored(rowsBean.getId());
                    }
                }
            });
// -------------------------------------------------------------------------------------------------
        } else { //文字
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUserHeadPortrait(), ((Tp3_Holder) holder).iv_pic, MyApplication.getImageLoaderOptions());     //头像
            ((Tp3_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp3_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp3_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            ((Tp3_Holder) holder).tv_content.setText(CommonUtils.unicode2String(rowsBean.getContent()));    //文字内容
            ((Tp3_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp3_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量
            if (rowsBean.isIsGiveUp()) {
                ((Tp3_Holder) holder).iv_favorite3.setChecked(true, false);
            } else {
                ((Tp3_Holder) holder).iv_favorite3.setChecked(false, false);
                ((Tp3_Holder) holder).iv_favorite3.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_above));
            }
            ((Tp3_Holder) holder).tv_dashang.setText(rowsBean.getDssl() + "人打赏了道具");
            ((Tp3_Holder) holder).iv_tag.setVisibility(isStar ? View.VISIBLE : View.INVISIBLE);
            //点击分享
            ((Tp3_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickshare(rowsBean, HttpContans.LOGO_ADDRESS);
                    }
                }
            });

            //点击评论
            ((Tp3_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickCommends(position, rowsBean, false, true);
                    }
                }
            });

            //点赞
            ((Tp3_Holder) holder).ll_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickGoods(position, rowsBean, ((Tp3_Holder) holder).iv_favorite3);
                    }
                }
            });

            //点赞爱心
            ((Tp3_Holder) holder).iv_favorite3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickGoods(position, rowsBean, ((Tp3_Holder) holder).iv_favorite3);
                    }
                }
            });

            //点击条目
            ((Tp3_Holder) holder).ll_type3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickCommends(position, rowsBean, false, false);
                    }
                }
            });

            //点击打赏
            ((Tp3_Holder) holder).iv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDaShang(rowsBean.getId(), position);
                    }
                }
            });

            //打赏列表
            ((Tp3_Holder) holder).tv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDsRecored(rowsBean.getId());
                    }
                }
            });

            //点击头像
            ((Tp3_Holder) holder).iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId(), rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR);
                    }
                }
            });
            //点击昵称
            ((Tp3_Holder) holder).tv_neckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        boolean b = rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR;
                        MyLogger.jLog().i("isstar:" + b + ",rolaid:" + rowsBean.getCreateUserRoleId());
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId(), rowsBean.getCreateUserRoleId() == Constans.STATUS_STAR);
                    }
                }
            });

            ((Tp3_Holder) holder).iv_ds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDsRecored(rowsBean.getId());
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mQueryFtInfos == null || rows == null || rows.size() == 0) {
            return 0;
        }
        return rows.size();
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
        private LinearLayout rl_comment;

        //评论数量
        @ViewInject(R.id.tv_session)
        private TextView tv_session;

        //点赞
        @ViewInject(R.id.iv_favorite3)
        private ShineButton iv_favorite3;

        //点赞数量
        @ViewInject(R.id.tv_favorite)
        private TextView tv_favorite;

        //点击播放视频
        @ViewInject(R.id.rl_goto_video)
        private RelativeLayout rl_goto_video;

        //点击条目
        @ViewInject(R.id.ll_type1)
        private LinearLayout ll_type1;

        //点赞区域
        @ViewInject(R.id.ll_good)
        private LinearLayout ll_good;

        //打赏人数
        @ViewInject(R.id.tv_dashang)
        private TextView tv_dashang;

        //打赏按钮
        @ViewInject(R.id.iv_dashang)
        private ImageView iv_dashang;

        //明星标识
        @ViewInject(R.id.iv_tag)
        private ImageView iv_tag;

        //打赏记录
        @ViewInject(R.id.iv_ds)
        private ImageView iv_ds;

        public Tp1_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            iv_favorite3.init(activity);
        }
    }

    //Picture
    class Tp2_Holder extends RecyclerView.ViewHolder {
        //用户头像
        @ViewInject(R.id.iv_pic)
        private ImageView iv_pic;

        //图片列表
        @ViewInject(R.id.image_layout)
        private FlowImageLayout image_layout;

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
        private LinearLayout rl_comment;

        //评论数量
        @ViewInject(R.id.tv_session)
        private TextView tv_session;

        //点赞
        @ViewInject(R.id.iv_favorite3)
        private ShineButton iv_favorite3;

        //点赞数量
        @ViewInject(R.id.tv_favorite)
        private TextView tv_favorite;

        //点击条目
        @ViewInject(R.id.ll_type2)
        private LinearLayout ll_type2;

        //点赞区域
        @ViewInject(R.id.ll_good)
        private LinearLayout ll_good;

        //打赏人数
        @ViewInject(R.id.tv_dashang)
        private TextView tv_dashang;

        //打赏按钮
        @ViewInject(R.id.iv_dashang)
        private ImageView iv_dashang;

        //明星标识
        @ViewInject(R.id.iv_tag)
        private ImageView iv_tag;

        //打赏记录
        @ViewInject(R.id.iv_ds)
        private ImageView iv_ds;

        public Tp2_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            iv_favorite3.init(activity);
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
        private LinearLayout rl_comment;

        //评论数量
        @ViewInject(R.id.tv_session)
        private TextView tv_session;

        //点赞
        @ViewInject(R.id.iv_favorite3)
        private ShineButton iv_favorite3;

        //点赞数量
        @ViewInject(R.id.tv_favorite)
        private TextView tv_favorite;

        //点击条目
        @ViewInject(R.id.ll_type3)
        private LinearLayout ll_type3;

        //打赏人数
        @ViewInject(R.id.tv_dashang)
        private TextView tv_dashang;

        //打赏按钮
        @ViewInject(R.id.iv_dashang)
        private ImageView iv_dashang;

        //点赞区域
        @ViewInject(R.id.ll_good)
        private LinearLayout ll_good;

        //明星标识
        @ViewInject(R.id.iv_tag)
        private ImageView iv_tag;

        //打赏记录
        @ViewInject(R.id.iv_ds)
        private ImageView iv_ds;

        public Tp3_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
            iv_favorite3.init(activity);
        }
    }

    private OnClickBt mOnClickBt;

    public void setOnClickBt(OnClickBt onClickBt) {
        mOnClickBt = onClickBt;
    }

    public interface OnClickBt {

        void onClickGoods(int position, QueryFtInfos.RowsBean rowsBean, ShineButton sib);

        void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean formCommond);

        void onClickshare(QueryFtInfos.RowsBean rowsBean, String imgUrl);

        void onClickDaShang(int contentId, int position);

        void onClickDsRecored(int contentId);

        void onClickUser(int userId, boolean isStar);

        void onClickVideoCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean formCommond);

    }
}
