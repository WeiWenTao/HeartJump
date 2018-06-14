package com.cucr.myapplication.fragment.other;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.activity.comment.FenTuanCatgoryActiviry;
import com.cucr.myapplication.activity.comment.FenTuanVideoCatgoryActiviry;
import com.cucr.myapplication.activity.fansCatgory.AboutActivity;
import com.cucr.myapplication.activity.fansCatgory.HytActivity;
import com.cucr.myapplication.activity.fansCatgory.JourneyActivity;
import com.cucr.myapplication.activity.fansCatgory.ShuJuActivity;
import com.cucr.myapplication.activity.fansCatgory.XingWenActivity;
import com.cucr.myapplication.activity.fansCatgory.YyzcActivity;
import com.cucr.myapplication.activity.fenTuan.DaShangCatgoryActivity;
import com.cucr.myapplication.activity.fenTuan.PublishActivity;
import com.cucr.myapplication.activity.picWall.PhotosAlbumActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.PagerAdapter.CommonPagerAdapter;
import com.cucr.myapplication.adapter.PagerAdapter.DaShangPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FtAdapter_forstar;
import com.cucr.myapplication.adapter.RlVAdapter.StarListAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventContentId;
import com.cucr.myapplication.bean.eventBus.EventDsSuccess;
import com.cucr.myapplication.bean.eventBus.EventDuiHuanSuccess;
import com.cucr.myapplication.bean.eventBus.EventNotifyStarInfo;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.bean.fenTuan.FtGiftsInfo;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.bean.fenTuan.SignleFtInfo;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.fragment.star.Fragment_fans_one;
import com.cucr.myapplication.fragment.star.Fragment_fans_two;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.cucr.myapplication.widget.ftGiveUp.ShineButton;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.stateLayout.MultiStateView;
import com.cucr.myapplication.widget.viewpager.NoScrollPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionsMenu;

/**
 * Created by cucr on 2017/8/31.
 * 粉丝看的
 */

public class FragmentFans extends BaseFragment implements SwipeRecyclerView.OnLoadListener, RequersCallBackListener, ViewPager.OnPageChangeListener, Fragment_fans_one.OnClickCatgoryOne, Fragment_fans_two.OnClickCatgoryTwo, FtAdapter_forstar.OnClickBt {

    //点击显示下拉菜单
    @ViewInject(R.id.ll_show_stars)
    private LinearLayout ll_show_stars;

    //主容器
    @ViewInject(R.id.rlv_continer)
    private RelativeLayout rlv_continer;

    //明星频道
    @ViewInject(R.id.rlv_catgory)
    private RelativeLayout rlv_catgory;

    //vp指示器1
    @ViewInject(R.id.iv_dot1)
    private ImageView iv_dot1;

    //vp指示器2
    @ViewInject(R.id.iv_dot2)
    private ImageView iv_dot2;

    //点击显示下拉菜单 旋转图标
    @ViewInject(R.id.iv_icon_unfold)
    private ImageView iv_icon_unfold;

    //下拉内容
    @ViewInject(R.id.drawer_rcv)
    private RecyclerView drawer_rcv;

    //背景
    @ViewInject(R.id.bg)
    private View bg;

    //新闻列表
    @ViewInject(R.id.rlv_xingwen)
    private SwipeRecyclerView rlv_news;

    //状态布局
    @ViewInject(R.id.multiStateView)
    private MultiStateView multiStateView;

    //粉丝数量
    @ViewInject(R.id.tv_fans)
    private TextView tv_fans;

    //明星姓名
    @ViewInject(R.id.tv_starname)
    private TextView tv_starname;

    //标题姓名
    @ViewInject(R.id.tv_star_title)
    private TextView tv_star_title;

    //明星封面
    @ViewInject(R.id.backdrop)
    private ImageView backdrop;

    //礼物动画
    @ViewInject(R.id.iv_gift)
    private ImageView iv_gift;

    //频道分类
    @ViewInject(R.id.vp_catgory)
    private ViewPager vp_catgory;

    @ViewInject(R.id.multiple_actions)
    private FloatingActionsMenu mFam;

