package com.cucr.myapplication.fragment.star;

import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.fragment.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by cucr on 2018/5/10.
 */

public class Fragment_fans_two extends BaseFragment {

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_fans_two;
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    @OnClick(R.id.ll_about)
    public void clickAbout(View view) {
        if (mOnClickCatgoryTwo != null) {
            mOnClickCatgoryTwo.onClickAbout();
        }
    }

    @OnClick(R.id.ll_yy)
    public void clickYyzc(View view) {
        if (mOnClickCatgoryTwo != null) {
            mOnClickCatgoryTwo.onClickYyzc();
        }
    }

    @OnClick(R.id.ll_weibo)
    public void clickWeiBo(View view) {
        if (mOnClickCatgoryTwo != null) {
            mOnClickCatgoryTwo.onClickWeiBo();
        }
    }

    public interface OnClickCatgoryTwo {
        void onClickAbout();

        void onClickYyzc();

        void onClickWeiBo();

    }

    private OnClickCatgoryTwo mOnClickCatgoryTwo;

    public void setOnClickCatgoryTwo(OnClickCatgoryTwo onClickCatgoryTwo) {
        mOnClickCatgoryTwo = onClickCatgoryTwo;
    }
}
