package com.cucr.myapplication.widget.swipeRlv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.model.starJourney.StarJourneyList;

import java.util.List;

/**
 * Created by cucr on 2017/9/7.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.SimpleViewHolder> {

    private ItemTouchListener mItemTouchListener;
    private List<StarJourneyList.RowsBean> rows;

    public void setItemTouchListener(ItemTouchListener itemTouchListener) {
        mItemTouchListener = itemTouchListener;
    }

    public void setData(List<StarJourneyList.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<StarJourneyList.RowsBean> rows) {
        this.rows.addAll(rows);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_menu, parent, false);
        return new SimpleViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        StarJourneyList.RowsBean rowsBean = rows.get(position);
        holder.mContent.setText(rowsBean.getTitle());
        holder.tv_journey_date.setText(rowsBean.getTripTime().substring(0,10));
        holder.tv_journey_local.setText(rowsBean.getPlace());
        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mItemTouchListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTouchListener.onItemClcik(v, position);
                }
            });

            if (holder.mRightMenu != null) {
                holder.mRightMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemTouchListener.onClcikDelete(v, position);
                        holder.mSwipeItemLayout.close();
                    }
                });
            }
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        private View mRightMenu;
        private TextView mContent;
        private TextView tv_journey_date;
        private TextView tv_journey_local;
        private SwipeItemLayout mSwipeItemLayout;

        SimpleViewHolder(View itemView) {
            super(itemView);
            mSwipeItemLayout = (SwipeItemLayout) itemView.findViewById(R.id.swipe_layout);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);
            tv_journey_date = (TextView) itemView.findViewById(R.id.tv_journey_date);
            tv_journey_local = (TextView) itemView.findViewById(R.id.tv_journey_local);
            mRightMenu = itemView.findViewById(R.id.right_menu);
        }
    }

}
