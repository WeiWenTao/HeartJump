package com.cucr.myapplication.adapter.PagerAdapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.widget.photoView.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by cucr on 2018/1/5.
 */

public class MyPicWall extends PagerAdapter implements View.OnClickListener {
    private PicWallInfo mData;

    public MyPicWall(PicWallInfo data) {
        mData = data;

    }

    @Override
    public int getCount() {
        return mData.getRows().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = HttpContans.HTTP_HOST + mData.getRows().get(position).getPicUrl();
        PhotoView view = new PhotoView(MyApplication.getInstance());
        view.setOnClickListener(this);
        //开启缩放
        view.enable();
        //开启旋转
//        view.enableRotate();

        view.setScaleType(ImageView.ScaleType.FIT_CENTER);

        ImageLoader.getInstance().displayImage(url, view, MyApplication.getImageLoaderOptions());
//                Glide.with(MyApplication.getInstance()).load(url)
//                        .apply(MyApplication.getGlideOptions())
//                        .into(view);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClick != null) {
            mOnItemClick.OnClickItem();
        }
    }

    public interface OnItemClick {
        void OnClickItem();
    }

};
