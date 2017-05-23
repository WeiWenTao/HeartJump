package com.cucr.myapplication.adapter.LvAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.home.PersonalHomePageActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;


/**
 * Created by 911 on 2017/4/10.
 */

public class HomeAdapter extends BaseAdapter implements View.OnClickListener {
    Context mContext;
    private DialogShareStyle mShareDialog;



    public HomeAdapter(Context context) {
        mContext = context;
        mShareDialog = new DialogShareStyle(context, R.style.ShowAddressStyleTheme);

    }


    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    RelativeLayout rl_comment;
    RelativeLayout rl_share;

    ImageView iv_favorite1;

    ImageView iv_favorite2;

    ImageView iv_favorite3;

    LinearLayout ll_star_page;
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        CommonViewHolder cvh1 = null;
        CommonViewHolder cvh2 = null;
        CommonViewHolder cvh3 = null;




        int type = getItemViewType(position % 3);
            switch (type){
                case Constans.TYPE_ONE:
                    cvh1 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_home_type1,null);

                    rl_share = cvh1.getView(R.id.rl_share, RelativeLayout.class);
                    rl_comment = cvh1.getView(R.id.rl_comment, RelativeLayout.class);

                    iv_favorite1 = cvh1.getView(R.id.iv_favorite3, ImageView.class);
                    ll_star_page = cvh1.getView(R.id.ll_star_page, LinearLayout.class);
                    ll_star_page.setOnClickListener(this);


                    rl_share.setOnClickListener(this);
                    rl_comment.setOnClickListener(this);
                    iv_favorite1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_favorite1.setImageResource(R.drawable.icon_good_sel);
                        }
                    });

                    rl_share.setTag(position);
                    return cvh1.convertView;

                case Constans.TYPE_TWO:
                    cvh2 = CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_home_type2,null);

                    //gridView 初始化
//                    GridView gv = cvh2.getView(R.id.gridview, GridView.class);
//                    gv.setAdapter();


                    rl_share = cvh2.getView(R.id.rl_share, RelativeLayout.class);
                    rl_comment = cvh2.getView(R.id.rl_comment, RelativeLayout.class);

                    iv_favorite2 = cvh2.getView(R.id.iv_favorite3, ImageView.class);
                    ll_star_page = cvh2.getView(R.id.ll_star_page, LinearLayout.class);
                    ll_star_page.setOnClickListener(this);

                    rl_share.setOnClickListener(this);
                    rl_comment.setOnClickListener(this);
                    iv_favorite2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_favorite2.setImageResource(R.drawable.icon_good_sel);
                        }
                    });

                    rl_share.setTag(position);
                    return cvh2.convertView;

                case Constans.TYPE_THREE:
                    cvh3= CommonViewHolder.createCVH(convertView, parent.getContext(), R.layout.item_home_type3,null);

                    rl_share = cvh3.getView(R.id.rl_share, RelativeLayout.class);
                    rl_comment = cvh3.getView(R.id.rl_comment, RelativeLayout.class);

                    iv_favorite3 = cvh3.getView(R.id.iv_favorite3, ImageView.class);
                    ll_star_page = cvh3.getView(R.id.ll_star_page, LinearLayout.class);
                    ll_star_page.setOnClickListener(this);



                    rl_share.setOnClickListener(this);
                    rl_comment.setOnClickListener(this);
                    iv_favorite3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_favorite3.setImageResource(R.drawable.icon_good_sel);
                        }
                    });

                    rl_share.setTag(position);
                    return cvh3.convertView;

            }

        return null;

    }

    //返回不同类型的条目
    @Override
    public int getItemViewType(int position) {
            switch (position){
                case 0:
                    return Constans.TYPE_ONE;
                case 1:
                    return Constans.TYPE_THREE;
                case 2:
                    return Constans.TYPE_TWO;
            }
        return -1;
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
        switch (v.getId()){

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

            //评论
            case R.id.rl_comment:

                Toast.makeText(mContext,"评论",Toast.LENGTH_SHORT).show();
                break;

            //明星界面
            case R.id.ll_star_page:

                mContext.startActivity(new Intent(mContext, PersonalHomePageActivity.class));
                break;


        }
    }
}
