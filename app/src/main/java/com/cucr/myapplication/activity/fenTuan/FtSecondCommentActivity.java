package com.cucr.myapplication.activity.fenTuan;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;

public class FtSecondCommentActivity extends BaseActivity {

    @Override
    protected void initChild() {
        initTitle("6条评论");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ft_second_comment;
    }
}
