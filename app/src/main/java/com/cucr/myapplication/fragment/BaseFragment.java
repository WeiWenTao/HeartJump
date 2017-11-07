package com.cucr.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.HomeSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.utils.CommonUtils;
import com.google.gson.Gson;

/**
 * Created by 911 on 2017/4/11.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    public Context mContext;
    private ImageView iv_search;
    private ImageView mIv_header_msg;
    private RelativeLayout mHead;
    protected Gson mGson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ViewUtils.inject(getActivity());
        mContext = MyApplication.getInstance();
        mGson = new Gson();

        // 五星级重要，复用View
        if (mRootView == null) {
            if (needHeader()) {
                mRootView = inflater.inflate(R.layout.fragment_base, container, false);
                ViewGroup childContainer = (ViewGroup) mRootView.findViewById(R.id.base_fragment_child_container);
                View childView = inflater.inflate(getContentLayoutRes(), childContainer, true);
                //返回view给子类
                initView(childView);
                initHeader();
            } else {
                mRootView = inflater.inflate(getContentLayoutRes(), container, false);
                initView(mRootView);
            }

        }


        return mRootView;
    }

//    protected String initHeadText(){
//        return "三生三世十里桃花";
//    }

    //询问子类是否需要头部
    protected boolean needHeader() {
        return true;
    }


    //返回view给子类进行初始化
    protected abstract void initView(View childView);

    //接收子类布局资源加载布局
    public abstract int getContentLayoutRes();

    //初始化头部
    protected void initHeader() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mHead = (RelativeLayout) mRootView.findViewById(R.id.rl_head);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mHead.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(mContext, 73.0f);
            mHead.setLayoutParams(layoutParams);
            mHead.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }


        iv_search = (ImageView) mRootView.findViewById(R.id.iv_search);
        mIv_header_msg = (ImageView) mRootView.findViewById(R.id.iv_header_msg);

        iv_search.setOnClickListener(this);
        mIv_header_msg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                startActivity(new Intent(mContext, HomeSearchActivity.class));
                break;

            case R.id.iv_header_msg:
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
        }
    }
}
