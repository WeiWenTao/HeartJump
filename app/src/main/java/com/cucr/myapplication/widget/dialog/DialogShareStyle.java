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

    }

    //分享到qq空间
    private void sharToQzone() {

    }

    //分享到qq好友
    private void sharToQqhy() {

    }

    //分享到朋友圈
    private void sharToPyq() {

    }

    //分享到微信好友
    private void sharToWxhy() {

    }


    private void showShare(String platform) {

    }
}
