package com.cucr.myapplication.fragment.discover;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.JourneyAndTopic.TopicClassifyActivity;
import com.cucr.myapplication.activity.fenTuan.FenTuanActivity;
import com.cucr.myapplication.adapter.LvAdapter.HomeAdapter;
import com.cucr.myapplication.fragment.BaseFragment;

/**
 * Created by 911 on 2017/4/10.
 */

public class DiscoverFragment extends BaseFragment {

    @Override
    protected void initView(View childView) {
        ListView lv_discover = (ListView) childView.findViewById(R.id.lv_discover);
        View headerView = View.inflate(childView.getContext(), R.layout.header_discover_lv, null);
        lv_discover.addHeaderView(headerView);
        lv_discover.setAdapter(new HomeAdapter(mContext));

        childView.findViewById(R.id.tv_topic).setOnClickListener(this);
        childView.findViewById(R.id.tv_fan_tuan).setOnClickListener(this);

    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_topic:
                mContext.startActivity(new Intent(mContext, TopicClassifyActivity.class));
                break;

            case R.id.tv_fan_tuan:
                mContext.startActivity(new Intent(mContext, FenTuanActivity.class));
                break;

        }
    }
}
