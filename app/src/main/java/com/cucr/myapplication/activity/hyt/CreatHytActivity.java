package com.cucr.myapplication.activity.hyt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogCreatHyt;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.yanzhenjie.nohttp.rest.Response;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

public class CreatHytActivity extends BaseActivity implements View.OnClickListener {

    //后援团封面
    @ViewInject(R.id.iv_pic_cover)
    private ImageView iv_pic_cover;

    //身份证正面
    @ViewInject(R.id.iv_idcard_posi)
    private ImageView iv_idcard_posi;

    //身份证反面
    @ViewInject(R.id.iv_idcard_nega)
    private ImageView iv_idcard_nega;

    //后援团名称
    @ViewInject(R.id.et_hyt_name)
    private EditText et_hyt_name;

    //创建人姓名
    @ViewInject(R.id.et_creat_body)
    private EditText et_creat_body;

    //创建人手机号
    @ViewInject(R.id.et_phone)
    private EditText et_phone;

    //创建人邮箱
    @ViewInject(R.id.et_email)
    private EditText et_email;

    //创建人身份证
    @ViewInject(R.id.et_idcard)
    private EditText et_idcard;

    //地区
    @ViewInject(R.id.tv_local)
    private TextView tv_local;


    private PictureSelectionModel mPictureSelectionModel;
    private ImageView tempView;
    private String coverPath;
    private String idCardPosiPath;
    private String idCardNegaPath;
    private HytCore mCore;
    private String hytName;
    private String creatBody;
    private String idCard;
    private String phoneNum;
    private String emali;
    private String address;
    private int mStarId;
    private MyWaitDialog mDialog;
    private Intent mIntent;
    private DialogCreatHyt mRuleDialog;


    @Override
    protected void initChild() {
        init();
    }


    @Override
    protected int getChildRes() {
        return R.layout.activity_creat_hyt;
    }

    private void init() {
        mCore = new HytCore();
        mIntent = new Intent(this, LocalityProvienceActivity.class);
        initPhotoConfig();
        mStarId = getIntent().getIntExtra("starId", -1);
        mDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mRuleDialog = new DialogCreatHyt(this, R.style.BirthdayStyleTheme);
        iv_pic_cover.setOnClickListener(this);
        iv_idcard_posi.setOnClickListener(this);
        iv_idcard_nega.setOnClickListener(this);
    }

    //点击选择地区
    @OnClick(R.id.rlv_local)
    public void clickLocal(View view) {
        mIntent.putExtra("needShow", false);
        mIntent.putExtra("className", "CreatHytActivity");
        startActivity(mIntent);
    }

    //相册属性配置
    private void initPhotoConfig() {
        mPictureSelectionModel = PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageSpanCount(3)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(false)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)
                .compress(true)
                .cropCompressQuality(90)
                .compressMode(LUBAN_COMPRESS_MODE)
                .isGif(true)
                .previewEggs(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String picPath = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
            tempView.setImageBitmap(CommonUtils.decodeBitmap(picPath));
            tempView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            switch (requestCode) {
                case R.id.iv_pic_cover: //后援团封面
                    coverPath = picPath;
                    break;

                case R.id.iv_idcard_posi://身份证正面
                    idCardPosiPath = picPath;
                    break;

                case R.id.iv_idcard_nega://身份证反面
                    idCardNegaPath = picPath;
                    break;
            }
        }
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
            String provience = locationData.getName();
            address = provience + "  " + city;
            tv_local.setText(address);
        }

    }

    private Integer ViewId;

    //无论是哪个控件选择照片 都直接赋值跳转 用id区分

    @Override
    public void onClick(View v) {
        tempView = (ImageView) v;
        ViewId = v.getId();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            mPictureSelectionModel.forResult(ViewId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean writeAccepted = false;
        switch (requestCode) {
            case 1:
                writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (writeAccepted) {
                    mPictureSelectionModel.forResult(ViewId);
                } else {
                    ToastUtils.showToast(getResources().getString(R.string.request_permission));
                }
                break;
        }
    }

    //提交审核
    @OnClick(R.id.tv_commit)
    public void commit(View view) {
        hytName = et_hyt_name.getText().toString();
        creatBody = et_creat_body.getText().toString();
        phoneNum = et_phone.getText().toString();
        emali = et_email.getText().toString();
        idCard = et_idcard.getText().toString();
        boolean empty = CommonUtils.isEmpty(coverPath, idCardPosiPath, idCardNegaPath, hytName,
                creatBody, phoneNum, emali, idCard, address);
        //检查信息是否完善
        if (empty) {
            ToastUtils.showToast("请先完善信息哟");
            return;
        }

        //提交审核
        mCore.creatHyt(hytName, idCard, emali, creatBody, idCardPosiPath, idCardNegaPath,
                coverPath, mStarId, phoneNum, address, new RequersCallBackListener() {
                    @Override
                    public void onRequestSuccess(int what, Response<String> response) {
                        CommonRebackMsg reBackMsg = MyApplication.getGson().fromJson(response.get(), CommonRebackMsg.class);
                        if (reBackMsg.isSuccess()) {
                            ToastUtils.showToast("已提交审核");
                            finish();
                        } else {
                            ToastUtils.showToast(reBackMsg.getMsg());
                        }
                    }

                    @Override
                    public void onRequestStar(int what) {
                        mDialog.show();
                    }

                    @Override
                    public void onRequestError(int what, Response<String> response) {

                    }

                    @Override
                    public void onRequestFinish(int what) {
                        mDialog.dismiss();
                    }
                });
    }

    @OnClick(R.id.iv_rule)
    public void createRule(View view) {
        mRuleDialog.show();
    }
}
