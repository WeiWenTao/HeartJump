package com.cucr.myapplication.activity.comment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.fenTuan.FtCommentInfo;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.funTuanAndXingWen.FtCommentCore;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.RefreshLayout;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class XingWenCommentActivity extends BaseActivity implements View.OnFocusChangeListener, FtCatgoryAadapter.OnClickCommentGoods, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    //根布局
    @ViewInject(R.id.rootview)
    private ViewGroup rootview;

    //评论列表
    @ViewInject(R.id.lv_ft_catgory)
    private ListView lv_ft_catgory;

    //点赞
    @ViewInject(R.id.iv_zan)
    private ImageView iv_zan;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    private TextView tv_givecount;

    //评论框
    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

    //评论数量
    @ViewInject(R.id.tv_comment_count)
    private TextView tv_comment_count;

    //评论和点赞
    @ViewInject(R.id.ll_comment_good)
    private LinearLayout ll_comment_good;

    //表情和发送
    @ViewInject(R.id.ll_emoji_send)
    private LinearLayout ll_emoji_send;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    private ImageView iv_emoji;

    //刷新
    @ViewInject(R.id.ref)
    private RefreshLayout mRefreshLayout;

    //状态布局
    @ViewInject(R.id.iv_empty)
    private ImageView iv_empty;


    private boolean mIsFormConmmomd;
    private QueryFtInfos.RowsBean mRowsBean;
    private FtCommentCore mCommentCore;
    private Integer page;
    private Integer rows;
    private Integer giveNum;
    private QueryFtInfoCore queryCore;
    private Intent mIntent;
    private FtCatgoryAadapter mAdapter;
    //emoji表情
    private EmojiPopup emojiPopup;
    private List<FtCommentInfo.RowsBean> mRows;
    private List<FtCommentInfo.RowsBean> allRows;

    @Override
    protected void initChild() {
        EventBus.getDefault().register(this);
        initTitle("全部评论");
        rows = 5;
        allRows = new ArrayList<>();
        mRows = new ArrayList<>();
        initData();
        //阅读量
        queryCore.ftRead(mRowsBean.getId() + "");
        setUpEmojiPopup();
        initLV();
        onRefresh();
    }


    //获取传过来的数据
    private void initData() {
        //默认关闭软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mIntent = getIntent();
        mRowsBean = (QueryFtInfos.RowsBean) mIntent.getSerializableExtra("rowsBean");
        mCommentCore = new FtCommentCore();
        upDataInfo();
        queryCore = new QueryFtInfoCore();
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadListener(this);
    }

    //更新数据
    private void upDataInfo() {
        tv_givecount.setText(mRowsBean.getGiveUpCount() + "");
        iv_zan.setImageResource(mRowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_comment_count.setText(mRowsBean.getCommentCount() + "");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_xin_wen_comment_activity;
    }

    private void initLV() {
        mAdapter = new FtCatgoryAadapter(MyApplication.getInstance());
        mAdapter.setClickGoodsListener(this);
        lv_ft_catgory.setAdapter(mAdapter);
        et_comment.setOnFocusChangeListener(this);
    }


    //点击评论时
    @OnClick(R.id.ll_comment)
    public void comment(View view) {
        lv_ft_catgory.smoothScrollToPositionFromTop(1, 75, 300);
    }

    //点赞时
    @OnClick(R.id.ll_goods)
    public void zan(View view) {
        queryCore.ftGoods(mRowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (mRowsBean.isIsGiveUp()) {
                        giveNum = mRowsBean.getGiveUpCount() - 1;
                        mRowsBean.setIsGiveUp(false);
                        mRowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = mRowsBean.getGiveUpCount() + 1;
                        mRowsBean.setIsGiveUp(true);
                        mRowsBean.setGiveUpCount(giveNum);
                    }
                    upDataInfo();
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    //emoji表情
    @OnClick(R.id.iv_emoji)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }

    //发送按钮
    @OnClick(R.id.tv_send)
    public void clickSend(View view) {
        //一级评论传-1
        queryCore.toComment(mRowsBean.getId(), -1, CommonUtils.string2Unicode(et_comment.getText().toString().trim()), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    ToastUtils.showToast("评论成功!");
                    //查询一遍
                    onRefresh();
                    et_comment.setText("");
                    emojiPopup.dismiss();
                    CommonUtils.hideKeyBorad(MyApplication.getInstance(), rootview, true);
                    et_comment.clearFocus();
                    mRowsBean.setCommentCount(mRowsBean.getCommentCount() + 1);
                    upDataInfo();
                    //评论成功后滚动到最顶部
//                    lv_ft_catgory.smoothScrollToPositionFromTop(0, 0, 300);
                    lv_ft_catgory.setSelection(0);
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }


    @Override
    protected void onStop() {
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }
        super.onStop();
    }

    //返回操作
    @Override
    protected void onBackBefore() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            finish();
        }
    }

    //根据状态改变发送栏
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ll_emoji_send.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        ll_comment_good.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
    }

    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootview)
                .setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
                    @Override
                    public void onEmojiBackspaceClick(final View v) {
                        MyLogger.jLog().i("表情_Clicked on Backspace");
                    }
                })
                .setOnEmojiClickListener(new OnEmojiClickListener() {
                    @Override
                    public void onEmojiClick(@NonNull final EmojiImageView imageView, @NonNull final Emoji emoji) {
                        MyLogger.jLog().i("表情_Clicked on emoji");
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
                        MyLogger.jLog().i("表情_Opened soft keyboard");
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
                        MyLogger.jLog().i("表情_Closed soft keyboard");
                    }
                })
                .build(et_comment);
    }

    @Override
    public void clickGoods(final FtCommentInfo.RowsBean rowsBean) {
        mCommentCore.ftCommentGoods(rowsBean.getContentId(), rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (rowsBean.getIsGiveUp()) {
                        giveNum = rowsBean.getGiveUpCount() - 1;
                        rowsBean.setIsGiveUp(false);
                        rowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = rowsBean.getGiveUpCount() + 1;
                        rowsBean.setIsGiveUp(true);
                        rowsBean.setGiveUpCount(giveNum);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    //点击条目  啥也不做
    @Override
    public void clickItem(FtCommentInfo.RowsBean mRowsBean, int position) {

    }

    //点击进入用户主页
    @Override
    public void clickUser(int userId) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //点击lv头部的用户时  直接跳转个人主页
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", mRowsBean.getCreateUserId());
        startActivity(intent);
    }

    //刷新
    @Override
    public void onRefresh() {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }

        //查询评论
        page = 1;
        mCommentCore.queryFtComment(mRowsBean.getId(), -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    if (ftCommentInfo.getTotal() == 0) {
                        iv_empty.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.GONE);
                    } else {
                        iv_empty.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.VISIBLE);
                    }
                    mRows = ftCommentInfo.getRows();
                    allRows.addAll(mRows);
                    mAdapter.setData(mRows);
                    if (mIsFormConmmomd) {
                        lv_ft_catgory.smoothScrollToPositionFromTop(1, 75, 500);
                        mIsFormConmmomd = false;
                    }
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    //加载
    @Override
    public void onLoad() {
        if (mRefreshLayout.isRefreshing()) {
            return;
        }
        page++;
        mCommentCore.queryFtComment(mRowsBean.getId(), -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    mRows = ftCommentInfo.getRows();
                    allRows.addAll(mRows);
                    mAdapter.addData(mRows);
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    //请求完成  如果还在加载  就停止加载(包括无网络情况)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinish(EventRequestFinish event) {
        if (event.getWhat().equals(HttpContans.ADDRESS_FT_COMMENT_QUERY)) {
            mRefreshLayout.setRefreshing(false);
            mRefreshLayout.setLoading(false);
        }
    }

}
