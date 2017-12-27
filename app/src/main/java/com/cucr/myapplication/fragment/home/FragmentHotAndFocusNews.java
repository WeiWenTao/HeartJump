package com.cucr.myapplication.fragment.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.HomeSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.adapter.PagerAdapter.HomeNewsPagerAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.model.login.ReBackMsg1;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/9/9.
 */

public class FragmentHotAndFocusNews extends BaseFragment {

    //ViewPager
    @ViewInject(R.id.vp_hot_focus)
    private ViewPager vp_hot_focus;


    private WebView wv;

    //导航栏
    @ViewInject(R.id.tablayout)
    TabLayout tablayout;

    //头部
    @ViewInject(R.id.head)
    RelativeLayout head;


    private List<Fragment> mFragments;
    private Gson mGson;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (itemView == null) {
//            mFragments = new ArrayList<>();
//            itemView = inflater.inflate(R.layout.fragment_home_hot_focus_news, container, false);
//            ViewUtils.inject(this, itemView);
//            initView();
//        }
//        return itemView;
//    }

    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);
//        initHead();
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        mGson = MyApplication.getGson();

        initTableLayout();
        initView();
    }


    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_home_hot_focus_news;
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new HomeFragment());
        vp_hot_focus.setAdapter(new HomeNewsPagerAdapter(getFragmentManager(), mFragments));
    }

    //初始化标签
    private void initTableLayout() {
        tablayout.addTab(tablayout.newTab().setText("热门"));
        tablayout.addTab(tablayout.newTab().setText("关注"));
        tablayout.setupWithViewPager(vp_hot_focus);//将导航栏和viewpager进行关联
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(mContext, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


   /* // TODO: 2017/11/3
    @OnClick(R.id.iv_search)
    public void clickSearch(View view){
        wv.setVisibility(View.VISIBLE);
       *//* Platform wechat= ShareSDK.getPlatform(SinaWeibo.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                MyLogger.jLog().i("onComplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                MyLogger.jLog().i("onError:"+platform+","+i+","+throwable);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                MyLogger.jLog().i("onCancel");
            }
        });
        wechat.authorize();*//*
        RequestQueue mQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://www.cucrxt.com/interface/test/test", RequestMethod.POST);

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }*/

    @OnClick(R.id.iv_search)
    public void toSearch(View view) {
        startActivity(new Intent(MyApplication.getInstance(), HomeSearchActivity.class));
    }


    @OnClick(R.id.iv_header_msg)
    public void toMsg(View view) {
        startActivity(new Intent(MyApplication.getInstance(), MessageActivity.class));
    }


    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response<String> response) {

            ReBackMsg1 reBackMsg1 = mGson.fromJson(response.get(), ReBackMsg1.class);
            wv.loadDataWithBaseURL(null, reBackMsg1.getMsg(), "text/html", "utf-8", null);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebChromeClient(new WebChromeClient());
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {

        }
    };

    @Override
    protected boolean needHeader() {
        return false;
    }
}
