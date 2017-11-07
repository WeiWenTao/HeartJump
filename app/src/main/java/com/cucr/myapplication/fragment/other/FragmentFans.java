package com.cucr.myapplication.fragment.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.adapter.PagerAdapter.StarPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.StarListAdapter;
import com.cucr.myapplication.alipay.PayResult;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.starListAndJourney.QueryMyFocusStars;
import com.cucr.myapplication.core.starListAndJourney.QueryStarList;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.fragment.star.Fragment_star_fentuan;
import com.cucr.myapplication.fragment.star.Fragment_star_shuju;
import com.cucr.myapplication.fragment.star.Fragment_star_xingcheng;
import com.cucr.myapplication.fragment.star.Fragment_star_xingwen;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.others.FragmentInfos;
import com.cucr.myapplication.model.starList.MyFocusStarInfo;
import com.cucr.myapplication.model.starList.StarListInfos;
import com.cucr.myapplication.temp.ColorFlipPagerTitleView;
import com.cucr.myapplication.utils.CommonUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cucr on 2017/8/31.
 */

public class FragmentFans extends BaseFragment {

    private List<FragmentInfos> mDataList;

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

    //是否需要显示
    private boolean isShow = true;
    private StarListAdapter mAdapter;
    private QueryMyFocusStars mCore;
    private List<MyFocusStarInfo.RowsBean> mRows;
    private QueryStarList mStarCore;
    private int page = 1;
    private int rows = 100;
    private int type = 2;

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);


        mCore = new QueryMyFocusStars(getActivity());
        initRlv();
        mAdapter.setPosition(0);
        initHead();
        initIndicator();
        initVp();
    }

    private void initDatas(int starId) {
        mStarCore = new QueryStarList(getActivity());
        mStarCore.queryStarList(type, page, rows, starId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                StarListInfos starInfos = mGson.fromJson(response.get(), StarListInfos.class);
                if (starInfos.isSuccess()){
                    StarListInfos.RowsBean rowsBean = starInfos.getRows().get(0);
                    tv_fans.setText("粉丝 "+rowsBean.getFansCount());
                    tv_starname.setText(rowsBean.getRealName());
                    tv_star_title.setText(rowsBean.getRealName());
                    ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST+rowsBean.getUserPicCover(),backdrop, MyApplication.getImageLoaderOptions());
                }else {
                    ToastUtils.showToast(starInfos.getErrorMsg());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryMsg();
    }

    private void queryMsg() {
        mCore.queryMyFocuses(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyFocusStarInfo Info = mGson.fromJson(response.get(), MyFocusStarInfo.class);
                if (Info.isSuccess()) {
                    mRows = Info.getRows();
                    mAdapter.setData(mRows);
                    if (mRows == null || mRows.size() == 0){
                        return;
                    }
                    initDatas(mRows.get(0).getStartId());
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
                initDatas(mRows.get(position).getStartId());
                initDrawer(false);
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
        mViewPager.setOffscreenPageLimit(2);
    }

    //初始化标签栏
    private void initIndicator() {

        mDataList = new ArrayList<>();
        //TODO
        mDataList.add(new FragmentInfos(new Fragment_star_xingwen(), "星闻"));
        //        if (明星用户) {
        mDataList.add(new FragmentInfos(new Fragment_star_shuju(), "数据"));
//        }
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
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context, 4.0f);
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
            CommonUtils.animationRotate(iv_icon_unfold, true);
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
            CommonUtils.animationRotate(iv_icon_unfold, false);
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

    //===========================================================================================


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        };
    };

    //消息界面
    @OnClick(R.id.iv_header_msg)
    public void pay(View view) {
        startActivity(new Intent(mContext, MessageActivity.class));
    }

    //预约提示框
    @OnClick(R.id.tv_yuyue)
    public void toYuYue(View view){
        ToastUtils.showToast("企业用户才能预约明星哟,赶快去认证吧!");
    }
}
