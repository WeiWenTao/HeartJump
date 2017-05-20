package com.cucr.myapplication.fragment.home;

import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.CommentFragmentAdapter;

/**
 * Created by 911 on 2017/4/17.
 */

public class CommentFragment extends CommentAndLikeBaseFragment {

    private ListView mLv_comment;

    @Override
    protected void initChildView(View childView) {
        mLv_comment = (ListView) childView.findViewById(R.id.comment_lv);
        mLv_comment.setAdapter(new CommentFragmentAdapter());
    }

    @Override
    protected int getChildRes() {
        return R.layout.fragment_comment;
    }
}
