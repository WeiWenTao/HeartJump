package com.cucr.myapplication.fragment.personalMainPager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.LVStarAdapter;

/**
 * Created by 911 on 2017/7/19.
 */

public class StarFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_star,null);
            initView(mView);
        }
        return mView;
    }

    private void initView(View mView) {
        ListView lv_star = (ListView) mView.findViewById(R.id.lv_star);
        lv_star.setAdapter(new LVStarAdapter());
    }
}
