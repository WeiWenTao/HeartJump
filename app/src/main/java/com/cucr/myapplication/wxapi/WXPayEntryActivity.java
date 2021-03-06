package com.cucr.myapplication.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventIsSuccess;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.mMsgApi.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    // TODO: 2018/3/23
    //交易结果必须以微信后台为准     很玄学 暂时以微信后台为准
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                EventBus.getDefault().post(new EventIsSuccess(true));
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }


}
