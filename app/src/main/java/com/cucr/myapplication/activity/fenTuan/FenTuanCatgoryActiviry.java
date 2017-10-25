package com.cucr.myapplication.activity.fenTuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.GvAdapter.GridAdapter;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.funTuan.FtCommentCore;
import com.cucr.myapplication.core.funTuan.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.fenTuan.FtCommentInfo;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaShangStyle;
import com.cucr.myapplication.widget.gridView.NoScrollGridView;
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

import java.io.Serializable;
import java.util.List;

import static com.cucr.myapplication.utils.MyLogger.jLog;
import static com.cucr.myapplication.widget.swipeRlv.SwipeItemLayout.TAG;

public class FenTuanCatgoryActiviry extends BaseActivity implements View.OnFocusChangeListener, FtCatgoryAadapter.OnClickCommentGoods {

    //根布局
    @ViewInject(R.id.rootview)
    ViewGroup rootview;

    //评论列表
    @ViewInject(R.id.lv_ft_catgory)
    ListView lv_ft_catgory;

    //点赞
    @ViewInject(R.id.iv_zan)
    ImageView iv_zan;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    TextView tv_givecount;

    //评论框
    @ViewInject(R.id.et_comment)
    EmojiEditText et_comment;

    //评论数量
    @ViewInject(R.id.tv_comment_count)
    TextView tv_comment_count;

    //评论和点赞
    @ViewInject(R.id.ll_comment_good)
    LinearLayout ll_comment_good;

    //表情和发送
    @ViewInject(R.id.ll_emoji_send)
    LinearLayout ll_emoji_send;

    //表情按钮
    @ViewInject(R.id.iv_emoji)
    ImageView iv_emoji;


    private DialogDaShangStyle mDialogDaShangStyle;
    private boolean mHasPicture;
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
    private TextView mTv_all_comment;
    private List<FtCommentInfo.RowsBean> mRows;
    private int position;

    @Override
    protected void initChild() {
        initTitle("详情");
        initData();
        setUpEmojiPopup();
        initLV();
        getDatas();
    }

    //获取传过来的数据
    private void initData() {
        mIntent = getIntent();
        mHasPicture = mIntent.getBooleanExtra("hasPicture", false);
        mIsFormConmmomd = mIntent.getBooleanExtra("isFormConmmomd", false);
        mRowsBean = (QueryFtInfos.RowsBean) mIntent.getSerializableExtra("rowsBean");
        jLog().i("mRowsBean:" + mRowsBean);
        mCommentCore = new FtCommentCore(this);
        upDataInfo();
        queryCore = new QueryFtInfoCore(this);
    }

