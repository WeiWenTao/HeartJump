package com.cucr.myapplication.activity.fenTuan;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.LvAdapter.CreatFtAdapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.dialog.DialogCreatFtStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

public class CreatFtActivity extends BaseActivity {

    //ListView
    @ViewInject(R.id.lv_creating_ft)
    ListView lv_creating_ft;

    //沉浸栏
    @ViewInject(R.id.rl_head)
    RelativeLayout rl_head_bar;

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg_creat_ft)
    FrameLayout fl_pop_bg_creat_ft;

    //圆角图片
    @ViewInject(R.id.img_creat_ft_star_pic)
    ImageView img_creat_ft_star_pic;

    private PopupWindow popWindow;
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


    private DialogCreatFtStyle mDialogCreatFtStyle;


    @Override
    protected void initChild() {
        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_creat_ft;
    }

    private void initView() {
        //规则对话框
        mDialogCreatFtStyle = new DialogCreatFtStyle(this, R.style.BirthdayStyleTheme);

        View headView = View.inflate(this, R.layout.head_creat_ft, null);
        ViewUtils.inject(this, headView);
        initHead();
        lv_creating_ft.addHeaderView(headView);
        lv_creating_ft.setAdapter(new CreatFtAdapter(this));
    }

    private void initHead() {
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        File file = new File(Environment.getExternalStorageDirectory(), "CreatFt/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/CreatFt/cache/";
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
                CommonUtils.initPopBg(false, fl_pop_bg_creat_ft);
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
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);// 图片在的路径
                //当不可见时在显示
                if (img_creat_ft_star_pic.getVisibility() == View.GONE) {
                    img_creat_ft_star_pic.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                img_creat_ft_star_pic.setImageBitmap(CommonUtils.decodeBitmap(path));
                break;

            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(path));
                //当不可见时在显示
                if (img_creat_ft_star_pic.getVisibility() == View.GONE) {
                    img_creat_ft_star_pic.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                img_creat_ft_star_pic.setImageBitmap(CommonUtils.decodeBitmap(path));

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //显示PopupWindow
    @OnClick(R.id.rl_creat_ft_pic)
    public void addPic(View view) {
        CommonUtils.initPopBg(true, fl_pop_bg_creat_ft);
        showPopupWindow(img_creat_ft_star_pic);
    }


    //创建规则
    @OnClick(R.id.iv_rule)
    public void showDialog(View view) {
        mDialogCreatFtStyle.show();
    }



}
