package com.cucr.myapplication.fragment.hyt;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.hyt.YyhdActivity_1;
import com.cucr.myapplication.activity.hyt.YyhdActivity_2;
import com.cucr.myapplication.activity.hyt.YyhdActivity_3;
import com.cucr.myapplication.activity.hyt.YyhdCatgoryActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.dialog.DialogYyhd;

/**
 * Created by cucrx on 2018/1/16.
 */

@SuppressLint("ValidFragment")
public class Fragment_yyhd extends Fragment implements YyhdAdapter.OnClickItems, DialogYyhd.OnClickBt {

    private View rootView;
    private YyhdAdapter mAdapter;
    private Intent mIntent;
    private int starId;
    private DialogYyhd mDialog;

    public Fragment_yyhd(int starId) {
        this.starId = starId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hyt_yyhd, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        mDialog = new DialogYyhd(getActivity(), R.style.MyDialogStyle);
        Window dialogWindow = mDialog.getWindow();
        mDialog.setOnClickBt(this);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);

        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra("starId", starId);
        RecyclerView rlv_yyhd = (RecyclerView) rootView.findViewById(R.id.rlv_yyhd);
        rlv_yyhd.addItemDecoration(new SpaceItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 10)));
        rlv_yyhd.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new YyhdAdapter();
        rlv_yyhd.setAdapter(mAdapter);
        mAdapter.setOnClickItems(this);
    }

    @Override
    public void onClickItem(int position) {
        if (position == 0) {
//            mIntent.setClass(MyApplication.getInstance(), CreatYyhdActivity.class);
//            startActivity(mIntent);

            mDialog.show();
        } else {
            mIntent.setClass(MyApplication.getInstance(), YyhdCatgoryActivity.class);
            startActivity(mIntent);
        }
    }

    //点亮开屏
    @Override
    public void clickYyhd1() {
        mIntent.setClass(MyApplication.getInstance(), YyhdActivity_1.class);
        startActivity(mIntent);
    }

    //武汉BIGPAD
    @Override
    public void clickYyhd2() {
        mIntent.setClass(MyApplication.getInstance(), YyhdActivity_2.class);
        startActivity(mIntent);
    }

    //粉丝见面会
    @Override
    public void clickYyhd3() {
        mIntent.setClass(MyApplication.getInstance(), YyhdActivity_3.class);
        startActivity(mIntent);
    }
}
