package com.cucr.myapplication.activity.photos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.comment.XingWenCommentActivity;
import com.cucr.myapplication.adapter.PagerAdapter.HomePhotoPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
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

public class PhotoActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnFocusChangeListener, Animation.AnimationListener, HomePhotoPagerAdapter.OnItemClick {

    //ViewPager
    @ViewInject(R.id.vp_photo)
    private ViewPager vp_photo;

    //根布局
    @ViewInject(R.id.activity_photo)
    private View rootview;

    //标题
    @ViewInject(R.id.tv_photo_title)
    private TextView tv_photo_title;

    //内容
    @ViewInject(R.id.tv_content)
    private TextView tv_content;

    //索引
    @ViewInject(R.id.tv_posi)
    private TextView tv_posi;

    //评论框
    @ViewInject(R.id.et_comment)
    private EmojiEditText et_comment;

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

    //图片文字
    @ViewInject(R.id.rlv_shadow)
    private RelativeLayout rlv_shadow;

    private QueryFtInfos.RowsBean photos;
    private Intent mIntent;
    //emoji表情
    private EmojiPopup emojiPopup;
    private Integer giveNum;
    private QueryFtInfoCore queryCore;
    private boolean isShow;
    private TranslateAnimation mFootShow;
    private TranslateAnimation mFootHide;
    private boolean canStarAni;//播放完成才能开始新的动画
    private DialogShareStyle mDialog;

    @Override
    protected void initChild() {

        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_photo;
    }

    private void initView() {
        photos = (QueryFtInfos.RowsBean) getIntent().getSerializableExtra("date");
        queryCore = new QueryFtInfoCore();
        upDataInfo();
        initAni();
        initDialog();
        mIntent = new Intent(MyApplication.getInstance(), XingWenCommentActivity.class);
        mIntent.putExtra("rowsBean", photos);
        et_comment.clearFocus();
        et_comment.setOnFocusChangeListener(this);
        setUpEmojiPopup();
        HomePhotoPagerAdapter adapter = new HomePhotoPagerAdapter(photos);
        adapter.setOnItemClick(this);
        vp_photo.setAdapter(adapter);
        vp_photo.addOnPageChangeListener(this);
        onPageSelected(0);
    }

    private void initDialog() {

        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_posi.setText(position + 1 + "/" + photos.getAttrFileList().size());
        tv_photo_title.setText(photos.getTitle());
        tv_content.setText(photos.getAttrFileList().get(position).getFileContent());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
        if (photos.isIsGiveUp()) {
            giveNum = photos.getGiveUpCount() - 1;
            photos.setIsGiveUp(false);
            photos.setGiveUpCount(giveNum);
        } else {
            giveNum = photos.getGiveUpCount() + 1;
            photos.setIsGiveUp(true);
            photos.setGiveUpCount(giveNum);
        }
        upDataInfo();
        queryCore.ftGoods(photos.getId(), new OnCommonListener() {
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
        tv_givecount.setText(photos.getGiveUpCount() + "");
        iv_zan.setImageResource(photos.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_comment_count.setText(photos.getCommentCount() + "");
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
        queryCore.toComment(photos.getId(), -1, CommonUtils.string2Unicode(et_comment.getText().toString().trim()),
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
                            photos.setCommentCount(photos.getCommentCount() + 1);
                            upDataInfo();
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

    private void initAni() {

        mFootShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        mFootShow.setDuration(500);
        mFootHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f
        );
        mFootShow.setInterpolator(new DecelerateInterpolator());
        mFootHide.setInterpolator(new DecelerateInterpolator());
        mFootHide.setDuration(500);
        mFootHide.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        canStarAni = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        canStarAni = false;

        if (animation == mFootHide) {
            rlv_shadow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void clickIyem() {
        if (canStarAni) {
            return;
        }

        if (isShow) {
            rlv_shadow.setVisibility(View.VISIBLE);
            rlv_shadow.startAnimation(mFootShow);
        } else {
            rlv_shadow.startAnimation(mFootHide);
        }
        isShow = !isShow;
    }

}
