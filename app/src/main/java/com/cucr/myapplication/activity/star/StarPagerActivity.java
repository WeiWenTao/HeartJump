package com.cucr.myapplication.activity.star;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.activity.hyt.HYTActivity;
import com.cucr.myapplication.activity.picWall.PhotosAlbumActivity;
import com.cucr.myapplication.activity.yuyue.YuYueCatgoryActivity;
import com.cucr.myapplication.adapter.PagerAdapter.StarPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventDaBangStarPagerId;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.others.FragmentInfos;
import com.cucr.myapplication.bean.starList.StarListInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.core.starListAndJourney.QueryStarListCore;
import com.cucr.myapplication.fragment.star.Fragment_star_fentuan;
import com.cucr.myapplication.fragment.star.Fragment_star_shuju;
import com.cucr.myapplication.fragment.star.Fragment_star_xingcheng;
import com.cucr.myapplication.fragment.star.Fragment_star_xingwen;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMShareAPI;
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

public class StarPagerActivity extends FragmentActivity implements RequersCallBackListener {

    //ViewPager
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;

    //指示器
    @ViewInject(R.id.magic_star_forqiye)
    private MagicIndicator magicIndicator;

    //粉丝数量
    @ViewInject(R.id.tv_fans)
    private TextView tv_fans;

    //明星姓名
    @ViewInject(R.id.tv_starname)
    private TextView tv_starname;

    //标题姓名
    @ViewInject(R.id.tv_base_title)
    private TextView tv_base_title;

    //明星封面
    @ViewInject(R.id.backdrop)
    private ImageView backdrop;

    //关注
    @ViewInject(R.id.tv_focus_forqiye)
    private TextView tv_focus_forqiye;

    //礼物动画
    @ViewInject(R.id.iv_gift)
    private ImageView iv_gift;

    //微博图标
    @ViewInject(R.id.iv_weib)
    private ImageView iv_weib;

    //企业看的
    @ViewInject(R.id.ll_qiye_look)
    private LinearLayout ll_qiye_look;

    private StarListInfos.RowsBean mData;
    private FocusCore mCore;
    private Gson mGson;
    private int mStarId;
    private QueryStarListCore mStarCore;
    private List<FragmentInfos> mDataList;
    private Intent mIntent;
    private float percent;//占屏比

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_pager);

        EventBus.getDefault().register(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        ViewUtils.inject(this);
        //确定身份 企业用户才显示预约栏
        ll_qiye_look.setVisibility(CommonUtils.isQiYe() ? View.VISIBLE : View.GONE);
        mCore = new FocusCore();
        percent = 3.0f;
        mGson = new Gson();
        mStarCore = new QueryStarListCore();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getDatas();
        initIndicator();
        initVp();
    }

    private void initVp() {
        mViewPager.setAdapter(new StarPagerAdapter(getSupportFragmentManager(), mDataList));
        mViewPager.setOffscreenPageLimit(2);
    }

    //初始化标签栏
    private void initIndicator() {

        mDataList = new ArrayList<>();

        mDataList.add(new FragmentInfos(new Fragment_star_xingwen(), "星闻"));
        //企业和明星用户才能看到数据信息
        if (CommonUtils.isStar() || CommonUtils.isQiYe()) {
            mDataList.add(new FragmentInfos(new Fragment_star_shuju(), "数据"));
            percent = 4.0f;
        }
        mDataList.add(new FragmentInfos(new Fragment_star_fentuan(), "粉团"));
        mDataList.add(new FragmentInfos(new Fragment_star_xingcheng(), "行程"));

        //背景
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator7 = new CommonNavigator(MyApplication.getInstance());
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

    //出演要求
    @OnClick(R.id.tv_request)
    public void request(View view) {
        Intent intent = new Intent(MyApplication.getInstance(), StarRequiresActivity.class);
        intent.putExtra("starId", mStarId);
        startActivity(intent);
    }

    //关注
    @OnClick(R.id.tv_focus_forqiye)
    public void focus(View view) {
        //是否已经关注该明星
        if (mData.getIsfollow() == 1) {
            mCore.cancaleFocus(mStarId, new OnCommonListener() {
                @Override
                public void onRequestSuccess(Response<String> response) {
                    ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                    if (reBackMsg.isSuccess()) {
                        ToastUtils.showToast("已取消关注！");
                        mData.setIsfollow(0);
                        tv_focus_forqiye.setText("关注");
                    } else {
                        ToastUtils.showToast(reBackMsg.getMsg());
                    }
                }
            });
        } else {
            mCore.toFocus(mStarId);
            mData.setIsfollow(1);
            tv_focus_forqiye.setText("已关注");
        }

    }

    //跳转预约界面
    @OnClick(R.id.tv_yuyue)
    public void goYuYue(View view) {
        Intent intent = new Intent(MyApplication.getInstance(), YuYueCatgoryActivity.class);
        intent.putExtra("data", mData);
        startActivity(intent);
    }

    public void getDatas() {

        //获取数据
        mStarId = getIntent().getIntExtra("starId", -1);
        //发送明星id到明星主页
        EventBus.getDefault().postSticky(new EventDaBangStarPagerId(mStarId));
        //并初始化
        mStarCore.queryStar(2, 1, 1, mStarId, null, null, this);
    }

    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        setResult(111, getIntent().putExtra("data", mData));
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(111, getIntent().putExtra("data", mData));
        finish();
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

    @OnClick(R.id.ll_photos)
    public void goPhotos(View view) {
        mIntent.putExtra("starId", mStarId);
        mIntent.setClass(MyApplication.getInstance(), PhotosAlbumActivity.class);
        startActivity(mIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //后援团
    @OnClick(R.id.ll_hyt)
    public void goHouYuanTuan(View view) {
        mIntent.putExtra("starId", mStarId);
        mIntent.setClass(MyApplication.getInstance(), HYTActivity.class);
        startActivity(mIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
        mDataList.clear();
        mDataList = null;

    }

    @OnClick(R.id.iv_weib)
    public void click(View iv_weib) {
        Intent intent = new Intent(MyApplication.getInstance(), TestWebViewActivity.class);
        intent.putExtra("url", mData.getWeiboUrl());
        startActivity(intent);
    }

    //查询明星信息回调
    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        StarListInfos starInfos = mGson.fromJson(response.get(), StarListInfos.class);
        if (starInfos.isSuccess()) {
            mData = starInfos.getRows().get(0);
            //并初始化
            tv_fans.setText("粉丝 " + mData.getFansCount());
            tv_starname.setText(mData.getRealName());
            tv_base_title.setText(mData.getRealName());
            tv_focus_forqiye.setText(mData.getIsfollow() == 1 ? "已关注" : "关注");
            if (TextUtils.isEmpty(mData.getWeiboUrl())) {
                iv_weib.setVisibility(View.GONE);
            }
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mData.getUserPicCover(), backdrop, MyApplication.getImageLoaderOptions());
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
}
