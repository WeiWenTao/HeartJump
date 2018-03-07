package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.pay.YyhdPayActivity;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.YyhdSupprotAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.Hyt.YyhdInfos;
import com.cucr.myapplication.bean.Hyt.YyhdSupports;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.DataUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import java.text.ParseException;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class YyhdCatgoryActivity extends BaseActivity implements RequersCallBackListener, YyhdSupprotAdapter.OnItemClick {

    @ViewInject(R.id.rlv_support)
    private RecyclerView rlv_support;

    @ViewInject(R.id.ll_supports)
    private LinearLayout ll_supports;

    //评论数量
    @ViewInject(R.id.tv_comments)
    private TextView tv_comments;

    //点赞数量
    @ViewInject(R.id.tv_give_count)
    private TextView tv_give_count;

    //点赞红心
    @ViewInject(R.id.iv_give_up)
    private ImageView iv_give_up;

    //封面
    @ViewInject(R.id.iv_yy_pic)
    private ImageView iv_yy_pic;

    //活动名称
    @ViewInject(R.id.tv_hd_catgory)
    private TextView tv_hd_catgory;

    //当前进度
    @ViewInject(R.id.tv_current_progress)
    private TextView tv_current_progress;

    //总进度
    @ViewInject(R.id.tv_total_progress)
    private TextView tv_total_progress;

    //百分比
    @ViewInject(R.id.tv_percent)
    private TextView tv_percent;

    //后援团头像
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;

    //后援团名称
    @ViewInject(R.id.tv_hyt_name)
    private TextView tv_hyt_name;

    //后援团内容
    @ViewInject(R.id.tv_yyhd_content)
    private TextView tv_yyhd_content;

    //应援方式
    @ViewInject(R.id.tv_way)
    private TextView tv_way;

    //用资申明
    @ViewInject(R.id.tv_yzsm)
    private TextView tv_yzsm;

    //应援进度条
    @ViewInject(R.id.pb_yhhd_progress)
    private ProgressBar pb_yhhd_progress;

    //应援进度条
    @ViewInject(R.id.cv)
    private CountdownView cv;

    private HytCore mCore;
    private int page;
    private int rows;
    private Gson mGson;
    private YyhdSupprotAdapter mAdapter;
    private int totalMoney;
    private Intent mIntent;
    private int mId;
    private boolean mIsgood;
    private YyhdInfos.RowsBean mRowsBean;

    @Override
    protected void initChild() {
        page = 1;
        rows = 4;   //最多只能显示4个
        mRowsBean = (YyhdInfos.RowsBean) getIntent().getSerializableExtra("date");
        mCore = new HytCore();
        mIntent = new Intent();
        mGson = MyApplication.getGson();
        rlv_support.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance(),
                LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new YyhdSupprotAdapter();
        mAdapter.setOnItemClick(this);
        rlv_support.setAdapter(mAdapter);
        mId = mRowsBean.getId();
        try {
            long differTime = DataUtils.getDifferTime(mRowsBean.getEndTime());
            cv.start(differTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mCore.querySupport(page, rows, mId, this);
        initPager();
    }

    //页面初始化
    private void initPager() {
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + mRowsBean.getPicUrl(),
                iv_yy_pic, MyApplication.getImageLoaderOptions());
        tv_hd_catgory.setText(mRowsBean.getActiveName());
        tv_yyhd_content.setText(mRowsBean.getActiveContent());
        tv_current_progress.setText("¥" + mRowsBean.getSignUpAmount());
        switch (mRowsBean.getActiveType()) {
            case 1://点亮开屏
                tv_yzsm.setVisibility(View.GONE);
                tv_way.setText("应援方式:点亮开屏");
                totalMoney = mRowsBean.getSysHytActiveOpenscreen().getAmount();
                break;

            case 2://bigPad
                tv_yzsm.setVisibility(View.VISIBLE);
                tv_yzsm.setText(mRowsBean.getSysHytActiveBigpad().getYzsm());
                tv_way.setText("应援方式:BIGPAD");
                totalMoney = mRowsBean.getSysHytActiveBigpad().getYyje();
                break;

            case 3://粉丝众筹
                tv_yzsm.setVisibility(View.VISIBLE);
                tv_yzsm.setText(mRowsBean.getSysHytActiveZc().getExplains());
                tv_way.setText("应援方式:粉丝众筹");
                totalMoney = mRowsBean.getSysHytActiveZc().getAmount();
                break;
        }
        int progress = (mRowsBean.getSignUpAmount() / totalMoney);
        //----------------------------------避免 0<金额<1%  金额为0的情况
        if (mRowsBean.getSignUpAmount() != 0) {
            progress = progress + 1;
            if (progress > 100) {
                progress = 100;
            }
            tv_percent.setText(progress + "%");
        } else {
            tv_percent.setText(progress + "%");
        }
        //----------------------------------
        tv_total_progress.setText("/¥" + totalMoney);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pb_yhhd_progress.setProgress(progress, true);
        } else {
            pb_yhhd_progress.setProgress(progress);
        }
        //后援团信息
        YyhdInfos.RowsBean.HytInfoBean hytInfo = mRowsBean.getHytInfo();
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + hytInfo.getPicUrl(),
                iv_head, MyApplication.getImageLoaderOptions());
        tv_hyt_name.setText(hytInfo.getName());
        tv_comments.setText(mRowsBean.getCommentCount() + "");
        tv_give_count.setText(mRowsBean.getGiveUpCount() + "");
        mIsgood = mRowsBean.getIsGiveUp() == 1;
        iv_give_up.setImageResource(mIsgood ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_yyhd_catgory;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_EIGHT) {
            YyhdSupports yyhdSupports = mGson.fromJson(response.get(), YyhdSupports.class);
            if (yyhdSupports.isSuccess()) {
                List<YyhdSupports.RowsBean> rows = yyhdSupports.getRows();
                if (rows == null || rows.size() == 0) {
                    ll_supports.setVisibility(View.GONE);
                } else {
                    ll_supports.setVisibility(View.VISIBLE);
                }
                mAdapter.setData(rows);
            } else {
                ToastUtils.showToast(yyhdSupports.getErrorMsg());
            }
        } else if (what == Constans.TYPE_TWEVEN) {
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (!msg.isSuccess()) {
                ToastUtils.showToast(msg.getMsg());
                return;
            }
            int giveUpCount = mRowsBean.getGiveUpCount();
            if (mIsgood) {
                giveUpCount = giveUpCount - 1;
                iv_give_up.setImageResource(R.drawable.icon_good_nor);
            } else {
                iv_give_up.setImageResource(R.drawable.icon_good_sel);
                giveUpCount = giveUpCount + 1;
            }
            mRowsBean.setGiveUpCount(giveUpCount);
            mIsgood = !mIsgood;
            tv_give_count.setText(giveUpCount + "");
        }
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestFinish(int what) {

    }

    //点击评论
    @OnClick(R.id.ll_comment)
    public void click1(View view) {
        mIntent.setClass(MyApplication.getInstance(), YyhdCommentActivity.class);
        mIntent.putExtra("activeId", mId);
        startActivityForResult(mIntent, 1);
    }

    //点击支持
    @OnClick(R.id.tv_support)
    public void click2(View view) {
        mIntent.setClass(MyApplication.getInstance(), YyhdPayActivity.class);
        startActivity(mIntent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            int count = data.getIntExtra("count", -1);
            mRowsBean.setGiveUpCount(count);
            tv_comments.setText(count + "");
        }
    }

    //点击条目
    @Override
    public void onClickItem(int userId) {
        mIntent.setClass(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mIntent.putExtra("userId", userId);
        startActivity(mIntent);
    }

    //点击加载更多
    @Override
    public void onClickMore() {
        mIntent.setClass(MyApplication.getInstance(), MoreSupportsActivity.class);
        mIntent.putExtra("activeId", mId);
        startActivity(mIntent);
    }

    //点赞
    @OnClick(R.id.iv_give_up)
    public void clickGiveUp(View view) {
        mCore.YyhdGood(mId, this);
    }

}
