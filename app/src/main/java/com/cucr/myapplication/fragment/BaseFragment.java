package com.cucr.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.HomeSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.lidroid.xutils.ViewUtils;

/**
 * Created by 911 on 2017/4/11.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    public Context mContext;
    private RelativeLayout mRl_home_search;
    private TextView mTv_title_search;
    private ImageView mIv_header_msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewUtils.inject(getActivity());
        mContext = container.getContext();
        // 五星级重要，复用View
        if (mRootView == null) {
            if (needHeader()) {
                mRootView = inflater.inflate(R.layout.fragment_base, container, false);

                ViewGroup childContainer = (ViewGroup) mRootView.findViewById(R.id.base_fragment_child_container);
                View childView = inflater.inflate(getContentLayoutRes(), childContainer, true);
                //返回view给子类
                initView(childView);
                initHeader(initHeadText());
            }else {
                mRootView = inflater.inflate(getContentLayoutRes(), container, false);
                initView(mRootView);
            }

        }


    return mRootView;
    }

    protected String initHeadText(){
        return "三生三世十里桃花";
    };

    //询问子类是否需要头部
    protected boolean needHeader() {
        return true;
    }


    //返回view给子类进行初始化
    protected abstract void initView(View childView);

    //接收子类布局资源加载布局
    public abstract int getContentLayoutRes();

    //初始化头部
    protected void initHeader(String text) {
        mRl_home_search = (RelativeLayout) mRootView.findViewById(R.id.rl_home_search);
        mTv_title_search = (TextView) mRootView.findViewById(R.id.tv_title_search);
        mIv_header_msg = (ImageView) mRootView.findViewById(R.id.iv_header_msg);

        mTv_title_search.setText(text);
        mRl_home_search.setOnClickListener(this);
        mIv_header_msg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home_search:
                startActivity(new Intent(mContext, HomeSearchActivity.class));
                break;

            case R.id.iv_header_msg:
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
        }
    }
}
