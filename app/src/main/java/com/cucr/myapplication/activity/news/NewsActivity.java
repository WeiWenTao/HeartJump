package com.cucr.myapplication.activity.news;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.comment.XingWenCommentActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
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

public class NewsActivity extends BaseActivity implements View.OnFocusChangeListener {

    //根布局
    @ViewInject(R.id.activity_news)
    private ViewGroup rootview;

    //评论框
    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

    @ViewInject(R.id.web_content)
    private WebView web_content;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    private ImageView iv_emoji;

    //评论和点赞
    @ViewInject(R.id.ll_comment_good)
    private LinearLayout ll_comment_good;

    //表情和发送
    @ViewInject(R.id.ll_emoji_send)
    private LinearLayout ll_emoji_send;

    //评论数量
    @ViewInject(R.id.tv_comment_count)
    private TextView tv_comment_count;

    //点赞红心
    @ViewInject(R.id.iv_zan)
    private ImageView iv_zan;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    private TextView tv_givecount;

    private Intent mIntent;
    //emoji表情
    private EmojiPopup emojiPopup;
    private QueryFtInfos.RowsBean rowsBean;
    private Integer giveNum;
    private QueryFtInfoCore queryCore;
    private DialogShareStyle mDialog;
    private MyWaitDialog mWaitDialog;

    @Override
    protected void initChild() {
        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_news;
    }


    private void initView() {
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        queryCore = new QueryFtInfoCore();
        rowsBean = (QueryFtInfos.RowsBean) getIntent().getSerializableExtra("date");
        upDataInfo();
        mIntent = new Intent(MyApplication.getInstance(), XingWenCommentActivity.class);
        mIntent.putExtra("rowsBean", rowsBean);
        et_comment.clearFocus();
        et_comment.setOnFocusChangeListener(this);

        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
        setUpEmojiPopup();
        //封装头文件 根据屏幕缩放图片比例
        String sHead = "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />" +
                "<style>img{max-width:100% !important;height:auto !important;}</style>"
                + "<style>body{max-width:100% !important;}</style>" + "</head><body>";

//      web_content.loadDataWithBaseURL(null, rowsBean.getContent(), "text/html" , "utf-8", null);
        web_content.setWebViewClient(new WebViewClient());
        web_content.loadDataWithBaseURL(null, sHead + rowsBean.getContent() + "</body></html>", "text/html", "UTF-8", null);

    }

    //emoji表情
    @OnClick(R.id.iv_emoji)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }


    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootview)
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


    @OnClick(R.id.ll_comment)
    public void clickCommon(View view) {
        startActivity(mIntent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ll_emoji_send.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        ll_comment_good.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
    }

    //点赞时
    @OnClick(R.id.ll_goods)
    public void zan(View view) {
        if (rowsBean.isIsGiveUp()) {
            giveNum = rowsBean.getGiveUpCount() - 1;
            rowsBean.setIsGiveUp(false);
            rowsBean.setGiveUpCount(giveNum);
        } else {
            giveNum = rowsBean.getGiveUpCount() + 1;
            rowsBean.setIsGiveUp(true);
            rowsBean.setGiveUpCount(giveNum);
        }
        upDataInfo();
        queryCore.ftGoods(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    //更新数据
    private void upDataInfo() {
        tv_givecount.setText(rowsBean.getGiveUpCount() + "");
        iv_zan.setImageResource(rowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_comment_count.setText(rowsBean.getCommentCount() + "");
    }

    //返回操作
    @Override
    protected void onBackBefore() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        }
    }

    //发送按钮
    @OnClick(R.id.tv_send)
    public void clickSend(View view) {
        //一级评论传-1
        queryCore.toComment(rowsBean.getId(), -1, CommonUtils.string2Unicode(et_comment.getText().toString().trim()),
                new RequersCallBackListener() {
                    @Override
                    public void onRequestSuccess(int what, Response<String> response) {
                        CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                        if (commonRebackMsg.isSuccess()) {
                            ToastUtils.showToast("评论成功!");
                            //查询一遍
                            et_comment.setText("");
                            emojiPopup.dismiss();
                            CommonUtils.hideKeyBorad(MyApplication.getInstance(), rootview, true);
                            et_comment.clearFocus();
                            rowsBean.setCommentCount(rowsBean.getCommentCount() + 1);
                            upDataInfo();
                        } else {
                            ToastUtils.showToast(commonRebackMsg.getMsg());
                        }
                    }

                    @Override
                    public void onRequestStar(int what) {
                        mWaitDialog.show();
                    }

                    @Override
                    public void onRequestError(int what, Response<String> response) {

                    }

                    @Override
                    public void onRequestFinish(int what) {
                        mWaitDialog.dismiss();
                    }
                });
    }
}
