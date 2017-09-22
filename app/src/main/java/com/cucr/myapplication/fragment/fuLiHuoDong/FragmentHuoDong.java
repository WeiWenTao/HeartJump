package com.cucr.myapplication.fragment.fuLiHuoDong;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.huodong.FaBuHuoDongActivity;
import com.cucr.myapplication.activity.huodong.HuoDongCatgoryActivity;
import com.cucr.myapplication.adapter.LvAdapter.HuoDongTaiAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by cucr on 2017/9/8.
 */

public class FragmentHuoDong extends Fragment {

    //活动列表
    @ViewInject(R.id.lv_active)
    ListView lv_active;

    private View mView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = inflater.getContext();
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_huo_dong, container, false);
            ViewUtils.inject(this,mView);
            initLV();
        }

        return mView;
    }

    private void initLV() {
        lv_active.setAdapter(new HuoDongTaiAdapter(mContext));
        lv_active.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, HuoDongCatgoryActivity.class));
            }
        });
    }

    //跳转发布活动界面
    @OnClick(R.id.iv_huodong_add)
    public void faBuHuoDong(View view){
        startActivity(new Intent(mContext,FaBuHuoDongActivity.class));
    }
}
