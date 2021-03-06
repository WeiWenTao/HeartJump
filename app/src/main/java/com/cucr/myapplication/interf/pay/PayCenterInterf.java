package com.cucr.myapplication.interf.pay;

import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;
import com.cucr.myapplication.listener.RequersCallBackListener;

/**
 * Created by cucr on 2017/10/16.
 */

public interface PayCenterInterf {

    void aliPay(double howMuch, String subject, int type, int activeId, RequersCallBackListener listener);

    void wxPay(int total_fee, String body, int type, int activeId, RequersCallBackListener listener);

    void queryResult(String order, PayLisntener listener);

    void queryUserMoney(OnCommonListener listener);

    void queryTxRecoed(int page, int row, int type, RequersCallBackListener listener);

    void TxRequest(String txAccount, String name, String amount, RequersCallBackListener listener);

}
