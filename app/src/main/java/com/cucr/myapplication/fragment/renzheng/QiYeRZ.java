package com.cucr.myapplication.fragment.renzheng;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 911 on 2017/6/16.
 */

public class QiYeRZ extends Fragment {private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private TextView photograph, albums;
    private LinearLayout cancel;

    public static final int PHOTOZOOM = 0; // 相册/拍照
    public static final int PHOTOTAKE = 1; // 相册/拍照
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final int CROPREQCODE = 3; // 截取
    private String photoSavePath;//保存路径
    private String photoSaveName;//图pian名
    private String path;//图片全路径

    private Context mContext;
    Activity activity;

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg_qiye)
    FrameLayout fl_pop_bg_qiye;

    //正面
    @ViewInject(R.id.img_qiye_positive)
    ImageView img_qiye_positive;

    //反面
    @ViewInject(R.id.img_qiye_nagetive)
    ImageView img_qiye_nagetive;

    //反面
    @ViewInject(R.id.img_qieye_zhizhao)
    ImageView img_qieye_zhizhao;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        mContext = container.getContext();
        View rootView = inflater.inflate(R.layout.fragment_ren_zheng_qiye, container, false);
        ViewUtils.inject(this,rootView);
        initHead();
        return rootView;

    }

    private void initHead() {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        File file = new File(Environment.getExternalStorageDirectory(), "QiYeAttestation/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/QiYeAttestation/cache/";
    }

    public void initPop(View view) {
        photograph = (TextView) view.findViewById(R.id.photograph);//拍照
        albums = (TextView) view.findViewById(R.id.albums);//相册
        cancel = (LinearLayout) view.findViewById(R.id.cancel);//取消
        view.findViewById(R.id.rl_popWindow_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                Uri imageUri = null;
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
                openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, PHOTOTAKE);
            }
        });
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(openAlbumIntent, PHOTOZOOM);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();

            }
        });
    }

    @SuppressWarnings("deprecation")
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = layoutInflater.inflate(R.layout.pop_select_photo, null);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initPop(view);
        }
        popWindow.setAnimationStyle(R.style.AnimationFade);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setBackgroundDrawable(new BitmapDrawable());

        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommonUtils.initPopBg(false, fl_pop_bg_qiye);
            }
        });
    }

    /**
     * 图片选择及拍照结果
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = null;
        switch (requestCode) {
            case PHOTOZOOM://相册
                if (data == null) {
                    return;
                }
                uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);// 图片在的路径
                //当不可见时在显示
                if (whichView.getVisibility() == View.GONE) {
                    whichView.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                whichView.setImageBitmap(CommonUtils.decodeBitmap(path));
                break;

            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(path));
                //当不可见时在显示
                if (whichView.getVisibility() == View.GONE) {
                    whichView.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                whichView.setImageBitmap(CommonUtils.decodeBitmap(path));

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private ImageView whichView;

    //身份证正面
    @OnClick(R.id.rl_sfz_positive)
    public void positive(View view) {
        whichView = img_qiye_positive;
        CommonUtils.initPopBg(true, fl_pop_bg_qiye);
        showPopupWindow(img_qiye_positive);

    }

    //身份证反面
    @OnClick(R.id.rl_sfz_nagetive)
    public void negative(View view) {
        whichView = img_qiye_nagetive;
        CommonUtils.initPopBg(true, fl_pop_bg_qiye);
        showPopupWindow(img_qiye_nagetive);
    }

    //营业执照
    @OnClick(R.id.rl_yinyezhizhao)
    public void yinYeZhiZhao(View view) {
        whichView = img_qieye_zhizhao;
        CommonUtils.initPopBg(true, fl_pop_bg_qiye);
        showPopupWindow(img_qieye_zhizhao);
    }

    //提交审核
    @OnClick(R.id.tv_commit_check)
    public void commitCheck(View view) {
    }

}
