package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.comment.FenTuanCatgoryActiviry;
import com.cucr.myapplication.adapter.LvAdapter.FtListAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.alphaHead.GradationScrollView;
import com.cucr.myapplication.widget.alphaHead.NoScrollListview;
import com.cucr.myapplication.widget.alphaHead.StatusBarUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.qiujuer.genius.blur.StackBlur;

public class FenTuanListActivity extends Activity implements GradationScrollView.ScrollViewListener {

    @ViewInject(R.id.listview)
    NoScrollListview listview;

    //模糊背景
    @ViewInject(R.id.iv_fuzzy_bg)
    ImageView iv_fuzzy_bg;

    @ViewInject(R.id.scrollview)
    GradationScrollView scrollView;

    private int height;
    private int tempH;

    @ViewInject(R.id.head)
    RelativeLayout head;

    @ViewInject(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setImgTransparent(this);
        setContentView(R.layout.activity_fen_tuan_list);
        ViewUtils.inject(this);

        initHead();

        iv_fuzzy_bg.setFocusable(true);
        iv_fuzzy_bg.setFocusableInTouchMode(true);
        iv_fuzzy_bg.requestFocus();

        initListeners();
        initData();



    }
    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = iv_fuzzy_bg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                height = iv_fuzzy_bg.getHeight() -CommonUtils.dip2px(FenTuanListActivity.this,tempH == 0 ? 48.0f : 73.0f) ;

                scrollView.setScrollViewListener(FenTuanListActivity.this);
            }
        });
    }

    private void initData() {

        //设置模糊背景
        Bitmap newBitmap = StackBlur.blur(BitmapFactory.decodeResource(getResources(),R.drawable.star3_new), 60, false);
        iv_fuzzy_bg.setImageBitmap(newBitmap);

        //设置适配器
        listview.setAdapter(new FtListAdapter(this));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FenTuanListActivity.this,FenTuanCatgoryActiviry.class));
            }
        });
    }

    /**
     * 滑动监听
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            textView.setBackgroundColor(Color.argb((int) 0, 20,21,23));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            textView.setBackgroundColor(Color.argb((int) alpha, 20,21,23));
        } else {    //滑动到banner下面设置普通颜色
            textView.setBackgroundColor(Color.argb((int) 255, 20,21,23));
        }
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) head.getLayoutParams();
            tempH = CommonUtils.dip2px(this, 73.0f);
            layoutParams.height = tempH;
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



    @OnClick(R.id.iv_publish)
    public void goPublish(View view){
        startActivity(new Intent(this, PublishActivity.class));
    }

    //返回
    @OnClick(R.id.iv_ft_back)
    public void back(View view){
        finish();
    }
}
