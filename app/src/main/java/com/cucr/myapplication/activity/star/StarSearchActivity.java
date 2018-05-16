package com.cucr.myapplication.activity.star;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.SearchStarAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventNotifyStarInfo;
import com.cucr.myapplication.bean.eventBus.EventOnClickCancleFocus;
import com.cucr.myapplication.bean.eventBus.EventOnClickFocus;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpaceItemDecoration;
import com.cucr.myapplication.widget.dialog.DialogCanaleFocusStyle;
import com.cucr.myapplication.widget.dialog.DialogSearchEmpty;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

public class StarSearchActivity extends Activity implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener, SearchStarAdapter.OnItemClick, DialogCanaleFocusStyle.OnClickBtListener, DialogSearchEmpty.ClickListener {

    @ViewInject(R.id.rlv_stars)
    private SwipeRecyclerView rlv_stars;

    @ViewInject(R.id.edit_search)
    private EditText edit_search;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private boolean needShowLoading;
    private boolean isRefresh;
    private int rows;
    private int page;
    private QueryStarListCore mCore;
    private String mCode;
    private Gson mGson;
    private SearchStarAdapter mAdapter;
    private FocusCore mFCore;
    private DialogSearchEmpty mDialogSearchEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_search);
        ViewUtils.inject(this);
        page = 1;
        rows = 15;
        initViews();
        watchSearch();
    }

    private void initViews() {
        //设置状态栏字体颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.white), 0);
        mCore = new QueryStarListCore();
        mFCore = new FocusCore();
        rlv_stars.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_stars.getRecyclerView().addItemDecoration(new SpaceItemDecoration(1));
        mAdapter = new SearchStarAdapter();
        mAdapter.setOnItemClick(this);
        rlv_stars.setAdapter(mAdapter);
        rlv_stars.setOnLoadListener(this);
        mGson = MyApplication.getGson();
        mDialogCanaleFocusStyle = new DialogCanaleFocusStyle(this, R.style.ShowAddressStyleTheme);
        mDialogCanaleFocusStyle.setOnClickBtListener(this);
        mDialogSearchEmpty = new DialogSearchEmpty(this, R.style.BirthdayStyleTheme);
        mDialogSearchEmpty.setClickListener(this);
    }

    public void watchSearch() {
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) edit_search.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(StarSearchActivity.this
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
        mCode = edit_search.getText().toString().trim();
        if (TextUtils.isEmpty(mCode)) {
            ToastUtils.showToast("请输入搜索关键字哦");
            return;
        }
        needShowLoading = true;
        isRefresh = true;
        page = 1;
        mCore.querStarByName(rows, page, mCode, this);
    }

    @OnClick(R.id.iv_search_back)
    public void clickBack(View view) {
        finish();
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        StarListInfos starList = mGson.fromJson(response.get(), StarListInfos.class);
        if (starList.isSuccess()) {
            if (isRefresh) {
                if (starList.getTotal() == 0) {
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    mDialogSearchEmpty.show();
                    mDialogSearchEmpty.setName(mCode);
                } else {
                    mAdapter.setData(starList.getRows());
                    multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            } else {
                mAdapter.addData(starList.getRows());
            }
            if (starList.getTotal() <= page * rows) {
                rlv_stars.onNoMore("没有更多了");
            } else {
                rlv_stars.complete();
            }
        } else {
            ToastUtils.showToast(starList.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (needShowLoading) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            needShowLoading = false;
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {
        if (isRefresh && response.getException() instanceof NetworkError) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_THREE) {
            if (rlv_stars.isRefreshing()) {
                rlv_stars.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (rlv_stars.isLoadingMore()) {
                rlv_stars.stopLoadingMore();
            }
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_stars.getSwipeRefreshLayout().setRefreshing(true);
        mCore.querStarByName(rows, page, mCode, this);
    }

    //加载
    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_stars.onLoadingMore();
        mCore.querStarByName(rows, page, mCode, this);
    }

    //点击条目
    @Override
    public void onItemClick(int id) {
        //发送明星id到明星主页
        EventBus.getDefault().postSticky(new EventFIrstStarId(id));
        Intent intent = new Intent(MyApplication.getInstance(), StarPagerActivity.class);
        intent.putExtra("starId", id);
        startActivity(intent);
    }

    private DialogCanaleFocusStyle mDialogCanaleFocusStyle;
    private StarListInfos.RowsBean rowsBean;

    //点击(取消)关注
    @Override
    public void onFocusClick(StarListInfos.RowsBean rowsBean) {
        if (rowsBean.getIsfollow() == 0) {
            EventBus.getDefault().post(new EventOnClickFocus());
            mFCore.toFocus(rowsBean.getId());
            rowsBean.setIsfollow(1);
        } else {
            this.rowsBean = rowsBean;
            mDialogCanaleFocusStyle.show();
            mDialogCanaleFocusStyle.initTitle(rowsBean.getRealName());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickConfirm() {
        ToastUtils.showToast("已取消关注！");
        rowsBean.setIsfollow(0);
        EventBus.getDefault().post(new EventNotifyStarInfo());
        EventBus.getDefault().post(new EventOnClickCancleFocus());
        mAdapter.notifyDataSetChanged();
        mDialogCanaleFocusStyle.dismiss();

        mFCore.cancaleFocus(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (reBackMsg.isSuccess()) {
                } else {
                    ToastUtils.showToast(reBackMsg.getMsg());
                }
            }
        });
    }

    @Override
    public void clickCancle() {
        mDialogCanaleFocusStyle.dismiss();
    }

    @Override
    public void onClickConfirm(String who) {
        mCore.upLoadStar(who, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("我们已经收到您的推荐啦");
                } else {
                    ToastUtils.showToast(msg.getMsg());
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

            }
        });
    }
}
