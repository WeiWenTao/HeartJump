package com.cucr.myapplication.interf.focus;

import com.cucr.myapplication.listener.OnCommonListener;

/**
 * Created by cucr on 2017/9/5.
 */

public interface Focus {
    void toFocus(int id);

    void cancaleFocus(int id, OnCommonListener listener);
}
