package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.EditPersonalInfo.PersonMessage;
import com.cucr.myapplication.bean.EditPersonalInfo.PersonalInfo;
import com.cucr.myapplication.bean.eventBus.EventQueryPersonalInfo;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.editPersonalInfo.EditInfoCore;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
import com.cucr.myapplication.widget.dialog.DialogGender;
import com.cucr.myapplication.widget.dialog.DialogPhoto;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;


public class PersonalInfoActivity extends BaseActivity implements DialogPhoto.OnClickBt, DialogGender.OnClickBt {

    //选择生日
    @ViewInject(R.id.tv_birthday_edit)
    private TextView tv_birthday_edit;

    //选择性别
    @ViewInject(R.id.tv_gender)
    private TextView tv_gender;

    //选择所在地
    @ViewInject(R.id.tv_set_location)
    private TextView tv_set_location;

    //更改头像
    @ViewInject(R.id.iv_head)
    private CircleImageView iv_head;

    //个性签名
    @ViewInject(R.id.et_my_sign)
    private EditText et_my_sign;

    //昵称
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;

    //昵称
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;

    private DialogBirthdayStyle mBirthdayStyle;
    private String mYear = "0000";
    private String mMon = "00";
    private String mDay = "00";
    private String dateText;
    public static final int IMAGE_COMPLETE = 2; // 结果
    private EditInfoCore mCore;//编辑
    private String mProvince;
    private String mCity;
    private String mTemppath;
    private String birthdayMsg;
    private PersonMessage.ObjBean obj;
    private DialogPhoto mDialog;
    private DialogGender mGenderDialog;
    private PictureSelectionModel mModel;

    @Override
    protected void initChild() {
        initTitle("个人资料");
        initDialog();
        //用户编辑
        mCore = new EditInfoCore();
        //查询
        obj = (PersonMessage.ObjBean) getIntent().getSerializableExtra("data");
        //初始化对话框
        mBirthdayStyle = new DialogBirthdayStyle(this, R.style.BirthdayStyleTheme, true);

        initView();
    }

    private void initDialog() {
//        dialog = new DialogErWeiMa(this, R.style.MyDialogStyle);

        mDialog = new DialogPhoto(this, R.style.MyDialogStyle);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        mDialog.setOnClickBt(this);

        mGenderDialog = new DialogGender(this, R.style.MyDialogStyle);
        Window window = mGenderDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation);
        mGenderDialog.setOnClickBt(this);
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
        mTemppath = obj.getUserHeadPortrait();
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
                tv_birthday_edit.setText(mYear + "-" + mMon + "-" + mDay);
            }
        });

        //显示日期对话框
        mBirthdayStyle.show();
    }

    //   todo pop_gender_select
    //弹出性别选择框
    @OnClick(R.id.rl_gender)
    public void selectGender(View parent) {
        mGenderDialog.show();
    }

    //选择所在地
    @OnClick(R.id.rl_location)
    public void selectLocation(View view) {
        Intent intent = new Intent(this, LocalityProvienceActivity.class);
        intent.putExtra("needShow", false);
        intent.putExtra("className", "PersonalInfoActivity");
        startActivity(intent);
    }

    //换头像
    @OnClick(R.id.rl_change_headpic)
    public void chnagePic(View view) {
        mDialog.show();
//        dialog.setDate(new ErWeiMaInfo("123","aaa","qewqewqe"));
//        dialog.show();

    }

    /**
     * 图片选择及拍照结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //相机和相册的回调结果都在这里
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                Intent intent1 = new Intent(PersonalInfoActivity.this, ClipActivity.class);
                intent1.putExtra("path", selectList.get(0).getCompressPath());
                startActivityForResult(intent1, IMAGE_COMPLETE);
                break;

            case IMAGE_COMPLETE:
                mTemppath = data.getStringExtra("path");
                iv_head.setImageBitmap(CommonUtils.getLoacalBitmap(mTemppath));
                break;
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

        mCore.save(MyApplication.getInstance(), new PersonalInfo(userId, sign, nickName, sex, birthdayMsg, mProvince, mCity, singName, mTemppath), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                ReBackMsg msg = mGson.fromJson(response.get(), ReBackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("保存成功!");
                    EventBus.getDefault().post(new EventQueryPersonalInfo());
                    finish();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        mCore.stopRequest();
        super.onDestroy();
    }

    //打开相机
    @Override
    public void clickCamera() {
        openCameraOrAblum(true);
    }

    //打开相册
    @Override
    public void clickAlbum() {
        openCameraOrAblum(false);
    }

    //打开相册或相机
    private void openCameraOrAblum(boolean isCamera) {
        if (isCamera) {
            mModel = PictureSelector.create(this)//相机
                    .openCamera(PictureMimeType.ofImage());
        } else {
            mModel = PictureSelector.create(this)//相册
                    .openGallery(PictureMimeType.ofImage());
        }

        mModel.maxSelectNum(9)
                .imageSpanCount(3)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(false)
//                            .selectionMedia(mData)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)
                .compress(true)
                .cropCompressQuality(90)
                .compressMode(LUBAN_COMPRESS_MODE)
                .isGif(true)
                .previewEggs(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void clickMan() {
        tv_gender.setText("男");
    }

    @Override
    public void clickWoman() {
        tv_gender.setText("女");
    }
}