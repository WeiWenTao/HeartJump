package com.cucr.myapplication.activity.hyt;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytUnlockAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.Hyt.HytLockList;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

public class UnLockActivity extends BaseActivity implements View.OnClickListener, RequersCallBackListener {
//
//    @ViewInject(R.id.tv_unlock)
//    private TextView tv_unlock;

    @ViewInject(R.id.rlv_list)
    private RecyclerView rlv_list;

    @ViewInject(R.id.bt_unlock)
    private Button bt_unlock;

    private HytCore mCore;
    private String mHytId;
    private HytUnlockAdapter mAdapter;

    @Override
    protected void initChild() {
        mHytId = getIntent().getStringExtra("id");
        mCore = new HytCore();
        mCore.hytLockList(mHytId,this);
        bt_unlock.setOnClickListener(this);
        mAdapter = new HytUnlockAdapter();
        rlv_list.setAdapter(mAdapter);
        rlv_list.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_jin_yan;
    }

    //确定解除
    @Override
    public void onClick(View v) {
        String lockId = mAdapter.getLockId();
        if (TextUtils.isEmpty(lockId)) {
            ToastUtils.showToast("还没有选择用户哦");
            return;
        }
        mCore.hytUnLock(lockId, mHytId, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_SIXTEEN) {     //解除禁言
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                ToastUtils.showToast("已解除禁言");
                //解除成功后再查一遍
                mCore.hytLockList(mHytId,this);
            } else {
                ToastUtils.showToast(msg.getMsg());
            }
        } else if (what == Constans.TYPE_SEVENTEEN) { //禁言列表
            HytLockList msg = mGson.fromJson(response.get(), HytLockList.class);
            if (msg.isSuccess()) {
                mAdapter.setData(msg.getRows());
                if (msg.getRows().size() == 0) {
                    bt_unlock.setText("暂无禁言用户");
                    bt_unlock.setEnabled(false);
                    bt_unlock.setBackgroundResource(R.drawable.shape_load_bg_nor);
                }
            } else {
                ToastUtils.showToast(msg.getErrorMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
