package com.cucr.myapplication.fragment.DaBang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.star.StarPagerActivity;
import com.cucr.myapplication.adapter.LvAdapter.DaBangAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.dabang.BangDanInfo;
import com.cucr.myapplication.bean.eventBus.EventFIrstStarId;
import com.cucr.myapplication.bean.eventBus.EventRequestFinish;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.dabang.BangDanCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaBangStyle;
import com.cucr.myapplication.widget.refresh.RefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.List;

/**
 * Created by 911 on 2017/6/22.
 */

public class DaBangFragment extends BaseFragment implements DialogDaBangStyle.ClickconfirmListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private BangDanCore mCore;
    private DaBangAdapter mAdapter;
    private View headView;
    private DialogDaBangStyle mDialog;
    private int starId;
    private BangDanInfo.RowsBean mRowsBean1;
    private BangDanInfo.RowsBean mRowsBean2;
    private BangDanInfo.RowsBean mRowsBean3;
    private int page;
    private int rows;
    private RefreshLayout mMyRefreshListView;
    private Intent mIntent;
    private Context dialogContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(View childView) {
        dialogContext = childView.getContext();
        childView.findViewById(R.id.iv_header_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MessageActivity.class));
            }
        });
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
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
        mMyRefreshListView = (RefreshLayout) childView.findViewById(R.id.swipe_layout);
        ListView lv_dabang = (ListView) childView.findViewById(R.id.lv_dabang);
        mMyRefreshListView.setOnLoadListener(this);
        mMyRefreshListView.setOnRefreshListener(this);
        page = 1;
        rows = 15;
        headView = View.inflate(mContext, R.layout.head_dabang, null);
        ViewUtils.inject(this, headView);

        mCore = new BangDanCore(mContext);

        lv_dabang.addHeaderView(headView);
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
                //发送明星id到明星主页
                EventBus.getDefault().postSticky(new EventFIrstStarId(starId));
            }
        });

        onRefresh();
    }

    private void initHead(List<BangDanInfo.RowsBean> rows) {

        mRowsBean1 = rows.get(0);
        mRowsBean2 = rows.get(1);
        mRowsBean3 = rows.get(2);

        ImageView userHead1 = (ImageView) headView.findViewById(R.id.iv_head1);
        ImageView userHead2 = (ImageView) headView.findViewById(R.id.iv_head2);
        ImageView userHead3 = (ImageView) headView.findViewById(R.id.iv_head3);
        TextView tv_name1 = (TextView) headView.findViewById(R.id.tv_name1);
        TextView tv_name2 = (TextView) headView.findViewById(R.id.tv_name2);
        TextView tv_name3 = (TextView) headView.findViewById(R.id.tv_name3);
        TextView tv_num1 = (TextView) headView.findViewById(R.id.tv_num1);
        TextView tv_num2 = (TextView) headView.findViewById(R.id.tv_num2);
        TextView tv_num3 = (TextView) headView.findViewById(R.id.tv_num3);

        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean1.getUserHeadPortrait(), userHead1, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean2.getUserHeadPortrait(), userHead2, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean3.getUserHeadPortrait(), userHead3, MyApplication.getImageLoaderOptions());

        tv_name1.setText(mRowsBean1.getRealName());
        tv_name2.setText(mRowsBean2.getRealName());
        tv_name3.setText(mRowsBean3.getRealName());

        tv_num1.setText(mRowsBean1.getUserMoney() + "");
        tv_num2.setText(mRowsBean2.getUserMoney() + "");
        tv_num3.setText(mRowsBean3.getUserMoney() + "");
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
        if (!mMyRefreshListView.isRefreshing()) {
            mMyRefreshListView.setRefreshing(true);
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
        mMyRefreshListView.setRefreshing(false);
        mMyRefreshListView.setLoading(false);
    }


    @Override
    public void onLoad() {
        page++;
        mCore.queryBangDanInfo(page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                BangDanInfo bangDanInfo = mGson.fromJson(response.get(), BangDanInfo.class);
                if (bangDanInfo.isSuccess()) {
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

}
