package com.cucr.myapplication.adapter.PagerAdapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.DsDuiHuanActivity;
import com.cucr.myapplication.activity.pay.PayCenterActivity_new;
import com.cucr.myapplication.adapter.RlVAdapter.DaShangBackpackAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.DaShangGiftAdapter;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.bean.fenTuan.FtGiftsInfo;

/**
 * Created by 911 on 2017/5/19.
 * <p>
 * 粉团页面的pagerAdapter
 */

public class DaShangPagerAdapter extends PagerAdapter {

    private LinearLayoutManager layoutManager;
    private FtGiftsInfo mFtGiftsInfo;
    private FtBackpackInfo mFtBackpackInfo;
    private double userMoney;
    private Context mContext;

    private TextView mTv_residue;
    private int mZjg;
    private TextView mTv_cost;

    public void setGiftInfos(FtGiftsInfo mFtGiftsInfo) {
        this.mFtGiftsInfo = mFtGiftsInfo;
        notifyDataSetChanged();
    }

    public void setUserMoney(double userMoney) {
        this.userMoney = userMoney;
        notifyDataSetChanged();
    }

    public void setBackpackInfos(FtBackpackInfo mFtBackpackInfo) {
        this.mFtBackpackInfo = mFtBackpackInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = null;
        layoutManager = new LinearLayoutManager(MyApplication.getInstance(), LinearLayoutManager.HORIZONTAL, false);
        mContext = MyApplication.getInstance();
        if (itemView == null) {
            if (position == 0) {
                itemView = View.inflate(MyApplication.getInstance(), R.layout.fragment_dashang_gift, null);
                initGift(itemView);
            }
            if (position == 1) {
                itemView = View.inflate(MyApplication.getInstance(), R.layout.fragment_dashang_backpack, null);
                initBackpack(itemView);
            }
        }
        container.addView(itemView);
        return itemView;
    }

    //礼物
    private void initGift(View view) {

        RecyclerView rl_gift = (RecyclerView) view.findViewById(R.id.rl_gift);
        //余额
        mTv_residue = (TextView) view.findViewById(R.id.tv_residue);

        mTv_residue.setText("星币余额: " + ((int) userMoney));

        //充值
        view.findViewById(R.id.tv_go_cz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayCenterActivity_new.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        rl_gift.setLayoutManager(layoutManager);
        DaShangGiftAdapter adapter = new DaShangGiftAdapter(mFtGiftsInfo);
        rl_gift.setAdapter(adapter);
        adapter.setClickDashang(new DaShangGiftAdapter.OnClickDashang() {
            @Override
            public void clickDaShang(int giftCoast) {
                userMoney = userMoney - giftCoast;
                mTv_residue.setText("星币余额: " + ((int) userMoney));
            }
        });
    }

    //背包
    private void initBackpack(View view) {
        RecyclerView rl_backpack = (RecyclerView) view.findViewById(R.id.rl_backpack);
        mTv_cost = (TextView) view.findViewById(R.id.tv_cost);
        mZjg = mFtBackpackInfo == null ? 0 : mFtBackpackInfo.getObj().getZjg();
        mTv_cost.setText("礼物价值: " + mZjg);
        rl_backpack.setLayoutManager(layoutManager);
        DaShangBackpackAdapter adapter = new DaShangBackpackAdapter(mFtBackpackInfo);
        rl_backpack.setAdapter(adapter);

        adapter.setClickDashang(new DaShangBackpackAdapter.OnClickDashang() {
            @Override
            public void clickDaShang(int giftCoast) {
                mZjg = mZjg - giftCoast;
                mTv_cost.setText("礼物价值: " + mZjg);
            }
        });

        view.findViewById(R.id.tv_go_duihuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DsDuiHuanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
