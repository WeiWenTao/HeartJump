package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.starJourney.StarJourneyList;

import java.util.List;

/**
 * Created by 911 on 2017/6/27.
 */
public class RlvPersonalJourneyAdapter extends RecyclerView.Adapter<RlvPersonalJourneyAdapter.PersonaJourneylHolder> {

    private Context context;
    private List<StarJourneyList.RowsBean> mRows;

    public void setData(List<StarJourneyList.RowsBean> mRows){
        this.mRows = mRows;
        notifyDataSetChanged();
    }

    public RlvPersonalJourneyAdapter(Context context,List<StarJourneyList.RowsBean> mRows) {
        this.context = context;
        this.mRows = mRows;

    }

    @Override
    public PersonaJourneylHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rlv_journey, parent, false);
        PersonaJourneylHolder holder = new PersonaJourneylHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonaJourneylHolder holder, int position) {
        StarJourneyList.RowsBean rowsBean = mRows.get(position);
        holder.tv_journey_catgory.setText(rowsBean.getTitle());
        holder.tv_location.setText(rowsBean.getPlace());
        holder.tv_journey_date.setText(rowsBean.getTripTime().substring(0,10));
    }

    @Override
    public int getItemCount() {
        return mRows == null ? 0 : mRows.size();
    }


    class PersonaJourneylHolder extends RecyclerView.ViewHolder {
        //时间
        TextView tv_journey_date;

        //内容
        TextView tv_journey_catgory;

        //地址
        TextView tv_location;

        //圆点图片
        ImageView iv_drop;


        public PersonaJourneylHolder(View itemView) {
            super(itemView);
            tv_journey_date = (TextView) itemView.findViewById(R.id.tv_journey_date);
            tv_journey_catgory = (TextView) itemView.findViewById(R.id.tv_journey_catgory);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);
            iv_drop = (ImageView) itemView.findViewById(R.id.iv_drop);
        }
    }
}
