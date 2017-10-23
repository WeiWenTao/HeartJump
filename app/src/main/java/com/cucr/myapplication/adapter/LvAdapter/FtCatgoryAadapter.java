package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.dongtai.PersonalMainPagerActivity;
import com.cucr.myapplication.activity.fenTuan.FtSecondCommentActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.FtCommentInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.widget.text.MyClickGoHomePager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vanniktech.emoji.EmojiTextView;

import java.util.List;

/**
 * Created by 911 on 2017/6/27.
 */
public class FtCatgoryAadapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;

    private List<FtCommentInfo.RowsBean> rows;

    public void setData(List<FtCommentInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public FtCatgoryAadapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return rows == null ? 0 : rows.size();
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
        final FtCommentInfo.RowsBean mRowsBean = rows.get(position);
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_ft_catgory, null);
        ImageView userHead = cvh.getIv(R.id.iv_userhead);
        ImageView iv_good = cvh.getIv(R.id.iv_good);
        TextView tv_username = cvh.getTv(R.id.tv_username);
        TextView tv_comment_time = cvh.getTv(R.id.tv_comment_time);
        TextView tv_comment = cvh.getTv(R.id.tv_comment_person);
        TextView tv_good_value = cvh.getTv(R.id.tv_good_value);
        EmojiTextView tv_comment_content = cvh.getView(R.id.tv_comment_content, EmojiTextView.class);
        TextView tv_comment_size = cvh.getTv(R.id.tv_comment_size);
        LinearLayout ll_comment_more = cvh.getView(R.id.ll_comment_more, LinearLayout.class);

        //设置点击监听
        userHead.setOnClickListener(this);
        tv_username.setOnClickListener(this);
        tv_comment_time.setOnClickListener(this);
        ll_comment_more.setOnClickListener(this);
        iv_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickGoodsListener.clickGoods(mRowsBean);
            }
        });

        //是否有二级评论
        boolean hasSecondCom = mRowsBean.getChildList().size() > 0;
        ll_comment_more.setVisibility(hasSecondCom ? View.VISIBLE : View.GONE);
        if (hasSecondCom) {
            //获取二级评论的第一个用户
            String commentName = mRowsBean.getChildList().get(0).getUser().getName();

            SpannableString sp = new SpannableString(commentName + "等人");

            //高亮点击监听
            sp.setSpan(new MyClickGoHomePager("123", mContext), 0, commentName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            //设置高亮样式二
            sp.setSpan(new ForegroundColorSpan(Color.parseColor("#A02F2D")), 0, commentName.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            //SpannableString对象设置给TextView
            tv_comment.setText(sp);

            //设置TextView可点击
            tv_comment.setMovementMethod(LinkMovementMethod.getInstance());
            tv_comment_size.setText("共" + mRowsBean.getChildList().size() + "条评论");
        }


        //设置数据
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean.getUser().getUserHeadPortrait(), userHead, MyApplication.getOptions());
        tv_comment_time.setText(mRowsBean.getReleaseTime());
        iv_good.setImageResource(mRowsBean.getIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_good_value.setText(mRowsBean.getGiveUpCount() + "");
        tv_username.setText(mRowsBean.getUser().getName());
        tv_comment_content.setText(CommonUtils.unicode2String(mRowsBean.getComment()));
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

            case R.id.ll_comment_more:
                mContext.startActivity(new Intent(mContext, FtSecondCommentActivity.class));
                break;

        }
    }


    public void setClickGoodsListener(OnClickCommentGoods clickGoodsListener) {
        this.clickGoodsListener = clickGoodsListener;
    }

    private OnClickCommentGoods clickGoodsListener;

    public interface OnClickCommentGoods{
        void clickGoods(FtCommentInfo.RowsBean mRowsBean);
    }
}
