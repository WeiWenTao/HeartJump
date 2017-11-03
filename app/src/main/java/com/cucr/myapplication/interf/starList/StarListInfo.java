package com.cucr.myapplication.interf.starList;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/1.
 */

public interface StarListInfo {
    void queryStarList(int type,int page,int row,int starId,OnCommonListener onCommonListener);
}
