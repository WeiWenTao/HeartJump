package com.cucr.myapplication.activity.star;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.GvAdapter.StarRecommendAdapter;
import com.cucr.myapplication.core.starListAndJourney.QueryStarList;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.starList.StarListInfos;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.List;

public class StarListForAddActivity extends Activity {

    //明星列表
    @ViewInject(R.id.gv_star_list)
    private GridView gv_star_list;

    private QueryStarList mCore;
    private Gson mGson;
    private int type;   //查全部
    private int rows;   //每页显示数目
    private int page;   //页数
    private List<StarListInfos.RowsBean> mRows;
    private StarRecommendAdapter mGvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_list_for_add);
        ViewUtils.inject(this);
        initView();
        queryData();
    }


    private void initView() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
        mCore = new QueryStarList(this);
        mGson = new Gson();
//        mRows = new ArrayList<>();
        mGvAdapter = new StarRecommendAdapter(this,mRows);
        gv_star_list.setAdapter(mGvAdapter);
    }


    //    查询数据
    private void queryData() {
        type = 2;
        rows = 10;
        page = 1;
        //参数: 2:查全部
        mCore.queryStarList(2, page, rows, 0, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarListInfos starListInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starListInfos.isSuccess()) {
                    mRows = starListInfos.getRows();
                    mGvAdapter.setData(mRows);
                } else {
                    ToastUtils.showToast(StarListForAddActivity.this, starListInfos.getErrorMsg());
                }
            }
        });
    }
}
