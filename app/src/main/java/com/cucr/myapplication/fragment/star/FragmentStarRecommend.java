package com.cucr.myapplication.fragment.star;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.star.StarPagerForQiYeActivity_111;
import com.cucr.myapplication.adapter.RlVAdapter.StarListForQiYeAdapter;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;

import java.util.List;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarRecommend extends Fragment implements SwipeRecyclerView.OnLoadListener {

    private SwipeRecyclerView rlv_starlist;
    private View view;
    private List<StarListInfos.RowsBean> rows;
    private int finalPosition;
    private StarListForQiYeAdapter mAdapter;
    private Context mContext;
    private Activity mActivity;

    public FragmentStarRecommend() {
    }

    @SuppressLint("ValidFragment")
    public FragmentStarRecommend(Activity activity) {
        this.mActivity = activity;
        mAdapter = new StarListForQiYeAdapter(mActivity);
    }

    public void setData(List<StarListInfos.RowsBean> rows) {
        this.rows = rows;
        mAdapter.setData(rows);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = MyApplication.getInstance();
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_star_recommend, container, false);
        }
        rlv_starlist = (SwipeRecyclerView) view.findViewById(R.id.rlv_starlist);
        initRlv();

        return view;
    }

    private void initRlv() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        rlv_starlist.getRecyclerView().setLayoutManager(manager);
        rlv_starlist.setAdapter(mAdapter);
        rlv_starlist.setOnLoadListener(this);
        mAdapter.setOnItemClickListener(new StarListForQiYeAdapter.OnItemClickListener() {
            @Override
            public void onClickItems(int position) {
                final StarListInfos.RowsBean rowsBean = rows.get(position);
                Intent intent = new Intent(mContext, StarPagerForQiYeActivity_111.class);
                intent.putExtra("data", rowsBean);
                finalPosition = position;
                startActivityForResult(intent, 222);

//                //发送明星id到明星主页
//                EventBus.getDefault().postSticky(new EventXwStarId(rowsBean.getId()));
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

    //关注同步
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StarListInfos.RowsBean mData = (StarListInfos.RowsBean) data.getSerializableExtra("data");
        rows.remove(finalPosition);
        rows.add(finalPosition, mData);
        mAdapter.setData(rows);
    }

//   刷新
    @Override
    public void onRefresh() {

    }



//   加载更多
    @Override
    public void onLoadMore() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止请求
        mAdapter.stop();
    }


}
