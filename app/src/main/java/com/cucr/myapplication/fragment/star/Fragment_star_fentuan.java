package com.cucr.myapplication.fragment.star;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.DaShangCatgoryActivity;
import com.cucr.myapplication.activity.fenTuan.FenTuanCatgoryActiviry;
import com.cucr.myapplication.activity.fenTuan.PublishActivity;
import com.cucr.myapplication.adapter.PagerAdapter.DaShangPagerAdapter;
import com.cucr.myapplication.adapter.RlVAdapter.FtAdapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.funTuan.QueryFtInfoCore;
import com.cucr.myapplication.core.pay.PayCenterCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.eventBus.EventContentId;
import com.cucr.myapplication.model.eventBus.EventDuiHuanSuccess;
import com.cucr.myapplication.model.eventBus.EventFIrstStarId;
import com.cucr.myapplication.model.eventBus.EventStarId;
import com.cucr.myapplication.model.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.model.fenTuan.FtGiftsInfo;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.cucr.myapplication.widget.viewpager.NoScrollPager;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_fentuan extends Fragment implements View.OnClickListener, SwipeRecyclerView.OnLoadListener, FtAdapter.OnClickBt {

    //礼物
    @ViewInject(R.id.tv_gift)
    private TextView gift;

    //礼物和背包 ViewPager
    @ViewInject(R.id.vp_dahsnag)
    private NoScrollPager vp_dahsnag;

    //背包
    @ViewInject(R.id.tv_backpack)
    private TextView backpack;

    private PayCenterCore mPayCenterCore;
    private View view;
    private Context mContext;
    private FloatingActionsMenu mFam;
    private FloatingActionButton action_a, action_b;
    private QueryFtInfoCore queryCore;
    private Gson mGson;
    // TODO: 2017/9/22 eventBus 获取
    private int starId;
    private int qYStarId; //企业用户可以直接传递id   企业用户直接点击明星列表进来的  所以只查看点击明星的信息
    private int page = 1;
    private int rows = 2;
    private SwipeRecyclerView rlv_fentuan;  //这不是RecyclerView  而是RecyclerView + swipeRefreshLayout
    private QueryFtInfos mQueryFtInfos;
    private QueryFtInfos mQueryFtInfoss;
    private FtAdapter mAdapter;
    private Integer giveNum;
    private int position = -1;
    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private DaShangPagerAdapter mDaShangPagerAdapter;

    @SuppressLint("ValidFragment")
    public Fragment_star_fentuan(int id) {
        qYStarId = id;
    }

    public Fragment_star_fentuan() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//订阅
        mContext = MyApplication.getInstance();
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        queryCore = new QueryFtInfoCore();
        mPayCenterCore = new PayCenterCore();
        mGson = new Gson();
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_other_fans_fentuan, container, false);
            initView();
            initRlV();
            inipopWindow();
            initInfos();
        }


        return view;
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

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void eventData(EventDuiHuanSuccess event) {
        MyLogger.jLog().i("queryBackpack");
        queryBackpack();
    }

    private void queryFtInfo() {
        // TODO: 2017/11/3
        //如果是企业用户
        /*if (((int) SpUtil.getParam(SpConstant.SP_STATUS, -1)) == Constans.STATUS_QIYE) {
            starId = qYStarId;
        }*/
        MyLogger.jLog().i("粉团参数 starId=" + starId + ",page=" + page + ",rows=" + rows);
        queryCore.queryFtInfo(starId, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    MyLogger.jLog().i("mQueryFtInfos:" + mQueryFtInfos);
                    mAdapter.setData(mQueryFtInfos);
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
            }
        });
    }


    //切换明星的时候
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(EventStarId event) {
        starId = event.getStarId();
        page = 1;
        rows = 2;
        MyLogger.jLog().i("EventStarId：" + starId);
        if (queryCore == null) {
            queryCore = new QueryFtInfoCore();
        }
        queryFtInfo();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(EventFIrstStarId event) {
        starId = event.getFirstId();
        queryFtInfo();
        if (event!=null){
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    private void initRlV() {
        MyLogger.jLog().i("initRlV");
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        rlv_fentuan.getRecyclerView().setLayoutManager(layout);
        mAdapter = new FtAdapter(mContext);
        rlv_fentuan.setAdapter(mAdapter);
        mAdapter.setOnClickBt(this);

//        TextView textView = new TextView(mContext);
//        textView.setText("empty view");
//        rlv_fentuan.setEmptyView(textView);

    }

    private void initView() {
//        rlv_fentuan = (RecyclerView) view.findViewById(R.id.rlv_fentuan);
//        rlv_fentuan.setItemAnimator(new DefaultItemAnimator());

        rlv_fentuan = (SwipeRecyclerView) view.findViewById(R.id.rlv_fentuan);
//        rlv_fentuan.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        rlv_fentuan.setOnLoadListener(this);
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
            onRefresh();
            rlv_fentuan.getRecyclerView().smoothScrollToPosition(0);
        }

        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
            QueryFtInfos.RowsBean mRowsBean = (QueryFtInfos.RowsBean) data.getSerializableExtra("rowsBean");
            final QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(position);
            rowsBean.setGiveUpCount(mRowsBean.getGiveUpCount());
            rowsBean.setIsGiveUp(mRowsBean.isIsGiveUp());
            rowsBean.setCommentCount(mRowsBean.getCommentCount());
            mAdapter.notifyDataSetChanged();
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
        page = 1;
        queryFtInfo();
        rlv_fentuan.complete();

    }


    @Override
    public void onLoadMore() {
        MyLogger.jLog().i("onLoadMore");
        page++;
        queryCore.queryFtInfo(starId, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfoss = mGson.fromJson(response.get(), QueryFtInfos.class);
                MyLogger.jLog().i("mQueryFtInfoss.getRows:" + mQueryFtInfoss.getRows().size() + ":" + mQueryFtInfos.getRows());
                //判断是否还有数据
                if (mQueryFtInfoss.getTotal() <= page * rows) {
                    rlv_fentuan.onNoMore("没有更多了");
                    MyLogger.jLog().i("onNoMore(没有更多了);");
                }
                if (mQueryFtInfoss.isSuccess()) {
//                    mQueryFtInfos.getRows().addAll(mQueryFtInfoss.getRows());
                    MyLogger.jLog().i("addAll(mQueryFtInfoss.getRows())");
                    mAdapter.addData(mQueryFtInfoss.getRows());
                } else {
                    ToastUtils.showToast(mQueryFtInfoss.getErrorMsg());
                }
                rlv_fentuan.complete();
            }
        });
    }

    //打赏框
    private void inipopWindow() {
        if (popWindow == null) {
            View view = layoutInflater.inflate(R.layout.popupwindow_dashang, null);
            ViewUtils.inject(this, view);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            initPop(view);
        }
        popWindow.setAnimationStyle(R.style.AnimationFade);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mDaShangPagerAdapter = new DaShangPagerAdapter();
        vp_dahsnag.setAdapter(mDaShangPagerAdapter);

    }

    private void initPop(View view) {

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

    //评论
    @Override
    public void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean isFormConmmomd) {
        MyLogger.jLog().i("onClickCommends");
        this.position = position;
        MyLogger.jLog().i("Commendposition:" + position);
        Intent intent = new Intent(MyApplication.getInstance(), FenTuanCatgoryActiviry.class);
        intent.putExtra("hasPicture", hasPicture);
        intent.putExtra("rowsBean", rowsBean);
        intent.putExtra("isFormConmmomd", isFormConmmomd);
        startActivityForResult(intent, Constans.REQUEST_CODE);
    }


    //分享
    @Override
    public void onClickshare(int position) {

    }

    //弹出打赏框
    @Override
    public void onClickDaShang(int contentId) {
        popWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //可以考虑用eventbus传值
        EventBus.getDefault().postSticky(new EventContentId(contentId));
    }

    @Override
    public void onClickDsRecored(int contentId) {
        Intent intent = new Intent(mContext, DaShangCatgoryActivity.class);
        intent.putExtra("contentId", contentId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//解除订阅
        EventBus.getDefault().removeAllStickyEvents(); //移除所有粘性事件
    }
}
