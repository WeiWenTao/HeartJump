package com.cucr.myapplication.adapter.LvAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.user.XbRecord;
import com.cucr.myapplication.utils.CommonViewHolder;

import java.util.List;

/**
 * Created by 911 on 2017/4/25.
 */
public class TxRecordAdapter extends BaseAdapter {

    private List<XbRecord.RowsBean> rows;

    public void setDate(List<XbRecord.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
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
        XbRecord.RowsBean rowsBean = rows.get(position);
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_expense_calendar, null);
        TextView date = cvh.getTv(R.id.tv_date);
        TextView money = cvh.getTv(R.id.tv_money);
        date.setText(rowsBean.getConsumptionTime());
        money.setText("-" + rowsBean.getConsumptionAmount());
        return cvh.convertView;
    }
}
