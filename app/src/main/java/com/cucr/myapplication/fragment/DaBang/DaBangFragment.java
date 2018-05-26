package com.cucr.myapplication.fragment.DaBang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.adapter.LvAdapter.DaBangAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.dabang.BangDanInfo;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.dabang.BangDanCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaBangStyle;
import com.cucr.myapplication.widget.refresh.RefreshLayout;
import com.cucr.myapplication.widget.scroll.ObservableScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by 911 on 2017/6/22.
 */

public class DaBangFragment extends BaseFragment implements DialogDaBangStyle.ClickconfirmListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, ObservableScrollView.ScrollViewListener {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.rl_banner)
    private RelativeLayout rl_banner;

    @ViewInject(R.id.scrollview)
    private ObservableScrollView scrollView;

    @ViewInject(R.id.head)
    private RelativeLayout head;

    @ViewInject(R.id.line)
    private View line;

    private BangDanCore mCore;
    private DaBangAdapter mAdapter;
    private DialogDaBangStyle mDialog;
    private int starId;
    private BangDanInfo.RowsBean mRowsBean1;
    private BangDanInfo.RowsBean mRowsBean2;
    private BangDanInfo.RowsBean mRowsBean3;
    private int page;
    private int rows;
    private RefreshLayout slv;
    private Intent mIntent;
    private Context dialogContext;
    private int height;
    private boolean canRefresh;
    private View child;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initListeners() {
        line.setVisibility(View.GONE);
        ViewTreeObserver vto = rl_banner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                head.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = rl_banner.getHeight() - CommonUtils.dip2px(68);
                scrollView.setScrollViewListener(DaBangFragment.this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void initView(View childView) {
        child = childView;
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        ViewUtils.inject(this, childView);
        initListeners();
        canRefresh = true;      //第一次进页面可以刷新
        dialogContext = childView.getContext();
        childView.findViewById(R.id.iv_header_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MessageActivity.class));
            }
        });
        //如果是企业用户
       /* if (((int) SpUtil.getParam(SpConstant.SP_STATUS, -1)) == Constans.STATUS_QIYE) {
            //跳转企业用户看的明星主页
            mIntent = new Intent(MyApplication.getInstance(), StarPagerForQiYeActivity_111.class);
        } else {
            //其他用户跳转粉丝看的主页
            mIntent = new Intent(MyApplication.getInstance(), StarPagerForFans.class);
        }*/

        mIntent = new Intent(MyApplication.getInstance(), StarPagerActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        slv = (RefreshLayout) childView.findViewById(R.id.swipe_layout);
        ListView lv_dabang = (ListView) childView.findViewById(R.id.lv_dabang);
        slv.setOnLoadListener(this);
        slv.setOnRefreshListener(this);
        slv.setProgressViewOffset(true, 100, 300);
        page = 1;
        rows = 15;

//        headView = View.inflate(mContext, R.layout.head_dabang, null);
//        ViewUtils.inject(this, headView);
//        lv_dabang.addHeaderView(headView);

        mCore = new BangDanCore(mContext);

        mAdapter = new DaBangAdapter();
        lv_dabang.setAdapter(mAdapter);
        mAdapter.setOnClick(new DaBangAdapter.OnClick() {
            @Override
            public void daBang(BangDanInfo.RowsBean rowsBean) {
                mDialog = new DialogDaBangStyle(dialogContext, R.style.BirthdayStyleTheme);
                mDialog.setConfirmListener(DaBangFragment.this);
                mDialog.setData(rowsBean.getRealName());
                mDialog.show();
                starId = rowsBean.getId();
            }

            @Override
            public void clickStar(int starid) {
                starId = starid;
                mIntent.putExtra("starId", starId);
                startActivity(mIntent);
            }
        });

        onRefresh();
    }

    private void initHead(List<BangDanInfo.RowsBean> rows) {

        mRowsBean1 = rows.get(0);
        mRowsBean2 = rows.get(1);
        mRowsBean3 = rows.get(2);

        ImageView userHead1 = (ImageView) child.findViewById(R.id.iv_head1);
        ImageView userHead2 = (ImageView) child.findViewById(R.id.iv_head2);
        ImageView userHead3 = (ImageView) child.findViewById(R.id.iv_head3);
        TextView tv_name1 = (TextView) child.findViewById(R.id.tv_name1);
        TextView tv_name2 = (TextView) child.findViewById(R.id.tv_name2);
        TextView tv_name3 = (TextView) child.findViewById(R.id.tv_name3);
        TextView tv_num1 = (TextView) child.findViewById(R.id.tv_num1);
        TextView tv_num2 = (TextView) child.findViewById(R.id.tv_num2);
        TextView tv_num3 = (TextView) child.findViewById(R.id.tv_num3);

        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean1.getUserHeadPortrait(), userHead1, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean2.getUserHeadPortrait(), userHead2, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean3.getUserHeadPortrait(), userHead3, MyApplication.getImageLoaderOptions());

        tv_name1.setText(mRowsBean1.getRealName());
        tv_name2.setText(mRowsBean2.getRealName());
        tv_name3.setText(mRowsBean3.getRealName());

        tv_num1.setText("心跳值 " + mRowsBean1.getUserMoney());
        tv_num2.setText("心跳值 " + mRowsBean2.getUserMoney() + "");
        tv_num3.setText("心跳值 " + mRowsBean3.getUserMoney() + "");
    }

    @OnClick(R.id.iv_head3)
    public void iv_head3(View view) {
        starId = mRowsBean3.getId();
        mIntent.putExtra("starId", starId);
        startActivity(mIntent);
        //发送明星id到明星主页
        EventBus.getDefault().postSticky(new EventFIrstStarId(starId));
    }

    @OnClick(R.id.iv_head2)
    public void iv_head2(View view) {
        starId = mRowsBean2.getId();
        mIntent.putExtra("starId", starId);
        startActivity(mIntent);
        //发送明星id到明星主页
        EventBus.getDefault().postSticky(new EventFIrstStarId(starId));
    }

    @OnClick(R.id.iv_head1)
    public void iv_head1(View view) {
        starId = mRowsBean1.getId();
        mIntent.putExtra("starId", starId);
        startActivity(mIntent);
        //发送明星id到明星主页
        EventBus.getDefault().postSticky(new EventFIrstStarId(starId));
    }

    @OnClick(R.id.iv_dabang_first)
    public void daBangFirst(View view) {
        mDialog = new DialogDaBangStyle(dialogContext, R.style.BirthdayStyleTheme);
        mDialog.setData(mRowsBean1.getRealName());
        mDialog.setConfirmListener(DaBangFragment.this);
        mDialog.show();
        starId = mRowsBean1.getId();
    }

    @OnClick(R.id.iv_dabang_second)
    public void daBangSecond(View view) {
        mDialog = new DialogDaBangStyle(dialogContext, R.style.BirthdayStyleTheme);
        mDialog.setData(mRowsBean2.getRealName());
        mDialog.setConfirmListener(DaBangFragment.this);
        mDialog.show();
        starId = mRowsBean2.getId();
    }

    @OnClick(R.id.iv_dabang_third)
    public void daBangThird(View view) {
        mDialog = new DialogDaBangStyle(dialogContext, R.style.BirthdayStyleTheme);
        mDialog.setData(mRowsBean3.getRealName());
        mDialog.setConfirmListener(DaBangFragment.this);
        mDialog.show();
        starId = mRowsBean3.getId();
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_dabang;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCore.stopRequest();
    }

    @Override
    public void onClickConfirm(int howMuch) {
        mCore.daBang(howMuch, starId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("打榜成功!");
                    //刷新一遍
//                    queryBdInfo();
                    onRefresh();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
            }
        });
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    public void onRefresh() {
        if (!slv.isRefreshing()) {
            slv.setRefreshing(true);
        }
        page = 1;
        mCore.queryBangDanInfo(page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                BangDanInfo bangDanInfo = mGson.fromJson(response.get(), BangDanInfo.class);
                if (bangDanInfo.isSuccess()) {
                    mAdapter.setData(bangDanInfo.getRows());
                    initHead(bangDanInfo.getRows());
                } else {
                    ToastUtils.showToast(bangDanInfo.getErrorMsg());
                }
            }
        });
    }


    //请求完成  如果还在加载  就停止加载(无网络情况)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onFinish(EventRequestFinish event) {
        slv.setRefreshing(false);
        slv.setLoading(false);
    }


    @Override
    public void onLoad() {
        page++;
        mCore.queryBangDanInfo(page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                BangDanInfo bangDanInfo = mGson.fromJson(response.get(), BangDanInfo.class);
                if (bangDanInfo.isSuccess()) {
                    canRefresh = true;
                    mAdapter.addData(bangDanInfo.getRows());
                    if (rows > bangDanInfo.getRows().size()) {
                        ToastUtils.showEnd();
                    }
                } else {
                    ToastUtils.showToast(bangDanInfo.getErrorMsg());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (canRefresh && scrollView.getScrollY() + scrollView.getHeight() - scrollView.getPaddingTop() - scrollView.getPaddingBottom() == scrollView.getChildAt(0).getHeight()) {
            canRefresh = false;
            onLoad();
        }
        if (y <= 0) {   //设置标题的背景颜色
            head.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            line.setVisibility(View.GONE);
            tv_title.setTextColor(getResources().getColor(R.color.white));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            line.setVisibility(View.GONE);
            tv_title.setTextColor(Color.rgb(255 - ((int) alpha), 255 - ((int) alpha), 255 - ((int) alpha)));
            head.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {    //滑动到banner下面设置普通颜色
            head.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
            line.setVisibility(View.VISIBLE);
            tv_title.setTextColor(getResources().getColor(R.color.title_color));
        }
    }

}
