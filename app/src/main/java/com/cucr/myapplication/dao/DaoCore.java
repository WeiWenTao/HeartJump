package com.cucr.myapplication.dao;

import com.cucr.myapplication.gen.LoadUserInfosDao;

/**
 * Created by cucr on 2018/3/24.
 */

public class DaoCore {
    private static DaoCore sCore;
    public LoadUserInfosDao userDao;

    /**
     * 创建User表实例
     *
     * @return
     */
    public LoadUserInfosDao getUserDao() {
        userDao = DaoManager.getInstance().getSession().getLoadUserInfosDao();
        return userDao;
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static DaoCore getInstance() {
        if (sCore == null) {
            sCore = new DaoCore();
        }
        return sCore;
    }
}
