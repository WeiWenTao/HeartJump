package com.cucr.myapplication.activity.huodong;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.model.setting.LocationData;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

public class FaBuHuoDongActivity extends BaseActivity {

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg_fb_huodong)
    FrameLayout fl_pop_bg_fb_huodong;

    //圆角图片
    @ViewInject(R.id.img_fabu_huodong_pic)
    ImageView img_fabu_huodong_pic;

    //活动地区
    @ViewInject(R.id.tv_active_local)
    TextView tv_active_local;

    //活动地区
    @ViewInject(R.id.et_local_catgory)
    EditText et_local_catgory;


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



    @Override
    protected void initChild() {
        initTitle("活动发布");
        initHead();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_fa_bu_huo_dong;
    }

    //活动地区
    @OnClick(R.id.tv_active_local)
    public void selLocal(View view) {
        Intent intent = new Intent(this, LocalityProvienceActivity.class);
        intent.putExtra("needShow", true);
        intent.putExtra("className","FaBuHuoDongActivity");
        startActivity(intent);
    }

    //这个界面配置了signTask启动模式  用getIntent获取数据会为null  用onNewIntent + setIntent()
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LocationData location = (LocationData) getIntent().getSerializableExtra("finalData");

        if (location != null) {

            String qu = location.getName();

            LocationData shi = CityDao.queryCityBycode(location.getpCode());

            LocationData sheng = CityDao.queryPrivnceBycode(shi.getpCode());

            String district = shi.getName();
            String province = sheng.getName();

            tv_active_local.setText(province + " " + district + " " + qu);
            et_local_catgory.requestFocus();
        }

    }


    //点击添加图片封面
    @OnClick(R.id.rl_fabu_huodong_pic)
    public void addPic(View view) {
        CommonUtils.initPopBg(true, fl_pop_bg_fb_huodong);
        showPopupWindow(img_fabu_huodong_pic);
    }

    private void initHead() {
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        File file = new File(Environment.getExternalStorageDirectory(), "HuoDongFaBu/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/HuoDongFaBu/cache/";
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
                CommonUtils.initPopBg(false, fl_pop_bg_fb_huodong);
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
                if (img_fabu_huodong_pic.getVisibility() == View.GONE) {
                    img_fabu_huodong_pic.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                img_fabu_huodong_pic.setImageBitmap(CommonUtils.decodeBitmap(path));
                break;

            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(path));
                //当不可见时在显示
                if (img_fabu_huodong_pic.getVisibility() == View.GONE) {
                    img_fabu_huodong_pic.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                img_fabu_huodong_pic.setImageBitmap(CommonUtils.decodeBitmap(path));

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
