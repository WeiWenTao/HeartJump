package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytMembersAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytMembers;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDelMembers;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class HytMemberListActivity extends BaseActivity implements DialogDelMembers.OnClickBt, RequersCallBackListener, SwipeRecyclerView.OnLoadListener, HytMembersAdapter.OnItemClick {

    @ViewInject(R.id.iv_member)
    private ImageView iv_member;

    @ViewInject(R.id.rlv_members)
    private SwipeRecyclerView rlv_members;

    private DialogDelMembers mDialog;
    private String mHytId;
    private HytCore mCore;
    private HytMembersAdapter mAdapter;
    private Intent mIntent;

    @Override
    protected void initChild() {
        page = 1;
        rows = 15;
        rlv_members.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new HytMembersAdapter();
        rlv_members.setAdapter(mAdapter);
        mAdapter.setOnItemClick(this);
        mHytId = getIntent().getStringExtra("id");
        mCore = new HytCore();
        mIntent = new Intent();
        mIntent.putExtra("id", mHytId);
        mDialog = new DialogDelMembers(this, R.style.MyDialogStyle);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mDialog.setOnClickBt(this);
        rlv_members.setOnLoadListener(this);
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_hyt_member_list;
    }

    //显示删除对话框
    @OnClick(R.id.iv_member)
    public void del(View view) {
        mDialog.show();
    }

    //解除禁言
    @Override
    public void clickBt1() {
        mIntent.setClass(MyApplication.getInstance(), UnLockActivity.class);
        startActivity(mIntent);
    }

    //禁言
    @Override
    public void clickBt2() {
        mIntent.setClass(MyApplication.getInstance(), LockActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        HytMembers hytMembers = mGson.fromJson(response.get(), HytMembers.class);
        if (hytMembers.isSuccess()) {
            if (isRefresh) {
                if (hytMembers.getRows().get(0).getUser().getId() == ((int) SpUtil.getParam(SpConstant.USER_ID, -1))) {
                    iv_member.setVisibility(View.VISIBLE);
                }
                mAdapter.setData(hytMembers.getRows());
            } else {
                mAdapter.addData(hytMembers.getRows());
            }
            if (hytMembers.getTotal() <= page * rows) {
                rlv_members.onNoMore("没有更多了");
            } else {
                rlv_members.complete();
            }
        } else {
            ToastUtils.showToast(hytMembers.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_THIRTEEN) {
            if (rlv_members.isRefreshing()) {
                rlv_members.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (rlv_members.isLoadingMore()) {
                rlv_members.stopLoadingMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_members.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryMembers(page, rows, mHytId, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_members.onLoadingMore();
        mCore.queryMembers(page, rows, mHytId, this);
    }

    @Override
    public void onClickItem(int id) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", id);
        startActivity(intent);
    }
}
