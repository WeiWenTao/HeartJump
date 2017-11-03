package com.cucr.myapplication.fragment.star;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarPagerForQiYeActivity_111;
import com.cucr.myapplication.adapter.GvAdapter.StarRecommendAdapter;
import com.cucr.myapplication.model.starList.StarListInfos;

import java.util.List;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarRecommend extends Fragment {

    private GridView gv_star_recommend;
    private StarRecommendAdapter mGvAdapter;
    private View view;
    private List<StarListInfos.RowsBean> rows;

    public FragmentStarRecommend(List<StarListInfos.RowsBean> rows) {
        this.rows = rows;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_star_recommend, container, false);
        }
        gv_star_recommend = (GridView) view.findViewById(R.id.gv_star_recommend);
        initGV(inflater.getContext());

        return view;
    }

    private void initGV(final Context context) {
        mGvAdapter = new StarRecommendAdapter(context, rows);
        gv_star_recommend.setAdapter(mGvAdapter);
        gv_star_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mGvAdapter.setCheck(position);
                final StarListInfos.RowsBean rowsBean = rows.get(position);
                Intent intent = new Intent(context, StarPagerForQiYeActivity_111.class);
                intent.putExtra("data", rowsBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        rows.get(0).setIsfollow(1);
        mGvAdapter.setData(rows);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止请求
        mGvAdapter.stop();
    }
}
