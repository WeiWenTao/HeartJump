package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.user.XbRecord;

import java.util.List;

/**
 * Created by cucr on 2018/4/8.
 */

public class TxRecordAdapter extends RecyclerView.Adapter<TxRecordAdapter.TxHolder> {

    private List<XbRecord.RowsBean> rows;

    public void setData(List<XbRecord.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void addData(List<XbRecord.RowsBean> rows) {
        notifyItemInserted(this.rows.size() + 1);
        this.rows.addAll(rows);
    }

    @Override
    public TxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_calendar, parent, false);
        return new TxHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TxHolder holder, int position) {
        XbRecord.RowsBean rowsBean = rows.get(position);
        holder.tv_date.setText(rowsBean.getConsumptionTime());
        holder.tv_money.setText("-" + rowsBean.getConsumptionAmount());

    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class TxHolder extends RecyclerView.ViewHolder {

        private TextView tv_date;
        private TextView tv_money;

        public TxHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }
}