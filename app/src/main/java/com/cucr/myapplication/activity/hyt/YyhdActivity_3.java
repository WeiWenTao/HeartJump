package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.local.LocalityProvienceActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.dao.CityDao;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.bean.setting.LocationData;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
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

public class YyhdActivity_3 extends BaseActivity implements DialogBirthdayStyle.onDialogBtClick, RequersCallBackListener {

    @ViewInject(R.id.iv_fans_cover)     //封面
    private ImageView iv_fans_cover;

    @ViewInject(R.id.et_fans_name)     //名称
    private EditText et_fans_name;

    @ViewInject(R.id.tv_yyhd_time)     //截止时间
    private TextView tv_yyhd_time;

    @ViewInject(R.id.tv_fans_local)     //城市
    private TextView tv_fans_local;

    @ViewInject(R.id.et_fans_scale)     //规模
    private EditText et_fans_scale;

    @ViewInject(R.id.et_fans_je)        //众筹金额
    private EditText et_fans_je;

    @ViewInject(R.id.et_content)        //活动内容
    private EditText et_content;

    @ViewInject(R.id.et_yzsm)           //用资申明
    private EditText et_yzsm;


    private PictureSelectionModel mPictureSelectionModel;
    private DialogBirthdayStyle mBirthdayStyle;
    private String dateText;
    private String mYear;
    private String mMon;
    private String mDay;
    private HytCore mCore;
    private String mPicPath;
    private String endTime;
    private int starId;
    private MyWaitDialog mDialog;
    private Intent mIntent;
    private String mAddress;

    @Override
    protected void initChild() {
        mCore = new HytCore();
        starId = getIntent().getIntExtra("starId", -1);
        mIntent = new Intent(this, LocalityProvienceActivity.class);
        mDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        //初始化对话框
        mBirthdayStyle = new DialogBirthdayStyle(this, R.style.BirthdayStyleTheme, false);
        mBirthdayStyle.setOnDialogBtClick(this);
        if (TextUtils.isEmpty(dateText)) {
            dateText = CommonUtils.getCurrentDate();
        }
        mYear = dateText.substring(0, 4).trim();
        mMon = dateText.substring(5, 7).trim();
        mDay = dateText.substring(8, 10).trim();
        //初始化日期数据
        mBirthdayStyle.initDate(Integer.parseInt(mYear), Integer.parseInt(mMon) - 1, Integer.parseInt(mDay));

        //相册属性
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
    protected int getChildRes() {
        return R.layout.activity_yyhd_3;
    }

    @OnClick(R.id.iv_fans_cover)
    public void setPic(View view) {
        mPictureSelectionModel.forResult(1);
    }

    //选择截止时间
    @OnClick(R.id.rlv_select_time)
    public void setEndTime(View view) {
        mBirthdayStyle.show();
    }

    //人数规模
    @OnClick(R.id.rl_peoples)
    public void peoples(View view) {
        et_fans_scale.setFocusable(true);
        et_fans_scale.setFocusableInTouchMode(true);
        et_fans_scale.requestFocus();
        CommonUtils.hideKeyBorad(MyApplication.getInstance(), et_fans_scale, false);
    }

    //选择城市
    @OnClick(R.id.rlv_select_loacl)
    public void setCity(View view) {
        mIntent.putExtra("needShow", false);
        mIntent.putExtra("className", "YyhdActivity_3");
        startActivity(mIntent);
    }

    @Override
    public void onClickComplete(BirthdayDate date, boolean isChange) {
        mYear = date.getYear() + "";
        mMon = (date.getMonth() + (isChange ? 1 : 2)) + "";
        mDay = date.getDay() + "";
        endTime = mYear + "-" + mMon + "-" + mDay;
        tv_yyhd_time.setText(endTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPicPath = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
            iv_fans_cover.setImageBitmap(CommonUtils.decodeBitmap(mPicPath));
            iv_fans_cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
            mAddress = provience + "  " + city;
            tv_fans_local.setText(mAddress);
            peoples(et_fans_scale);
        }
    }

    @OnClick(R.id.tv_creat_fans_commit)
    public void commit(View view) {
        String activeName = et_fans_name.getText().toString();
        String peoples = et_fans_scale.getText().toString();
        String fans_je = et_fans_je.getText().toString();
        String content = et_content.getText().toString();
        String yzsm = et_yzsm.getText().toString();

        if (CommonUtils.isEmpty(activeName, endTime, mAddress, peoples, fans_je, content, content, yzsm, mPicPath)) {
            ToastUtils.showToast("请完善信息哦");
            return;
        }

        mCore.createYyhd(activeName, endTime, content, starId, 3, null, null,
                null, null, mAddress, peoples, fans_je, yzsm, mPicPath,
                this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        switch (what) {
            case Constans.TYPE_FIVE:
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("成功提交申请,请等待审核!");
                    finish();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
                break;
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
}
