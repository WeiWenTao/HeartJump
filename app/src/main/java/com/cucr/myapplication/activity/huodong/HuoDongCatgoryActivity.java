package com.cucr.myapplication.activity.huodong;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HuoDongCatgoryActivity extends Activity {

    @ViewInject(R.id.lv_huodong_catgory)
    ListView lv_huodong_catgory;

    @ViewInject(R.id.head)
    RelativeLayout head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo_dong_catgory);
        ViewUtils.inject(this);
        initHead();
        initLV();
    }

    //ListView
    private void initLV() {

        View lvHead = View.inflate(this, R.layout.head_huo_dong_catgory, null);

        lv_huodong_catgory.addHeaderView(lvHead,null,true);
        lv_huodong_catgory.setHeaderDividersEnabled(false);

        lv_huodong_catgory.setAdapter(new FtCatgoryAadapter(this));
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this,73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //返回
    @OnClick(R.id.iv_huodong_catgory_back)
    public void back(View view){
        finish();
    }
}
