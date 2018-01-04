package com.cucr.myapplication.interf.user;

import com.cucr.myapplication.listener.OnCommonListener;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by cucr on 2017/12/29.
 */

public interface PicturesWall {

    void queryPic(int page,int rows,int orderType,boolean queryMine,int startId,OnCommonListener onCommonListener);

    void upLoadPic(int strid , List<LocalMedia> mData, OnCommonListener commonListener);

    void picGoods(int dataId,OnCommonListener onCommonListener);
}
