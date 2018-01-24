package com.cucr.myapplication.activity.hyt;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.cachapa.expandablelayout.ExpandableLayout;

public class CreatYyhdActivity extends BaseActivity {

    @ViewInject(R.id.expandable_layout_1)
    private ExpandableLayout el_1;

    @ViewInject(R.id.expandable_layout_2)
    private ExpandableLayout el_2;

    @ViewInject(R.id.expandable_layout_3)
    private ExpandableLayout el_3;

    @ViewInject(R.id.iv_icon1)
    private ImageView iv_icon1;

    @ViewInject(R.id.iv_icon2)
    private ImageView iv_icon2;

    @ViewInject(R.id.iv_icon3)
    private ImageView iv_icon3;

    @Override
    protected void initChild() {
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_creat_yyhd;
    }

    public void excuteAni(ImageView imageView, boolean b) {
        ObjectAnimator animator;
        if (b) {
            animator = ObjectAnimator.ofFloat(imageView, "rotation",
                    0, 180);

        } else {
            animator = ObjectAnimator.ofFloat(imageView, "rotation",
                    180, 0);

        }

        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    //点亮开屏
    @OnClick(R.id.rl_open)
    public void open(View view) {
        if (el_1.isExpanded()) {
            el_1.collapse();
        } else {
            el_1.expand();
        }
        excuteAni(iv_icon1, el_1.isExpanded());
    }

    //BigPad
    @OnClick(R.id.rl_bigpad)
    public void bigPad(View view) {
        if (el_2.isExpanded()) {
            el_2.collapse();
        } else {
            el_2.expand();
        }
        excuteAni(iv_icon2, el_2.isExpanded());
    }

    //粉丝见面会
    @OnClick(R.id.rl_fans)
    public void fans(View view) {
        if (el_3.isExpanded()) {
            el_3.collapse();
        } else {
            el_3.expand();
        }
        excuteAni(iv_icon3, el_3.isExpanded());
    }


}
