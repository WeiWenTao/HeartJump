package com.cucr.myapplication.fragment.home;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.HomeNewsPagerAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cucr on 2017/9/9.
 */

public class FragmentHotAndFocusNews extends BaseFragment {

    //ViewPager
    @ViewInject(R.id.vp_hot_focus)
    private ViewPager vp_hot_focus;

    //导航栏
    @ViewInject(R.id.tablayout)
    TabLayout tablayout;

    //头部
    @ViewInject(R.id.head)
    RelativeLayout head;


    private List<Fragment> mFragments;

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
        ViewUtils.inject(this,childView);
        initHead();

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


    @Override
    protected boolean needHeader() {
        return false;
    }
}
