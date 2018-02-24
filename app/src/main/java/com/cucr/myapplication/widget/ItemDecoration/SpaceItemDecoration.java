package com.cucr.myapplication.widget.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用于给GridLayoutManager设置间距
 */

/**
 * mRecyclerView.addItemDecoration(new RecycleViewDivider(
 * mContext, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.divide_gray_color)));
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int verticalSpace;

    public SpaceItemDecoration(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.bottom = verticalSpace;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
    }
}