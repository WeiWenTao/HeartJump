package com.cucr.myapplication.interf.user;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by cucr on 2017/12/29.
 */

public interface PicturesWall {

    void queryPic(int page,int rows,int orderType,boolean queryMine,int startId,RequersCallBackListener onCommonListener);

    void queryMyFavoritePic(int page,int rows,RequersCallBackListener onCommonListener);

    void upLoadPic(int strid , List<LocalMedia> mData, RequersCallBackListener commonListener);

    void picGoods(int dataId,int goodCount,OnCommonListener onCommonListener);

    void delPic(int dataId,RequersCallBackListener onCommonListener);
}
