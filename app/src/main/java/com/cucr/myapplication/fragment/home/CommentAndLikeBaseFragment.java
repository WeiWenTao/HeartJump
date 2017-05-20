package com.cucr.myapplication.fragment.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/17.
 */

public abstract class CommentAndLikeBaseFragment extends Fragment {

    View RootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (RootView == null){


        RootView = inflater.inflate(R.layout.fragment_comment_and_like, container, false);
        ViewGroup childContiner = (ViewGroup) RootView.findViewById(R.id.fl_comment_and_like_body);
        View childView = inflater.inflate(getChildRes(), childContiner, true);
        initChildView(childView);
        }
        return RootView;
    }

    protected abstract void initChildView(View childView);

    protected abstract int getChildRes();
}
