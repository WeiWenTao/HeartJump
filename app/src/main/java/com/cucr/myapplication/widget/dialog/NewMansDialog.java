package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/4/26.
 */

public class NewMansDialog extends Dialog {

    private List<Integer> mList;

    public NewMansDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_newman);
        initView();

    }

    private void initView() {
        mList = new ArrayList<>();
        mList.add(R.drawable.xinshouzd1);
        mList.add(R.drawable.xinshouzd2);
        mList.add(R.drawable.xinshouzd3);
        mList.add(R.drawable.xinshouzd4);
        //设置点击外部消失
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录之后保存登录数据  下次登录判断是否第一次登录
                SpUtil.setParam(SpConstant.HAS_LOAD, true);
                dismiss();
            }
        });
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(container.getContext(), R.layout.pager_xszd, null);
                container.addView(view);
                ImageView iv = (ImageView) view.findViewById(R.id.iv);
                iv.setImageResource(mList.get(position));
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

    }

}
