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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.ImagePagerActivity;
import com.cucr.myapplication.activity.video.VideoActivity;
import com.cucr.myapplication.adapter.GvAdapter.GridAdapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
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
    private List<QueryFtInfos.RowsBean> rows;

    public FtAdapter() {
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

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final QueryFtInfos.RowsBean rowsBean = rows.get(position);
// -------------------------------------------------------------------------------------------------
        if (holder instanceof Tp1_Holder) {  //视频

            //跳转信息
            final Intent intent = new Intent(context, VideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("rowsBean", rowsBean);//序列化,要注意转化(Serializable)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);//发送数据

            //头像
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUserHeadPortrait(), ((Tp1_Holder) holder).iv_pic, MyApplication.getImageLoaderOptions());
            //视频封面
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(0).getVideoPagePic(), ((Tp1_Holder) holder).iv_video_pic);

            ((Tp1_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp1_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp1_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            if (TextUtils.isEmpty(rowsBean.getContent())) {                 //文字内容
                ((Tp1_Holder) holder).tv_content.setVisibility(View.GONE);
            }else {
                ((Tp1_Holder) holder).tv_content.setText(rowsBean.getContent());
            }
            ((Tp1_Holder) holder).iv_favorite3.setImageResource(rowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
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
                    context.startActivity(intent);
                }
            });

            //点击分享
            ((Tp1_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickshare(position);
                    }
                }
            });

            //点击评论
            ((Tp1_Holder) holder).rl_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(intent);
                }
            });

            //点赞
            ((Tp1_Holder) holder).ll_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    ((Tp1_Holder) holder).iv_favorite3.setImageResource(R.drawable.icon_good_sel);
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickGoods(position, rowsBean);
                    }
                }
            });

            //点击条目
            ((Tp1_Holder) holder).ll_type1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(intent);
                }
            });

            //点击打赏
            ((Tp1_Holder) holder).iv_dashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickDaShang(rowsBean.getId(),position);
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
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId());
                    }
                }
            });
            //点击昵称
            ((Tp1_Holder) holder).tv_neckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId());
                    }
                }
            });
// -------------------------------------------------------------------------------------------------
        } else if (holder instanceof Tp2_Holder) {
            //图片
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUserHeadPortrait(), ((Tp2_Holder) holder).iv_pic, MyApplication.getImageLoaderOptions());     //头像
            ((Tp2_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp2_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp2_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            ((Tp2_Holder) holder).gridview.setAdapter(new GridAdapter(context, rowsBean.getAttrFileList()));  //图片列表
            ((Tp2_Holder) holder).tv_content.setText(rowsBean.getContent());    //文字内容
            ((Tp2_Holder) holder).iv_favorite3.setImageResource(rowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
            ((Tp2_Holder) holder).tv_dashang.setText(rowsBean.getDssl() + "人打赏了道具");
            MyLogger.jLog().i("position:" + position + "ISGIVEUP_GETVIEW:" + rowsBean.isIsGiveUp());
            if (TextUtils.isEmpty(rowsBean.getContent())) {
                ((Tp2_Holder) holder).tv_content.setVisibility(View.GONE);
            } else {
                ((Tp2_Holder) holder).tv_content.setVisibility(View.VISIBLE);
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
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);//发送数据
                    intent.putExtra("position", position);
                    context.startActivity(intent);

                }
            });
            //点击分享
            ((Tp2_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickshare(position);
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
                        mOnClickBt.onClickGoods(position, rowsBean);
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
                        mOnClickBt.onClickDaShang(rowsBean.getId(),position);
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
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId());
                    }
                }
            });
            //点击昵称
            ((Tp2_Holder) holder).tv_neckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId());
                    }
                }
            });
// -------------------------------------------------------------------------------------------------
        } else { //文字
            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUserHeadPortrait(), ((Tp3_Holder) holder).iv_pic, MyApplication.getImageLoaderOptions());     //头像
            ((Tp3_Holder) holder).tv_neckname.setText(rowsBean.getCreateUserName());    //昵称
            ((Tp3_Holder) holder).tv_forminfo.setText(rowsBean.getCreaetTime());    //时间和来源
            ((Tp3_Holder) holder).tv_read.setText(rowsBean.getReadCount() + "");    //阅读量
            ((Tp3_Holder) holder).tv_content.setText(rowsBean.getContent());    //文字内容
            ((Tp3_Holder) holder).tv_session.setText(rowsBean.getCommentCount() + "");    //评论数量
            ((Tp3_Holder) holder).tv_favorite.setText(rowsBean.getGiveUpCount() + "");    //点赞数量
            ((Tp3_Holder) holder).iv_favorite3.setImageResource(rowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
            ((Tp3_Holder) holder).tv_dashang.setText(rowsBean.getDssl() + "人打赏了道具");
            //点击分享
            ((Tp3_Holder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickshare(position);
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
                        mOnClickBt.onClickGoods(position, rowsBean);
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
                        mOnClickBt.onClickDaShang(rowsBean.getId(),position);
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
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId());
                    }
                }
            });
            //点击昵称
            ((Tp3_Holder) holder).tv_neckname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickBt != null) {
                        mOnClickBt.onClickUser(rowsBean.getCreateUserId());
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

        //点击条目
        @ViewInject(R.id.ll_type1)
        private LinearLayout ll_type1;

        //点赞热力区域
        @ViewInject(R.id.ll_good)
        private LinearLayout ll_good;


        //打赏人数
        @ViewInject(R.id.tv_dashang)
        private TextView tv_dashang;

        //打赏按钮
        @ViewInject(R.id.iv_dashang)
        private ImageView iv_dashang;

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

        public Tp3_Holder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnClickBt mOnClickBt;

    public void setOnClickBt(OnClickBt onClickBt) {
        mOnClickBt = onClickBt;
    }

    public interface OnClickBt {
        void onClickGoods(int position, QueryFtInfos.RowsBean rowsBean);

        void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean formCommond);

        void onClickshare(int position);

        void onClickDaShang(int contentId,int position);

        void onClickDsRecored(int contentId);

        void onClickUser(int userId);

    }
}
