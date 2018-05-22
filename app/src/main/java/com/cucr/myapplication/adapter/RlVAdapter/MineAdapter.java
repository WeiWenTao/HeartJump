package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.setting.MineInfo;
import com.cucr.myapplication.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2018/5/17.
 */

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.MyHolder> {

    private List<MineInfo> datas;

    public void getDatas() {
        datas = new ArrayList<>();
        datas.add(new MineInfo(R.drawable.icon_sixin, "我的私信", 1));
        datas.add(new MineInfo(R.drawable.m_rz, "我的认证", 2));
        datas.add(new MineInfo(R.drawable.m_cz, "充值中心", 3));
        datas.add(new MineInfo(R.drawable.icon_mypic, "我的图集", 4));

        datas.add(new MineInfo(R.drawable.m_invate, "邀请有礼", 5));
        datas.add(new MineInfo(R.drawable.icon_learning, "新手教程", 6));
        datas.add(new MineInfo(R.drawable.m_pw, "福利票务", 7));
        datas.add(new MineInfo(R.drawable.icon_pack, "礼物背包", 8));

        if (CommonUtils.isStar()) {         //明星
            datas.add(new MineInfo(R.drawable.m_xc, "我的行程", 9));
            datas.add(new MineInfo(R.drawable.m_requires, "我的要求", 10));

        } else if (CommonUtils.isQiYe()) {  //企业
            datas.add(new MineInfo(R.drawable.m_appoinment, "我的预约", 11));
            datas.add(new MineInfo(R.drawable.m_huodong, "我的活动", 12));

        }
        datas.add(new MineInfo(R.drawable.m_set, "设置", 13));
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine, parent, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final MineInfo mineInfo = datas.get(position);
        holder.iv_icon.setImageResource(mineInfo.getIcon());
        holder.tv_catgory.setText(mineInfo.getCatgory());
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItems != null) {
                    mOnClickItems.clickItem(mineInfo.getFlag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_catgory;
        private LinearLayout ll_item;

        public MyHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_catgory = (TextView) itemView.findViewById(R.id.tv_catgory);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    public interface OnClickItems {
        void clickItem(int flag);
    }

    private OnClickItems mOnClickItems;

    public void setOnClickItems(OnClickItems onClickItems) {
        mOnClickItems = onClickItems;
    }
}
