package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.utils.SpUtil;

import java.util.List;

/**
 * Created by cucr on 2018/3/9.
 */

public class GuidePagerAdapter extends PagerAdapter {

    private List<Integer> imgs;
    private int mChildCount = 0;

    public GuidePagerAdapter(List<Integer> imgs) {
        this.imgs = imgs;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.item_guide, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        iv.setImageResource(imgs.get(position));
        if (position == imgs.size() - 1) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.getInstance().startActivity(new Intent(MyApplication.getInstance(), MainActivity.class));
                    SharedPreferences.Editor edit = SpUtil.getNewSp().edit();
                    edit.putBoolean(SpConstant.IS_FIRST_RUN, true).commit();
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imgs == null ? 0 : imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
