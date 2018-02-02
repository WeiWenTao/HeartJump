package com.cucr.myapplication.interf.pay;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;

/**
 * Created by cucr on 2017/10/16.
 */

public interface PayCenterInterf {

    void aliPay(double howMuch, String subject,int type,int activeId, OnCommonListener listener);

    void wxPay(double total_fee, String body,int type,int activeId, OnCommonListener listener);

    void queryResult(String order, PayLisntener listener);

    void queryUserMoney(OnCommonListener listener);
}
