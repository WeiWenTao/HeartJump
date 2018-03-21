package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.bean.starList.StarListKey;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.yanzhenjie.nohttp.rest.Response;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

public class YyhdActivity_1 extends BaseActivity implements DialogBirthdayStyle.onDialogBtClick, RequersCallBackListener {

    @ViewInject(R.id.iv_yyhd_cover)         //封面
    private ImageView iv_yyhd_cover;

    @ViewInject(R.id.et_yyhd_name)          //应援活动名称
    private EditText et_yyhd_name;

    @ViewInject(R.id.tv_yyhd_time)          //截止时间
    private TextView tv_yyhd_time;

    @ViewInject(R.id.et_content)            //应援活动内容
    private EditText et_content;

    @ViewInject(R.id.tv_yyje)               //应援金额
    private TextView tv_yyje;


    private PictureSelectionModel mPictureSelectionModel;
    private DialogBirthdayStyle mBirthdayStyle;
    private String dateText;
    private String mYear;
    private String mMon;
    private String mDay;
    private HytCore mCore;
    private Gson mGson;
    private String mPicPath;
    private String endTime;
    private int starId;
    private String mValueFild;
    private MyWaitDialog mDialog;

    @Override
    protected void initChild() {
        initDialog();
        mCore = new HytCore();
        starId = getIntent().getIntExtra("starId", -1);
        mGson = MyApplication.getGson();
        mCore.querYyhdJE("yyje", this);  //查询应援金额
    }

    private void initDialog() {

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
        return R.layout.activity_yyhd_1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPicPath = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
            iv_yyhd_cover.setImageBitmap(CommonUtils.decodeBitmap(mPicPath));
            iv_yyhd_cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    //选择时间
    @OnClick(R.id.rlv_select_time)
    public void setTime(View view) {
        mBirthdayStyle.show();
    }

    //点击时间对话框完成
    @Override
    public void onClickComplete(BirthdayDate date, boolean isChange) {
        mYear = date.getYear() + "";
        mMon = (date.getMonth() + (isChange ? 1 : 2)) + "";
        mDay = date.getDay() + "";
        endTime = mYear + "-" + mMon + "-" + mDay;
        tv_yyhd_time.setText(endTime);
        et_content.requestFocus();
    }

    //点击选择照片
    @OnClick(R.id.iv_yyhd_cover)
    public void selectPhoto(View view) {
        mPictureSelectionModel.forResult(1);
    }

    //点击提交
    @OnClick(R.id.tv_creat_yyhd_commit)
    public void commit(View view) {
        String activeName = et_yyhd_name.getText().toString();
        String content = et_content.getText().toString();

        if (CommonUtils.isEmpty(activeName,endTime,content,mPicPath)) {
            ToastUtils.showToast("请完善信息哦");
            return;
        }

        mCore.createYyhd(activeName, endTime, content, starId, Constans.TYPE_ONE, mValueFild,
                null, null, null, null, null, null,
                null, mPicPath, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_FORE) {
            StarListKey starListKey = mGson.fromJson(response.get(), StarListKey.class);
            mValueFild = starListKey.getRows().get(0).getValueFild();
            tv_yyje.setText(" ¥ " + mValueFild);
        } else if (what == Constans.TYPE_FIVE) {
            CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                ToastUtils.showToast("成功提交申请,请等待审核!");
                finish();
            } else {
                ToastUtils.showToast(msg.getMsg());
            }
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
