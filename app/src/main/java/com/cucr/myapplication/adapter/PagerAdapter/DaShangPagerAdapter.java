package com.cucr.myapplication.adapter.PagerAdapter;


import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.model.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.model.fenTuan.FtGiftsInfo;

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

    public void setGiftInfos(FtGiftsInfo mFtGiftsInfo){
        this.mFtGiftsInfo = mFtGiftsInfo;
    }

    public void setUserMoney(double userMoney){
        this.userMoney = userMoney;
    }

    public void setBackpackInfos(FtBackpackInfo mFtBackpackInfo){
        this.mFtBackpackInfo = mFtBackpackInfo;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = null;
        layoutManager = new LinearLayoutManager(MyApplication.getInstance(), LinearLayoutManager.HORIZONTAL, false);

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
        TextView tv_residue = (TextView) view.findViewById(R.id.tv_residue);
        //充值
        view.findViewById(R.id.tv_go_cz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_gift.setLayoutManager(layoutManager);
//        rl_gift.setAdapter();
    }

    //背包
    private void initBackpack(View view) {
        RecyclerView rl_backpack = (RecyclerView) view.findViewById(R.id.rl_backpack);
        rl_backpack.setLayoutManager(layoutManager);
//        rl_backpack.setAdapter();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
