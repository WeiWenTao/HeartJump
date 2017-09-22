package com.cucr.myapplication.fragment.star;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.XingWenAdapter;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_xingwen extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null){
            view = inflater.inflate(R.layout.item_personal_pager_xingwen, container, false);
            initRlV(container.getContext());
        }

        return view;
    }

    private void initRlV(final Context context) {
        RecyclerView rlv_dongtai = (RecyclerView) view.findViewById(R.id.rlv_xingwen);
        rlv_dongtai.setLayoutManager(new LinearLayoutManager(context));
        rlv_dongtai.setAdapter(new XingWenAdapter(context));
    }
}
