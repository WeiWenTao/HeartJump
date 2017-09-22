package com.cucr.myapplication.widget.picture;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cucr.myapplication.R;
import com.jaiky.imagespickers.ImageLoader;

/**
 * Created by cucr on 2017/9/12.
 */

public class MyLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.drawable.global_img_default);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);

    }
}
