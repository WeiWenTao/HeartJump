package com.cucr.myapplication.activity.dongtai;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.comment.FenTuanCatgoryActiviry;
import com.cucr.myapplication.activity.fenTuan.DaShangCatgoryActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.PagerAdapter.DaShangPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FtAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventContentId;
import com.cucr.myapplication.bean.eventBus.EventDsSuccess;
import com.cucr.myapplication.bean.eventBus.EventDuiHuanSuccess;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.bean.fenTuan.FtGiftsInfo;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.bean.fenTuan.SignleFtInfo;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.viewpager.NoScrollPager;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DongTaiActivity extends BaseActivity implements FtAdapter.OnClickBt, SwipeRecyclerView.OnLoadListener {

    //礼物
    @ViewInject(R.id.tv_gift)
    private TextView gift;

    //背包
    @ViewInject(R.id.tv_backpack)
    private TextView backpack;

    //礼物和背包 ViewPager
    @ViewInject(R.id.vp_dahsnag)
    private NoScrollPager vp_dahsnag;

    //动态列表
    @ViewInject(R.id.rlv_dongtai)
    private SwipeRecyclerView rlv_dongtai;

    //动态列表
    @ViewInject(R.id.activity_dong_tai)
    private View mView;

    private int page;
    private int rows;
    private int userId;
    private QueryFtInfoCore queryCore;
    private FtAdapter mAdapter;
    private QueryFtInfos mQueryFtInfos;
    private QueryFtInfos mQueryFtInfoss;
    private LayoutInflater layoutInflater;
    private PopupWindow popWindow;
    private DaShangPagerAdapter mDaShangPagerAdapter;
    private PayCenterCore mPayCenterCore;
    private Gson mGson;
    private int position;
    private Integer giveNum;
    private DialogShareStyle mDialog;

    @Override
    protected void initChild() {
        EventBus.getDefault().register(this);
        initTitle("我发布的");
        initView();
        inipopWindow();
        initInfos();
    }

    //返回布局给父类
    @Override
    protected int getChildRes() {
        return R.layout.activity_dong_tai;
    }

    private void initInfos() {
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

    private void inipopWindow() {
        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
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
        mDaShangPagerAdapter = new DaShangPagerAdapter();
        vp_dahsnag.setAdapter(mDaShangPagerAdapter);
    }

    private void initView() {
        userId = (int) SpUtil.getParam(SpConstant.USER_ID, -1);
        rows = 10;
        layoutInflater = getLayoutInflater();
        queryCore = new QueryFtInfoCore();
        mPayCenterCore = new PayCenterCore();
        mGson = MyApplication.getGson();
        rlv_dongtai.setOnLoadListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(MyApplication.getInstance());
        rlv_dongtai.getRecyclerView().setLayoutManager(layout);
        mAdapter = new FtAdapter();
        rlv_dongtai.setAdapter(mAdapter);
        mAdapter.setOnClickBt(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (!rlv_dongtai.getSwipeRefreshLayout().isRefreshing()) {
            rlv_dongtai.getSwipeRefreshLayout().setRefreshing(true);
        }
        page = 1;
        queryFtInfo();
    }

    @Override
    public void onLoadMore() {
        page++;
        queryCore.queryFtInfo(-1, 1, userId, false, page, rows, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                mQueryFtInfoss = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfoss.isSuccess()) {
//                    mQueryFtInfos.getRows().addAll(mQueryFtInfoss.getRows());
                    mAdapter.addData(mQueryFtInfoss.getRows());
                    //判断是否还有数据
                    if (mQueryFtInfoss.getTotal() <= page * rows) {
                        rlv_dongtai.onNoMore("没有更多了");
                    } else {
                        rlv_dongtai.complete();
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfoss.getErrorMsg());
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

    private void queryFtInfo() {
        MyLogger.jLog().i("动态参数 userId=" + userId + ",page=" + page + ",rows=" + rows);
        queryCore.queryFtInfo(-1, 1, userId, false, page, rows, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    MyLogger.jLog().i("mQueryFtInfos:" + mQueryFtInfos);
                    mAdapter.setData(mQueryFtInfos);
                    rlv_dongtai.complete();
                    if (mQueryFtInfos.getTotal() == mQueryFtInfos.getRows().size()) {
                        rlv_dongtai.onNoMore("木有了");
                    }
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
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

    //兑换成功再查询背包
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void eventData(EventDuiHuanSuccess event) {
        MyLogger.jLog().i("queryBackpack");
        queryBackpack();
    }

    //打赏成功后打赏人数增加
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDsRecord(EventDsSuccess event) {
        QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(event.getPosition());
        rowsBean.setDssl(rowsBean.getDssl() + 1);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickGoods(int position, final QueryFtInfos.RowsBean rowsBean) {
        MyLogger.jLog().i("onClickGoods");
        queryCore.ftGoods(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (rowsBean.isIsGiveUp()) {
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
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }
            }
        });
    }

    @Override
    public void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean formCommond) {
        MyLogger.jLog().i("onClickCommends");
        this.position = position;
        MyLogger.jLog().i("Commendposition:" + position);
        Intent intent = new Intent(MyApplication.getInstance(), FenTuanCatgoryActiviry.class);
        intent.putExtra("hasPicture", hasPicture);
        intent.putExtra("dataId", rowsBean.getId() + "");
        intent.putExtra("isFormConmmomd", formCommond);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }

    @Override
    public void onClickshare(int id) {
        mDialog.setData(new ShareEntity("this is title", " this is describe", HttpContans.ADDRESS_FT_SHARE + id, ""));
    }

    @Override
    public void onClickDaShang(int contentId, int position) {
        popWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
        //可以考虑用eventbus传值
        EventBus.getDefault().postSticky(new EventContentId(contentId, position));
    }

    @Override
    public void onClickDsRecored(int contentId) {
        Intent intent = new Intent(MyApplication.getInstance(), DaShangCatgoryActivity.class);
        intent.putExtra("contentId", contentId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //点击用户头像
    @Override
    public void onClickUser(int userId, boolean isStar) {
        Intent intent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
            SignleFtInfo.ObjBean mRowsBean = (SignleFtInfo.ObjBean) data.getSerializableExtra("rowsBean");
            final QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(position);
            rowsBean.setGiveUpCount(mRowsBean.getGiveUpCount());
            rowsBean.setIsGiveUp(mRowsBean.isIsGiveUp());
            rowsBean.setCommentCount(mRowsBean.getCommentCount());
            mAdapter.notifyDataSetChanged();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        UMShareAPI.get(this).release();
    }

}
