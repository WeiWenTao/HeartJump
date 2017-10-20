package com.cucr.myapplication.interf.pay;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;

/**
 * Created by cucr on 2017/10/16.
 */

public interface PayCenterInterf {
    void aliPay(double howMuch, String subject, OnCommonListener listener);

    void wxPay();

    void queryResult(String order, PayLisntener listener);

    void queryUserMoney(OnCommonListener listener);
}
