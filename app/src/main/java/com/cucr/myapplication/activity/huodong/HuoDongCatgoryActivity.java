package com.cucr.myapplication.activity.huodong;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventNotifyDataSetChange;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.bean.fenTuan.FtCommentInfo;
import com.cucr.myapplication.bean.fuli.QiYeHuoDongInfo;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.fuLi.HuoDongCore;
import com.cucr.myapplication.core.funTuanAndXingWen.DeleteCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDelPics;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
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

import java.util.ArrayList;
import java.util.List;

import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

public class HuoDongCatgoryActivity extends BaseActivity implements View.OnFocusChangeListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, FtCatgoryAadapter.OnClickCommentGoods, View.OnClickListener, DialogDelPics.OnClickBt, RequersCallBackListener {

    //根布局
    @ViewInject(R.id.activity_hd_catgory)
    ViewGroup rootview;

    //ListView
    @ViewInject(R.id.lv_huodong_catgory)
    ListView lv_huodong_catgory;

    //评论数量
    @ViewInject(R.id.tv_comment_count_hd)
    TextView tv_conmmands;

    //评论点赞
    @ViewInject(R.id.ll_hd_comment_good)
    LinearLayout ll_hd_comment_good;

    //表情发送
    @ViewInject(R.id.ll_hd_emoji_send)
    LinearLayout ll_hd_emoji_send;

    //点赞数量
    @ViewInject(R.id.tv_givecount_hd)
    TextView tv_goods;

    //评论框
    @ViewInject(R.id.et_comment_hd)
    EmojiEditText et_comment;

    //点赞图片
    @ViewInject(R.id.iv_zan)
    ImageView iv_zan;

    //表情按钮
    @ViewInject(R.id.iv_emoji_hd)
    ImageView iv_emoji;

    //刷新
    @ViewInject(R.id.ref_hd_catgory)
    private RefreshLayout mRefreshLayout;

    //删除功能
    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    //emoji表情
    private EmojiPopup emojiPopup;
    private HuoDongCore mHuoDongCore;
    private QiYeHuoDongInfo.RowsBean mReceivedBean;
    private Integer giveNum;
    private TextView mTv_all_comment;
    private int page;
    private int rows;
    private FtCatgoryAadapter mAdapter;
    private int position;
    private List<FtCommentInfo.RowsBean> allRows;
    private DialogDelPics mDialog;
    private MyWaitDialog waitDialog;
    private DeleteCore mDelCore;
    private Intent mIntent;

    @Override
    protected void initChild() {
        EventBus.getDefault().register(this);
        allRows = new ArrayList<>();
        rows = 25;
        initDialog();
        init();
        onRefresh();
    }

    private void initDialog() {
        mDelCore = new DeleteCore();
        mDialog = new DialogDelPics(this, R.style.MyDialogStyle);
        waitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mDialog.setOnClickBt(this);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_huo_dong_catgory;
    }

