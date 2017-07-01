package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonViewHolder;

/**
 * Created by 911 on 2017/6/27.
 */
public class FtCatgoryAadapter extends BaseAdapter {
    private Context mContext;

    public FtCatgoryAadapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
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

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_ft_catgory, null);
        TextView tv_comment = cvh.getTv(R.id.tv_comment_person);


        //模拟获取数据
        String commentName = "陈先森";

        SpannableString sp = new SpannableString(commentName+"等人");

        //高亮点击监听
//        sp.setSpan(new MyClickableSpan("123"), 0, commentName.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#A02F2D")),0,commentName.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //SpannableString对象设置给TextView
        tv_comment.setText(sp);

        //设置TextView可点击
        tv_comment.setMovementMethod(LinkMovementMethod.getInstance());

        return cvh.convertView;
    }
}
