package com.cucr.myapplication.adapter.PagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.fenTuan.SignleFtInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.widget.photoView.PhotoView;

import java.util.List;

/**
 * Created by cucr on 2017/9/30.
 */

public class ImgPagerAdapter_forSingle extends PagerAdapter {

    private List<SignleFtInfo.ObjBean.AttrFileListBean> attrFileList;
    private Context context;

    public ImgPagerAdapter_forSingle(Context context, List<SignleFtInfo.ObjBean.AttrFileListBean> attrFileList) {
        this.context = context;
        this.attrFileList = attrFileList;
    }

    @Override
    public int getCount() {
        return attrFileList == null ? 0 : attrFileList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = HttpContans.IMAGE_HOST + attrFileList.get(position).getFileUrl();
        PhotoView view = new PhotoView(context);

        //开启缩放
        view.enable();
        //开启旋转
//        view.enableRotate();

        view.setScaleType(ImageView.ScaleType.FIT_CENTER);

//        DisplayImageOptions options = new DisplayImageOptions.Builder()//
////                        .showImageOnLoading(R.mipmap.ic_launcher) // 加载中显示的默认图片
//                .showImageOnFail(R.mipmap.ic_launcher) // 设置加载失败的默认图片
//                .cacheInMemory(true) // 内存缓存
//                .cacheOnDisk(true) // sdcard缓存
//                .bitmapConfig(Bitmap.Config.RGB_565)// 设置最低配置
//                .build();//
//        ImageLoader.getInstance().displayImage(url, view, options);

//        ImageLoader.getInstance().displayImage(url, view, MyApplication.getImageLoaderOptions());
        Glide.with(context).load(url)
                .apply(MyApplication.getGlideOptions())
                .into(view);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
