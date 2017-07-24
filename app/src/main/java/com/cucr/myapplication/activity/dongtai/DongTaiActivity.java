package com.cucr.myapplication.activity.dongtai;

import android.app.Activity;
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
import com.cucr.myapplication.adapter.RlVAdapter.RlvDongTaiAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DongTaiActivity extends Activity {

    //沉浸栏
    @ViewInject(R.id.head)
    RelativeLayout head;

    //动态列表
    @ViewInject(R.id.rlv_dongtai)
    RecyclerView rlv_dongtai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_tai);
        ViewUtils.inject(this);

        initHead();
        initRLV();

    }



    //初始化动态列表
    private void initRLV() {
        rlv_dongtai.setLayoutManager(new LinearLayoutManager(this));
        rlv_dongtai.setAdapter(new RlvDongTaiAdapter(this));
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



    //返回键
    @OnClick(R.id.iv_dongtai_back)
    public void back(View view) {
        finish();
    }

}
