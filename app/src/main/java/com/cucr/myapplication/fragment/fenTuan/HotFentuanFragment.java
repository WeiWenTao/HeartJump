package com.cucr.myapplication.fragment.fenTuan;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.HotFentuanAdapter;

/**
 * Created by 911 on 2017/5/19.
 */
public class HotFentuanFragment extends Fragment {
    private Context mContext;


    public HotFentuanFragment(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_hot_fentuan,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //热门粉团
        ListView lv_hot_fentuan = (ListView) view.findViewById(R.id.lv_hot_fentuan);
        View headerView = View.inflate(mContext, R.layout.header_lv_hot_fentuan, null);
        lv_hot_fentuan.addHeaderView(headerView);
        lv_hot_fentuan.setAdapter(new HotFentuanAdapter(mContext));

    }
}
