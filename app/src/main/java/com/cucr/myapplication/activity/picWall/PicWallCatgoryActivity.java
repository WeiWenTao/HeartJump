package com.cucr.myapplication.activity.picWall;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.PagerAdapter.MyPicWall;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.core.user.PicWallCore;
import com.cucr.myapplication.listener.DownLoadListener;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.PromissUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogSaveTo;
import com.cucr.myapplication.widget.goodAnimation.PeriscopeLayout;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

public class PicWallCatgoryActivity extends Activity implements MyPicWall.OnItemClick, Animation.AnimationListener, DialogSaveTo.OnClickSaveTo, RequersCallBackListener, DownLoadListener {

    //    @ViewInject(R.id.rlv_pics)
    //    RecyclerView rlv_pics;

    @ViewInject(R.id.pl)
    private PeriscopeLayout pl;

    @ViewInject(R.id.vp)
    private ViewPager vp;

    @ViewInject(R.id.tv_goodnum)
    private TextView tv_goodnum;

    @ViewInject(R.id.iv_user_head)
    private ImageView iv_user_head;

    @ViewInject(R.id.tv_username)
    private TextView tv_username;

    @ViewInject(R.id.comment_head)
    private RelativeLayout comment_head;

    @ViewInject(R.id.rlv_foot)
    private RelativeLayout rlv_foot;

    @ViewInject(R.id.ll_good)
    private LinearLayout ll_good;

    private DialogSaveTo mSaveToDialog;
    private PicWallInfo mData;
    private int mPosotion;
    private PicWallCore mCore;
    private int count;
    private Gson mGson;
    private Intent mIntent;
    private TranslateAnimation mHeadShow;
    private TranslateAnimation mFootShow;
    private TranslateAnimation mHeadHide;
    private TranslateAnimation mFootHide;
    private AppCore mAppCore;
    private PicWallInfo.RowsBean mRowsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_wall_catgory);
        ViewUtils.inject(this);
