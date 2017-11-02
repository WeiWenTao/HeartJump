package com.cucr.myapplication.adapter.GvAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cucr on 2017/9/28.
 */

public class GridAdapter extends BaseAdapter {

    private Context mContext;

    private List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList;

    public GridAdapter(Context mContext, List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList) {
        this.mContext = mContext;
        this.attrFileList = attrFileList;
    }

    @Override
    public int getCount() {
        return attrFileList == null ? 0 : attrFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_ft_pics, null);
        ImageView iv = cvh.getIv(R.id.iv_image);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.ic_launcher)  // 加载时的占位图
                .showImageOnFail(R.drawable.ic_launcher)  // 加载失败占位图
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + attrFileList.get(position).getFileUrl(), iv, MyApplication.getImageLoaderOptions());
//       Glide.with(mContext).load(HttpContans.HTTP_HOST + attrFileList.get(position).getFileUrl()).into(iv);
        return cvh.convertView;
    }
}
