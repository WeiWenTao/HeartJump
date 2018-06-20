package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.invate.InvateSerchStar;
import com.cucr.myapplication.bean.setting.InvateInfo;
import com.cucr.myapplication.bean.share.ShareEntity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.invate.InvateCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogShareStyle;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

public class InvateActivity extends Activity implements RequersCallBackListener {

    //填写邀请码
    @ViewInject(R.id.et_code)
    private EditText et_code;

    //我的邀请码
    @ViewInject(R.id.tv_mycode)
    private TextView tv_mycode;

    //明星姓名
    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    //明星封面
    @ViewInject(R.id.iv_cover)
    private ImageView iv_cover;

    //领取星币
    @ViewInject(R.id.tv_draw)
    private TextView tv_draw;

    private DialogShareStyle mDialog;
    private InvateCore mCore;
    private Gson mGson;
    private InvateInfo.ObjBean mObj;
    private MyWaitDialog mWaitDialog;
    private InvateSerchStar.RowsBean mRowsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invate);
        ViewUtils.inject(this);
        init();
        getInfo();
    }

    private void getInfo() {
        mWaitDialog = new MyWaitDialog(this, R.style.MyDialogStyle);
        mGson = MyApplication.getGson();
        mCore = new InvateCore();
        mCore.querInvateCode(this);
    }

    private void init() {
        //设置状态栏字体颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(Color.parseColor("#6a8bf8"), 0);

        mDialog = new DialogShareStyle(this, R.style.MyDialogStyle);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation); //添加动画

        findViewById(R.id.iv_base_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //
    public void invate(View view) {
        mDialog.setData2(new ShareEntity("心跳互娱，明星守护神！", "追爱豆，领红包! 更多精彩等你来玩",
                HttpContans.ADDRESS_INVATE_REGIST + SpUtil.getParam(SpConstant.USER_ID,
                        -1), HttpContans.LOGO_ADDRESS));
        mDialog.show();
    }

    //领取星币
    @OnClick(R.id.tv_draw)
    public void clickDraw(View v) {
        mCore.putCode(et_code.getText().toString(), this);
    }

    //点击我的邀请码
    @OnClick(R.id.tv_mycode)
    public void click(View view) {
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(mObj.getCode());
        ToastUtils.showToast("已复制我的邀请码");
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {

        switch (what) {
            case Constans.TYPE_ONE:
                InvateInfo invateInfo = mGson.fromJson(response.get(), InvateInfo.class);
                mObj = invateInfo.getObj();
                if (invateInfo.isSuccess()) {
                    if (!TextUtils.isEmpty(mObj.getShareCode())) {
                        et_code.setEnabled(false);
                        et_code.setText(mObj.getShareCode());
                        tv_draw.setText("领取成功");
                        tv_draw.setEnabled(false);
                    } else {
                        et_code.setEnabled(true);
                    }
                    tv_mycode.setText(mObj.getCode());
                    tv_name.setText(mObj.getRealName());
                    ImageLoader.getInstance().displayImage(mObj.getUserPicCover(), iv_cover, MyApplication.getImageLoaderOptions());
                } else {
                    ToastUtils.showToast(invateInfo.getMsg());
                }
                break;

            case Constans.TYPE_TWO:
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    tv_draw.setText("领取成功");
                    ToastUtils.showToast("成功领取星币");
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
                break;
        }

    }

    @Override
    public void onRequestStar(int what) {
        if (what == Constans.TYPE_TWO) {
            mWaitDialog.show();
        }
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_TWO) {
            mWaitDialog.dismiss();
        }
    }

    @OnClick(R.id.tv_pick)
    public void toInvate(View view) {
        if (mRowsBean != null) {
            mDialog.setData(new ShareEntity("你的好友邀请你加入" + mRowsBean.getRealName() + "的粉丝团",
                    "快来加入吧", HttpContans.ADDRESS_INVATE_LINK + "code="
                    + mRowsBean.getCode() + "&userHeadPortrait=" + mRowsBean.getUserHeadPortrait()
                    + "&realName=" + mRowsBean.getRealName()
                    , mRowsBean.getUserHeadPortrait()));
        } else {
            mDialog.setData(new ShareEntity("你的好友邀请你加入" + mObj.getRealName() + "的粉丝团",
                    "快来加入吧", HttpContans.ADDRESS_INVATE_LINK + "code="
                    + mObj.getCode() + "&userHeadPortrait=" + mObj.getUserHeadPortrait()
                    + "&realName=" + mObj.getRealName()
                    , mObj.getUserHeadPortrait()));
        }
    }

    @OnClick(R.id.tv_change)
    public void toChange(View view) {
        startActivityForResult(new Intent(MyApplication.getInstance(), PickAiDouActivity.class), 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111) {
            mRowsBean = (InvateSerchStar.RowsBean) data.getSerializableExtra("data");
            ImageLoader.getInstance().displayImage(mRowsBean.getUserPicCover(), iv_cover, MyApplication.getImageLoaderOptions());
            tv_name.setText(mRowsBean.getRealName());
        }
    }
}
