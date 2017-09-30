package com.cucr.myapplication.interf.funTuan;

import com.cucr.myapplication.listener.OnUpLoadListener;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by cucr on 2017/9/21.
 */

public interface FenTuanInterf {

    /**
     * 发布粉团动态
     */
    void publishFtInfo(int starId, int type, String content, List<LocalMedia> mData, OnUpLoadListener listener);
}