    //是否需要显示
    private boolean isShow = true;
    private StarListAdapter mAdapter;
    private QueryFocus mCore;
    private List<FocusInfo.RowsBean> mRows;
    private QueryStarListCore mStarCore;
    private int page = 1;
    private int rows = 100;
    private int newsPage;
    private int newsRows;
    private int type = 2;
    private Intent mIntent;
    private int mStarId;
    private StarListInfos.RowsBean mRowsBean;
    private boolean needShowLoading;
    private QueryFtInfoCore mNewsCore;
    private boolean isRefresh;
    private FtAdapter_forstar newsAdapter;
    private List<Fragment> mDataList;
    private LayoutInflater layoutInflater;
    private DaShangPagerAdapter mDaShangPagerAdapter;
    private DialogShareStyle mDialog;
    private PayCenterCore mPayCenterCore;

    //礼物
    @ViewInject(R.id.tv_gift)
    private TextView gift;

    //背包
    @ViewInject(R.id.tv_backpack)
    private TextView backpack;

    //礼物和背包 ViewPager
    @ViewInject(R.id.vp_dahsnag)
    private NoScrollPager vp_dahsnag;
    private boolean canLoad;
    private int sortType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.white), 0);

        mStarCore = new QueryStarListCore();
        mCore = new QueryFocus();
        mNewsCore = new QueryFtInfoCore();
        mPayCenterCore = new PayCenterCore();
        drawer_rcv.setLayoutManager(new GridLayoutManager(mContext, 4));
        mAdapter = new StarListAdapter(mContext);
        mIntent = new Intent();
        this.layoutInflater = inflater;
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mDaShangPagerAdapter = new DaShangPagerAdapter();

        mFam.attachToRecyclerView(rlv_news.getRecyclerView());
        queryMsg();
        initRlv();
        inipopWindow();
        initInfos();
        initDialog();
        initCatgory();
        mAdapter.setPosition(0);
        initNewsInfo();
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


    //点击发布图片
    @OnClick(R.id.action_a)
    public void clickA(View view) {
        mFam.collapse();
        Intent intent = new Intent(mContext, PublishActivity.class);
        intent.putExtra("type", Constans.TYPE_PICTURE);
        intent.putExtra("starId", mStarId);
        startActivityForResult(intent, 3);
    }

    //点击发布视频
    @OnClick(R.id.action_b)
    public void clickB(View view) {
        mFam.collapse();
        Intent intent = new Intent(mContext, PublishActivity.class);
        intent.putExtra("type", Constans.TYPE_VIDEO);
        intent.putExtra("starId", mStarId);
        startActivityForResult(intent, 3);
    }

    //兑换成功
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void eventData(EventDuiHuanSuccess event) {
        if (mDaShangPagerAdapter == null) {
            mDaShangPagerAdapter = new DaShangPagerAdapter();
        }
        queryBackpack();
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
        mNewsCore.queryGift(new OnCommonListener() {
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
        mNewsCore.queryBackpackInfo(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtBackpackInfo ftBackpackInfo = mGson.fromJson(response.get(), FtBackpackInfo.class);
                if (ftBackpackInfo.isSuccess()) {
                    mDaShangPagerAdapter.setBackpackInfos(ftBackpackInfo);
                } else {
                    ToastUtils.showToast(ftBackpackInfo.getMsg());
                }
            }
        });
    }

    private void initDialog() {
        mDialog = new DialogShareStyle(getActivity(), R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
    }

    private void inipopWindow() {
        if (popWindow == null) {
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

    private void initCatgory() {
        mDataList = new ArrayList<>();
        Fragment_fans_one catgoryOne = new Fragment_fans_one();
        Fragment_fans_two catgoryTwo = new Fragment_fans_two();
        mDataList.add(catgoryOne);
        mDataList.add(catgoryTwo);
        catgoryOne.setOnClickCatgoryOne(this);
        catgoryTwo.setOnClickCatgoryTwo(this);
        vp_catgory.setAdapter(new CommonPagerAdapter(getFragmentManager(), mDataList, null));
        vp_catgory.addOnPageChangeListener(this);
    }

    private void initNewsInfo() {
        newsAdapter = new FtAdapter_forstar(getActivity());
        newsAdapter.setOnClickBt(this);
        newsPage = 1;
        newsRows = 20;
        needShowLoading = true;
        //分割线
//        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
//        rlv_news.getRecyclerView().addItemDecoration(decor);
        rlv_news.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_news.setAdapter(newsAdapter);
        rlv_news.setOnLoadListener(this);
//        rlv_news.onLoadingMore();
    }


    //初始化明星封面数据
    private void initDatas(int starId) {
        mStarCore.queryStar(type, page, rows, starId, null, null, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                StarListInfos starInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starInfos.isSuccess()) {
                    mRowsBean = starInfos.getRows().get(0);
                    tv_fans.setText("粉丝 " + (mRowsBean.getFansCount() == null ? "0" : mRowsBean.getFansCount()));
                    tv_starname.setText(mRowsBean.getRealName());
                    tv_star_title.setText(mRowsBean.getRealName());
                    ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean.getUserPicCover(), backdrop, MyApplication.getImageLoaderOptions());
                    rlv_catgory.setVisibility(View.VISIBLE);
                } else {
                    ToastUtils.showToast(starInfos.getErrorMsg());
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
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //点击关注或取消关注时再查一遍
    public void notifyDatas(EventNotifyStarInfo event) {
        queryMsg();
        mAdapter.setPosition(0);//设置第一个被勾选
    }

    private void queryMsg() {
        //TODO: 2017/12/4   刷新和加载
        mCore.queryMyFocusStars(-1, 1, 100, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                FocusInfo Info = mGson.fromJson(response.get(), FocusInfo.class);
                if (Info.isSuccess()) {
                    iv_icon_unfold.setVisibility(View.VISIBLE);
                    //添加明星自己为第一个
                    mRows = Info.getRows();
                    if (Constans.STATUS_STAR == ((int) SpUtil.getParam(SpConstant.SP_STATUS, -1))) {
                        FocusInfo.RowsBean rowsBean = new FocusInfo.RowsBean(null, -1, new FocusInfo.RowsBean.StartBean(((int) SpUtil.getParam(SpConstant.USER_ID, -1)), (String) SpUtil.getParam(SpConstant.SP_USERHEAD, "")));
                        mRows.add(0, rowsBean);
                    }

                    mAdapter.setData(mRows);
                    if (mRows == null || mRows.size() == 0) {
                        return;
                    }

                    //默认查询第一个明星数据
                    mStarId = mRows.get(0).getStart().getId();
                    initDatas(mStarId);
                    onRefresh();
                } else {
                    ToastUtils.showToast(mContext, Info.getErrorMsg());
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
        });
    }

    private void initRlv() {
        mAdapter.setOnClick(new StarListAdapter.OnClick() {
            @Override
            public void onClickPosition(View view, int position) {
                mAdapter.setPosition(position);
                mStarId = mRows.get(position).getStart().getId();
                initDatas(mStarId);
                initDrawer(false);
                needShowLoading = true;
                onRefresh();
            }

            @Override
            public void onClickAdd(View view) {
                Intent intent = new Intent(MyApplication.getInstance(), StarListForAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 111);
            }
        });
        drawer_rcv.setAdapter(mAdapter);
    }

    @Override
    protected boolean needHeader() {
        return false;
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

    //显示PopupWindow
    @OnClick(R.id.ll_show_stars)
    public void showFocusStars(View view) {
        initDrawer(isShow);
    }

    @OnClick(R.id.bg)
    public void clickBg(View view) {
        initDrawer(false);
    }

    public void initDrawer(boolean needShow) {
        if (needShow) {
            ll_show_stars.setEnabled(false);
            drawer_rcv.setVisibility(View.VISIBLE);
            drawer_rcv.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.classify_menu_in));
            CommonUtils.animationAlpha(bg, true);
            CommonUtils.animationRotate(iv_icon_unfold, true, 300);
            bg.setEnabled(false);
            bg.setVisibility(View.VISIBLE);
            isShow = false;
        } else {
            bg.setEnabled(false);
            ll_show_stars.setEnabled(false);
            drawer_rcv.clearAnimation();
            drawer_rcv.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.classify_menu_out));
            isShow = true;
            drawer_rcv.setVisibility(View.GONE);
            CommonUtils.animationAlpha(bg, false);
            CommonUtils.animationRotate(iv_icon_unfold, false, 300);
        }

        CommonUtils.setAnimationListener(new CommonUtils.AnimationListener() {
            @Override
            public void onInAnimationFinish() {
                ll_show_stars.setEnabled(true);
                bg.setVisibility(View.GONE);
            }

            @Override
            public void onOutAnimationFinish() {
                ll_show_stars.setEnabled(true);
                bg.setEnabled(true);
            }
        });
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_other_fans;
    }

    //消息界面
    @OnClick(R.id.iv_header_msg)
    public void pay(View view) {
        startActivity(new Intent(mContext, MessageActivity.class));
    }

    //预约提示框
    @OnClick(R.id.tv_yuyue)
    public void toYuYue(View view) {
        if (CommonUtils.isQiYe()) {
            Intent intent = new Intent(MyApplication.getInstance(), YuYueCatgoryActivity.class);
            intent.putExtra("data", mRowsBean);
            startActivity(intent);
        } else {
            ToastUtils.showToast("企业用户才能预约哦，赶快去认证吧");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        MyLogger.jLog().i("EventNotifyStarInfo() 注销");
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        newsPage = 1;
        mNewsCore.queryFtInfo(sortType, mStarId, 1, -1, false, newsPage, newsRows, this);
    }

    @Override
    public void onLoadMore() {
        if (mStarId == 0) {
            return;
        }
        isRefresh = false;
        newsPage++;
        rlv_news.onLoadingMore();
        mNewsCore.queryFtInfo(sortType, mStarId, 1, -1, false, newsPage, newsRows, this);
    }

    private QueryFtInfos mQueryFtInfos;

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_ONE) {
            QueryFtInfos infos = mGson.fromJson(response.get(), QueryFtInfos.class);
            if (infos.isSuccess()) {
                if (isRefresh) {
                    if (infos.getTotal() == 0) {
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    } else {
                        mQueryFtInfos = infos;
                        newsAdapter.setData(infos);
                        rlv_news.getRecyclerView().smoothScrollToPosition(0);
                        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    }
                    canLoad = true;
                } else {
                    if (infos != null && mQueryFtInfos != null) {
                        mQueryFtInfos.getRows().addAll(infos.getRows());
                        newsAdapter.addData(infos.getRows());
                    }

                    if (infos.getTotal() <= (newsPage - 1) * newsRows) {
                        rlv_news.onNoMore("没有更多了");
                    } else {
                        rlv_news.complete();
                    }
                }
            } else {
                ToastUtils.showToast(infos.getErrorMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (isRefresh) {
            rlv_news.getSwipeRefreshLayout().setRefreshing(true);
        }
        if (needShowLoading && isRefresh) {
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
        if (rlv_news.isRefreshing()) {
            rlv_news.getSwipeRefreshLayout().setRefreshing(false);
        }
        if (rlv_news.isLoadingMore()) {
            rlv_news.stopLoadingMore();
        }
    }

    @OnClick(R.id.ll_error)
    public void refres(View view) {
        needShowLoading = true;
        onRefresh();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setIndecator(position == 0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setIndecator(boolean isOne) {
        iv_dot1.setImageResource(isOne ? R.drawable.cricle_sel : R.drawable.cricle_nor_ccc);
        iv_dot2.setImageResource(isOne ? R.drawable.cricle_nor_ccc : R.drawable.cricle_sel);
    }

    @OnClick(R.id.iv_dot1)
    public void clickOneDot(View view) {
        vp_catgory.setCurrentItem(0);
    }

    @OnClick(R.id.iv_dot2)
    public void clickOneTwo(View view) {
        vp_catgory.setCurrentItem(1);
    }

    //星闻
    @Override
    public void onClickXingWen() {
        Intent intent = new Intent(mContext, XingWenActivity.class);
        intent.putExtra("starId", mStarId);
        startActivity(intent);
    }

    //数据
    @Override
    public void onClickShuJu() {
        Intent intent = new Intent(mContext, ShuJuActivity.class);
        intent.putExtra("starId", mStarId);
        startActivity(intent);
    }

    //后援团
    @Override
    public void onClickHYT() {
        Intent intent = new Intent(mContext, HytActivity.class);
        intent.putExtra("starId", mStarId);
        startActivity(intent);
    }

    //图片墙
    @Override
    public void onClickTPQ() {
        Intent intent = new Intent(mContext, PhotosAlbumActivity.class);
        intent.putExtra("starId", mStarId);
        startActivity(intent);
    }

    //行程
    @Override
    public void onClickjourney() {
        Intent intent = new Intent(mContext, JourneyActivity.class);
        intent.putExtra("data", mRowsBean);
        startActivity(intent);
    }

    //第一页的关于Ta(粉丝)
    @Override
    public void onClickAbout_one() {
        Intent intent = new Intent(mContext, AboutActivity.class);
        intent.putExtra("data", mRowsBean);
        startActivity(intent);
    }

    //关于Ta(企业或明星)
    @Override
    public void onClickAbout() {
        Intent intent = new Intent(mContext, AboutActivity.class);
        intent.putExtra("data", mRowsBean);
        startActivity(intent);
    }

    //应援众筹
    @Override
    public void onClickYyzc() {
        Intent intent = new Intent(mContext, YyzcActivity.class);
        intent.putExtra("starId", mStarId);
        startActivity(intent);
    }

    //微博
    @Override
    public void onClickWeiBo() {
        Intent intent = new Intent(mContext, TestWebViewActivity.class);
        intent.putExtra("url", mRowsBean.getWeiboUrl());
        startActivity(intent);
    }

    //贴吧
    @Override
    public void onClickTieBa() {
        Intent intent = new Intent(mContext, TestWebViewActivity.class);
        intent.putExtra("url", mRowsBean.getTiebaUrl());
        startActivity(intent);
    }

    private int giveNum;

    @Override
    public void onClickGoods(int position, QueryFtInfos.RowsBean rowsBean, ShineButton sib) {
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
        newsAdapter.notifyDataSetChanged();

        mNewsCore.ftGoods(rowsBean.getId(), new OnCommonListener() {
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
    public void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean formCommond) {
        this.position = position;
        Intent intent = new Intent(MyApplication.getInstance(), FenTuanCatgoryActiviry.class);
        intent.putExtra("hasPicture", hasPicture);
        intent.putExtra("dataId", rowsBean.getId() + "");
        intent.putExtra("isFormConmmomd", formCommond);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    @Override
    public void onClickshare(QueryFtInfos.RowsBean rowsBean, String imgUrl) {
        mDialog.setData(new ShareEntity(CommonUtils.unicode2String(rowsBean.getContent()), getString(R.string.share_title), HttpContans.ADDRESS_FT_SHARE + rowsBean.getId(), imgUrl));
    }

    private PopupWindow popWindow;

    //弹出打赏框
    @Override
    public void onClickDaShang(int contentId, int position) {
        popWindow.showAtLocation(rlv_continer, Gravity.BOTTOM, 0, 0);
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

    private int position = -1;

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
    public void onClickFansq() {
//        needShowLoading = true;
        sortType = 0;
        onRefresh();
    }

    @Override
    public void onClickHot() {
//        needShowLoading = true;
        sortType = 1;
        onRefresh();
    }

    //打赏成功后打赏人数增加
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDsRecord(EventDsSuccess event) {
        QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(event.getPosition());
        rowsBean.setDssl(rowsBean.getDssl() + 1);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 10) {
            //发布成功  再次查询
            onRefresh();
            rlv_news.getRecyclerView().smoothScrollToPosition(0);
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
            newsAdapter.notifyDataSetChanged();
        } else if (requestCode == Constans.REQUEST_CODE && resultCode == 999) {
            boolean delete = data.getBooleanExtra("delete", false);
            if (delete) {
//                mAdapter.delData(position);
                onRefresh();
            }
        }
    }

}
