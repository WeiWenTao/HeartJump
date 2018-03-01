package com.cucr.myapplication.interf.fuli;

import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/8/25.
 */

public interface QueryFuLi {
    /**
     * @param page 页码
     * @param rows 每页多少条
     */
    void QueryDuiHuan(int page, int rows, RequersCallBackListener listener);

    void QueryHuoDong(int page, int rows, RequersCallBackListener listener);

    void QueryMyActive(int page, int rows, RequersCallBackListener listener);
}
