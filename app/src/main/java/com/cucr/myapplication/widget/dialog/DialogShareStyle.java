package com.cucr.myapplication.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 亲，您的这个问题需要我们技术进一步单独为您排查，请您直接发送邮件至support@umeng.com。
 * 为提高问题解决效率，请您在邮件内提供
 * 1、咨询的是那个产品。
 * 2、对应的【友盟+】账号、出现异常的appkey、使用的产品的sdk的版本号。
 * 3、遇到的问题和对应的截图。把问题描述清晰发送邮件，技术会以邮件形式直接与您联系确认问题。
 * Created by 911 on 2017/4/14.
 */

public class DialogShareStyle extends Dialog implements View.OnClickListener {

    private Context context;
    private Activity mActivity;
    private ShareEntity entity;
    private final WaitDialog mDialog;
    private UMWeb mWeb;

    //粉团的分享
    public void setData(ShareEntity entity) {
        this.entity = entity;
        mWeb = new UMWeb(entity.getLink());
        if (!TextUtils.isEmpty(entity.getImgURL())) {
            mWeb.setThumb(new UMImage(context, entity.getImgURL()));
        }
//        mWeb.setDescription(entity.getDescribe());
//        mWeb.setTitle(entity.getTitle());
        mWeb.setDescription("追爱豆，领红包! 更多精彩等你来玩");
        mWeb.setTitle("下载心跳互娱");
        show();
    }

    //邀请有礼的分享
    public void setData2(ShareEntity entity) {
        this.entity = entity;
        mWeb = new UMWeb(entity.getLink());
        if (!TextUtils.isEmpty(entity.getImgURL())) {
            mWeb.setThumb(new UMImage(context, entity.getImgURL()));
        }
        mWeb.setDescription(Html.fromHtml(entity.getDescribe()).toString());
        mWeb.setTitle(entity.getTitle());
        show();
    }

    public DialogShareStyle(Context context, int themeResId) {
        super(context, themeResId);
        this.context = MyApplication.getInstance();
        this.mActivity = (Activity) context;
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);

        mDialog = new WaitDialog(mActivity, "正在跳转微博...");
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
                if (!UMShareAPI.get(context).isInstall(mActivity, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.showToast("请先装微信客户端");
                } else {
                    sharToWxhy();
                }
                dismiss();
                break;

            case R.id.ll_share_pyq:
                if (!UMShareAPI.get(context).isInstall(mActivity, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.showToast("请先装微信客户端");
                } else {
                    sharToPyq();
                }
                dismiss();
                break;

            case R.id.ll_share_qqhy:
                sharToQqhy();
                dismiss();
                break;

            case R.id.ll_share_qzone:
                sharToQzone();
                dismiss();
                break;

            case R.id.ll_share_sina:
                sharToSina();
                dismiss();
                break;

            case R.id.ll_share_copy_link:
                copyLink();
                dismiss();
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
        cmb.setText(entity.getLink());
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
        mWeb.setThumb(new UMImage(context, HttpContans.LOGO_ADDRESS));
        new ShareAction(mActivity)
                .withMedia(mWeb).
                setPlatform(SHARE_MEDIA.SINA).
                setCallback(listener).
                share();
    }

    //分享到qq空间
    private void sharToQzone() {
//        UMWeb web = new UMWeb(entity.getLink());
//        web.setThumb(new UMImage(context, entity.getImgURL()));
//        web.setDescription("测试描述111");
//        web.setTitle("测试标题222");

        new ShareAction(mActivity)
                .withMedia(mWeb).
                setPlatform(SHARE_MEDIA.QZONE).
                setCallback(listener).
                share();
    }

    //分享到qq好友
    private void sharToQqhy() {
        new ShareAction(mActivity)
                .withMedia(mWeb).
                setPlatform(SHARE_MEDIA.QQ).
                setCallback(listener).
                share();
    }

    //分享到朋友圈
    private void sharToPyq() {
        new ShareAction(mActivity)
                .withMedia(mWeb).
                setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).
                setCallback(listener).
                share();
    }

    //分享到微信好友
    private void sharToWxhy() {
        new ShareAction(mActivity)
                .withMedia(mWeb).
                setPlatform(SHARE_MEDIA.WEIXIN).
                setCallback(listener).
                share();
    }

    private UMShareListener listener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            if (share_media == SHARE_MEDIA.SINA) {
                mDialog.show();
            }
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            mDialog.dismiss();
            ToastUtils.showToast("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            mDialog.dismiss();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            mDialog.dismiss();
        }
    };
}
