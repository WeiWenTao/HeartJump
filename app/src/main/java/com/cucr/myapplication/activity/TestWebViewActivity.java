package com.cucr.myapplication.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TestWebViewActivity extends BaseActivity {

    @ViewInject(R.id.wv)
    private WebView wv;

    @ViewInject(R.id.ll_load)
    private LinearLayout ll_load;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initChild() {
        initTitle("详情");
        String url = getIntent().getStringExtra("url");
//        url = "https://weibo.com/yangmiblog?topnav=1&wvr=6&topsug=1&is_hot=1";
        MyLogger.jLog().i("url:" + url);
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
                MyLogger.jLog().i("开始加载");
            }
        });
        wv.loadUrl(url);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_test_web_view;
    }
}
