package com.cucr.myapplication.activity.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.ViderRecommendAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class VideoActivity extends Activity {

    //相关推荐
    @ViewInject(R.id.lv_video_recommend)
    ListView lv_video_recommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ViewUtils.inject(this);

        initView();

    }

    private void initView() {
        View headerView = View.inflate(this, R.layout.header_video_lv, null);
        ((TextView) headerView.findViewById(R.id.tv_new_title)).getPaint().setFakeBoldText(true);
        lv_video_recommend.addHeaderView(headerView,null,true);
        lv_video_recommend.setHeaderDividersEnabled(false);
        lv_video_recommend.setAdapter(new ViderRecommendAdapter());
    }
}
