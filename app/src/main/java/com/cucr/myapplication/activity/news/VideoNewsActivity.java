package com.cucr.myapplication.activity.news;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
import com.cucr.myapplication.bean.fenTuan.FtCommentInfo;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.funTuanAndXingWen.FtCommentCore;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
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

import java.util.List;

import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

public class VideoNewsActivity extends BaseActivity implements View.OnFocusChangeListener {

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
    private ShineButton iv_zan;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    private TextView tv_givecount;

    //弹幕1
    @ViewInject(R.id.ll_dm_1)
    private LinearLayout ll_dm1;

    //弹幕头像1
    @ViewInject(R.id.iv_dm_1)
    private ImageView iv_dm1;

    //弹幕文字1
    @ViewInject(R.id.tv_dm_1)
    private TextView tv_dm1;

    //弹幕2
    @ViewInject(R.id.ll_dm_2)
    private LinearLayout ll_dm2;

    //弹幕头像2
    @ViewInject(R.id.iv_dm_2)
    private ImageView iv_dm2;

    //弹幕文字2
    @ViewInject(R.id.tv_dm_2)
    private TextView tv_dm2;

    //加载页面
    @ViewInject(R.id.ll_load)
    private LinearLayout ll_load;

    private Intent mIntent;
    //emoji表情
    private EmojiPopup emojiPopup;
    private QueryFtInfos.RowsBean rowsBean;
    private Integer giveNum;
    private QueryFtInfoCore queryCore;
    private DialogShareStyle mDialog;
    private MyWaitDialog mWaitDialog;
    private DialogShareStyle mShareDialog;
    private int dmCount;
    private Handler mHandler;
    private ObjectAnimator mRa1;
    private boolean isFinish;
    private ObjectAnimator mRa2;
    private List<FtCommentInfo.RowsBean> mDmRows;
    private FtCommentCore mCommentCore;


