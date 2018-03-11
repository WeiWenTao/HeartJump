package com.cucr.myapplication.adapter.LvAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by 911 on 2017/4/10.
 */

public class HomeAdapter extends BaseAdapter implements View.OnClickListener {
    Context mContext;
    private DialogShareStyle mShareDialog;
    private QueryFtInfos mQueryFtInfos;
    private List<QueryFtInfos.RowsBean> rows;
    private Intent mIntent;

    public void setData(QueryFtInfos mQueryFtInfos) {
        this.mQueryFtInfos = mQueryFtInfos;
        rows = mQueryFtInfos.getRows();
        notifyDataSetChanged();
    }


    public HomeAdapter(Activity activity) {
        mContext = MyApplication.getInstance();
        mShareDialog = new DialogShareStyle(activity, R.style.ShowAddressStyleTheme);
    }


    @Override
    public int getCount() {
        if (mQueryFtInfos == null || rows == null || rows.size() == 0) {
            return 0;
        }
        return rows.size();
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final QueryFtInfos.RowsBean rowsBean = rows.get(position);
        mIntent = new Intent(mContext, TestWebViewActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        CommonViewHolder cvh1;
        CommonViewHolder cvh2;
        CommonViewHolder cvh3;
        int type = getItemViewType(position);
        switch (type) {
            //视频
            case Constans.TYPE_TWO:
                cvh1 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_xw_type02, null);
                cvh1.getTv(R.id.tv_title).setText(rowsBean.getTitle());            //标题
                cvh1.getTv(R.id.tv_from).setText(rowsBean.getCreateUserName());           //来自
//                cvh1.getTv(R.id.tv_reads).setText(rowsBean.getReadCount() + "");     //阅读量


                ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(0).getVideoPagePic(), cvh1.getIv(R.id.iv_video), MyApplication.getImageLoaderOptions());

                return cvh1.convertView;

            //图片
            case Constans.TYPE_ONE:
                cvh2 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_xw_type01, null);
                cvh2.getTv(R.id.tv_title).setText(rowsBean.getTitle());            //标题
                cvh2.getTv(R.id.tv_from).setText(rowsBean.getCreateUserName());     //来自
//                cvh2.getTv(R.id.tv_reads).setText(rowsBean.getReadCount() + "");     //阅读量
                try {
                    ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(0).getFileUrl(), cvh2.getIv(R.id.iv_pic1), MyApplication.getImageLoaderOptions());
                    ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(1).getFileUrl(), cvh2.getIv(R.id.iv_pic2), MyApplication.getImageLoaderOptions());
                    ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + rowsBean.getAttrFileList().get(2).getFileUrl(), cvh2.getIv(R.id.iv_pic3), MyApplication.getImageLoaderOptions());

                } catch (Exception e) {
                    MyLogger.jLog().i("跳过");
                    notifyDataSetChanged();
                }

                return cvh2.convertView;

            //文字
            case Constans.TYPE_ZERO:
                cvh3 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_xw_type00, null);
                cvh3.getTv(R.id.tv_title).setText(rowsBean.getTitle());            //标题
                cvh3.getTv(R.id.tv_from).setText(rowsBean.getCreateUserName());           //来自
//                cvh3.getTv(R.id.tv_reads).setText(rowsBean.getReadCount() + "");     //阅读量
                cvh3.getTv(R.id.tv_content).setText(rowsBean.getContent());     //内容

                return cvh3.convertView;

        }

        return null;

    }

    //返回不同类型的条目
    @Override
    public int getItemViewType(int position) {
        QueryFtInfos.RowsBean rowsBean = rows.get(position);
        return rowsBean.getType();
    }

    //多写一个  否则会索引越界
    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            //分享
            case R.id.rl_share:
                int position = (int) v.getTag();
//                showShare();
                mShareDialog.setCanceledOnTouchOutside(true);

                Window window = mShareDialog.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.y = 100;
                window.setAttributes(attributes);


                mShareDialog.show();
                break;


        }
    }
}
