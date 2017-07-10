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
 * Created by 911 on 2017/7/6.
 */
public class CreatFtAdapter extends BaseAdapter {
    private Context context;
    private TextView mTv_like_num;

    public CreatFtAdapter(Context context) {
        this.context = context;
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
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, context, R.layout.item_creat_ft, null);

        mTv_like_num = cvh.getTv(R.id.tv_like_num);
        initView();

        return cvh.convertView;
    }

    private void initView() {
        //模拟获取数据
        String likeNum = "12";

        SpannableString sp = new SpannableString(likeNum+"人喜欢");

        //设置高亮样式
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 0, likeNum.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //SpannableString对象设置给TextView
        mTv_like_num.setText(sp);
        //设置TextView可点击
        mTv_like_num.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
