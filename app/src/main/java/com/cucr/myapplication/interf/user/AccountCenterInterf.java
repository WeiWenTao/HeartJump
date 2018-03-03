package com.cucr.myapplication.interf.user;

import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2018/3/3.
 */

public interface AccountCenterInterf {
    void relasePsw(String oldPsw, String newPsw, RequersCallBackListener listener);
}
