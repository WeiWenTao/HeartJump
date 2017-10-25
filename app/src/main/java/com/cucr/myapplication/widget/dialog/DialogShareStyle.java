package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by 911 on 2017/4/14.
 */

public class DialogShareStyle extends Dialog implements View.OnClickListener {
    private Context context;

    public DialogShareStyle(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);

        initView();

    }

    private void initView() {
        LinearLayout ll_share_wxhy = (LinearLayout) findViewById(R.id.ll_share_wxhy);
        LinearLayout ll_share_pyq = (LinearLayout) findViewById(R.id.ll_share_pyq);
        LinearLayout ll_share_qqhy = (LinearLayout) findViewById(R.id.ll_share_qqhy);
        LinearLayout ll_share_qzone = (LinearLayout) findViewById(R.id.ll_share_qzone);
        LinearLayout ll_share_sina = (LinearLayout) findViewById(R.id.ll_share_sina);
        LinearLayout ll_share_copy_link = (LinearLayout) findViewById(R.id.ll_share_copy_link);
        ImageView iv_cancle = (ImageView) findViewById(R.id.iv_cancle);

        ll_share_wxhy.setOnClickListener(this);
        ll_share_pyq.setOnClickListener(this);
        ll_share_qqhy.setOnClickListener(this);
        ll_share_qzone.setOnClickListener(this);
        ll_share_sina.setOnClickListener(this);
        ll_share_copy_link.setOnClickListener(this);
        iv_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_share_wxhy:
                sharToWxhy();
                break;

            case R.id.ll_share_pyq:
                sharToPyq();
                break;

            case R.id.ll_share_qqhy:
                sharToQqhy();
                break;

            case R.id.ll_share_qzone:
                sharToQzone();
                break;

            case R.id.ll_share_sina:
                sharToSina();
                break;

            case R.id.ll_share_copy_link:
                copyLink();
                break;

            case R.id.iv_cancle:
                dismiss();
                break;

        }
    }

    public void ToastShow(Context context, ViewGroup root, String tvString) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_xml, root);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(tvString);
        text.setTextColor(Color.WHITE);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    //复制链接
    private void copyLink() {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText("测试内容...");
        Toast.makeText(context, "已复制链接", Toast.LENGTH_SHORT).show();
//        Toast toast = Toast.makeText(context, "已复制链接", Toast.LENGTH_SHORT);
//        TextView tv = new TextView(context);
//        tv.setText("已复制链接");
//        tv.setTextColor(Color.WHITE);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f);
//        tv.setBackgroundResource(R.drawable.shape_toast_bg);
//        toast.setView(tv);
//        toast.show();
        dismiss();
    }

    //分享到新浪微博
    private void sharToSina() {
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setText("新浪微博");
        sp.setImagePath("");


        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        }); // 设置分享事件回调
// 执行图文分享
        weibo.share(sp);
    }

    //分享到qq空间
    private void sharToQzone() {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle("测试分享的标题");
        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        sp.setText("测试分享的文本");
        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
        sp.setSite("发布分享的网站名称");
        sp.setSiteUrl("发布分享网站的地址");

        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        qzone.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
// 执行图文分享
        qzone.share(sp);
    }

    //分享到qq好友
    private void sharToQqhy() {
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setText("qq好友");
        sp.setImagePath("");

        Platform qq = ShareSDK.getPlatform(QQ.NAME);

        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        qq.share(sp);
    }

    //分享到朋友圈
    private void sharToPyq() {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
//        sp.setText("朋友圈");
        sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");

        Platform pyq = ShareSDK.getPlatform(WechatMoments.NAME);

        pyq.share(sp);
    }

    //分享到微信好友
    private void sharToWxhy() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setText("微信好友");
        sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");



        Platform wxhy = ShareSDK.getPlatform(Wechat.NAME);
        wxhy.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        wxhy.share(sp);
    }


    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        //启动分享
        oks.show(context);
    }
}
