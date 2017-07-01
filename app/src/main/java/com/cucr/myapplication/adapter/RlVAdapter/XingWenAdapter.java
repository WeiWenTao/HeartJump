package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/27.
 */
public class XingWenAdapter extends RecyclerView.Adapter<XingWenAdapter.XinWenHolder> {
    public XingWenAdapter(Context context) {
    }

    @Override
    public XinWenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        XingWenAdapter.XinWenHolder holder = null;
        switch (viewType){
            case 0:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_type1,parent,false);
                holder = new XingWenAdapter.XinWenHolder(view1);
                break;

            case 1:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_type2,parent,false);
                holder = new XingWenAdapter.XinWenHolder(view2);
                break;

            case 2:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_type3,parent,false);
                holder = new XingWenAdapter.XinWenHolder(view3);
                break;

            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_type1,parent,false);
                holder = new XingWenAdapter.XinWenHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(XinWenHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 20;
    }


    @Override
    public int getItemViewType(int position) {
//        if (0 == dataList.get(position).getDataType()) {
//            return TYPE_EDIT;// 编辑框
//        } else if (1 == dataList.get(position).getDataType()) {
//            return TYPE_BUTTON;// 按钮
//        } else if (2 == dataList.get(position).getDataType()) {
//            return TYPE_SPINNER;//下拉列表
//        } else {
//            return 0;
//        }
        return position % 3;

    }

    class XinWenHolder extends RecyclerView.ViewHolder{

        public XinWenHolder(View itemView) {
            super(itemView);
        }
    }
}