    @Override
    protected void initChild() {
        initView();
        initDm();
        isFinish = true;
        mCommentCore = new FtCommentCore();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCommentCore.queryFtComment(rowsBean.getId(), -1, 1, 20, new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                        if (ftCommentInfo.isSuccess()) {
//------------------------------------------评论弹幕-------------------------------------------------
                            mDmRows = ftCommentInfo.getRows();
                            if (ftCommentInfo.getTotal() != 0) {
                                //开启弹幕1
                                tv_dm1.setText(CommonUtils.unicode2String(mDmRows.get(0).getComment()));
                                ImageLoader.getInstance().displayImage(mDmRows.get(0).getUser().getUserHeadPortrait(), iv_dm1, MyApplication.getImageLoaderOptions());
                                starDm1();
                                if (mDmRows.size() > 1) {
                                    //开启弹幕2
                                    starDm2();
                                }
                            }
//--------------------------------------------------------------------------------------------------
                        } else {
                            ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                        }
                    }
                });
            }
        }, 2000);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_news;
    }


    private void initView() {
        mShareDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window1 = mShareDialog.getWindow();
        window1.setGravity(Gravity.BOTTOM);
        window1.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画

        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        queryCore = new QueryFtInfoCore();
        rowsBean = (QueryFtInfos.RowsBean) getIntent().getSerializableExtra("data");
        upDataInfo(false);
        mIntent = new Intent(MyApplication.getInstance(), XingWenCommentActivity.class);
        mIntent.putExtra("rowsBean", rowsBean);
        et_comment.clearFocus();
        et_comment.setOnFocusChangeListener(this);

        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
        setUpEmojiPopup();
        InitWeb();
    }

    private void InitWeb() {
        String url = rowsBean.getLocationUrl();
        WebSettings settings = web_content.getSettings();

        //webView  加载视频，下面五行必须

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setJavaScriptEnabled(true);//支持js
        settings.setPluginState(WebSettings.PluginState.ON);// 支持插件
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小  无效
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        web_content.setWebChromeClient(new WebChromeClient()); //设置支持弹窗
        web_content.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                MyLogger.jLog().i("加载完成");
                ll_load.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                MyLogger.jLog().i("开始加载 url:" + url);
            }
        });
        web_content.loadUrl(url);
    }

    //弹幕
    private void initDm() {

        mHandler = new Handler();
        mRa1 = ObjectAnimator.ofFloat(ll_dm1, "translationY", 0, -600);
        mRa1.setDuration(4000);
        mRa1.setInterpolator(new AccelerateInterpolator());

        mRa2 = ObjectAnimator.ofFloat(ll_dm2, "translationY", 0, -600);
        mRa2.setDuration(4000);
        mRa2.setInterpolator(new AccelerateInterpolator());

    }

    //设置弹幕 1
    private void starDm1() {
        ll_dm1.setVisibility(View.VISIBLE);
        mRa1.setRepeatCount((int) (mDmRows.size() - 0.1 / 2));
        mRa1.start();
        mHandler.post(new Runnable() {
            public void run() {
                if (dmCount < mDmRows.size()) {
                    mHandler.postDelayed(this, 3950);
                    tv_dm1.setText(CommonUtils.unicode2String(mDmRows.get(dmCount).getComment()));
                    ImageLoader.getInstance().displayImage(mDmRows.get(dmCount).getUser().getUserHeadPortrait(), iv_dm1, MyApplication.getImageLoaderOptions());
                    dmCount++;
                } else {
                    ll_dm1.clearAnimation();
                    ll_dm1.setVisibility(View.GONE);
                    isFinish = true;
                }
            }
        });
    }

    //设置弹幕2
    private void starDm2() {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                ll_dm2.setVisibility(View.VISIBLE);
                mRa2.setRepeatCount(mDmRows.size() / 2 - 1);
                mRa2.start();
            }
        }, 2000);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (dmCount < mDmRows.size()) {
                    mHandler.postDelayed(this, 3950);
                    tv_dm2.setText(CommonUtils.unicode2String(mDmRows.get(dmCount).getComment()));
                    ImageLoader.getInstance().displayImage(mDmRows.get(dmCount).getUser().getUserHeadPortrait(), iv_dm2, MyApplication.getImageLoaderOptions());
                    dmCount++;
                } else {
                    ll_dm2.clearAnimation();
                    ll_dm2.setVisibility(View.GONE);
                    isFinish = true;
                }
            }
        }, 2000);
    }

    //emoji表情
    @OnClick(R.id.iv_emoji)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }

    //点击分享
    @OnClick(R.id.iv_news_share)
    public void clickShare(View view) {
        String url = "";
        if (rowsBean.getType() != Constans.TYPE_TEXT) {
            url = HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(0).getFileUrl();
        }
        mShareDialog.setData(new ShareEntity(rowsBean.getTitle(), "追爱豆,领红包,尽在心跳互娱", HttpContans.ADDRESS_NEWS_SHARE + rowsBean.getId(), url));
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

    @OnClick(R.id.iv_zan)
    public void zan_(View view) {
        zan(view);
    }

    //点赞时
    @OnClick(R.id.ll_goods)
    public void zan(View view) {
        if (rowsBean == null) {
            return;
        }
        if (rowsBean.isIsGiveUp()) {
            iv_zan.setChecked(false, true);
            iv_zan.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));
        } else {
            iv_zan.setChecked(true, true);
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
        upDataInfo(true);
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
    private void upDataInfo(boolean ani) {
        tv_givecount.setText(rowsBean.getGiveUpCount() + "");
        if (rowsBean.isIsGiveUp()) {
            iv_zan.setChecked(true, ani);
        } else {
            iv_zan.setChecked(false, false);
            iv_zan.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));

        }
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
                            //添加一条评论弹幕
                            addDM(et_comment.getText().toString().trim());
                            //查询一遍
                            et_comment.setText("");
                            emojiPopup.dismiss();
                            CommonUtils.hideKeyBorad(MyApplication.getInstance(), rootview, true);
                            et_comment.clearFocus();
                            rowsBean.setCommentCount(rowsBean.getCommentCount() + 1);
                            upDataInfo(false);
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

    private void addDM(String trim) {
        mDmRows.add(dmCount, new FtCommentInfo.RowsBean(trim, new FtCommentInfo.RowsBean.UserBean((String) SpUtil.getParam(SpConstant.SP_USERHEAD, ""))));
        if (isFinish) {
            tv_dm1.setText(CommonUtils.unicode2String(trim));
            ll_dm1.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage((String) SpUtil.getParam(SpConstant.SP_USERHEAD, ""), iv_dm1);
            mRa1.setRepeatCount(0);
            mRa1.start();
        }
    }
}
