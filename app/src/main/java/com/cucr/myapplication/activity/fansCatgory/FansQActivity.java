package com.cucr.myapplication.activity.fansCatgory;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.comment.FenTuanCatgoryActiviry;
import com.cucr.myapplication.activity.comment.FenTuanVideoCatgoryActiviry;
import com.cucr.myapplication.activity.fenTuan.DaShangCatgoryActivity;
import com.cucr.myapplication.activity.fenTuan.PublishActivity;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.PagerAdapter.DaShangPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FtAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.CommentEvent;
import com.cucr.myapplication.bean.eventBus.EventContentId;
import com.cucr.myapplication.bean.eventBus.EventDsSuccess;
import com.cucr.myapplication.bean.eventBus.EventDuiHuanSuccess;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.bean.fenTuan.FtGiftsInfo;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.bean.fenTuan.SignleFtInfo;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.cucr.myapplication.widget.viewpager.NoScrollPager;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

public class FansQActivity extends BaseActivity implements FtAdapter.OnClickBt, SwipeRecyclerView.OnLoadListener, View.OnClickListener, RequersCallBackListener, Animator.AnimatorListener {

    //礼物
    @ViewInject(R.id.tv_gift)
    private TextView gift;

    //背包
    @ViewInject(R.id.tv_backpack)
    private TextView backpack;

    //礼物和背包 ViewPager
    @ViewInject(R.id.vp_dahsnag)
    private NoScrollPager vp_dahsnag;

    //根布局
    @ViewInject(R.id.view)
    private View view;

    //礼物动画
    @ViewInject(R.id.iv_gift)
    private ImageView iv_gift;

    //排序分类
    @ViewInject(R.id.ll_sort)
    private LinearLayout ll_sort;

    //人气分类
    @ViewInject(R.id.tv_sort_hot)
    private TextView tv_sort_hot;

    //最新分类
    @ViewInject(R.id.tv_sort_new)
    private TextView tv_sort_new;

    //填充布局
    private MultiStateView multiStateView;
    private PayCenterCore mPayCenterCore;
    private Context mContext;
    private FloatingActionsMenu mFam;
    private FloatingActionButton action_a, action_b;
    private QueryFtInfoCore queryCore;
    private Gson mGson;
    private int starId;
    private int page = 1;
    private int rows = 15;
    private int dataType = 1; //dataType: 0 星闻,1 粉团文章.
    private SwipeRecyclerView rlv_fentuan;  //这不是RecyclerView  而是RecyclerView + swipeRefreshLayout
    private RecyclerView rlv_test;
    private QueryFtInfos mQueryFtInfos;
    private FtAdapter mAdapter;
    private int giveNum;
    private int position = -1;
    private PopupWindow popWindow;
    private DaShangPagerAdapter mDaShangPagerAdapter;
    private DialogShareStyle mDialog;
    private Intent mIntent;
    private boolean isRefresh;
    private boolean needShowLoading;
    private ObjectAnimator mAni_open;
    private ObjectAnimator mAni_close;
    private boolean isOpened;
    private int sortType;


    @Override
    protected void initChild() {
        initTitle("fans圈");
        init();
        initView();
        initRlV();
        inipopWindow();
        initInfos();
        initSort();
        onRefresh();
    }

    private void initSort() {
        //默认人气排序
        sortType = 0;
        int hight = CommonUtils.dip2px(40);
        mAni_open = ObjectAnimator.ofFloat(view, "translationY", 0, hight);
        mAni_close = ObjectAnimator.ofFloat(view, "translationY", hight, 0);
        mAni_open.setDuration(300);
        mAni_close.setDuration(300);
        mAni_open.addListener(this);
        mAni_close.addListener(this);
    }


