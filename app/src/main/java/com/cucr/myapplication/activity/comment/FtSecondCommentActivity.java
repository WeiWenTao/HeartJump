package com.cucr.myapplication.activity.comment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtAllCommentAadapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.fenTuan.FtCommentInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.core.funTuanAndXingWen.FtCommentCore;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogReportTo;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
import com.cucr.myapplication.widget.refresh.RefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
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

import java.util.List;

import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

public class FtSecondCommentActivity extends BaseActivity implements View.OnFocusChangeListener, FtAllCommentAadapter.OnClickCommentGoods, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener, DialogReportTo.OnClickShareTo, RequersCallBackListener {

    //root_view
    @ViewInject(R.id.root_view)
    private LinearLayout root_view;

    //ListView
    @ViewInject(R.id.lv_all_comment)
    private ListView lv_all_comment;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    private TextView tv_givecount;
    //点赞
    @ViewInject(R.id.iv_zan)
    private ShineButton iv_zan;

    //表情和发送
    @ViewInject(R.id.ll_emoji_send)
    private LinearLayout ll_emoji_send;

    //评论和点赞
    @ViewInject(R.id.ll_comment_good)
    private LinearLayout ll_comment_good;

    //评论数量
    @ViewInject(R.id.tv_comment_count)
    private TextView tv_comment_count;

    //评论框
    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    private ImageView iv_emoji;

    //刷新控件
    @ViewInject(R.id.ref)
    private RefreshLayout ref;

    private FtCommentInfo.RowsBean mRowsBean;
    private QueryFtInfoCore queryCore;
    private Integer giveNum;
    //emoji表情
    private EmojiPopup emojiPopup;
    private int page;
    private int rows;
    private FtCommentCore mCommentCore;
    private FtAllCommentAadapter mAdapter;
    private DialogReportTo mDialogReportTo;
    private AppCore mCore;
    private int dataId;
    private int creatUserId;
    private FtCommentInfo mFtCommentInfo;

    @Override
    protected void initChild() {

        EventBus.getDefault().register(this);
        page = 1;
        rows = 15;
        initDatas();
        initViews();
        onRefresh();
        initDialog();

    }

    private void initDialog() {
        mCore = new AppCore();
        mDialogReportTo = new DialogReportTo(this, R.style.MyDialogStyle);
        Window shareWindow = mDialogReportTo.getWindow();
        shareWindow.setGravity(Gravity.BOTTOM);
        shareWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mDialogReportTo.setOnClickBt(this);

    }

    private void initDatas() {
        mCommentCore = new FtCommentCore();
        queryCore = new QueryFtInfoCore();
        mRowsBean = (FtCommentInfo.RowsBean) getIntent().getSerializableExtra("mRows");
        dataId = mRowsBean.getId();
        creatUserId = mRowsBean.getUser().getId();
        ref.setOnRefreshListener(this);
        ref.setOnLoadListener(this);
        upDataInfo(false);
        setUpEmojiPopup();
    }

    private void initViews() {
        et_comment.setOnFocusChangeListener(this);
        View head = View.inflate(this, R.layout.header_lv_ft_all_comment, null);
        lv_all_comment.addHeaderView(head);
        initHead(head);
        mAdapter = new FtAllCommentAadapter(this);
        mAdapter.setClickGoodsListener(this);
        lv_all_comment.setAdapter(mAdapter);
    }

