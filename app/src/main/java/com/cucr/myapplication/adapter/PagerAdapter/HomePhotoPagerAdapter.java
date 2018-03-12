package com.cucr.myapplication.adapter.PagerAdapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.widget.photoView.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by hackware on 2016/9/10.
 */

public class HomePhotoPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private QueryFtInfos.RowsBean rowsBean;

    public HomePhotoPagerAdapter(QueryFtInfos.RowsBean rowsBean) {
        this.rowsBean = rowsBean;
    }

    @Override
    public int getCount() {
        return rowsBean.getAttrFileList() == null ? 0 : rowsBean.getAttrFileList().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        PhotoView iv_home_photo = new PhotoView(container.getContext());

        //开启缩放
        iv_home_photo.enable();
        iv_home_photo.setBackgroundColor(Color.BLACK);
        //开启旋转
//        view.enableRotate();

        iv_home_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv_home_photo.setOnClickListener(this);
        ImageLoader.getInstance().displayImage(rowsBean.getAttrFileList().get(position).getFileUrl(),
                iv_home_photo, MyApplication.getImageLoaderOptions());
        container.addView(iv_home_photo);
        return iv_home_photo;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = rowsBean.getAttrFileList().indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClick != null) {
            mOnItemClick.clickIyem();
        }
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public interface OnItemClick {
        void clickIyem();
    }
}
