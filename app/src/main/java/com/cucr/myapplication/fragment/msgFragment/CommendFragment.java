package com.cucr.myapplication.fragment.msgFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.MsgCommendAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.MsgBean.MsgInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.msg.MsgCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
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
import com.yanzhenjie.nohttp.rest.Response;

import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

/**
 * Created by cucr on 2018/3/16.
 */

public class CommendFragment extends Fragment implements RequersCallBackListener, SwipeRecyclerView.OnLoadListener, MsgCommendAdapter.OnClickRelpay {


    //底部回复
    @ViewInject(R.id.ll_foot)
    private LinearLayout ll_foot;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    private ImageView iv_emoji;

    //评论框
    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

    private View rootView;
    private Gson mGson;
    private MsgCommendAdapter mAdapter;
    private int page;
    private int rows;
    private boolean isRefresh;
    private SwipeRecyclerView mSrlv;
    private MsgCore mCore;
    private QueryFtInfoCore queryCore;
    //emoji表情
    private EmojiPopup emojiPopup;
    private int dataId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_common_rlv, container, false);
            ViewUtils.inject(this, rootView);
            initView();
            onRefresh();
        }
        return rootView;
    }

    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
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

    private void initView() {
        rows = 10;
        mGson = MyApplication.getGson();
        mCore = new MsgCore();
        queryCore = new QueryFtInfoCore();
        mSrlv = (SwipeRecyclerView) rootView.findViewById(R.id.srlv);
        mAdapter = new MsgCommendAdapter();
        mSrlv.getRecyclerView().setAdapter(mAdapter);
        mSrlv.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mSrlv.setOnLoadListener(this);
        mAdapter.setOnClickRelpay(this);
        setUpEmojiPopup();
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        MsgInfo msgInfo = mGson.fromJson(response.get(), MsgInfo.class);
        if (msgInfo.isSuccess()) {
            if (isRefresh) {
                mAdapter.setDate(msgInfo.getRows());
            } else {
                mAdapter.addDate(msgInfo.getRows());
            }
            if (msgInfo.getTotal() <= page * rows) {
                mSrlv.onNoMore("没有更多了");
            } else {
                mSrlv.complete();
            }
        } else {
            ToastUtils.showToast(msgInfo.getErrorMsg());
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
        if (what == Constans.TYPE_ONE) {
            if (mSrlv.isRefreshing()) {
                mSrlv.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (mSrlv.isLoadingMore()) {
                mSrlv.stopLoadingMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mSrlv.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryMsgInfo(page, rows, 4, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        mSrlv.onLoadingMore();
        mCore.queryMsgInfo(page, rows, 4, this);
    }

    @Override
    public void clickReplay(int dataId) {
        this.dataId = dataId;
        ll_foot.setVisibility(View.VISIBLE);
        et_comment.setFocusable(true);
        et_comment.setFocusableInTouchMode(true);
        et_comment.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    //emoji表情
    @OnClick(R.id.iv_emoji)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }
    }

    //发送按钮
    @OnClick(R.id.tv_send)
    public void clickSend(View view) {
        //一级评论传-1
        queryCore.toComment(dataId, -1, CommonUtils.string2Unicode(et_comment.getText().toString().trim()), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    ToastUtils.showToast("评论成功!");
                    //查询一遍
                    onRefresh();
                    et_comment.setText("");
                    emojiPopup.dismiss();
                    CommonUtils.hideKeyBorad(MyApplication.getInstance(), rootView, true);
                    et_comment.clearFocus();
                    ll_foot.setVisibility(View.GONE);
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

}
