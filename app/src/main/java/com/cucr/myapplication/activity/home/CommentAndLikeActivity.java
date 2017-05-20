package com.cucr.myapplication.activity.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.CommentFragmentAdapter;
import com.cucr.myapplication.adapter.LvAdapter.LikeFragmentLvAdapter;
import com.cucr.myapplication.constants.Constans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class CommentAndLikeActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.lv_comment_like)
    ListView lv_comment_like;

    @ViewInject(R.id.tv_like)
    TextView tv_like;

    @ViewInject(R.id.tv_comment)
    TextView tv_comment;

    @ViewInject(R.id.ll_stick)
    LinearLayout ll_stick;

    @ViewInject(R.id.line)
    View line;

    private TextView mTv_like_stick;
    private TextView mTv_comment_stick;
    private CommentFragmentAdapter mAdapter;
    private int CURRENT_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_comment_and_like);
        ViewUtils.inject(this);

        initView();


    }

    private void initView() {
        View header = View.inflate(this, R.layout.header_lv_like_comment, null);
        View stick = View.inflate(this, R.layout.stick_like_comment, null);
        //找控件
        mTv_like_stick = (TextView) stick.findViewById(R.id.tv_like_stick);
        mTv_comment_stick = (TextView) stick.findViewById(R.id.tv_comment_stick);

        //设置点击监听
        mTv_comment_stick.setOnClickListener(this);
        mTv_like_stick.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_like.setOnClickListener(this);

        //初始化listView
        lv_comment_like.addHeaderView(header,null,true);
        lv_comment_like.addHeaderView(stick,null,true);

        //隐藏头部分割线
        lv_comment_like.setHeaderDividersEnabled(false);

        //初始化lv
        mAdapter = new CommentFragmentAdapter();
        lv_comment_like.setAdapter(mAdapter);

        CURRENT_STATE = Constans.STATE_COMMENT;

        //listView的滑动监听
        lv_comment_like.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    ll_stick.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                } else {
                    ll_stick.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        if ((v.getId()==R.id.tv_like||v.getId()==R.id.tv_like_stick)&&CURRENT_STATE!=Constans.STATE_LIKE){
            mTv_like_stick.setTextColor(Color.parseColor("#f68d89"));
            tv_like.setTextColor(Color.parseColor("#f68d89"));
            mTv_comment_stick.setTextColor(Color.parseColor("#bfbfbf"));
            tv_comment.setTextColor(Color.parseColor("#bfbfbf"));
            lv_comment_like.setAdapter(new LikeFragmentLvAdapter());
            lv_comment_like.setSelection(1);
            CURRENT_STATE = Constans.STATE_LIKE;
            Log.i("test","if"+CURRENT_STATE+"");

        } else if ((v.getId()==R.id.tv_comment||v.getId()==R.id.tv_comment_stick)&&CURRENT_STATE!=Constans.STATE_COMMENT){
            mTv_like_stick.setTextColor(Color.parseColor("#bfbfbf"));
            tv_like.setTextColor(Color.parseColor("#bfbfbf"));
            mTv_comment_stick.setTextColor(Color.parseColor("#f68d89"));
            tv_comment.setTextColor(Color.parseColor("#f68d89"));
            lv_comment_like.setAdapter(mAdapter);
            lv_comment_like.setSelection(1);
            CURRENT_STATE = Constans.STATE_COMMENT;
            Log.i("test","else"+CURRENT_STATE+"");
        }
    }

    @OnClick(R.id.iv_back)
    public void back(View view){
        finish();
    }
}
