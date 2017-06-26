package com.cucr.myapplication.fragment.fenTuan;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.HotFunTuanAdapter;
import com.cucr.myapplication.widget.listview.ListViewForScrollView;

/**
 * Created by 911 on 2017/6/24.
 */
public class HotFenTuanFragment extends android.app.Fragment {
    private ListViewForScrollView mLv1;
    private ListViewForScrollView mLv2;
    private ListViewForScrollView mLv3;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null){
            view = inflater.inflate(R.layout.fragment_hot_fentuan, container, false);
            initLV(container.getContext());
        }

        return view;
    }

    private void initLV(final Context context) {
        mLv1 = (ListViewForScrollView) view.findViewById(R.id.lv1);
        mLv2 = (ListViewForScrollView) view.findViewById(R.id.lv2);
        mLv3 = (ListViewForScrollView) view.findViewById(R.id.lv3);

        HotFunTuanAdapter adapter = new HotFunTuanAdapter();

        mLv1.setAdapter(adapter);
        mLv2.setAdapter(adapter);
        mLv3.setAdapter(adapter);


    }
}