//        UltimateBar ultimateBar = new UltimateBar(this);
//        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);

        initBar();
        initDialog();
        init();
        upData();

    }

    private void initDialog() {
        mSaveToDialog = new DialogSaveTo(this, R.style.MyDialogStyle);
        Window shareWindow = mSaveToDialog.getWindow();
        shareWindow.setGravity(Gravity.BOTTOM);
        shareWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mSaveToDialog.setOnClickBt(this);
    }

    private void initBar() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        //设置导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && CommonUtils.checkDeviceHasNavigationBar(MyApplication.getInstance())) {
            boolean b = CommonUtils.checkDeviceHasNavigationBar(MyApplication.getInstance());
            MyLogger.jLog().i("hasNB?" + b);
            getWindow().setNavigationBarColor(getResources().getColor(R.color.blue_black));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlv_foot.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, ultimateBar.getNavigationHeight(MyApplication.getInstance()));
            rlv_foot.setLayoutParams(layoutParams);
        }
    }

    private void init() {
        mCore = new PicWallCore();
        mAppCore = new AppCore();
        mGson = MyApplication.getGson();
        mData = (PicWallInfo) getIntent().getSerializableExtra("data");
        mPosotion = getIntent().getIntExtra("posotion", 0);
        mIntent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mRowsBean = mData.getRows().get(mPosotion);
        initAni();
        MyPicWall adapter = new MyPicWall(mData);
        adapter.setOnItemClick(this);
        vp.setAdapter(adapter);
        vp.setCurrentItem(mPosotion);
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                mPosotion = position;
                upData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    mRowsBean = mData.getRows().get(mPosotion);
                    mRowsBean.setGiveUpCount(mRowsBean.getGiveUpCount() + count);
                    if (count == 0) {
                        return;
                    }
                    giveUp(count, mRowsBean.getId());
                    count = 0;
                }
            }
        });
    }

    private void initAni() {
        mHeadShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        mHeadShow.setDuration(500);
        mHeadShow.setAnimationListener(this);

        mFootShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        mFootShow.setDuration(500);

        mHeadHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f
        );
        mHeadHide.setDuration(500);

        mFootHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f
        );
        mFootHide.setDuration(500);
        mFootHide.setAnimationListener(this);
    }

    //页面滑动了 更新数据
    private void upData() {
        PicWallInfo.RowsBean rowsBean = mData.getRows().get(mPosotion);
        mIntent.putExtra("userId", rowsBean.getUser().getId());
        //只有审核通过的图片才有点赞功能
        ll_good.setVisibility(rowsBean.getStatu() == 1 ? View.VISIBLE : View.GONE);
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getUser()
                .getUserHeadPortrait(), iv_user_head, MyApplication.getImageLoaderOptions());
        tv_goodnum.setText(rowsBean.getGiveUpCount() + "");
        tv_username.setText(rowsBean.getUser().getName());
    }


    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        onBackPressed();
    }

    @OnClick(R.id.iv_goods)
    public void yoGoods(View view) {
        count++;
        tv_goodnum.setText(Integer.parseInt(tv_goodnum.getText().toString()) + 1 + "");
        pl.addHeart();
    }

    //点赞
    public void giveUp(int count, int dataId) {
        mCore.picGoods(dataId, count, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg reBackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (reBackMsg.isSuccess()) {
                    MyLogger.jLog().i("点赞成功了哦");
                } else {
                    ToastUtils.showToast(reBackMsg.getMsg());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        giveUp(count, mData.getRows().get(mPosotion).getId());
        mData.getRows().get(mPosotion).setGiveUpCount(mData.getRows().get(mPosotion).getGiveUpCount() + count);
        count = 0;
        Intent intent = getIntent();
        intent.putExtra("data", mData);
//        PicWallInfo.RowsBean rowsBean = mData.getRows().get(3);
//        rowsBean.setGiveUpCount(0);
        setResult(-1, intent);
        finish();
    }

    private boolean isShow; //是否是显示动画
    private boolean isStart;//是否正在执行动画

    //vp条目点击监听
    @Override
    public void OnClickItem() {
        if (isStart) {
            return;
        }
        if (isShow) {
            showAni();
        } else {
            hideAni();
        }
        isShow = !isShow;
    }

    public void showAni() {
        rlv_foot.setVisibility(View.VISIBLE);
        comment_head.setVisibility(View.VISIBLE);
        comment_head.startAnimation(mHeadShow);
        rlv_foot.startAnimation(mFootShow);

    }

    public void hideAni() {
        comment_head.startAnimation(mHeadHide);
        rlv_foot.startAnimation(mFootHide);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isStart = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isStart = false;
        if (animation == mFootHide) {
            comment_head.setVisibility(View.GONE);
            rlv_foot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @OnClick(R.id.iv_user_head)
    public void goPersonal(View view) {
        startActivity(mIntent);
    }

    @OnClick(R.id.tv_username)
    public void goPersonalToo(View view) {
        startActivity(mIntent);
    }

    //显示更多
    @OnClick(R.id.iv_more)
    public void showMore(View view) {
        mSaveToDialog.show();
    }

    //保存
    @Override
    public void clickSaveTo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            mAppCore.saveImgs(mRowsBean.getPicUrl(), Constans.PICWALL_FOLDER, "xthy" + mRowsBean.getId() + ".png", true, false, this);
        }
    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (writeAccepted) {
            mAppCore.saveImgs(mRowsBean.getPicUrl(), Constans.PICWALL_FOLDER, "xthy" + mRowsBean.getId() + ".png", true, false, this);
        } else {
            PromissUtils.showDialogTipUserGoToAppSettting(this, "存储");
            ToastUtils.showToast(getResources().getString(R.string.request_permission));
        }
    }

    //举报
    @Override
    public void clickReportTo() {
        mAppCore.report(1, mRowsBean.getId() + "", mRowsBean.getUser().getId(), this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
        if (msg.isSuccess()) {
            ToastUtils.showToast("我们收到您的举报啦");
        } else {
            ToastUtils.showToast(msg.getMsg());
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

    @Override
    public void onFinish(int what, String filePath) {
        Toast.makeText(MyApplication.getInstance(), "图片已保存在" + Constans.PICWALL_FOLDER, Toast.LENGTH_LONG).show();
    }
}
