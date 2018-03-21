package com.cucr.myapplication.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cucr.myapplication.BuildConfig;
import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.utils.throwableCatch.Cockroach;
import com.cucr.myapplication.utils.throwableCatch.Utils;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;


/**
 * Created by 911 on 2017/8/11.
 */

public class MyApplication extends Application {

    private static MyApplication _instance;

    private static DisplayImageOptions options;

    private static RequestOptions glideOptions;

    private static Gson mGson;
    public static IWXAPI mMsgApi;


    @Override
    public void onCreate() {
        super.onCreate();

        //异常处理
//        throwableInstall();

        //融云
        RongIM.init(this);

        //友盟
        Config.DEBUG = true;
        UMShareAPI.get(this);
//-----------------------------微信支付
        mMsgApi = WXAPIFactory.createWXAPI(this, "wxbe72c16183cf70da");
        mMsgApi.registerApp("wxbe72c16183cf70da");
//-----------------------------
        //内存泄漏框架
//        LeakCanary.install(this);
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //emoji表情初始化
        EmojiManager.install(new IosEmojiProvider());

        _instance = this;

        Logger.setDebug(BuildConfig.DEBUG);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpDeBug");// 设置NoHttp打印Log的tag。

        // 一般情况下你只需要这样初始化：
//        NoHttp.initialize(this);Headers.HEAD_VALUE_CONTENT_TYPE_OCTET_STREAM

        // 如果你需要自定义配置：
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                // 设置全局连接超时时间，单位毫秒，默认10s。
                .connectionTimeout(30 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(30 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        new DBCacheStore(this).setEnable(true) // 如果不使用缓存，设置setEnable(false)禁用。
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                .cookieStore(
                        new DBCookieStore(this).setEnable(true) // 如果不维护cookie，设置false禁用。
                )
                // 配置网络层，URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new URLConnectionNetworkExecutor())
                .build()
        );


        // 如果你需要用OkHttp，请依赖下面的项目，version表示版本号：
        // compile 'com.yanzhenjie.nohttp:okhttp:1.1.1'

        // NoHttp详细使用文档：http://doc.nohttp.net

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.pic_bg)  // 加载时的占位图
                .showImageOnFail(android.R.drawable.stat_notify_error)  // 加载失败占位图
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        glideOptions = new RequestOptions()
                .placeholder(R.drawable.pic_bg)
                .error(android.R.drawable.stat_notify_error)
                .priority(Priority.LOW)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }

    private void throwableInstall() {
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
//                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            ToastUtils.showToast("程序异常:\n" + thread + "\n" + throwable.toString());
                            Utils.handleT(getCacheDir().getAbsolutePath(), throwable.toString());
                            MyLogger.jLog().i("--->MyCatchException:" + thread + "<---" + throwable.toString());
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });

    }

    //视频缓存
    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }


    //解决重复依赖bug
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        return _instance;
    }

    public static DisplayImageOptions getImageLoaderOptions() {
        return options;
    }

    public static RequestOptions getGlideOptions() {
        return glideOptions;
    }


    public static Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    {
        PlatformConfig.setWeixin("wxbe72c16183cf70da", "c26860d780adbae2ee43fd2363daeb12");
        PlatformConfig.setSinaWeibo("3008130677", "c3d7b94e018e217fddbb2f57fc97d650", "http://www.cucrxt.com");
        PlatformConfig.setQQZone("1106501884", "aEO7SIixJOZjsmqj");
    }
}
