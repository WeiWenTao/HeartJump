package com.cucr.myapplication.temp;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Sai on 15/8/4.
 * 本地图片Holder例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private SelectableRoundedImageView imageView;


    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
//        imageView = new SelectableRoundedImageView(context);
//        imageView.setCornerRadiiDP(5.0f, 5.0f, 5.0f, 5.0f);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        View inflate = View.inflate(context, R.layout.item_banner, null);
        imageView = (SelectableRoundedImageView) inflate.findViewById(R.id.iv);

        return inflate;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + data, imageView, MyApplication.getImageLoaderOptions());
    }
}
