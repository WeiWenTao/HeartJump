package com.cucr.myapplication.interf.funTuan;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/10/18.
 */

public interface FenTuanComment {

    void queryFtComment(Integer dataId,Integer parentId, Integer page, Integer rows, OnCommonListener listener);

    void ftCommentGoods(Integer contentId,Integer commentId,OnCommonListener listener);
}
