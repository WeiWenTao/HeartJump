package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.dongtai.DongTaiCatgoryActivity;
import com.cucr.myapplication.activity.dongtai.PersonalMainPagerActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.widget.text.MyClickableSpan;

/**
 * Created by 911 on 2017/7/17.
 */
public class RlvDongTaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mLayoutInflater;
    private Context context;

    public RlvDongTaiAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == Constans.TYPE_ONE) {
            return new Item1ViewHolder(mLayoutInflater.inflate(R.layout.rlv_item_dongtai_one, parent, false));
        } else {
            return new Item2ViewHolder(mLayoutInflater.inflate(R.layout.rlv_item_dongtai_two, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //如果是第一种条目
        if (holder instanceof Item1ViewHolder) {

            //模拟获取数据
            String who = "胡歌";

            SpannableString sp = new SpannableString("来自" + who + "粉团");

            //设置高亮样式二
            sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 2, 2 + who.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            //设置点击
            MyClickableSpan mySpan = new MyClickableSpan(who);
            sp.setSpan(mySpan, 2, 2 + who.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            //SpannableString对象设置给TextView
            ((Item1ViewHolder) holder).tv_from.setText(sp);
            //设置TextView可点击
            ((Item1ViewHolder) holder).tv_from.setMovementMethod(LinkMovementMethod.getInstance());

            //设置点击监听
            ((Item1ViewHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DongTaiCatgoryActivity.class);
                    intent.putExtra(Constans.POSITION, position);
                    context.startActivity(intent);
                }
            });

            ((Item1ViewHolder) holder).iv_pic.setOnClickListener(this);
            ((Item1ViewHolder) holder).tv_nickName.setOnClickListener(this);

            //如果是第二种条目
        } else if (holder instanceof Item2ViewHolder) {

        }

    }


    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_pic:
            case R.id.tv_nickname:
                context.startActivity(new Intent(context, PersonalMainPagerActivity.class));
        }
    }


    //item1 的ViewHolder
    public static class Item1ViewHolder extends RecyclerView.ViewHolder {
        //哪个明星
        TextView tv_from;
        //内容
        TextView tv_content;
        //明星姓名
        TextView tv_nickName;
        //明星头像
        ImageView iv_pic;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            tv_nickName = (TextView) itemView.findViewById(R.id.tv_nickname);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }


    //item2 的ViewHolder
    public static class Item2ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public Item2ViewHolder(View itemView) {
            super(itemView);
//            mTextView=(TextView)itemView.findViewById(R.id.tv_item2_text);
        }
    }
}
