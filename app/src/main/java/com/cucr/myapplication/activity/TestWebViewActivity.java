package com.cucr.myapplication.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TestWebViewActivity extends BaseActivity {

    @ViewInject(R.id.wv)
    private WebView wv;

    @ViewInject(R.id.ll_load)
    private LinearLayout ll_load;

    @ViewInject(R.id.iv_news_share)
    private ImageView iv_news_share;

    private DialogShareStyle mShareDialog;
    private int mBannerId;
    private int activeId;
    private String activeTitle;
    private int choujiang;
    private String mPicUrl;
    private String mBannerPic;
    private String fuliBanner;
    private String mUrl;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initChild() {

        initDialog();
        mUrl = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        if (TextUtils.isEmpty(title)) {
            initTitle("详情");
        } else {
            initTitle(title);
        }
        mBannerId = getIntent().getIntExtra("bannershare", -1);
        activeId = getIntent().getIntExtra("activeId", -1);
        activeTitle = getIntent().getStringExtra("activeTitle");
        choujiang = getIntent().getIntExtra("choujiang", -1);
        mPicUrl = getIntent().getStringExtra("activePic");
        mBannerPic = getIntent().getStringExtra("bannerPic");
        fuliBanner = getIntent().getStringExtra("fuliBanner");
        if (mBannerId != -1 || activeId != -1 || activeTitle != null || fuliBanner != null || choujiang != -1) {
            iv_news_share.setVisibility(View.VISIBLE);
        }
//        url = "https://weibo.com/yangmiblog?topnav=1&wvr=6&topsug=1&is_hot=1";
        WebSettings settings = wv.getSettings();

        //webView  加载视频，下面五行必须
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);//支持js
        settings.setPluginState(WebSettings.PluginState.ON);// 支持插件
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        if (getIntent().getBooleanExtra("from", false)) {
            //如果是用户协议 就把字体设置大号
            settings.setTextSize(WebSettings.TextSize.LARGEST);
            iv_news_share.setVisibility(View.GONE);
        }
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小  无效
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        wv.setWebChromeClient(new WebChromeClient()/*{
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
// 弹窗处理
                AlertDialog.Builder b2 = new AlertDialog.Builder(TestWebViewActivity.this)
                        .setTitle(R.string.app_name).setMessage(message)
                        .setPositiveButton("ok", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });

                b2.setCancelable(false);
                b2.create();
                b2.show();

                return true;
            }
        }*/); //设置支持弹窗
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                MyLogger.jLog().i("加载完成");
                ll_load.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                MyLogger.jLog().i("开始加载 url:" + url);
            }
        });
        wv.loadUrl(mUrl);
    }

    private void initDialog() {
        mShareDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window1 = mShareDialog.getWindow();
        window1.setGravity(Gravity.BOTTOM);
        window1.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_test_web_view;
    }

    //点击分享
    @OnClick(R.id.iv_news_share)
    public void clickShare(View view) {
        if (mBannerId != -1) {//banner分享
            mShareDialog.setData(new ShareEntity("明星守护神", "追爱豆,领红包,尽在心跳互娱", HttpContans.ADDRESS_BANNER_SHARE + mBannerId, mBannerPic));
        } else if (activeId != -1) {//活动分享
            mShareDialog.setData(new ShareEntity(activeTitle, "追爱豆,领红包,尽在心跳互娱", HttpContans.ADDRESS_FULI_HUOD_SHARE + activeId, mPicUrl));
        } else if (fuliBanner != null) {//活动Banner分享
            mShareDialog.setData(new ShareEntity("心跳互娱后援招募", "在线兼职，时薪20，追爱豆能赚钱！", HttpContans.ADDRESS_RECRUIT_SHARE, fuliBanner));
        } else if (choujiang != -1) {//抽奖分享
            mShareDialog.setData(new ShareEntity("我在心跳互娱抽到大奖啦！哈哈哈", "iPhoneX、演唱会门票，视频会员……超级福利中奖百分百", HttpContans.ADDRESS_CHOU_JIANG_SHARE + ((int) SpUtil.getParam(SpConstant.USER_ID, -1)), ""));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
