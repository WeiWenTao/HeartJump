package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.home.PublishActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtListAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.qiujuer.genius.blur.StackBlur;

public class FenTuanListActivity extends Activity {

    @ViewInject(R.id.lv_ft_catgory)
    ListView lv_ft_catgory;

    @ViewInject(R.id.title_star_ft)
    RelativeLayout head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_tuan_list);
        ViewUtils.inject(this);

        initHead();



        initView();
    }


    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) head.getLayoutParams();
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

    private void initView() {
        View headView = View.inflate(this, R.layout.header_ft_list, null);

//        模糊背景
        ImageView iv_head_bg = (ImageView) headView.findViewById(R.id.iv_head_bg);
        Bitmap newBitmap = StackBlur.blur(BitmapFactory.decodeResource(getResources(),R.drawable.star3_new), 60, false);
        iv_head_bg.setImageBitmap(newBitmap);


        lv_ft_catgory.addHeaderView(headView,null,true);
        lv_ft_catgory.setHeaderDividersEnabled(false);
        lv_ft_catgory.setAdapter(new FtListAdapter(this));

        lv_ft_catgory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FenTuanListActivity.this,FenTuanCatgoryActiviry.class));
            }
        });
    }

    @OnClick(R.id.iv_publish)
    public void goPublish(View view){
        startActivity(new Intent(this, PublishActivity.class));
    }
}
