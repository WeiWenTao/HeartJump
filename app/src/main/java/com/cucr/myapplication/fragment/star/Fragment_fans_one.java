package com.cucr.myapplication.fragment.star;

import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.fragment.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by cucr on 2018/5/10.
 */

public class Fragment_fans_one extends BaseFragment {

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);

    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_fans_one;
    }

    @Override
    protected boolean needHeader() {
        return false;
    }


    @OnClick(R.id.ll_fansq)
    public void fansQ(View view) {
        if (mOnClickCatgoryOne != null) {
            mOnClickCatgoryOne.onClickFanQ();
        }
    }

    @OnClick(R.id.ll_shuju)
    public void shuJu(View view) {
        if (mOnClickCatgoryOne != null) {
            mOnClickCatgoryOne.onClickShuJu();
        }
    }

    @OnClick(R.id.ll_hyt)
    public void hyt(View view) {
        if (mOnClickCatgoryOne != null) {
            mOnClickCatgoryOne.onClickHYT();
        }
    }

    @OnClick(R.id.ll_tpq)
    public void tpq(View view) {
        if (mOnClickCatgoryOne != null) {
            mOnClickCatgoryOne.onClickTPQ();
        }
    }

    @OnClick(R.id.ll_journey)
    public void journey(View view) {
        if (mOnClickCatgoryOne != null) {
            mOnClickCatgoryOne.onClickjourney();
        }
    }

    public interface OnClickCatgoryOne {
        void onClickFanQ();

        void onClickShuJu();

        void onClickHYT();

        void onClickTPQ();

        void onClickjourney();

    }

    private OnClickCatgoryOne mOnClickCatgoryOne;

    public void setOnClickCatgoryOne(OnClickCatgoryOne onClickCatgoryOne) {
        mOnClickCatgoryOne = onClickCatgoryOne;
    }
}
