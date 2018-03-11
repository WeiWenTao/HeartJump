package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class HomePhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mDataList;

    public HomePhotoPagerAdapter(List<String> dataList, Context context) {
        mDataList = dataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = null;

        if (itemView == null){
            itemView = View.inflate(mContext, R.layout.item_pager_home_photo,null);
            ImageView iv_home_photo = (ImageView) itemView.findViewById(R.id.iv_home_photo);
            iv_home_photo.setImageResource(R.drawable.pic1_tv);
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
