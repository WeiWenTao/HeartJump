package com.cucr.myapplication.activity.fansCatgory;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ShuJuActivity extends BaseActivity {

    @ViewInject(R.id.wv)
    private WebView wv;

    @ViewInject(R.id.ll_load)
    private LinearLayout ll_load;


    @Override
    protected void initChild() {
        initTitle("数据");
        initViews();
        String url = HttpContans.ADDRESS_STAR_DATA + getIntent().getIntExtra("starId", -1);
        wv.loadUrl(url);
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_shu_ju;
    }

    private void initViews() {
        WebSettings settings = wv.getSettings();

        //webView  加载视频，下面五行必须
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);//支持js
        settings.setPluginState(WebSettings.PluginState.ON);// 支持插件
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片


        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小  无效
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//        wv.setWebChromeClient(new WebChromeClient() );
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
    }
}
