package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
import com.cucr.myapplication.widget.dialog.DialogGenderStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalInfoActivity extends Activity {

    //选择生日
    @ViewInject(R.id.tv_birthday_edit)
    TextView tv_birthday_edit;

    //选择性别
    @ViewInject(R.id.tv_gender)
    TextView tv_gender;

    //选择所在地
    @ViewInject(R.id.tv_set_location)
    TextView tv_set_location;

    //更改头像
    @ViewInject(R.id.iv_head)
    CircleImageView iv_head;

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg)
    FrameLayout fl_pop_bg;

    private DialogBirthdayStyle mBirthdayStyle;
    private String mYear;
    private String mMon;
    private String mDay;
    private String dateText;
    private DialogGenderStyle mGenderStyle;


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

    public PersonalInfoActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ViewUtils.inject(this);

        initHead();

        initDialog();

    }

    private void initHead() {
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        File file = new File(Environment.getExternalStorageDirectory(), "ClipHeadPhoto/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/ClipHeadPhoto/cache/";
        photoSaveName = System.currentTimeMillis() + ".png";
    }


    //这个界面配置了signTask启动模式  用getIntent获取数据会为null  用onNewIntent + setIntent()
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LocationData location = (LocationData) getIntent().getSerializableExtra("finalData");

        if (location != null) {
            String city = location.getName();

            LocationData locationData = CityDao.queryPrivnceBycode(location.getpCode());
            String province = locationData.getName();

            tv_set_location.setText(province + "  " + city);
        }

    }


    //初始化对话框
    private void initDialog() {
        mBirthdayStyle = new DialogBirthdayStyle(this, R.style.BirthdayStyleTheme);

        mGenderStyle = new DialogGenderStyle(this, R.style.BirthdayStyleTheme);
        mGenderStyle.setOnClickGender(new DialogGenderStyle.OnClickGender() {
            @Override
            public void onClickBoy() {
                tv_gender.setText("男");
            }

            @Override
            public void onClickGirl() {
                tv_gender.setText("女");
            }
        });
    }


    //选择生日
    @OnClick(R.id.rl_birthday)
    public void selectBirthday(View view) {
        dateText = tv_birthday_edit.getText().toString().trim();
        if (dateText.length() == 0) {
            dateText = CommonUtils.getCurrentDate();
        }

        mYear = dateText.substring(0, 4).trim();
        mMon = dateText.substring(dateText.indexOf("年") + 1, dateText.indexOf("月")).trim();
        mDay = dateText.substring(dateText.indexOf("月") + 1, dateText.indexOf("日")).trim();


        //初始化日期数据
        mBirthdayStyle.initDate(Integer.parseInt(mYear), Integer.parseInt(mMon) - 1, Integer.parseInt(mDay));

        //点击对话框外部消失
        mBirthdayStyle.setCanceledOnTouchOutside(true);
        mBirthdayStyle.setOnDialogBtClick(new DialogBirthdayStyle.onDialogBtClick() {
            @Override
            public void onClickComplete(BirthdayDate date, boolean isChange) {
                //如果用户改了生日就显示改了的  如果没改就显示原来的
                if (isChange) {
                    tv_birthday_edit.setText(date.getYear() + " 年 " + (date.getMonth() + 1) + " 月 " + date.getDay() + " 日 ");
                } else {

                    //如果是以零开头就去掉零
                    if (mMon.startsWith("0")) {
                        mMon = mMon.substring(1);
                    }

                    //同上
                    if (mDay.startsWith("0")) {
                        mDay = mDay.substring(1);
                    }

                    tv_birthday_edit.setText(mYear + " 年 " + mMon + " 月 " + mDay + " 日 ");
                }
            }
        });

        //显示日期对话框
        mBirthdayStyle.show();


    }

    //选择性别
    @OnClick(R.id.rl_gender)
    public void selectGender(View view) {
        mGenderStyle.show();
    }

    //选择所在地
    @OnClick(R.id.rl_location)
    public void selectLocation(View view) {
        startActivity(new Intent(this, LocalityProvienceActivity.class));
    }

    //换头像
    @OnClick(R.id.rl_change_headpic)
    public void chnagePic(View view) {
        CommonUtils.initPopBg(true,fl_pop_bg);

        showPopupWindow(iv_head);

    }

//    private void initPopBg(boolean isIn) {
//
//        //防止重复创建对象
//        AlphaAnimation animation1 = null;
//        AlphaAnimation animation2 = null;
//
//        //进入动画
//        if (animation1 == null) {
//            animation1 = new AlphaAnimation(0.0f, 1.0f);
//        }
//
//        //退出动画
//        if (animation2 == null) {
//            animation2 = new AlphaAnimation(1.0f, 0.0f);
//        }
//
//        animation1.setDuration(200);
//        animation2.setDuration(200);
//        fl_pop_bg.setAnimation(isIn ? animation1 : animation2);
//        fl_pop_bg.setVisibility(isIn ? View.VISIBLE : View.GONE);
//
//    }

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
                CommonUtils.initPopBg(false,fl_pop_bg);
            }
        });
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


    /**
     * 图片选择及拍照结果
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Intent intent3 = new Intent(PersonalInfoActivity.this, ClipActivity.class);
                intent3.putExtra("path", path);
                startActivityForResult(intent3, IMAGE_COMPLETE);
                break;

            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(path));
                Intent intent2 = new Intent(PersonalInfoActivity.this, ClipActivity.class);
                intent2.putExtra("path", path);
                startActivityForResult(intent2, IMAGE_COMPLETE);
                break;

            case IMAGE_COMPLETE:
                final String temppath = data.getStringExtra("path");
                iv_head.setImageBitmap(getLoacalBitmap(temppath));
                break;

            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //返回
    @OnClick(R.id.iv_personal_info_back)
    public void back(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        //TODO
//        if (popWindow.isShowing()){
//            cancel.performClick();
//        }
        super.onBackPressed();
    }
}