    private void init() {
        needShowLoading = true;  //进入页面的时候显示(仅一次)
        mContext = MyApplication.getInstance();
        queryCore = new QueryFtInfoCore();
        mPayCenterCore = new PayCenterCore();
        mGson = MyApplication.getGson();
        mDaShangPagerAdapter = new DaShangPagerAdapter();
        starId = getIntent().getIntExtra("starId", -1);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);//订阅
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);//取消订阅
    }


    @Override
    protected int getChildRes() {
        return R.layout.activity_fans_q;
    }

    private void initInfos() {
        if (mDaShangPagerAdapter == null) {
            mDaShangPagerAdapter = new DaShangPagerAdapter();
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void eventData(EventDuiHuanSuccess event) {
        if (mDaShangPagerAdapter == null) {
            mDaShangPagerAdapter = new DaShangPagerAdapter();
        }
        queryBackpack();
    }

    private void initRlV() {
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        rlv_fentuan.getRecyclerView().setLayoutManager(layout);
        mAdapter = new FtAdapter(this);
        rlv_fentuan.setAdapter(mAdapter);
        mAdapter.setOnClickBt(this);

        rlv_test.setLayoutManager(new LinearLayoutManager(mContext));
        rlv_test.setAdapter(mAdapter);

        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
    }

    //打赏成功后打赏人数增加
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDsRecord(EventDsSuccess event) {
        QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(event.getPosition());
        rowsBean.setDssl(rowsBean.getDssl() + 1);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        rlv_test = (RecyclerView) view.findViewById(R.id.rlv_test);
//        rlv_fentuan.setItemAnimator(new DefaultItemAnimator());

        rlv_fentuan = (SwipeRecyclerView) view.findViewById(R.id.rlv_fentuan);
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
//        rlv_fentuan.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        rlv_fentuan.setOnLoadListener(this);

        mFam = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        action_a = (FloatingActionButton) view.findViewById(R.id.action_a);
        action_b = (FloatingActionButton) view.findViewById(R.id.action_b);

        mFam.attachToRecyclerView(rlv_fentuan.getRecyclerView());
        action_a.setOnClickListener(this);
        action_b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mFam.collapse();
        Intent intent = new Intent(mContext, PublishActivity.class);
        switch (v.getId()) {
            //照片
            case R.id.action_a:
                intent.putExtra("type", Constans.TYPE_PICTURE);
//                PictureSelector.create(this)
//                        .openGallery(PictureMimeType.ofImage())
//                        .maxSelectNum(9)
//                        .imageSpanCount(4)
//                        .selectionMode(PictureConfig.MULTIPLE)
//                        .previewImage(false)
//                        .compressGrade(Luban.THIRD_GEAR)
//                        .isCamera(true)// 是否显示拍照按钮 true or false
//                        .sizeMultiplier(0.5f)
//                        .compress(true)
//                        .compressMode(LUBAN_COMPRESS_MODE)
//                        .isGif(true)
//                        .previewEggs(true)
//                        .forResult(Constans.TYPE_ONE);
                break;

            //视频
            case R.id.action_b:
                intent.putExtra("type", Constans.TYPE_VIDEO);
//                PictureSelector.create(this)
//                        .openGallery(PictureMimeType.ofVideo())
//                        .imageSpanCount(4)
//                        .selectionMode(PictureConfig.SINGLE)
//                        .previewVideo(true)
//                        .videoQuality(1)
//                        .compress(true)
//                        .recordVideoSecond(10)
//                        .forResult(Constans.TYPE_TWO);
                break;
        }
        intent.putExtra("starId", starId);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == 10) {
            //发布成功  再次查询
            onRefresh();
            rlv_fentuan.getRecyclerView().smoothScrollToPosition(0);
        }

        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
            SignleFtInfo.ObjBean mRowsBean = (SignleFtInfo.ObjBean) data.getSerializableExtra("rowsBean");
            if (mRowsBean == null) {
                return;
            }
            final QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(position);
            rowsBean.setGiveUpCount(mRowsBean.getGiveUpCount());
            rowsBean.setIsGiveUp(mRowsBean.isIsGiveUp());
            rowsBean.setCommentCount(mRowsBean.getCommentCount());
            mAdapter.notifyDataSetChanged();
        } else if (requestCode == Constans.REQUEST_CODE && resultCode == 999) {
            boolean delete = data.getBooleanExtra("delete", false);
            if (delete) {
//                mAdapter.delData(position);
                onRefresh();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        queryCore.stopRequest();
    }


    //刷新的时候查询最新数据 page = 1
    @Override
    public void onRefresh() {
        initInfos();//背包信息初始化
        isRefresh = true;
        page = 1;
        rlv_fentuan.getSwipeRefreshLayout().setRefreshing(true);
        queryCore.queryFtInfo(sortType, starId, dataType, -1, false, page, rows, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
//        rlv_fentuan.onLoadingMore();
        queryCore.queryFtInfo(sortType, starId, dataType, -1, false, page, rows, this);
    }

    //打赏框

    private void inipopWindow() {
        if (popWindow == null) {
            View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.popupwindow_dashang, null);
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

    //点赞
    @Override
    public void onClickGoods(int position, final QueryFtInfos.RowsBean rowsBean, ShineButton sib) {
        if (rowsBean.isIsGiveUp()) {
            sib.setChecked(false, true);
            sib.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.icon_good_under));
        } else {
            sib.setChecked(true, true);
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

        queryCore.ftGoods(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    mAdapter.notifyDataSetChanged();
                } else {
//                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    //评论
    @Override
    public void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean isFormConmmomd) {
        this.position = position;
        Intent intent = new Intent(MyApplication.getInstance(), FenTuanCatgoryActiviry.class);
        intent.putExtra("hasPicture", hasPicture);
        intent.putExtra("dataId", rowsBean.getId() + "");
        intent.putExtra("isFormConmmomd", isFormConmmomd);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    //分享
    @Override
    public void onClickshare(QueryFtInfos.RowsBean rowsBean, String imgUrl) {
        mDialog.setData(new ShareEntity(rowsBean.getContent(), getString(R.string.share_title), HttpContans.ADDRESS_FT_SHARE + rowsBean.getId(), imgUrl));
    }

    //弹出打赏框
    @Override
    public void onClickDaShang(int contentId, int position) {
        popWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //可以考虑用eventbus传值
        EventBus.getDefault().postSticky(new EventContentId(contentId, position));
    }

    //打赏详情
    @Override
    public void onClickDsRecored(int contentId) {
        Intent intent = new Intent(mContext, DaShangCatgoryActivity.class);
        intent.putExtra("contentId", contentId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickUser(int userId, boolean isStar) {
        if (isStar) {
            mIntent = new Intent(mContext, StarPagerActivity.class);
            mIntent.putExtra("starId", userId);
        } else {
            mIntent = new Intent(mContext, PersonalMainPagerActivity.class);
            mIntent.putExtra("userId", userId);
        }
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //删除了动态 刷新
    public void onDataSynEvent(CommentEvent event) {
        if (event.getFlag() == 999) {
            onRefresh();
        }
    }

    @Override
    public void onClickVideoCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean formCommond) {
        this.position = position;
        Intent intent = new Intent(MyApplication.getInstance(), FenTuanVideoCatgoryActiviry.class);
        intent.putExtra("hasPicture", hasPicture);
        intent.putExtra("dataId", rowsBean.getId() + "");
        intent.putExtra("isFormConmmomd", formCommond);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_ONE) {
            QueryFtInfos infos = mGson.fromJson(response.get(), QueryFtInfos.class);
            if (infos.isSuccess()) {
                if (isRefresh) {
                    if (infos.getTotal() == 0) {
                        rlv_test.setVisibility(View.VISIBLE);
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        rlv_test.setVisibility(View.GONE);
                        mQueryFtInfos = infos;
                        mAdapter.setData(infos);
                        rlv_fentuan.getRecyclerView().smoothScrollToPosition(0);
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    }
                } else {
                    if (infos != null) {
                        mQueryFtInfos.getRows().addAll(infos.getRows());
                        mAdapter.addData(infos.getRows());
                    }
                }
                if (infos.getTotal() <= page * rows) {
                    rlv_fentuan.onNoMore("没有更多了");
                } else {
                    rlv_fentuan.complete();
                }
            } else {
                ToastUtils.showToast(infos.getErrorMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (needShowLoading) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            needShowLoading = false;
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {
        if (isRefresh && response.getException() instanceof NetworkError) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_ONE) {
            if (rlv_fentuan.isRefreshing()) {
                rlv_fentuan.getSwipeRefreshLayout().setRefreshing(false);
            }
            if (rlv_fentuan.isLoadingMore()) {
                rlv_fentuan.stopLoadingMore();
            }
        }
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

    @OnClick(R.id.iv_sort)
    public void clickSort(View iview) {
        if (isOpened) {
            mAni_close.start();
        } else {
            mAni_open.start();
        }
    }

    //人气排序
    @OnClick(R.id.tv_sort_hot)
    private void sortByHot(View view) {
        sortType = 1;
        tv_sort_hot.setTextColor(getResources().getColor(R.color.xtred));
        tv_sort_new.setTextColor(getResources().getColor(R.color.color_999));
        needShowLoading = true;
        mAni_close.start();
        onRefresh();
    }

    //时间排序
    @OnClick(R.id.tv_sort_new)
    private void sortByNew(View view) {
        sortType = 0;
        tv_sort_hot.setTextColor(getResources().getColor(R.color.color_999));
        tv_sort_new.setTextColor(getResources().getColor(R.color.xtred));
        needShowLoading = true;
        mAni_close.start();
        onRefresh();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isOpened = animation == mAni_open;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
