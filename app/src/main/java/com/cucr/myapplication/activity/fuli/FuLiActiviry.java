package com.cucr.myapplication.activity.fuli;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiDuiHuanAdapter;
import com.cucr.myapplication.widget.recyclerView.FullyLinearLayoutManager;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FuLiActiviry extends BaseActivity {

    //水平福利
    @ViewInject(R.id.rlv_fuli_duihuan)
    RecyclerView rlv_fuli_duihuan;

    //垂直福利
    @ViewInject(R.id.rlv_fuli)
    RecyclerView rlv_fuli;


    @Override
    protected void initChild() {
        initTitle("福利");
        initRLV();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_fu_li_activiry;
    }

    private void initRLV() {

        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        //设置为横向滑动
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlv_fuli_duihuan.setLayoutManager(layoutManager);
        FuLiDuiHuanAdapter duihuan = new FuLiDuiHuanAdapter(this);
        duihuan.setOnItemListener(new FuLiDuiHuanAdapter.OnItemListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), DuiHuanCatgoryActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        rlv_fuli_duihuan.setAdapter(duihuan);



        rlv_fuli.setLayoutManager(new FullyLinearLayoutManager(this));
        FuLiAdapter adapter = new FuLiAdapter(this);
        rlv_fuli.setAdapter(adapter);
        adapter.setOnItemListener(new FuLiAdapter.OnItemListener() {
            @Override
            public void OnItemClick(View view, int position) {
//                startActivity(new Intent(FuLiActiviry.this,FuLiCatgoryActivity.class));
            }
        });

    }

}
