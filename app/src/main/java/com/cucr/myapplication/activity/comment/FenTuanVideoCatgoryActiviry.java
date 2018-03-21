package com.cucr.myapplication.activity.comment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.adapter.PagerAdapter.DaShangPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventDsSuccess;
import com.cucr.myapplication.bean.eventBus.EventDuiHuanSuccess;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.bean.fenTuan.FtCommentInfo;
import com.cucr.myapplication.bean.fenTuan.FtGiftsInfo;
import com.cucr.myapplication.bean.fenTuan.SignleFtInfo;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.funTuanAndXingWen.FtCommentCore;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaShangStyle;
import com.cucr.myapplication.widget.refresh.RefreshLayout;
import com.cucr.myapplication.widget.viewpager.NoScrollPager;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
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
import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tcking.github.com.giraffeplayer.GiraffePlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;

import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

public class FenTuanVideoCatgoryActiviry extends Activity implements View.OnFocusChangeListener, FtCatgoryAadapter.OnClickCommentGoods, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener, RequersCallBackListener {

    //根布局
    @ViewInject(R.id.rootview)
    private ViewGroup rootview;

    //评论栏
    @ViewInject(R.id.ll_common_bar)
    private LinearLayout ll_common_bar;

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

    //礼物和背包 ViewPager
    @ViewInject(R.id.vp_dahsnag)
    private NoScrollPager vp_dahsnag;

    //礼物
    @ViewInject(R.id.tv_gift)
    private TextView gift;

    //礼物动画
    @ViewInject(R.id.iv_gift)
    private ImageView iv_gift;

    //背包
    @ViewInject(R.id.tv_backpack)
    private TextView backpack;

    //刷新
    @ViewInject(R.id.ref)
    private RefreshLayout mRefreshLayout;

    //播放器
    private GiraffePlayer player;

