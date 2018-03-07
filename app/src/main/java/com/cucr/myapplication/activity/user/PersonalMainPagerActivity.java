package com.cucr.myapplication.activity.user;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.PersonalMainPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventRewardGifts;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.user.UserCenterInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.core.user.UserCore;
import com.cucr.myapplication.fragment.personalMainPager.DongTaiFragment;
import com.cucr.myapplication.fragment.personalMainPager.StarFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
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

public class PersonalMainPagerActivity extends Activity {

    //指示器
    @ViewInject(R.id.magic_indicator_personal_page)
    MagicIndicator magicIndicator;

    //ViewPager
    @ViewInject(R.id.personal_vp)
    ViewPager mViewPager;

    //底部关注
    @ViewInject(R.id.tv_foucs)
    TextView tv_foucs;

    //头像
    @ViewInject(R.id.iv_star_head)
    ImageView iv_star_head;

    //昵称
    @ViewInject(R.id.tv_nick_name)
    TextView tv_nick_name;

    //关注人数
    @ViewInject(R.id.tv_focuses)
    TextView tv_focuses;

    //粉丝人数
    @ViewInject(R.id.tv_fenes)
    TextView tv_fenes;

    //用户签名
    @ViewInject(R.id.tv_user_sign)
    TextView tv_user_sign;

    //用户性别
    @ViewInject(R.id.iv_general)
    ImageView iv_general;

    //背景封面 (静态数据)
    @ViewInject(R.id.iv_fuzzy_bg)
    ImageView iv_fuzzy_bg;

    //礼物动画
    @ViewInject(R.id.iv_gift)
    private ImageView iv_gift;

    //礼物动画
    @ViewInject(R.id.ll_footbar)
    private LinearLayout ll_footbar;


    private List<String> mDataList;
    private UserCore mUserCore;
    private List<Fragment> fragmentList;
    private boolean isFoucs;
    private FocusCore mFocusCore;
    private int mUserId;
    private int mFssl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_pager);
        //注册
        EventBus.getDefault().register(this);
        ViewUtils.inject(this);
        mUserId = getIntent().getIntExtra("userId", -1);
        if (mUserId == ((int) SpUtil.getParam(SpConstant.USER_ID, -1))) {
            ll_footbar.setVisibility(View.GONE);
        }
        initData();     //查个人信息
        initViews();


    }

    //查个人信息
    private void initData() {
        mDataList = new ArrayList<>();
        mUserCore = new UserCore();
        mFocusCore = new FocusCore();
        mUserCore.queryUserCenterInfo(mUserId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                String json = response.get();
                UserCenterInfo userInfo = MyApplication.getGson().fromJson(json, UserCenterInfo.class);
                if (userInfo.isSuccess()) {
                    setData(userInfo.getObj());
                } else {
                    ToastUtils.showToast(userInfo.getMsg());
                }
            }
        });
    }


    //将获取到的信息设置到控件上
    private void setData(UserCenterInfo.ObjBean obj) {
        mDataList.add("动态 " + obj.getDtsl());
        mDataList.add("明星 " + obj.getGzmxsl());
        initIndicator();
        isFoucs = obj.getIsgz() == 1;
        tv_foucs.setText(isFoucs ? "已关注" : "加关注");
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + obj.getYhtx(), iv_star_head, MyApplication.getImageLoaderOptions());
        tv_nick_name.setText(obj.getYhnc());
        tv_focuses.setText("关注 " + obj.getGzsl());
        mFssl = obj.getFssl();
        tv_fenes.setText("粉丝 " + mFssl);
        tv_user_sign.setText(obj.getQm() + "123");
        iv_general.setImageResource(obj.getSex() == 0 ? R.drawable.icon_boy_ : R.drawable.icon_girl_);
    }

    private void initViews() {
        //初始化头部 沉浸栏
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        fragmentList = new ArrayList<>();
        fragmentList.add(new DongTaiFragment(mUserId));
        fragmentList.add(new StarFragment(mUserId));
        mViewPager.setAdapter(new PersonalMainPagerAdapter(getFragmentManager(), fragmentList));
    }



    //初始化标签栏
    private void initIndicator() {
        //背景
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.5f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context, 2.0f);
                simplePagerTitleView.setText(mDataList.get(index));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //返回
    @OnClick(R.id.iv_pager_back)
    public void back(View view) {
        finish();
    }

    //点击关注
    @OnClick(R.id.tv_foucs)
    public void foucs(View view) {
        if (isFoucs) {
            mFocusCore.cancaleFocus(mUserId, new OnCommonListener() {
                @Override
                public void onRequestSuccess(Response<String> response) {
                    ReBackMsg reBackMsg = MyApplication.getGson().fromJson(response.get(), ReBackMsg.class);
                    if (reBackMsg.isSuccess()) {
                        ToastUtils.showToast("已取消关注！");
                        mFssl = mFssl - 1;
                        tv_fenes.setText("粉丝 " + (mFssl));
                    } else {
                        ToastUtils.showToast(reBackMsg.getMsg());
                    }
                }
            });
            tv_foucs.setText("加关注");
            isFoucs = false;

        } else {
            mFocusCore.toFocus(mUserId);
            tv_foucs.setText("已关注");
            isFoucs = true;
            mFssl = mFssl + 1;
            tv_fenes.setText("粉丝 " + (mFssl));
        }

    }

    //点击liao
    @OnClick(R.id.ll_liao)
    public void liao(View view) {

    }

    //打赏动画
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

    //注销
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
    }
}
