package com.cucr.myapplication.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.InvateStarAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.invate.InvateSerchStar;
import com.cucr.myapplication.core.invate.InvateCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

public class PickAiDouActivity extends BaseActivity implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener, InvateStarAdapter.OnItemClickListener {

    //明星列表
    @ViewInject(R.id.rlv_stars)
    private SwipeRecyclerView rlv_stars;

    //搜索框
    @ViewInject(R.id.et_search)
    private EditText et_search;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private boolean needShowLoading;
    private boolean isRefresh;
    private InvateCore mCore;
    private String name;
    private InvateStarAdapter mAdapter;

    @Override
    protected void initChild() {
        rows = 30;
        mCore = new InvateCore();
        rlv_stars.getRecyclerView().setLayoutManager(new GridLayoutManager(MyApplication.getInstance(), 3));
        mAdapter = new InvateStarAdapter();
        mAdapter.setOnItemClickListener(this);
        rlv_stars.setAdapter(mAdapter);
        rlv_stars.setOnLoadListener(this);
        watchSearch();
        onRefresh();
    }

    public void watchSearch() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_search.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(PickAiDouActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己要的操作...
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search() {
        name = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("请输入搜索关键字哦");
            return;
        }
        onRefresh();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_pick_ai_dou;
    }


    @Override
    public void onRefresh() {
        page = 1;
        isRefresh = true;
        rlv_stars.getSwipeRefreshLayout().setRefreshing(true);
        mCore.invateSearch(page, rows, name, this);
    }

    @Override
    public void onLoadMore() {
        page++;
        isRefresh = false;
        mCore.invateSearch(page, rows, name, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        InvateSerchStar starInfos = mGson.fromJson(response.get(), InvateSerchStar.class);
        if (starInfos.isSuccess()) {
            if (isRefresh) {
                if (starInfos.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                } else {
                    mAdapter.setData(starInfos.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(starInfos.getRows());
            }
            if (starInfos.getTotal() <= page * rows) {
                rlv_stars.onNoMore("没有更多了");
            } else {
                rlv_stars.complete();
            }
        } else {
            ToastUtils.showToast(starInfos.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {
        if (isRefresh && response.getException() instanceof NetworkError) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (rlv_stars.isRefreshing()) {
            rlv_stars.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_stars.isLoadingMore()) {
            rlv_stars.stopLoadingMore();
        }
    }

    @Override
    public void clickItem(InvateSerchStar.RowsBean rowsBean) {
        Intent intent = getIntent().putExtra("data", rowsBean);
        setResult(111, intent);
        finish();
    }
}
