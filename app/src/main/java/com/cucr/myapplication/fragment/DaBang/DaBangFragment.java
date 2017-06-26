package com.cucr.myapplication.fragment.DaBang;

import android.view.View;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.DaBangAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by 911 on 2017/6/22.
 */

public class DaBangFragment extends BaseFragment {

    @Override
    protected void initView(View childView) {
        ListView lv_dabang = (ListView) childView.findViewById(R.id.lv_dabang);
        View headView = View.inflate(mContext, R.layout.head_dabang, null);
        ViewUtils.inject(this,headView);

        lv_dabang.addHeaderView(headView);
        lv_dabang.setAdapter(new DaBangAdapter());
    }

    @OnClick(R.id.tv_dabang_first)
    public void daBangFirst(View view){
        ToastUtils.showToast(view.getContext(),"1");
    }

    @OnClick(R.id.tv_dabang_second)
    public void daBangSecond(View view){
        ToastUtils.showToast(view.getContext(),"2");
    }

    @OnClick(R.id.tv_dabang_third)
    public void daBangThird(View view){
        ToastUtils.showToast(view.getContext(),"3");
    }

//    private void initHeaderView(View headView) {
//        TextView tv_dabang_first = (TextView) headView.findViewById(R.id.tv_dabang_first);
//        TextView tv_dabang_first = (TextView) headView.findViewById(R.id.tv_dabang_first);
//        TextView tv_dabang_first = (TextView) headView.findViewById(R.id.tv_dabang_first);
//
//    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_dabang;
    }
}
