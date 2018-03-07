package com.cucr.myapplication.activity.setting;

import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LearnCatgoryActivity extends BaseActivity {

    @ViewInject(R.id.iv)
    private ImageView iv;

    @Override
    protected void initChild() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp = iv.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        iv.setLayoutParams(lp);
        iv.setMaxWidth(screenWidth);
        iv.setMaxHeight(screenWidth * 5);//这里其实可以根据需求而定，我这里测试为最大宽度的5倍


        int type = getIntent().getIntExtra("type", -1);
        switch (type) {
            case 1:
                initTitle("认证教程");
                iv.setImageResource(R.drawable.learning1);
                break;

            case 2:
                initTitle("预约教程");
                iv.setImageResource(R.drawable.learning2);
                break;

            case 3:
                initTitle("打赏教程");
                iv.setImageResource(R.drawable.learning3);
                break;

            case 4:
                initTitle("招募教程");
                iv.setImageResource(R.drawable.learning4);
                break;
        }
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_learn_catgory;
    }
}
