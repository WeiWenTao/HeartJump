package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.starList.MyFocusStarInfo;
import com.cucr.myapplication.utils.MyLogger;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cucr on 2017/9/5.
 */

public class StarListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MyFocusStarInfo.RowsBean> list;
    private int clickPosition;

    public StarListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MyFocusStarInfo.RowsBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setPosition(int clickPosition) {
        this.clickPosition = clickPosition;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_star_list, parent, false);
            MyStarHolder vh = new MyStarHolder(view);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_star_add, parent, false);
            MyAddHolder vh = new MyAddHolder(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyAddHolder) {
            ((MyAddHolder) holder).iv_add_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StarListForAddActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        } else {

            MyFocusStarInfo.RowsBean rowsBean = list.get(position);
            CircleImageView iv_head = ((MyStarHolder) holder).iv_head;

            ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getStartPicUrl(), iv_head, MyApplication.getImageLoaderOptions());

            iv_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClickPosition(v, position);
                }
            });

            if (position == clickPosition) {
                iv_head.setBorderColor(context.getResources().getColor(R.color.pink));
            } else {
                iv_head.setBorderColor(context.getResources().getColor(R.color.white));
            }
        }
    }


    @Override
    public int getItemCount() {
        MyLogger.jLog().i("list:"+list);
        return list == null ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != list.size()) {
            return 1;
        } else {
            return 2;
        }
    }

    public class MyStarHolder extends RecyclerView.ViewHolder {
        private CircleImageView iv_head;

        public MyStarHolder(View itemView) {
            super(itemView);
            iv_head = (CircleImageView) itemView.findViewById(R.id.iv_head);
        }
    }

    public class MyAddHolder extends RecyclerView.ViewHolder {
        private ImageView iv_add_star;

        public MyAddHolder(View itemView) {
            super(itemView);
            iv_add_star = (ImageView) itemView.findViewById(R.id.iv_add_star);
        }
    }

    public interface OnClick {
        void onClickPosition(View view, int position);
    }

    private OnClick mOnClick;

    public void setOnClick(OnClick onClick) {
        mOnClick = onClick;
    }
}
