package com.cucr.myapplication.activity.fenTuan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.FtAllCommentAadapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.funTuan.FtCommentCore;
import com.cucr.myapplication.core.funTuan.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.fenTuan.FtCommentInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
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

public class FtSecondCommentActivity extends BaseActivity implements View.OnFocusChangeListener, FtAllCommentAadapter.OnClickCommentGoods {

    //root_view
    @ViewInject(R.id.root_view)
    LinearLayout root_view;

    //ListView
    @ViewInject(R.id.lv_all_comment)
    ListView lv_all_comment;

    //点赞
    @ViewInject(R.id.ll_goods)
    LinearLayout ll_goods;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    TextView tv_givecount;

    //点赞
    @ViewInject(R.id.iv_zan)
    ImageView iv_zan;

    //表情和发送
    @ViewInject(R.id.ll_emoji_send)
    LinearLayout ll_emoji_send;

    //评论和点赞
    @ViewInject(R.id.ll_comment_good)
    LinearLayout ll_comment_good;

    //评论数量
    @ViewInject(R.id.tv_comment_count)
    TextView tv_comment_count;

    //评论框
    @ViewInject(R.id.et_comment)
    EmojiEditText et_comment;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    ImageView iv_emoji;


    private FtCommentInfo.RowsBean mRowsBean;
    private QueryFtInfoCore queryCore;
    private Integer giveNum;
    //emoji表情
    private EmojiPopup emojiPopup;
    private int page;
    private int rows;
    private FtCommentCore mCommentCore;
    private FtAllCommentAadapter mAdapter;


    @Override
    protected void initChild() {
        initTitle("全部评论");
        initDatas();
        initViews();
        getDatas();

    }

    private void initDatas() {
        mCommentCore = new FtCommentCore();
        queryCore = new QueryFtInfoCore();
        mRowsBean = (FtCommentInfo.RowsBean) getIntent().getSerializableExtra("mRows");

        upDataInfo();
        setUpEmojiPopup();
    }

    private void initViews() {
        et_comment.setOnFocusChangeListener(this);
        View head = View.inflate(this, R.layout.header_lv_ft_all_comment, null);
        lv_all_comment.addHeaderView(head);
        initHead(head);
        List<FtCommentInfo.RowsBean> childList = mRowsBean.getChildList();
        mAdapter = new FtAllCommentAadapter(this, childList);
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


        //设置数据
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean.getUser().getUserHeadPortrait(), iv_pic, MyApplication.getImageLoaderOptions());
        tv_neckname.setText(mRowsBean.getUser().getName());
        tv_time_form.setText(mRowsBean.getReleaseTime());
        tv_content.setText(CommonUtils.unicode2String(mRowsBean.getComment()));
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ft_second_comment;
    }

    private void upDataInfo() {
        tv_givecount.setText(mRowsBean.getGiveUpCount() + "");
        iv_zan.setImageResource(mRowsBean.getIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_comment_count.setText(mRowsBean.getChildList().size() + "");
    }

    @OnClick(R.id.ll_goods)
    public void clickGoods(View view) {
        queryCore.ftGoods(mRowsBean.getContentId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (mRowsBean.getIsGiveUp()) {
                        giveNum = mRowsBean.getGiveUpCount() - 1;
                        mRowsBean.setIsGiveUp(false);
                        mRowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = mRowsBean.getGiveUpCount() + 1;
                        mRowsBean.setIsGiveUp(true);
                        mRowsBean.setGiveUpCount(giveNum);
                    }
//                      upDataInfo();
                    tv_givecount.setText(mRowsBean.getGiveUpCount() + "");
                    iv_zan.setImageResource(mRowsBean.getIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
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
        queryCore.toComment(mRowsBean.getContentId(), mRowsBean.getId(), CommonUtils.string2Unicode(et_comment.getText().toString().trim()), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    ToastUtils.showToast("评论成功!");
                    //查询一遍
                    getDatas();
                    et_comment.setText("");
                    emojiPopup.dismiss();
                    CommonUtils.hideKeyBorad(FtSecondCommentActivity.this, root_view, true);
                    et_comment.clearFocus();
                    upDataInfo();
                    mRowsBean.setCommentCount(mRowsBean.getCommentCount() + 1);
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    //获取数据
    private void getDatas() {
        page = 1;
        rows = 100;
        mCommentCore.queryFtComment(mRowsBean.getContentId(), mRowsBean.getId(), page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyLogger.jLog().i("info:" + response.get());
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    MyLogger.jLog().i("ftCommentInfo:" + ftCommentInfo);
                    List<FtCommentInfo.RowsBean> rows = ftCommentInfo.getRows();
                    mAdapter.setData(rows);

                    tv_comment_count.setText(rows.size() + "");
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    //点赞
    @Override
    public void clickGoods(final FtCommentInfo.RowsBean childListBean) {
        mCommentCore.ftCommentGoods(childListBean.getContentId(), childListBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
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
                } else {
                    ToastUtils.showToast(FtSecondCommentActivity.this, commonRebackMsg.getMsg());
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
            setData();
        }

    }

    private void setData() {
        Intent intent = getIntent();
        intent.putExtra("rowsBean", mRowsBean);
        setResult(Constans.RESULT_CODE, intent);
    }

    @Override
    public void onBackPressed() {
        setData();
        finish();
    }
}
