package com.cucr.myapplication.activity.hyt;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytUnlockAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;

public class UnLockActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.tv_unlock)
    private TextView tv_unlock;

    @ViewInject(R.id.rlv_list)
    private RecyclerView rlv_list;

    @Override
    protected void initChild() {
        tv_unlock.setOnClickListener(this);
        HytUnlockAdapter adapter = new HytUnlockAdapter();
        //todo 获取禁言列表
//        rlv_list.setAdapter(adapter);
//        rlv_list.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_jin_yan;
    }

    //确定解除
    @Override
    public void onClick(View v) {

    }
}
