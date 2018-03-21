package com.cucr.myapplication.activity.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class FeedbackActivity extends BaseActivity implements RequersCallBackListener {

    @ViewInject(R.id.et_advice)
    private EditText et_advice;

    private AppCore mCore;
    private MyWaitDialog mWaitDialog;

    @Override
    protected void initChild() {
        mCore = new AppCore();
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
    }

    @OnClick(R.id.tv_send)
    public void click(View view) {
        String trim = et_advice.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            ToastUtils.showToast("还没有输入建议内容哦");
            return;
        }
        mCore.sendAdvise(trim, this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_feedback;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        CommonRebackMsg msg = MyApplication.getGson().fromJson(response.get(), CommonRebackMsg.class);
        if (msg.isSuccess()) {
            mWaitDialog.dismiss();
            ToastUtils.showToast("我们收到您的建议啦");
            finish();
        } else {
            ToastUtils.showToast(msg.getMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        mWaitDialog.show();
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        mWaitDialog.dismiss();
    }
}
