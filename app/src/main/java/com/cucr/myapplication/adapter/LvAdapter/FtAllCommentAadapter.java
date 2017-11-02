package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.dongtai.PersonalMainPagerActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.FtCommentInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vanniktech.emoji.EmojiTextView;

import java.util.List;

/**
 * Created by 911 on 2017/6/27.
 */
public class FtAllCommentAadapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private List<FtCommentInfo.RowsBean> childList;

    public void setData(List<FtCommentInfo.RowsBean> childList) {
        this.childList = childList;
        notifyDataSetChanged();
    }

    public FtAllCommentAadapter(Context context, List<FtCommentInfo.RowsBean> childList) {
        mContext = context;
        this.childList = childList;
    }

    @Override
    public int getCount() {
        return childList == null ? 0 : childList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FtCommentInfo.RowsBean childListBean = childList.get(position);
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_ft_catgory, null);
        ImageView userHead = cvh.getIv(R.id.iv_userhead);
        ImageView iv_good = cvh.getIv(R.id.iv_good);
        TextView tv_username = cvh.getTv(R.id.tv_username);
        TextView tv_comment_time = cvh.getTv(R.id.tv_comment_time);
        TextView tv_good_value = cvh.getTv(R.id.tv_good_value);
        EmojiTextView tv_comment_content = cvh.getView(R.id.tv_comment_content, EmojiTextView.class);
        LinearLayout ll_comment_more = cvh.getView(R.id.ll_comment_more, LinearLayout.class);
        cvh.getView(R.id.ll_item, LinearLayout.class).setBackgroundColor(Color.parseColor("#f6f6f6"));


        //设置点击监听
        userHead.setOnClickListener(this);
        tv_username.setOnClickListener(this);
        tv_comment_time.setOnClickListener(this);

        iv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickGoodsListener != null) {
                    clickGoodsListener.clickGoods(childListBean);
                }
            }
        });

        //隐藏二级评论
        ll_comment_more.setVisibility(View.GONE);



        //设置数据
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + childListBean.getUser().getUserHeadPortrait(), userHead, MyApplication.getImageLoaderOptions());
        tv_comment_time.setText(childListBean.getReleaseTime());
        iv_good.setImageResource(childListBean.getIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_good_value.setText(childListBean.getGiveUpCount() + "");
        tv_username.setText(childListBean.getUser().getName());
        tv_comment_content.setText(CommonUtils.unicode2String(childListBean.getComment()));
        return cvh.convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击这三个都跳转用户主页
            case R.id.tv_comment_time:
            case R.id.tv_username:
            case R.id.iv_userhead:
                mContext.startActivity(new Intent(mContext, PersonalMainPagerActivity.class));
                break;
        }
    }


    public void setClickGoodsListener(OnClickCommentGoods clickGoodsListener) {
        this.clickGoodsListener = clickGoodsListener;
    }

    private OnClickCommentGoods clickGoodsListener;

    public interface OnClickCommentGoods {
        void clickGoods(FtCommentInfo.RowsBean childListBean);
    }
}
