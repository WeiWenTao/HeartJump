package com.cucr.myapplication.widget.swipeRlv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.ToastUtils;

/**
 * Created by cucr on 2017/9/7.
 */
 public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.SimpleViewHolder> {

    private ItemTouchListener mItemTouchListener;


//    DemoAdapter( ItemTouchListener itemTouchListener) {
//        this.mData = data;
//        this.mItemTouchListener = itemTouchListener;
//    }



    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_menu, parent, false);
        return new SimpleViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        holder.mContent.setText("行程内容行程内容行程内容行程内容行程内容行程内容" + position);
        holder.mSwipeItemLayout.setSwipeEnable(true);
//        if (mItemTouchListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mItemTouchListener.onItemClick(holder.mContent.getText().toString());
                    ToastUtils.showToast(v.getContext(),"条目");
                }
            });


            if (holder.mRightMenu != null) {
                holder.mRightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(v.getContext(),"菜单");
//                        mItemTouchListener.onRightMenuClick("right " + holder.getAdapterPosition());
                        holder.mSwipeItemLayout.close();
                    }
                });
//            }
        }
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private final View mRightMenu;
        private final TextView mContent;
        private final SwipeItemLayout mSwipeItemLayout;

        SimpleViewHolder(View itemView) {
            super(itemView);
            mSwipeItemLayout = (SwipeItemLayout) itemView.findViewById(R.id.swipe_layout);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
            mRightMenu = itemView.findViewById(R.id.right_menu);
        }
    }

}
