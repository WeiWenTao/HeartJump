package com.cucr.myapplication.activity.fuli;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MyActivesAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fuli.ErWeiMaInfo;
import com.cucr.myapplication.bean.fuli.MyActives;
import com.cucr.myapplication.core.fuLi.FuLiCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogErWeiMa;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class PiaoWuActivity extends BaseActivity implements RequersCallBackListener, MyActivesAdapter.OnClickItem {

    @ViewInject(R.id.rlv)
    private RecyclerView rlv;

    private int page;
    private int rows;
    private MyActivesAdapter mAdapter;

    @Override
    protected void initChild() {
        page = 1;
        rows = 15;
        FuLiCore core = new FuLiCore();
        mAdapter = new MyActivesAdapter();
        mAdapter.setOnClickItem(this);
        rlv.setAdapter(mAdapter);
        rlv.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        core.QueryMyActive(page,rows,this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_piao_wu;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        MyActives myActives = mGson.fromJson(response.get(), MyActives.class);
        if (myActives.isSuccess()) {
            List<MyActives.RowsBean> rows = myActives.getRows();
            mAdapter.setData(rows);
        }else {
            ToastUtils.showToast(myActives.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }

    @Override
    public void onItemClick(ErWeiMaInfo info) {
        // TODO: 2018/3/2 每次弹窗都会new一个对象
        DialogErWeiMa dialog = new DialogErWeiMa(this, R.style.MyWaitDialog);
        dialog.setDate(info);
        dialog.show();
    }
}
