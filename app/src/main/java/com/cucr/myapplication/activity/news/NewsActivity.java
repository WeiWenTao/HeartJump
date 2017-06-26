package com.cucr.myapplication.activity.news;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class NewsActivity extends Activity {


    @ViewInject(R.id.tv_new_title)
    TextView tv_new_title;

    @ViewInject(R.id.head)
    RelativeLayout head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ViewUtils.inject(this);

        initHead();
        initView();
    }

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

    private void initView() {

        //字体加粗
        tv_new_title.getPaint().setFakeBoldText(true);

    }


    //关闭
    @OnClick(R.id.iv_news_back)
    public void back(View view){
        finish();
    }
}