    private void init() {
        setUpEmojiPopup();
        mRefreshLayout.setOnLoadListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mHuoDongCore = new HuoDongCore();
        et_comment.setOnFocusChangeListener(this);
        mIntent = getIntent();
        mReceivedBean = (QiYeHuoDongInfo.RowsBean) mIntent.getSerializableExtra("data");
        boolean showMore = getIntent().getBooleanExtra("showMore", false);
        iv_more.setVisibility(showMore ? View.VISIBLE : View.GONE);
        tv_conmmands.setText(mReceivedBean.getCommentCount() + "");
        tv_goods.setText(mReceivedBean.getGiveUpCount() + "");
        iv_zan.setImageResource(mReceivedBean.getIsSignUp() == 1 ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        View lvHead = View.inflate(this, R.layout.head_huo_dong_catgory, null);
        initLVHead(lvHead, mReceivedBean);
        lv_huodong_catgory.addHeaderView(lvHead, null, true);
        lv_huodong_catgory.setHeaderDividersEnabled(false);
        mAdapter = new FtCatgoryAadapter(this);
        mAdapter.setClickGoodsListener(this);
        lv_huodong_catgory.setAdapter(mAdapter);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {

            final FtCommentInfo.RowsBean mRowsBean = (FtCommentInfo.RowsBean) data.getSerializableExtra("rowsBean");
            final FtCommentInfo.RowsBean rowsBean = allRows.get(position);

            MyLogger.jLog().i("mRowsBean_CommentCount:" + mRowsBean.getCommentCount());
            MyLogger.jLog().i("rowsBean_CommentCount:" + rowsBean.getCommentCount());

            rowsBean.setGiveUpCount(mRowsBean.getGiveUpCount());
            rowsBean.setIsGiveUp(mRowsBean.getIsGiveUp());

            //=============================================================================
//            如果在secondComment页面评论了  就再查一遍(如果评论从无到有)
            if (mRowsBean.getCommentCount() == 1) { //如果只有一条评论就再查一遍 评论了之后不退出页面在进行二级评论时bug
                // TODO: 2017/12/12  有问题 待解决 在适配器中会报空指针 原因是 评论数量改变了 却没有评论数据
                onRefresh();
            } else {
                rowsBean.setCommentCount(mRowsBean.getCommentCount());
            }
            //=============================================================================

            mAdapter.notifyDataSetChanged();
        }
    }


    private void initLVHead(View lvHead, QiYeHuoDongInfo.RowsBean receivedBean) {
        //活动图片
        ImageView iv_active_pic = (ImageView) lvHead.findViewById(R.id.iv_active_pic);
        //头像
        ImageView iv_head = (ImageView) lvHead.findViewById(R.id.iv_head);
        //标题
        TextView tv_title = (TextView) lvHead.findViewById(R.id.tv_title);
        //作者
        TextView tv_auther_name = (TextView) lvHead.findViewById(R.id.tv_auther_name);
        //创建时间
        TextView tv_creattime = (TextView) lvHead.findViewById(R.id.tv_creattime);
        //内容
        TextView tv_content = (TextView) lvHead.findViewById(R.id.tv_content);
        //活动时间
        TextView tv_active_time = (TextView) lvHead.findViewById(R.id.tv_active_time);
        //活动地址
        TextView tv_active_address = (TextView) lvHead.findViewById(R.id.tv_active_address);
        //费用金额
        TextView tv_actives_money = (TextView) lvHead.findViewById(R.id.tv_actives_money);
        //全部/暂无 评论
        mTv_all_comment = (TextView) lvHead.findViewById(R.id.tv_all_comment);
        //费用栏
        LinearLayout ll_show_money = (LinearLayout) lvHead.findViewById(R.id.ll_show_money);
        //______________初始化______________
        iv_head.setOnClickListener(this);
        tv_auther_name.setOnClickListener(this);
        tv_title.setText(receivedBean.getActiveName());
        tv_auther_name.setText(receivedBean.getApplyUser().getRealName());
        tv_creattime.setText(receivedBean.getUpTime());
        tv_content.setText(receivedBean.getActiveInfo());
        tv_active_time.setText(receivedBean.getActiveStartTime());
        tv_active_address.setText(receivedBean.getActivePlace() + " " + receivedBean.getActiveAdress());
        tv_actives_money.setText(receivedBean.getYs() + "万");
        mTv_all_comment.setText(receivedBean.getCommentCount() == 0 ? "暂无评论" : "全部评论");
        ll_show_money.setVisibility(receivedBean.getOpenys() == 0 ? View.VISIBLE : View.GONE);
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        receivedBean.getApplyUser().getUserHeadPortrait(), iv_head,
                MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST +
                        receivedBean.getPicurl(), iv_active_pic,
                MyApplication.getImageLoaderOptions());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ll_hd_emoji_send.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        ll_hd_comment_good.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
    }

    //emoji表情
    @OnClick(R.id.iv_emoji_hd)
    public void clickEmoji(View view) {
        emojiPopup.toggle();
    }

