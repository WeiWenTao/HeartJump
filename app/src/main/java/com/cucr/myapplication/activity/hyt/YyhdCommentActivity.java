package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdCommentAdater;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.YyhdCommentInfo;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiImageView;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

public class YyhdCommentActivity extends BaseActivity implements RequersCallBackListener, YyhdCommentAdater.OnClickGood, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv_comment)
    private SwipeRecyclerView srlv;

    @ViewInject(R.id.ll_rootview)
    private LinearLayout ll_rootview;

    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

    @ViewInject(R.id.iv_emoji)
    private ImageView iv_emoji;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    private HytCore mCore;
    private int page;
    private int rows;
    private Gson mGson;
    private int giveNum;
    private YyhdCommentAdater mAdapter;
    private int mActiveId;
    //emoji表情
    private EmojiPopup emojiPopup;
    private int totalComments;
    private Intent mIntent;
    private boolean needShowLoading;
    private boolean isRefresh;

    @Override
    protected void initChild() {
        page = 1;
        rows = 10;
        mIntent = getIntent();
        mActiveId = mIntent.getIntExtra("activeId", -1);
        mCore = new HytCore();
        mGson = MyApplication.getGson();
        initRlv();
        onRefresh();
    }

    private void initRlv() {
        srlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new YyhdCommentAdater();
        mAdapter.setOnClickGood(this);
        srlv.getRecyclerView().setAdapter(mAdapter);
        srlv.setOnLoadListener(this);
        setUpEmojiPopup();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_yyhd_comment;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        switch (what) {
            case Constans.TYPE_NINE:
                YyhdCommentInfo info = mGson.fromJson(response.get(), YyhdCommentInfo.class);
                if (info.isSuccess()) {
                    if (isRefresh) {
                        if (info.getTotal() == 0) {
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                        } else {
                            mAdapter.setData(info.getRows());
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        }
                    } else {
                        mAdapter.addData(info.getRows());
                    }
                    if (info.getTotal() <= page * rows) {
                        srlv.onNoMore("没有更多了");
                    } else {
                        srlv.complete();
                    }
                    totalComments = info.getTotal();
                } else {
                    ToastUtils.showToast(info.getErrorMsg());
                }
                break;

            case Constans.TYPE_TEN:
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("评论成功");
                    onRefresh();
                    et_comment.setText("");
                    emojiPopup.dismiss();
                    CommonUtils.hideKeyBorad(ll_rootview.getContext(), ll_rootview, true);
                    et_comment.clearFocus();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }

                break;
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
        if (what == Constans.TYPE_NINE) {
            if (srlv.isRefreshing()) {
                srlv.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (srlv.isLoadingMore()) {
                srlv.stopLoadingMore();
            }
        }
    }

    @Override
    public void clickGood(int position, final YyhdCommentInfo.RowsBean rowsBean) {
        mCore.YyhdCommentGood(rowsBean.getId(), mActiveId, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (!msg.isSuccess()) {
                    ToastUtils.showToast(msg.getMsg());
                    return;
                }
                if (rowsBean.isIsGiveUp()) {
                    giveNum = rowsBean.getGiveUpCount() - 1;
                    rowsBean.setIsGiveUp(false);
                    rowsBean.setGiveUpCount(giveNum);
                } else {
                    giveNum = rowsBean.getGiveUpCount() + 1;
                    rowsBean.setIsGiveUp(true);
                    rowsBean.setGiveUpCount(giveNum);
                }
                mAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.iv_emoji)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }

    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(ll_rootview)
                .setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
                    @Override
                    public void onEmojiBackspaceClick(final View v) {
                        Log.d(TAG, "Clicked on Backspace");
                    }
                })
                .setOnEmojiClickListener(new OnEmojiClickListener() {
                    @Override
                    public void onEmojiClick(@NonNull final EmojiImageView imageView, @NonNull final Emoji emoji) {
                        Log.d(TAG, "Clicked on emoji");
                    }
                })
                .setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
                    @Override
                    public void onEmojiPopupShown() {
                        iv_emoji.setImageResource(R.drawable.ic_keyboard);
                    }
                })
                .setOnSoftKeyboardOpenListener(new OnSoftKeyboardOpenListener() {
                    @Override
                    public void onKeyboardOpen(@Px final int keyBoardHeight) {
                        Log.d(TAG, "Opened soft keyboard");
                    }
                })
                .setOnEmojiPopupDismissListener(new OnEmojiPopupDismissListener() {
                    @Override
                    public void onEmojiPopupDismiss() {
                        iv_emoji.setImageResource(R.drawable.icon_face);
                    }
                })
                .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override
                    public void onKeyboardClose() {
                        Log.d(TAG, "Closed soft keyboard");
                    }
                })
                .build(et_comment);
    }

    @OnClick(R.id.tv_send)
    public void sendComment(View view) {
        mCore.YyhdComment(mActiveId, CommonUtils.string2Unicode(et_comment.getText().toString().trim()), this);
    }

    @Override
    public void onBackPressed() {
        mIntent.putExtra("count", totalComments);
        setResult(2, mIntent);
        finish();
    }

    @Override
    protected void onBackBefore() {
        mIntent.putExtra("count", totalComments);
        setResult(2, mIntent);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        srlv.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryComment(page, rows, mActiveId, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        srlv.onLoadingMore();
        mCore.queryComment(page, rows, mActiveId, this);
    }
}