    private DialogDaShangStyle daShangStyle;
    private boolean mHasPicture;
    private boolean mIsFormConmmomd;
    private SignleFtInfo.ObjBean mRowsBean;
    private FtCommentCore mCommentCore;
    private Integer page;
    private Integer rows;
    private Integer giveNum;
    private QueryFtInfoCore queryCore;
    private Intent mIntent;
    private FtCatgoryAadapter mAdapter;
    //emoji表情
    private EmojiPopup emojiPopup;
    private TextView mTv_all_comment;
    private List<FtCommentInfo.RowsBean> mRows;
    private List<FtCommentInfo.RowsBean> allRows;
    private int position;
    private TextView tv_dashang;
    //打赏框
    private PopupWindow popWindow;
    private DaShangPagerAdapter mDaShangPagerAdapter;
    private PayCenterCore mPayCenterCore;
    private Gson mGson;
    private HttpProxyCacheServer mProxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_tuan_video_catgory_activiry);
        ViewUtils.inject(this);
        initChild();
    }

    protected void initChild() {
        mGson = MyApplication.getGson();
        EventBus.getDefault().register(this);
        rows = 5;
        allRows = new ArrayList<>();
        mRows = new ArrayList<>();
        initBar();
        initData();
        initVideo();
        queryHeadInfo();
        //阅读量
        setUpEmojiPopup();
        initGiftAndBackPack();
    }

    private void initBar() {

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        //设置导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && CommonUtils.checkDeviceHasNavigationBar(MyApplication.getInstance())) {
            boolean b = CommonUtils.checkDeviceHasNavigationBar(MyApplication.getInstance());
            MyLogger.jLog().i("hasNB?" + b);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.blue_black));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_common_bar.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, ultimateBar.getNavigationHeight(MyApplication.getInstance()));
            ll_common_bar.setLayoutParams(layoutParams);
        }
    }

    private void initVideo() {
        player = new GiraffePlayer(this);
        player.onControlPanelVisibilityChang(new GiraffePlayer.OnControlPanelVisibilityChangeListener() {
            @Override
            public void change(boolean isShowing) {
                if (player.isPlaying()) {
                    lv_ft_catgory.setSystemUiVisibility(isShowing ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });

        player.setDefaultRetryTime(5 * 1000);

        //视频缓存
        mProxy = MyApplication.getProxy(MyApplication.getInstance());

        player.onComplete(new Runnable() {
            @Override
            public void run() {
                //callback when video is finish
//                Toast.makeText(getApplicationContext(), "video play completed", Toast.LENGTH_SHORT).show();
            }
        }).onInfo(new GiraffePlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        Log.i("test", "buffering start" + extra);
                        //do something when buffering start
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //do something when buffering end
                        Log.i("test", "buffering end");
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                        //download speed
                        Log.i("test", "download speed");
//                        ((TextView) findViewById(R.id.tv_speed)).setText(Formatter.formatFileSize(getApplicationContext(),extra)+"/s");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        //do something when video rendering
                        Log.i("test", "video rendering");
//                        findViewById(R.id.tv_speed).setVisibility(View.GONE);
                        break;
                }
            }
        }).onError(new GiraffePlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
//                Toast.makeText(getApplicationContext(), "video play error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryHeadInfo() {
        String dataId = getIntent().getStringExtra("dataId");
        queryCore.querySignleFtInfo(dataId, this);
    }

    //查询道具信息
    private void initGiftAndBackPack() {
        mDaShangPagerAdapter = new DaShangPagerAdapter();
        mPayCenterCore = new PayCenterCore();
        //查询用户余额
        mPayCenterCore.queryUserMoney(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (reBackMsg.isSuccess()) {
                    mDaShangPagerAdapter.setUserMoney(Double.parseDouble(reBackMsg.getMsg()));
                } else {
                    ToastUtils.showToast(reBackMsg.getMsg());
                }
            }
        });

        //查询虚拟道具信息
        queryCore.queryGift(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtGiftsInfo ftGiftsInfo = mGson.fromJson(response.get(), FtGiftsInfo.class);
                if (ftGiftsInfo.isSuccess()) {
                    mDaShangPagerAdapter.setGiftInfos(ftGiftsInfo);
                } else {
                    ToastUtils.showToast(ftGiftsInfo.getErrorMsg());
                }
            }
        });

        queryBackpack();
    }

    //查询背包信息
    private void queryBackpack() {
        //查询背包信息
        queryCore.queryBackpackInfo(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtBackpackInfo ftBackpackInfo = mGson.fromJson(response.get(), FtBackpackInfo.class);
                MyLogger.jLog().i("ftBackpackInfo:" + response.get());
                if (ftBackpackInfo.isSuccess()) {
                    mDaShangPagerAdapter.setBackpackInfos(ftBackpackInfo);
                } else {
                    ToastUtils.showToast(ftBackpackInfo.getMsg());
                }
            }
        });
    }

    //获取传过来的数据
    private void initData() {

        mIntent = getIntent();
        mCommentCore = new FtCommentCore();
        mIsFormConmmomd = mIntent.getBooleanExtra("isFormConmmomd", false);//是否是点击评论跳转过来的
//        upDataInfo();
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

    private void initLV() {
        initPopWindow();
        mAdapter = new FtCatgoryAadapter(MyApplication.getInstance());
        mAdapter.setClickGoodsListener(this);
        View inflate = View.inflate(MyApplication.getInstance(), R.layout.item_ft_video_catgory_header, null);
        mTv_all_comment = (TextView) inflate.findViewById(R.id.tv_all_comment);
        lv_ft_catgory.addHeaderView(inflate, null, true);
        lv_ft_catgory.setHeaderDividersEnabled(false);
        lv_ft_catgory.setAdapter(mAdapter);
        et_comment.setOnFocusChangeListener(this);
    }

    //打赏框
    private void initPopWindow() {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.popupwindow_dashang, null);
            ViewUtils.inject(this, view);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        popWindow.setAnimationStyle(R.style.AnimationFade);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        vp_dahsnag.setAdapter(mDaShangPagerAdapter);
    }

    //点击评论时
    @OnClick(R.id.ll_comment)
    public void comment(View view) {
        lv_ft_catgory.smoothScrollToPositionFromTop(1, 75, 300);
    }

    //点赞时
    @OnClick(R.id.ll_goods)
    public void zan(View view) {
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

        queryCore.ftGoods(mRowsBean.getId(), new OnCommonListener() {
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

    public void setData() {
        Intent intent = getIntent();
        intent.putExtra("rowsBean", mRowsBean);
        setResult(Constans.RESULT_CODE, intent);
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
                    mTv_all_comment.setText(mRowsBean.getCommentCount() == 0 ? "暂无评论" : "全部评论");
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

    @OnClick(R.id.app_video_finish)
    public void click(View view) {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        }
        setData();
        finish();
    }

    @Override
    public void onBackPressed() {
        setData();
        finish();
        if (player != null && player.onBackPressed()) {
            return;
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

    //listView条目点击监听
    @Override
    public void clickItem(FtCommentInfo.RowsBean mRowsBean, int position) {
        this.position = position;
        Intent intent = new Intent(MyApplication.getInstance(), FtSecondCommentActivity.class);
        intent.putExtra("mRows", mRowsBean);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    //点击进入用户主页
    @Override
    public void clickUser(int userId) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    //礼物
    @OnClick(R.id.tv_gift)
    public void gift(View view) {
        vp_dahsnag.setCurrentItem(0);
        gift.setBackgroundDrawable(getResources().getDrawable(R.drawable.reward_btn_bg));
        backpack.setBackgroundColor(getResources().getColor(R.color.zise));
        gift.setTextColor(getResources().getColor(R.color.xtred));
        backpack.setTextColor(getResources().getColor(R.color.zongse));
    }

    //背包
    @OnClick(R.id.tv_backpack)
    public void backpack(View view) {
        vp_dahsnag.setCurrentItem(1);
        backpack.setBackgroundDrawable(getResources().getDrawable(R.drawable.reward_btn_bg));
        backpack.setTextColor(getResources().getColor(R.color.xtred));
        gift.setBackgroundColor(getResources().getColor(R.color.zise));
        backpack.setTextColor(getResources().getColor(R.color.xtred));
        gift.setTextColor(getResources().getColor(R.color.zongse));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {

            final FtCommentInfo.RowsBean rowsBean = (FtCommentInfo.RowsBean) data.getSerializableExtra("rowsBean");

            //=============================================================================
//            如果在secondComment页面评论了  就再查一遍(如果评论从无到有)
            if (rowsBean.getCommentCount() == 1) { //如果只有一条评论就再查一遍 评论了之后不退出页面在进行二级评论时bug
                // TODO: 2017/12/12  有问题 待解决 在适配器中会报空指针 原因是 评论数量改变了 却没有评论数据
                onRefresh();
                mAdapter.notifyDataSetChanged();
            } else {
                allRows.remove(position);
                allRows.add(position, rowsBean);
                mAdapter.setData(allRows);
//                rowsBean.setCommentCount(mRowsBean.getCommentCount());
            }
            //=============================================================================

        }
    }

    //兑换成功 再次查询背包信息
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void eventData(EventDuiHuanSuccess event) {
        MyLogger.jLog().i("queryBackpack");
        queryBackpack();
    }

    //打赏成功后打赏人数增加
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDsRecord(EventDsSuccess event) {
        mRowsBean.setDssl(mRowsBean.getDssl() + 1);
        tv_dashang.setText(mRowsBean.getDssl() + "人打赏了道具");
    }


    //点击lv头部的用户时  直接跳转个人主页
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", mRowsBean.getCreateUserId());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventRewardGifts event) {
        MyLogger.jLog().i("打赏动画");
        iv_gift.setVisibility(View.VISIBLE);
        MyLogger.jLog().i(event);
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + event.getGiftPic(), iv_gift);


        switch (event.getGiftId()) {

            case Constans.GIFT_KISS:      //么么哒
                iv_gift.clearAnimation();
                AnimatorSet mAnimSet = new AnimatorSet();
                ObjectAnimator scaleXAni = ObjectAnimator.ofFloat(iv_gift, "scaleX",
                        0f, 1.4f, 1f, 1f, 1.08f, 1.15f, 1.23f, 1.3f, 1.38f, 1.45f, 1.53f, 1.6f, 1.68f, 1.75f, 1.83f, 1.9f, 1.98f, 2.05f, 2.13f, 2.2f);

                ObjectAnimator scaleYAni = ObjectAnimator.ofFloat(iv_gift, "scaleY",
                        0f, 1.4f, 1f, 1f, 1.08f, 1.15f, 1.23f, 1.3f, 1.38f, 1.45f, 1.53f, 1.6f, 1.68f, 1.75f, 1.83f, 1.9f, 1.98f, 2.05f, 2.13f, 2.2f);

                ObjectAnimator alphaAni = ObjectAnimator.ofFloat(iv_gift, "alpha",
                        1f, 1f, 1f, 1f, 0.94f, 0.88f, 0.82f, 0.76f, 0.7f, 0.64f, 0.58f, 0.52f, 0.46f, 0.4f, 0.34f, 0.28f, 0.22f, 0.16f, 0.1f, 0f);

                ObjectAnimator transYAni = ObjectAnimator.ofFloat(iv_gift, "translationY",
                        0f, 0f);

                mAnimSet.playTogether(scaleXAni, scaleYAni, alphaAni, transYAni);
                mAnimSet.setDuration(2000);
                mAnimSet.start();
                break;

            case Constans.GIFT_666:       //666
                AnimatorSet animSet_666 = new AnimatorSet();
                ObjectAnimator scaleXAni_666 = ObjectAnimator.ofFloat(iv_gift, "scaleX",
                        0f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.8f, 2.0f);

                ObjectAnimator scaleYAni_666 = ObjectAnimator.ofFloat(iv_gift, "scaleY",
                        0f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.8f, 2.0f);

                ObjectAnimator alphaAni_666 = ObjectAnimator.ofFloat(iv_gift, "alpha",
                        1f, 1f, 1f, 1f, 1f, 1f, 1f, 0.7f, 0.4f, 0.3f, 0f);

                ObjectAnimator rotateAni_666 = ObjectAnimator.ofFloat(iv_gift, "rotation",
                        0f, 0f, 0f, 0f, 25f, 0f, 15f, 0f, 0f, 0f, 0f, 0f, 0f);

                ObjectAnimator transYAni_666 = ObjectAnimator.ofFloat(iv_gift, "translationY",
                        -1000f, 0f, 0f, 0f, 0f, 0f, 0f, -200f, -400f);

                animSet_666.playTogether(scaleXAni_666, scaleYAni_666, alphaAni_666, rotateAni_666, transYAni_666);
                animSet_666.setDuration(2000);
                animSet_666.start();
                break;

            case Constans.GIFT_DIAMON:    //钻石
                AnimatorSet animSet_diamon = new AnimatorSet();
                ObjectAnimator scaleXAni_diamon = ObjectAnimator.ofFloat(iv_gift, "scaleX",
                        0f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.6f, 1.7f, 1.8f, 1.9f, 2.0f);

                ObjectAnimator scaleYAni_diamon = ObjectAnimator.ofFloat(iv_gift, "scaleY",
                        0f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.6f, 1.7f, 1.8f, 1.9f, 2.0f);

                ObjectAnimator alphaAni_diamon = ObjectAnimator.ofFloat(iv_gift, "alpha",
                        0f, 0.3f, 1f, 1f, 1f, 1f, 0.8f, 0.6f, 0.4f, 0.2f, 0f);

                ObjectAnimator rotateAni_diamon = ObjectAnimator.ofFloat(iv_gift, "rotation",
                        0f, 20f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);

                ObjectAnimator transYAni_diamon = ObjectAnimator.ofFloat(iv_gift, "translationY",
                        0f, -20f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);

                animSet_diamon.playTogether(scaleXAni_diamon, scaleYAni_diamon, alphaAni_diamon, rotateAni_diamon, transYAni_diamon);
                animSet_diamon.setDuration(2000);
                animSet_diamon.start();
                break;

            case Constans.GIFT_ROCKET:    //火箭
                AnimatorSet animSet_r = new AnimatorSet();
                ObjectAnimator scaleXAni_r = ObjectAnimator.ofFloat(iv_gift, "scaleX",
                        1f, 2f, 2f, 2f, 2f, 2f, 1.5f, 0.8f);

                ObjectAnimator scaleYAni_r = ObjectAnimator.ofFloat(iv_gift, "scaleY",
                        1f, 2f, 2f, 2f, 2f, 2f, 1.5f, 0.8f);

                ObjectAnimator alphaAni_r = ObjectAnimator.ofFloat(iv_gift, "alpha",
                        1f, 1f, 1f, 0.5f, 1f, 0.7f, 1f, 1f, 1f);

                ObjectAnimator transYAni_r = ObjectAnimator.ofFloat(iv_gift, "translationY",
                        1000f, 0f, 0f, 0f, -500f, -1000f, -1500f);


                animSet_r.playTogether(scaleXAni_r, scaleYAni_r, alphaAni_r, transYAni_r);
                animSet_r.setDuration(2600);
                animSet_r.start();
                break;
        }
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
                    mRows = ftCommentInfo.getRows();
                    mTv_all_comment.setText(ftCommentInfo.getTotal() == 0 ? "暂无评论" : "全部评论");
                    allRows.clear();
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

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_SEVEN) {
            SignleFtInfo signleFtInfo = mGson.fromJson(response.get(), SignleFtInfo.class);
            if (signleFtInfo.isSuccess()) {
                mRowsBean = signleFtInfo.getObj();

                //        mProxy.registerCacheListener(cacheListener, url);
                //如果缓存过了就设置缓存100%
                if (mProxy.isCached(mRowsBean.getAttrFileList().get(0).getFileUrl())) {
                    player.setSecondPro(100);
                }

                String proxyUrl = mProxy.getProxyUrl(mRowsBean.getAttrFileList().get(0).getFileUrl());
                player.play(proxyUrl);
                player.setShowNavIcon(true);

                mHasPicture = mRowsBean.getAttrFileList().size() > 0;
                upDataInfo();
                initLV();
                queryCore.ftRead(mRowsBean.getId() + "");
                onRefresh();
            } else {
                ToastUtils.showToast(signleFtInfo.getMsg());
            }
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
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        //取消监听
        mProxy.unregisterCacheListener(cacheListener);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    //缓存监听
    private CacheListener cacheListener = new CacheListener() {
        @Override
        public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
            MyLogger.jLog().i("percentsAvailable:" + percentsAvailable);
            //设置缓存进度
            player.setSecondPro(percentsAvailable);
        }
    };
}