    private void initHead(View lvHead) {
        //头像
        ImageView iv_pic = (ImageView) lvHead.findViewById(R.id.iv_userhead);
        //昵称
        TextView tv_neckname = (TextView) lvHead.findViewById(R.id.tv_username);
        //时间来源
        TextView tv_time_form = (TextView) lvHead.findViewById(R.id.tv_comment_time);
        //评论内容
        TextView tv_content = (TextView) lvHead.findViewById(R.id.tv_comment_content);

        iv_pic.setOnClickListener(this);
        tv_neckname.setOnClickListener(this);
        //设置数据
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean.getUser().getUserHeadPortrait(), iv_pic, MyApplication.getImageLoaderOptions());
        tv_neckname.setText(mRowsBean.getUser().getName());
        tv_time_form.setText(mRowsBean.getReleaseTime());
        tv_content.setText(CommonUtils.unicode2String(mRowsBean.getComment()));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ft_second_comment;
    }

    private void upDataInfo(boolean ani) {
        tv_givecount.setText(mRowsBean.getGiveUpCount() + "");
        if (mRowsBean.getIsGiveUp()) {
            iv_zan.setChecked(true, ani);
        } else {
            iv_zan.setChecked(false, false);
            iv_zan.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));

        }
        tv_comment_count.setText(mRowsBean.getChildList().size() + "");
    }

    @OnClick(R.id.iv_zan)
    public void zan_(View view) {
        clickGoods(view);
    }

    @OnClick(R.id.ll_goods)
    public void clickGoods(View view) {
        if (mRowsBean == null) {
            return;
        }

        if (mRowsBean.getIsGiveUp()) {
            iv_zan.setChecked(false, true);
            iv_zan.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));
        } else {
            iv_zan.setChecked(true, true);
        }

        if (mRowsBean.getIsGiveUp()) {
            giveNum = mRowsBean.getGiveUpCount() - 1;
            mRowsBean.setIsGiveUp(false);
            mRowsBean.setGiveUpCount(giveNum);
        } else {
            giveNum = mRowsBean.getGiveUpCount() + 1;
            mRowsBean.setIsGiveUp(true);
            mRowsBean.setGiveUpCount(giveNum);
        }
        upDataInfo(true);
        mCommentCore.ftCommentGoods(mRowsBean.getContentId(), mRowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                } else {
//                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ll_emoji_send.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        ll_comment_good.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
    }

    //emoji表情
    @OnClick(R.id.iv_emoji)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }

    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(root_view)
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

    //点击评论时
    @OnClick(R.id.ll_comment)
    public void comment(View view) {
//        lv_all_comment.setSelection(1);
        lv_all_comment.smoothScrollToPositionFromTop(1, 0);
    }


    //发送按钮
    @OnClick(R.id.tv_send)
    public void clickSend(View view) {
        //一级评论传-1
        queryCore.toComment(mRowsBean.getContentId(), mRowsBean.getId(), CommonUtils.string2Unicode(et_comment.getText().toString().trim()),
                new RequersCallBackListener() {
                    @Override
                    public void onRequestSuccess(int what, Response<String> response) {
                        CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                        if (commonRebackMsg.isSuccess()) {
                            ToastUtils.showToast("评论成功!");
                            //查询一遍
                            onRefresh();
                            et_comment.setText("");
                            emojiPopup.dismiss();
                            CommonUtils.hideKeyBorad(FtSecondCommentActivity.this, root_view, true);
                            et_comment.clearFocus();
                            upDataInfo(false);
                            mRowsBean.setCommentCount(mRowsBean.getCommentCount() + 1);
                        } else {
                            ToastUtils.showToast(commonRebackMsg.getMsg());
                        }
                    }

                    @Override
                    public void onRequestStar(int what) {
                        waitDialog.show();
                    }

                    @Override
                    public void onRequestError(int what, Response<String> response) {

                    }

                    @Override
                    public void onRequestFinish(int what) {
                        waitDialog.dismiss();
                    }
                });
    }

    //点赞
    @Override
    public void clickGoods(final FtCommentInfo.RowsBean childListBean, ShineButton sib) {

        sib.init(this);
        if (childListBean.getIsGiveUp()) {
            sib.setChecked(false, true);
            sib.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));
        } else {
            sib.setChecked(true, true);
        }

        if (childListBean.getIsGiveUp()) {
            giveNum = childListBean.getGiveUpCount() - 1;
            childListBean.setIsGiveUp(false);
            childListBean.setGiveUpCount(giveNum);
        } else {
            giveNum = childListBean.getGiveUpCount() + 1;
            childListBean.setIsGiveUp(true);
            childListBean.setGiveUpCount(giveNum);
        }
        mAdapter.notifyDataSetChanged();

        mCommentCore.ftCommentGoods(childListBean.getContentId(), childListBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                } else {
//                    ToastUtils.showToast(FtSecondCommentActivity.this, commonRebackMsg.getMsg());
                }
            }
        });
    }

    @Override
    public void clickUser(int userId) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
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
            setData();
        }

    }

    private void setData() {
        Intent intent = getIntent();
        intent.putExtra("rowsBean", mRowsBean);
        setResult(Constans.RESULT_CODE, intent);
        MyLogger.jLog().i("rowsBean:" + mRowsBean);
    }

    @Override
    public void onBackPressed() {
        setData();
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", mRowsBean.getUser().getId());
        startActivity(intent);
    }

    //刷新
    @Override
    public void onRefresh() {
        if (!ref.isRefreshing()) {
            ref.setRefreshing(true);
        }
        page = 1;
        mCommentCore.queryFtComment(mRowsBean.getContentId(), mRowsBean.getId(), page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mFtCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (mFtCommentInfo.isSuccess()) {
                    List<FtCommentInfo.RowsBean> rows = mFtCommentInfo.getRows();
                    mAdapter.setData(rows);
                    tv_comment_count.setText(mFtCommentInfo.getTotal() + "");
                } else {
                    ToastUtils.showToast(mFtCommentInfo.getErrorMsg());
                }
                lv_all_comment.setSelection(0);
            }
        });
    }

    //加载
    @Override
    public void onLoad() {
        if (ref.isRefreshing()) {
            return;
        }
        page++;
        mCommentCore.queryFtComment(mRowsBean.getContentId(), mRowsBean.getId(), page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    List<FtCommentInfo.RowsBean> rows = ftCommentInfo.getRows();
                    mAdapter.addData(rows);
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
            ref.setRefreshing(false);
            ref.setLoading(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.iv_more)
    public void clickShowDialo(View view) {
        mDialogReportTo.show();
    }

    @Override
    public void clickReportTo() {
        mCore.report(3, dataId + "", creatUserId, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
        if (msg.isSuccess()) {
            ToastUtils.showToast("我们收到您的举报啦");
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

    @Override
    public void clickitems(int dataId, int userId) {
        this.dataId = dataId;
        this.creatUserId = userId;
        mDialogReportTo.show();
    }

}