    private void upDataInfo() {
        tv_givecount.setText(mRowsBean.getGiveUpCount() + "");
        iv_zan.setImageResource(mRowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
        tv_comment_count.setText(mRowsBean.getCommentCount() + "");
    }

    private void getDatas() {
        page = 1;
        rows = 100;
        mCommentCore.queryFtComment(mRowsBean.getId(), -1, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    MyLogger.jLog().i("ftCommentInfo:" + ftCommentInfo);
                    mRows = ftCommentInfo.getRows();
                    mAdapter.setData(mRows);
                    if (mIsFormConmmomd) {
                        MyLogger.jLog().i("setSelection(1)");
                        lv_ft_catgory.setSelection(1);
                        mIsFormConmmomd = false;
                    }
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_fen_tuan_catgory_activiry;
    }

    private void initLV() {
        mDialogDaShangStyle = new DialogDaShangStyle(this, R.style.ShowAddressStyleTheme);
        mDialogDaShangStyle.setCanceledOnTouchOutside(true);
        mDialogDaShangStyle.setConfirmListener(new DialogDaShangStyle.ClickconfirmListener() {
            @Override
            public void onClickConfirm(int howMuch) {
                if (howMuch != 0) {
                    ToastUtils.showToast(FenTuanCatgoryActiviry.this, howMuch + "" + " 星币");
                }
                mDialogDaShangStyle.dismiss();
            }
        });

        View lvHead = View.inflate(this, R.layout.item_ft_catgory_header, null);
        initLvHeader(lvHead);

        lv_ft_catgory.addHeaderView(lvHead, null, true);
        lv_ft_catgory.setHeaderDividersEnabled(false);
        mAdapter = new FtCatgoryAadapter(this);
        mAdapter.setClickGoodsListener(this);
        lv_ft_catgory.setAdapter(mAdapter);
        et_comment.setOnFocusChangeListener(this);
    }


    private void initLvHeader(View lvHead) {
        //头像
        ImageView iv_pic = (ImageView) lvHead.findViewById(R.id.iv_pic);
        //昵称
        TextView tv_neckname = (TextView) lvHead.findViewById(R.id.tv_neckname);
        //时间来源
        TextView tv_time_form = (TextView) lvHead.findViewById(R.id.tv_time_form);
        //浏览量
        TextView tv_lookcount = (TextView) lvHead.findViewById(R.id.tv_lookcount);
        //评论内容
        TextView tv_content = (TextView) lvHead.findViewById(R.id.tv_content);
        //评论内容
        mTv_all_comment = (TextView) lvHead.findViewById(R.id.tv_all_comment);

        //设置数据
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean.getUserHeadPortrait(), iv_pic, MyApplication.getOptions());
        tv_neckname.setText(mRowsBean.getCreateUserName());
        tv_time_form.setText(mRowsBean.getCreaetTime());
        tv_lookcount.setText(mRowsBean.getReadCount() + "");
        tv_content.setText(mRowsBean.getContent());
        mTv_all_comment.setText(mRowsBean.getCommentCount() == 0 ? "暂无评论" : "全部评论");

        lvHead.findViewById(R.id.tv_dashang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDaShangStyle.show();
            }
        });

        NoScrollGridView gridview = (NoScrollGridView) lvHead.findViewById(R.id.gridview);
        gridview.setVisibility(mHasPicture ? View.VISIBLE : View.GONE);
        gridview.setAdapter(new GridAdapter(this, mRowsBean.getAttrFileList()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FenTuanCatgoryActiviry.this, ImagePagerActivity.class);
                List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList = mRowsBean.getAttrFileList();
                Bundle bundle = new Bundle();
                bundle.putSerializable("imgs", (Serializable) attrFileList);//序列化,要注意转化(Serializable)
                intent.putExtras(bundle);//发送数据
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    //点击评论时
    @OnClick(R.id.ll_comment)
    public void comment(View view) {
        lv_ft_catgory.smoothScrollToPositionFromTop(1, 0);
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
                    getDatas();
                    et_comment.setText("");
                    emojiPopup.dismiss();
                    CommonUtils.hideKeyBorad(FenTuanCatgoryActiviry.this, rootview, true);
                    et_comment.clearFocus();
                    mRowsBean.setCommentCount(mRowsBean.getCommentCount() + 1);
                    upDataInfo();
                    mTv_all_comment.setText(mRowsBean.getCommentCount() == 0 ? "暂无评论" : "全部评论");
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
            setData();
        }

    }

    @Override
    public void onBackPressed() {
        setData();
        finish();
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
                    ToastUtils.showToast(FenTuanCatgoryActiviry.this, commonRebackMsg.getMsg());
                }
            }
        });
    }

    //listView条目点击监听
    @Override
    public void clickItem(FtCommentInfo.RowsBean mRowsBean) {
        MyLogger.jLog().i("onItemClick");
        Intent intent = new Intent(this, FtSecondCommentActivity.class);
        intent.putExtra("mRows", mRowsBean);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
//            final FtCommentInfo.RowsBean mRowsBean = (FtCommentInfo.RowsBean) data.getSerializableExtra("rowsBean");
//            final FtCommentInfo.RowsBean rowsBean = mRows.get(position);
//            rowsBean.setGiveUpCount(mRowsBean.getGiveUpCount());
//            rowsBean.setIsGiveUp(mRowsBean.getIsGiveUp());
//            MyLogger.jLog().i("commentCount:"+mRowsBean.getCommentCount());
//            rowsBean.setCommentCount(mRowsBean.getCommentCount());
//            mAdapter.notifyDataSetChanged();
            getDatas();
        }
    }
}
