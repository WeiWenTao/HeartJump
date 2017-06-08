package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlAdapter.StarClassifyAdapter_all;
import com.cucr.myapplication.bean.starClassify.StarClassif_all;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 911 on 2017/5/22.
 */

public class FragmentStarClassify extends Fragment implements OnQuickSideBarTouchListener {

    //定义一个变量记录初始化的页面角标
    private Context mContext;
    View view;

    private List<StarClassif_all> list = new ArrayList<>();
    private RecyclerView tast_RecyclerView;
    private GridLayoutManager manage;
    private StarClassifyAdapter_all adapter;

    //创建一个集合记录每个头部的索引
    private Map<Character, Integer> map = new HashMap<>();

    QuickSideBarView quickSideBarView;
    private TextView mTv_letter_tip;
    //    QuickSideBarTipsView quickSideBarTipsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = inflater.getContext();
        //view的复用
       if (view == null){
           view = inflater.inflate(R.layout.fragment_star_classify, container, false);
            initIndexBar(view);
       }
        return view;
    }



    private void initIndexBar(View view) {
        initFindView(view);
        initData();
        setData();

    }

    private void setData() {
        Log.i("test","data");
        Data();
        adapter = new StarClassifyAdapter_all(mContext, list);
        tast_RecyclerView.setAdapter(adapter);

//        添加间距
//      int dp_10 = CommonUtils.px2dip(context, 10.0f);
//        rl_star_classify.addItemDecoration(new SpaceItemDecoration(dp_10,dp_10));

        adapter.setOnItemListeren(new StarClassifyAdapter_all.OnItemListeren() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
            }
        });
        manage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//判断是否是0 如果为0获取布局显示的个数。否则显示一个
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == StarClassifyAdapter_all.TYPE_HEAD ? manage.getSpanCount() : 1;
            }
        });
    }

    private void initData() {

        //需求必须使用GridLayoutManager布局管理器
        manage = new GridLayoutManager(mContext, 3, OrientationHelper.VERTICAL, false);
        tast_RecyclerView.setLayoutManager(manage);
//        tast_RecyclerView.addItemDecoration(new CircleImageView2(getContext()));

    }

    private void initFindView(View view) {
        tast_RecyclerView = (RecyclerView) view.findViewById(R.id.rl_star_classify);
        quickSideBarView = (QuickSideBarView) view.findViewById(R.id.quickSideBarView);
//        quickSideBarTipsView = (QuickSideBarTipsView) view.findViewById(quickSideBarTipsView);
        mTv_letter_tip = (TextView) view.findViewById(R.id.tv_letter_tip);

        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);
    }

    /**
     * 假数据
     */
    /**
     * 假数据
     */
    private List<String> getList = null;

    private int position;

    private void Data() {

        for (int i = 0; i < 26; i++) {

            char head = (char) ('A' + i);
            map.put(head, position + i);
            //记录头部索引
            getList = new ArrayList<>();
            StarClassif_all info = new StarClassif_all();

            //模拟获取数据
            for (int j = 0; j < new Random().nextInt(23); j++) {
                //position记录每次变化
                ++position;
                getList.add("EXECL" + j);
            }


            info.setHeader(head + "");

            info.setDataList(getList);


            list.add(info);
        }
    }

    //改变字母的时候
    @Override
    public void onLetterChanged(String letter, int position, float y) {
//        quickSideBarTipsView.setText(letter, position, y);
        mTv_letter_tip.setText(letter);
        manage.scrollToPositionWithOffset(map.get(letter.charAt(0)), 0);
    }

    //触摸的时候
    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
//        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
        mTv_letter_tip.setVisibility(touching ? View.VISIBLE : View.GONE);
    }
}