    @Override
    protected void onStop() {
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }
        super.onStop();
    }

    //点赞
    @OnClick(R.id.ll_hd_goods)
    public void clickZan(View view) {
        mHuoDongCore.activeGiveUp(mReceivedBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (mReceivedBean.getIsSignUp() == 1) {
                        giveNum = mReceivedBean.getGiveUpCount() - 1;
                        mReceivedBean.setIsSignUp(0);
                        mReceivedBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = mReceivedBean.getGiveUpCount() + 1;
                        mReceivedBean.setIsSignUp(1);
                        mReceivedBean.setGiveUpCount(giveNum);
                    }
                    upDataInfo();
                    EventBus.getDefault().post(new EventNotifyDataSetChange(1));
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    //更新数据 评论 点赞
    private void upDataInfo() {
        tv_goods.setText(mReceivedBean.getGiveUpCount() + "");
        iv_zan.setImageResource(mReceivedBean.getIsSignUp() == 1 ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_conmmands.setText(mReceivedBean.getCommentCount() + "");
    }


    //返回操作
    @Override
    protected void onBackBefore() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            setData();
            finish();
        }
    }

    //返回前设置数据
    public void setData() {
        Intent intent = getIntent();
        intent.putExtra("rowsBean", mReceivedBean);
        setResult(Constans.RESULT_CODE, intent);
    }

    @Override
    public void onBackPressed() {
        setData();
        finish();
    }

    //发送评论
    @OnClick(R.id.tv_send)
    public void sendComment(View view) {
        mHuoDongCore.activeComment(mReceivedBean.getId(), CommonUtils.string2Unicode(et_comment.getText().toString().trim()), -1, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (reBackMsg.isSuccess()) {
                    ToastUtils.showToast("评论成功!");
                    //查询一遍
                    onRefresh();
                    et_comment.setText("");
                    emojiPopup.dismiss();
                    CommonUtils.hideKeyBorad(HuoDongCatgoryActivity.this, rootview, true);
                    et_comment.clearFocus();
                    mReceivedBean.setCommentCount(mReceivedBean.getCommentCount() + 1);
                    upDataInfo();
                    mTv_all_comment.setText(mReceivedBean.getCommentCount() == 0 ? "暂无评论" : "全部评论");
                    //评论成功后滚动到最顶部
                    lv_huodong_catgory.setSelection(0);
                } else {
                    ToastUtils.showToast(reBackMsg.getMsg());
                }
            }
        });
    }

    //刷新
    @Override
    public void onRefresh() {
        page = 1;
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
        mHuoDongCore.activeCommentQuery(mReceivedBean.getId(), -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    allRows.addAll(ftCommentInfo.getRows());
                    mAdapter.setData(ftCommentInfo.getRows());
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    //加载
    @Override
    public void onLoad() {
        page++;
        mHuoDongCore.activeCommentQuery(mReceivedBean.getId(), -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    allRows.addAll(ftCommentInfo.getRows());
                    mAdapter.addData(ftCommentInfo.getRows());
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    //点击点赞
    @Override
    public void clickGoods(final FtCommentInfo.RowsBean rowsBean) {
        mHuoDongCore.activeCommentGood(rowsBean.getContentId(), rowsBean.getId(), new OnCommonListener() {
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

    //点击条目
    @Override
    public void clickItem(FtCommentInfo.RowsBean mRowsBean, int position) {
        this.position = position;
        Intent intent = new Intent(this, HdSecondCommentActivity.class);
        intent.putExtra("mRows", mRowsBean);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    //点击用户
    @Override
    public void clickUser(int userId) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    //点击lv头部的用户时  直接跳转个人主页
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", mReceivedBean.getApplyUser().getId());
        startActivity(intent);
    }

    //请求完成  如果还在加载  就停止加载(包括无网络情况)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinish(EventRequestFinish event) {
        if (!event.getWhat().equals(HttpContans.ADDRESS_ACTIVE_COMMENT_QUERY)) {
            return;
        }
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setLoading(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void clickDel() {
        mDelCore.delActive(mReceivedBean.getId(), this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_998) {
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                ToastUtils.showToast("删除成功");
                mIntent.putExtra("delete", true);
                setResult(999, mIntent);
                finish();
            } else {
                ToastUtils.showToast(msg.getMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (what == Constans.TYPE_999) {
            waitDialog.show();
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    //显示删除
    @OnClick(R.id.iv_more)
    public void clickShowDialo(View view) {
        mDialog.show();
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_999) {
            waitDialog.dismiss();
        }
    }
}
