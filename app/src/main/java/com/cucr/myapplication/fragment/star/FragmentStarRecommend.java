package com.cucr.myapplication.fragment.star;


import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.cucr.myapplication.adapter.GvAdapter.StarRecommendAdapterForQiye;
import com.cucr.myapplication.model.eventBus.EventXwStarId;
import com.cucr.myapplication.model.starList.StarListInfos;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarRecommend extends Fragment {

    private GridView gv_star_recommend;
    private StarRecommendAdapterForQiye mGvAdapter;
    private View view;
    private List<StarListInfos.RowsBean> rows;
    private int finalPosition;

    public FragmentStarRecommend() {
    }

    @SuppressLint("ValidFragment")
    public FragmentStarRecommend(Activity activity) {
        mGvAdapter = new StarRecommendAdapterForQiye(activity);
    }

    public void setData(List<StarListInfos.RowsBean> rows){
        this.rows = rows;
        mGvAdapter.setData(rows);
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

        gv_star_recommend.setAdapter(mGvAdapter);
        gv_star_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mGvAdapter.setCheck(position);
                final StarListInfos.RowsBean rowsBean = rows.get(position);
                Intent intent = new Intent(context, StarPagerForQiYeActivity_111.class);
                intent.putExtra("data", rowsBean);
                finalPosition = position;
                startActivityForResult(intent, 222);

                //发送明星id到明星主页
                EventBus.getDefault().postSticky(new EventXwStarId(rowsBean.getId()));

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2017/11/3  
//        rows.get(0).setIsfollow(1);
//        mGvAdapter.setData(rows);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StarListInfos.RowsBean mData = (StarListInfos.RowsBean) data.getSerializableExtra("data");
        rows.remove(finalPosition);
        rows.add(finalPosition, mData);
        mGvAdapter.setData(rows);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止请求
        mGvAdapter.stop();
    }
}
