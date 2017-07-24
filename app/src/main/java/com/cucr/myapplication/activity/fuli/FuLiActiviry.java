package com.cucr.myapplication.activity.fuli;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FuLiDuiHuanAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.recyclerView.FullyLinearLayoutManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FuLiActiviry extends Activity {

    //水平福利
    @ViewInject(R.id.rlv_fuli_duihuan)
    RecyclerView rlv_fuli_duihuan;

    //垂直福利
    @ViewInject(R.id.rlv_fuli)
    RecyclerView rlv_fuli;

    @ViewInject(R.id.head)
    RelativeLayout head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fu_li_activiry);
        ViewUtils.inject(this);

        //沉浸栏
        initHead();

        initRLV();
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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


    //返回
    @OnClick(R.id.iv_fuli_back)
    public void clickBack(View view) {
        finish();
    }
}
