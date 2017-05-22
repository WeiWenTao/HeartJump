package com.cucr.myapplication.activity.home;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.fragment.star.FragmentStarClassify;
import com.cucr.myapplication.fragment.star.FragmentStarRecommend;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class HomeStarActivity extends Activity {


    //推荐明星
    @ViewInject(R.id.ll_star_recommend)
    LinearLayout ll_star_recommend;

    //明星分类
    @ViewInject(R.id.ll_star_classify)
    LinearLayout ll_star_classify;
    private List<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_star);

        ViewUtils.inject(this);

        mFragments = new ArrayList<>();
        mFragments.add(new FragmentStarRecommend());
        mFragments.add(new FragmentStarClassify());

        //切换fragment
        initFragment(0);
    }

    private void initFragment(int which) {
        getFragmentManager().beginTransaction().replace(R.id.fl_star_continer,mFragments.get(which)).commit();
    }

    //推荐明星
    @OnClick(R.id.ll_star_recommend)
    public void recommendStar(View view){
        initFragment(0);
    }

    //明星分类
    @OnClick(R.id.ll_star_classify)
    public void starClassify(View view){
        initFragment(1);
    }


}
