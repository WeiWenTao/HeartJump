package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.starList.MyFocusStarInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/7/19. R.layout.item_rlv_his_star
 */
public class RLVStarAdapter extends RecyclerView.Adapter<RLVStarAdapter.StarListHolder> {

    private Context mContext;
    private List<MyFocusStarInfo.RowsBean> list;

    @Override
    public StarListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = MyApplication.getInstance();
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_rlv_his_star, parent, false);
        return new StarListHolder(rootView);
    }

    public void setData(List<MyFocusStarInfo.RowsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<MyFocusStarInfo.RowsBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(StarListHolder holder, final int position) {
        final MyFocusStarInfo.RowsBean rowsBean = list.get(position);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST +      //头像
                rowsBean.getStart().getUserHeadPortrait(), holder.starPic, MyApplication.getImageLoaderOptions());
        holder.starName.setText(rowsBean.getStart().getRealName());   //name
        holder.ll_item_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick(rowsBean.getStart().getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class StarListHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_star_head)
        private ImageView starPic;

        @ViewInject(R.id.tv_star_name)
        private TextView starName;

        @ViewInject(R.id.ll_item_star)
        private LinearLayout ll_item_star;


        public StarListHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(int starId);
    }
}
