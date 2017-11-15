package com.cucr.myapplication.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.editPersonalInfo.EditInfoCore;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.EditPersonalInfo.PersonMessage;
import com.cucr.myapplication.model.EditPersonalInfo.PersonalInfo;
import com.cucr.myapplication.model.eventBus.EventQueryPersonalInfo;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.model.setting.BirthdayDate;
import com.cucr.myapplication.model.setting.LocationData;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.GetPathFromUri4kitkat;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {

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

    //个性签名
    @ViewInject(R.id.et_my_sign)
    EditText et_my_sign;

    //昵称
    @ViewInject(R.id.et_nickname)
    EditText et_nickname;

    //昵称
    @ViewInject(R.id.tv_phone)
    TextView tv_phone;


    private DialogBirthdayStyle mBirthdayStyle;
    private String mYear = "0000";
    private String mMon = "00";
    private String mDay = "00";
    private String dateText;


    private PopupWindow popWindow;
    private PopupWindow genderPopWindow;
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
    private EditInfoCore mCore;//编辑
    private String mProvince = "";
    private String mCity = "";
    private String mTemppath = "";
    private String birthdayMsg;
    private PersonMessage.ObjBean obj;


    public PersonalInfoActivity() {
        super();
    }


    @Override
    protected void initChild() {
        initTitle("个人资料");

        //用户编辑
        mCore = new EditInfoCore(this);
        //查询
        obj = (PersonMessage.ObjBean) getIntent().getSerializableExtra("data");
        //初始化对话框
        mBirthdayStyle = new DialogBirthdayStyle(this, R.style.BirthdayStyleTheme, true);

        initHead();

        initView();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_personal_info;
    }

    //初始化控件
    private void initView() {
        //将editText光标放置末尾
        et_my_sign.setSelection(et_my_sign.getText().length());
        et_nickname.setSelection(et_nickname.getText().length());

        //头像回显
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + obj.getUserHeadPortrait(), iv_head);
        //昵称回显
        et_nickname.setText(obj.getName());
        //性别回显
        tv_gender.setText(obj.getSex() == 0 ? "男" : "女");
        //电话回显
        tv_phone.setText(obj.getPhone());

        if (obj.getBirthday() == null) {
            obj.setBirthday(CommonUtils.getCurrentDate() + "00:00:00");
        }
        tv_birthday_edit.setText(obj.getBirthday().substring(0, 10));
        tv_set_location.setText(obj.getProvinceName() + " " + obj.getCityName());
        mProvince = obj.getProvinceName();
        mCity = obj.getCityName();
        et_my_sign.setText(obj.getSignName());
        //生日回显
        birthdayMsg = obj.getBirthday().substring(0, 10);
        MyLogger.jLog().i("saved:" + birthdayMsg);
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
            mCity = location.getName();
            LocationData locationData = CityDao.queryPrivnceBycode(location.getpCode());
            mProvince = locationData.getName();
            tv_set_location.setText(mProvince + "  " + mCity);
        }

    }

    //选择生日
    @OnClick(R.id.rl_birthday)
    public void selectBirthday(View view) {
        if (TextUtils.isEmpty(dateText)) {
            dateText = CommonUtils.getCurrentDate();
        }
        mYear = dateText.substring(0, 4).trim();
        mMon = dateText.substring(5, 7).trim();
        mDay = dateText.substring(8, 10).trim();

        //初始化日期数据
        mBirthdayStyle.initDate(Integer.parseInt(mYear), Integer.parseInt(mMon), Integer.parseInt(mDay));

        //点击对话框外部消失
        mBirthdayStyle.setCanceledOnTouchOutside(true);
        mBirthdayStyle.setOnDialogBtClick(new DialogBirthdayStyle.onDialogBtClick() {
            @Override
            public void onClickComplete(BirthdayDate date, boolean isChange) {
                //如果用户改了生日就显示改了的  如果没改就显示原来的
//                if (isChange) {
                birthdayMsg = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();
                tv_birthday_edit.setText(birthdayMsg);
                MyLogger.jLog().i("birthdayMsg:" + birthdayMsg);
                mYear = date.getYear() + "";
                mMon = (date.getMonth() + (isChange ? 1 : 2)) + "";
                mDay = date.getDay() + "";
//                } else {
//                    dateText = CommonUtils.getCurrentDate();
//                    mYear = dateText.substring(0, 4).trim();
//                    mMon = dateText.substring(5, 7).trim();
//                    mDay = dateText.substring(8, 10).trim();
//                    //如果是以零开头就去掉零
//                    if (mMon.startsWith("0")) {
//                        mMon = mMon.substring(1);
//                    }
//
//                    //同上
//                    if (mDay.startsWith("0")) {
//                        mDay = mDay.substring(1);
//                    }

                tv_birthday_edit.setText(mYear + "-" + mMon + "-" + mDay);
//                }

            }
        });

        //显示日期对话框
        mBirthdayStyle.show();


    }

    //弹出性别选择框
    @OnClick(R.id.rl_gender)
    public void selectGender(View parent) {
        CommonUtils.initPopBg(true, fl_pop_bg);

        if (genderPopWindow == null) {
            View genderView = layoutInflater.inflate(R.layout.pop_gender_select, null);
            genderPopWindow = new PopupWindow(genderView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initGenderPop(genderView);
        }
        genderPopWindow.setAnimationStyle(R.style.AnimationFade);
        genderPopWindow.setFocusable(true);
        genderPopWindow.setOutsideTouchable(true);

        genderPopWindow.setBackgroundDrawable(new BitmapDrawable());

        genderPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        genderPopWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        genderPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommonUtils.initPopBg(false, fl_pop_bg);
            }
        });
    }

    private void initGenderPop(View genderView) {

        genderView.findViewById(R.id.rl_popWindow_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderPopWindow.dismiss();
            }
        });

        genderView.findViewById(R.id.tv_man).setOnClickListener(this);
        genderView.findViewById(R.id.tv_women).setOnClickListener(this);
        genderView.findViewById(R.id.tv_cancle).setOnClickListener(this);
    }

    //选择所在地
    @OnClick(R.id.rl_location)
    public void selectLocation(View view) {
        Intent intent = new Intent(this, LocalityProvienceActivity.class);
        intent.putExtra("needShow", false);
        startActivity(intent);
    }

    //换头像
    @OnClick(R.id.rl_change_headpic)
    public void chnagePic(View view) {
        CommonUtils.initPopBg(true, fl_pop_bg);

        showPopupWindow(iv_head);

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
                CommonUtils.initPopBg(false, fl_pop_bg);
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
//                Cursor cursor = managedQuery(uri, proj, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                this.path = cursor.getString(column_index);// 图片在的路径

                path = GetPathFromUri4kitkat.getPath(MyApplication.getInstance(), uri);

                Intent intent3 = new Intent(PersonalInfoActivity.this, ClipActivity.class);
                intent3.putExtra("path", this.path);
                startActivityForResult(intent3, IMAGE_COMPLETE);
                break;

            case PHOTOTAKE://拍照
                this.path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(this.path));
                Intent intent2 = new Intent(PersonalInfoActivity.this, ClipActivity.class);
                intent2.putExtra("path", this.path);
                startActivityForResult(intent2, IMAGE_COMPLETE);
                break;

            case IMAGE_COMPLETE:
                mTemppath = data.getStringExtra("path");
                iv_head.setImageBitmap(getLoacalBitmap(mTemppath));
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


    //昵称
    @OnClick(R.id.rl_nickname)
    public void nickName(View view) {
        et_nickname.requestFocus();

    }

    //个性签名
    @OnClick(R.id.rl_mysign)
    public void mySign(View view) {
        et_my_sign.requestFocus();
    }

    //保存
    @OnClick(R.id.tv_save)
    public void saveInfo(View view) {
        int userId = (int) SpUtil.getParam(SpConstant.USER_ID, -1);
        String sign = (String) SpUtil.getParam(SpConstant.SIGN, "");
        String nickName = et_nickname.getText().toString();
        String singName = et_my_sign.getText().toString();
        int sex = tv_gender.getText().equals("男") ? 0 : 1;

        PersonalInfo personalInfo = new PersonalInfo(userId, sign, nickName, sex, birthdayMsg, mProvince, mCity, singName, mTemppath);

        mCore.save(this, personalInfo, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                ReBackMsg msg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("保存成功!");
                    EventBus.getDefault().post(new EventQueryPersonalInfo());
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //TODO
//        if (popWindow.isShowing()){
//            cancel.performClick();
//        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_man:
                tv_gender.setText("男");
                genderPopWindow.dismiss();
                break;

            case R.id.tv_women:
                tv_gender.setText("女");
                genderPopWindow.dismiss();
                break;

            case R.id.tv_cancle:
                genderPopWindow.dismiss();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mCore.stopRequest();
        super.onDestroy();
    }
}
