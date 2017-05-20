package com.cucr.myapplication.fragment.home;

import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.LikeFragmentLvAdapter;

/**
 * Created by 911 on 2017/4/17.
 */

public class LikeFragment extends CommentAndLikeBaseFragment {
    @Override
    protected void initChildView(View childView) {
        ListView like_lv = (ListView) childView.findViewById(R.id.like_lv);
        like_lv.setAdapter(new LikeFragmentLvAdapter());
    }

    @Override
    protected int getChildRes() {
        return R.layout.fragment_like;
    }
}
