package com.cucr.myapplication.adapter.LvAdapter;

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
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
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


    public HomeAdapter() {
        mContext = MyApplication.getInstance();
        mShareDialog = new DialogShareStyle(mContext, R.style.ShowAddressStyleTheme);
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
                cvh1.getTv(R.id.tv_reads).setText(rowsBean.getReadCount() + "");     //阅读量


                ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(0).getVideoPagePic(), cvh1.getIv(R.id.iv_video), MyApplication.getImageLoaderOptions());

                return cvh1.convertView;

            //图片
            case Constans.TYPE_ONE:
                cvh2 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_xw_type01, null);
                cvh2.getTv(R.id.tv_title).setText(rowsBean.getTitle());            //标题
                cvh2.getTv(R.id.tv_from).setText(rowsBean.getCreateUserName());     //来自
                cvh2.getTv(R.id.tv_reads).setText(rowsBean.getReadCount() + "");     //阅读量
                try {
                    ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(0).getFileUrl(), cvh2.getIv(R.id.iv_pic1), MyApplication.getImageLoaderOptions());
                    ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(1).getFileUrl(), cvh2.getIv(R.id.iv_pic2), MyApplication.getImageLoaderOptions());
                    ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getAttrFileList().get(2).getFileUrl(), cvh2.getIv(R.id.iv_pic3), MyApplication.getImageLoaderOptions());

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
                cvh3.getTv(R.id.tv_reads).setText(rowsBean.getReadCount() + "");     //阅读量
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


//    //分享功能
//    private void showShare() {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
//        oks.setTitle("标题");
//        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//
//        // 启动分享GUI
//        oks.show(mContext);
//    }

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
