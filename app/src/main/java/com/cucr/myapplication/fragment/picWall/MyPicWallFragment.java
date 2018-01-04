package com.cucr.myapplication.fragment.picWall;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.PicWallAdapter;

/**
 * Created by cucr on 2018/1/2.
 */

public class MyPicWallFragment extends Fragment {
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_my_picwall,container,false);
        }
        init();
        return rootView;
    }

    private void init() {
        RecyclerView rlv_my_pics = (RecyclerView) rootView.findViewById(R.id.rlv_my_pics);
        rlv_my_pics.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rlv_my_pics.setAdapter(new PicWallAdapter());
    }
}
