package com.cucr.myapplication.activity.hyt;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.PagerAdapter.BigPadPagerAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.Hyt.BigPadInfo;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
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

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

public class YyhdActivity_2 extends BaseActivity implements DialogBirthdayStyle.onDialogBtClick, RequersCallBackListener, BigPadPagerAdapter.OnItemClick {

    @ViewInject(R.id.iv_bigpad_cover)   //封面
    private ImageView iv_bigpad_cover;

    @ViewInject(R.id.et_yzsm)           //用资申明
    private EditText et_yzsm;

    @ViewInject(R.id.tv_yyje)           //应援金额
    private TextView tv_yyje;

    @ViewInject(R.id.et_bigpad_name)    //名称
    private EditText et_bigpad_name;

    @ViewInject(R.id.tv_yyhd_time)      //时间显示
    private TextView tv_yyhd_time;

    @ViewInject(R.id.et_content)        //活动内容
    private EditText et_content;

    @ViewInject(R.id.vp_bp)             //BigPad
    private ViewPager vp_bp;

    @ViewInject(R.id.indicator)         //指示器
    private CircleIndicator indicator;

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
    private MyWaitDialog mDialog;
    private BigPadPagerAdapter mAdapter;
    private BigPadInfo mBigPadInfo;
    private String bgInfoIds;
    private int money;
    private List<BigPadInfo.RowsBean> mRows;


    @Override
    protected void initChild() {
        starId = getIntent().getIntExtra("starId", -1);
        mAdapter = new BigPadPagerAdapter();
        mGson = MyApplication.getGson();
        mCore = new HytCore();
        mCore.queryBigPadInfo(this);
        vp_bp.setAdapter(mAdapter);
        indicator.setViewPager(vp_bp);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        mAdapter.setOnItemClick(this);
        initDialog();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPicPath = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
            iv_bigpad_cover.setImageBitmap(CommonUtils.decodeBitmap(mPicPath));
            iv_bigpad_cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_yyhd_2;
    }

    //选择截止时间
    @OnClick(R.id.rlv_select_time)
    public void endTime(View view) {
        mBirthdayStyle.show();
    }

    //选择封面
    @OnClick(R.id.iv_bigpad_cover)
    public void setPhoto(View view) {
        mPictureSelectionModel.forResult(1);
    }

    //提交审核
    @OnClick(R.id.tv_creat_yyhd_commit)
    public void commit(View view) {
        String bigPadName = et_bigpad_name.getText().toString();
        String content = et_content.getText().toString();
        String yzsm = et_yzsm.getText().toString();
        bgInfoIds = "";
        //获取选中的bigpad
        getActiveId();
        MyLogger.jLog().i("bgInfoIds:" + bgInfoIds);

        if (CommonUtils.isEmpty(mPicPath, bigPadName, endTime, content, yzsm)) {
            ToastUtils.showToast("请完善信息哦");
            return;
        }

        if (TextUtils.isEmpty(bgInfoIds)) {
            ToastUtils.showToast("至少勾选一个BigPad哦");
            return;
        }


        mCore.createYyhd(bigPadName, endTime, content, starId, 2, null, yzsm,
                money + "", bgInfoIds, null, null, null, null, mPicPath,
                this);
    }

    private void getActiveId() {
        money = 0;
        for (BigPadInfo.RowsBean row : mRows) {
            try {
                if (row.isSel()) {
                    bgInfoIds = bgInfoIds + row.getId() + ",";
                    money = money + Integer.parseInt(row.getPrice());
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onClickComplete(BirthdayDate date, boolean isChange) {
        mYear = date.getYear() + "";
        mMon = (date.getMonth() + (isChange ? 1 : 2)) + "";
        mDay = date.getDay() + "";
        endTime = mYear + "-" + mMon + "-" + mDay;
        tv_yyhd_time.setText(endTime);
        et_content.requestFocus();

    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {

        switch (what) {

            //发起后援团活动
            case Constans.TYPE_FIVE:
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("成功提交申请,请等待审核!");
                    finish();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
                break;

            //查询BigPad信息
            case Constans.TYPE_SIX:
                mBigPadInfo = mGson.fromJson(response.get(), BigPadInfo.class);
                if (mBigPadInfo.isSuccess()) {
                    mRows = mBigPadInfo.getRows();
                    mAdapter.setData(mRows);
                } else {
                    ToastUtils.showToast(mBigPadInfo.getErrorMsg());
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

    @Override
    public void onClickItem(int position) {
        mBigPadInfo.getRows().get(position).setSel(!mBigPadInfo.getRows().get(position).isSel());
        //更新应援金额
        getActiveId();
        tv_yyje.setText(" ¥ " + money);
        mAdapter.notifyDataSetChanged();
    }
}
