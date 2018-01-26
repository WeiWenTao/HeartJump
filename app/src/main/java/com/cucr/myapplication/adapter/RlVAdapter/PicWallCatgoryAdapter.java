package com.cucr.myapplication.adapter.RlVAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by cucr on 2018/1/3.
 */

public class PicWallCatgoryAdapter extends RecyclerView.Adapter<PicWallCatgoryAdapter.MyHolder> {

    private PicWallInfo data;

    public PicWallCatgoryAdapter(PicWallInfo data) {
        this.data = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_picwall_catgory, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST +
                data.getRows().get(position).getPicUrl(),
                holder.iv, MyApplication.getImageLoaderOptions());

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getRows().size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView ) itemView.findViewById(R.id.iv);
        }
    }
}
