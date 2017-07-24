package com.cucr.myapplication.activity.fuli;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.coverflow.GalleryRecyclerView;
import com.cucr.myapplication.widget.coverflow.Item;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DuiHuanCatgoryActivity extends Activity {

    @ViewInject(R.id.gallery)
    GalleryRecyclerView mGalleryRecyclerView;

    @ViewInject(R.id.head)
    RelativeLayout head;

    //    private int[] mImgs ={R.mipmap.test1,R.mipmap.test2,R.mipmap.test3,R.mipmap.test4,R.mipmap.test5,R.mipmap.test6,R.mipmap.test7
//            ,R.mipmap.test8,R.mipmap.test9,R.mipmap.test10,R.mipmap.test11,R.mipmap.test12,R.mipmap.test13,R.mipmap.test14,R.mipmap.test15
//            ,R.mipmap.test16,R.mipmap.test17,R.mipmap.test18,R.mipmap.test19,R.mipmap.test20,R.mipmap.test21,R.mipmap.test22,R.mipmap.test23};
    private List<Item> mlist = new ArrayList<>();
    private int[] mImgs = new int[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dui_huan_catgory);
        ViewUtils.inject(this);

        //沉浸栏
        initHeader();
        //初始化数据
        initData();
        //初始化gallery
        initView();



    }



    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < mImgs.length; i++) {
            Item item = new Item();
            item.setName(i + "km");
            mlist.add(item);
        }
    }

    private void initView() {

        mGalleryRecyclerView.setCanAlpha(true);
        mGalleryRecyclerView.setCanScale(true);
        mGalleryRecyclerView.setBaseScale(0.65f);
        mGalleryRecyclerView.setBaseAlpha(0.5f);
        mGalleryRecyclerView.selectItem(getIntent().getIntExtra("position",0));
//        mGalleryRecyclerView.setBaseScale(0.5f);
//        mGalleryRecyclerView.setBaseAlpha(0.95f);

//        mGalleryRecyclerView.setSelectedItem(3);




        mGalleryRecyclerView.setAdapter(new CommonAdapter<Item>(this, R.layout.item_duihuan, mlist) {
            @Override
            public void convert(ViewHolder holder, final Item s, final int position) {
//                holder.setImageResource(R.id.img_duihuan,R.drawable.star1_new);
                holder.getView(R.id.tv_duihuan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,position+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mGalleryRecyclerView.setOnViewSelectedListener(new GalleryRecyclerView.OnViewSelectedListener() {
            @Override
            public void onSelected(View view, final int position) {
                if (position == mImgs.length - 1)
                ToastUtils.showToast(DuiHuanCatgoryActivity.this,"到最后了哦");
            }
        });

    }

    //返回
    @OnClick(R.id.iv_fuli_duihuan_back)
    public void back(View view){
        finish();
    }

    //沉浸栏
    private void initHeader() {
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
}
