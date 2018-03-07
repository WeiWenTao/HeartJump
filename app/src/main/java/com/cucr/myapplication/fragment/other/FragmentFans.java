package com.cucr.myapplication.fragment.other;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.hyt.HYTActivity;
import com.cucr.myapplication.activity.picWall.PhotosAlbumActivity;
import com.cucr.myapplication.activity.star.StarListForAddActivity;
import com.cucr.myapplication.adapter.PagerAdapter.StarPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.StarListAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.starListAndJourney.QueryFocus;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.fragment.star.Fragment_star_fentuan;
import com.cucr.myapplication.fragment.star.Fragment_star_shuju;
import com.cucr.myapplication.fragment.star.Fragment_star_xingcheng;
import com.cucr.myapplication.fragment.star.Fragment_star_xingwen;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventNotifyStarInfo;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.eventBus.EventStarId;
import com.cucr.myapplication.bean.others.FragmentInfos;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/8/31.
 * 粉丝看的
 */

public class FragmentFans extends BaseFragment {

    //头部
    @ViewInject(R.id.head)
    private RelativeLayout head;

    //点击显示下拉菜单
    @ViewInject(R.id.ll_show_stars)
    private LinearLayout ll_show_stars;

    //点击显示下拉菜单 旋转图标
    @ViewInject(R.id.iv_icon_unfold)
    private ImageView iv_icon_unfold;

    //下拉内容
    @ViewInject(R.id.drawer_rcv)
    private RecyclerView drawer_rcv;

    //背景
    @ViewInject(R.id.bg)
    private View bg;

    //ViewPager
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;

    //指示器
    @ViewInject(R.id.magic_indicator_personal_page)
    private MagicIndicator magicIndicator;

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

    private List<FragmentInfos> mDataList;

    //是否需要显示
    private boolean isShow = true;
    private StarListAdapter mAdapter;
    private QueryFocus mCore;
    private List<FocusInfo.RowsBean> mRows;
    private QueryStarListCore mStarCore;
    private int page = 1;
    private int rows = 100;
    private int type = 2;
    private float percent;//占屏比
    private Intent mIntent;
    private int mStarId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLogger.jLog().i("EventNotifyStarInfo() 注册");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
        mStarCore = new QueryStarListCore();
        mCore = new QueryFocus();
        percent = 3.0f;
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        initRlv();
//        initHead();
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        initIndicator();
        initVp();
        queryMsg();
        mAdapter.setPosition(0);
    }


    //初始化明星封面数据
    private void initDatas(int starId) {
        mStarCore.queryStar(type, page, rows, starId, null, null, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarListInfos starInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starInfos.isSuccess()) {
                    StarListInfos.RowsBean rowsBean = starInfos.getRows().get(0);
                    tv_fans.setText("粉丝 " + rowsBean.getFansCount());
                    tv_starname.setText(rowsBean.getRealName());
                    tv_star_title.setText(rowsBean.getRealName());
                    ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUserPicCover(), backdrop, MyApplication.getImageLoaderOptions());
                } else {
                    ToastUtils.showToast(starInfos.getErrorMsg());
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //点击关注或取消关注时再查一遍
    public void notifyDatas(EventNotifyStarInfo event) {
        queryMsg();
        mAdapter.setPosition(0);//设置第一个被勾选
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        queryMsg();
//        mAdapter.setPosition(0);
//        MyLogger.jLog().i("onActivityResult");
    }

    private void queryMsg() {
        //TODO: 2017/12/4   刷新和加载
        mCore.queryMyFocusStars(-1, 1, 100, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyLogger.jLog().i("focusInfo:" + response.get());
                FocusInfo Info = mGson.fromJson(response.get(), FocusInfo.class);
                if (Info.isSuccess()) {
                    mRows = Info.getRows();
                    mAdapter.setData(mRows);
                    if (mRows == null || mRows.size() == 0) {
                        return;
                    }
                    //默认查询第一个明星数据
                    EventBus.getDefault().postSticky(new EventFIrstStarId(mRows.get(0).getStart().getId()));
                    mStarId = mRows.get(0).getStart().getId();
                    initDatas(mStarId);
                } else {
                    ToastUtils.showToast(mContext, Info.getErrorMsg());
                }
            }
        });
    }


    private void initRlv() {
        drawer_rcv.setLayoutManager(new GridLayoutManager(mContext, 4));
        mAdapter = new StarListAdapter(mContext);
        mAdapter.setOnClick(new StarListAdapter.OnClick() {
            @Override
            public void onClickPosition(View view, int position) {
                mAdapter.setPosition(position);
                mStarId = mRows.get(position).getStart().getId();
                initDatas(mStarId);
                initDrawer(false);
                //eventBus传递数据
                EventBus.getDefault().post(new EventStarId(mRows.get(position).getStart().getId()));
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

    private void initVp() {
        mViewPager.setAdapter(new StarPagerAdapter(getFragmentManager(), mDataList));
        mViewPager.setOffscreenPageLimit(3);
    }

    //初始化标签栏
    private void initIndicator() {

        mDataList = new ArrayList<>();
        //TODO
        mDataList.add(new FragmentInfos(new Fragment_star_xingwen(false), "星闻"));
        if (((int) SpUtil.getParam(SpConstant.SP_STATUS, -1)) == Constans.STATUS_STAR) {
            percent = 4.0f;
            mDataList.add(new FragmentInfos(new Fragment_star_shuju(), "数据"));
        }
        //这里随便传个数 粉团的有参构造 StarPagerForQiYeActivity_111界面用
        mDataList.add(new FragmentInfos(new Fragment_star_fentuan(-1), "粉团"));
        mDataList.add(new FragmentInfos(new Fragment_star_xingcheng(), "行程"));


        //背景
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator7 = new CommonNavigator(mContext);
        commonNavigator7.setScrollPivotX(0.5f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context, percent);
                simplePagerTitleView.setText(mDataList.get(index).getTitle());
                simplePagerTitleView.setNormalColor(Color.parseColor("#bfbfbf"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ff4f49"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 18));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#ff4f49"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(mContext, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
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
            CommonUtils.animationRotate(iv_icon_unfold, true,300);
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
            CommonUtils.animationRotate(iv_icon_unfold, false,300);
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
        ToastUtils.showToast("企业用户才能预约明星哟,赶快去认证吧!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        MyLogger.jLog().i("EventNotifyStarInfo() 注销");
    }

    //图集
    @OnClick(R.id.ll_photos)
    public void goPhotos(View view) {
        mIntent.putExtra("starId", mStarId);
        mIntent.setClass(MyApplication.getInstance(), PhotosAlbumActivity.class);
        startActivity(mIntent);
    }

    //后援团
    @OnClick(R.id.ll_hyt)
    public void goHouYuanTuan(View view) {
        mIntent.putExtra("starId", mStarId);
        mIntent.setClass(MyApplication.getInstance(), HYTActivity.class);
        startActivity(mIntent);
    }
}
