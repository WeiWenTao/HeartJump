package com.cucr.myapplication.widget.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用于给GridLayoutManager设置间距
 */


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int horizontalSpace;
    private int verticalSpace;

    public SpaceItemDecoration(int horizontalSpace,int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = horizontalSpace;
        outRect.bottom = horizontalSpace;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %3==0) {
            outRect.left = 0;
        }
    }
}