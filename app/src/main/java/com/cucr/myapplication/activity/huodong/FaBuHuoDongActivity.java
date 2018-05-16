package com.cucr.myapplication.activity.huodong;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
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
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.CommentEvent;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.setting.BirthdayDate;
import com.cucr.myapplication.core.fuLi.HuoDongCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.GetPathFromUri4kitkat;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogBirthdayStyle;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;
import com.zcw.togglebutton.ToggleButton;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissonItem;

import static android.content.ContentValues.TAG;

public class FaBuHuoDongActivity extends FragmentActivity implements ToggleButton.OnToggleChanged {

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg_fb_huodong)
    private FrameLayout fl_pop_bg_fb_huodong;

    //圆角图片
    @ViewInject(R.id.img_fabu_huodong_pic)
    private ImageView img_fabu_huodong_pic;

    //详细地区
    @ViewInject(R.id.et_local_catgory)
    private EditText et_local_catgory;

    //活动名称
    @ViewInject(R.id.et_active_name)
    private EditText et_active_name;

    //活动内容
    @ViewInject(R.id.et_active_content)
    private EditText et_active_content;

    //活动时间
    @ViewInject(R.id.tv_time_star)
    private TextView tv_time_star;

    //活动价格
    @ViewInject(R.id.et_money)
    private TextView et_money;

    //活动人数
    @ViewInject(R.id.et_peoples)
    private TextView et_peoples;

    //费用公开
    @ViewInject(R.id.toggle_show_price_tip)
    private ToggleButton toggle;

    private int isOpen;
    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private TextView photograph, albums;
    private LinearLayout cancel;
    private Gson mGson;
    public static final int PHOTOZOOM = 0; // 相册/拍照
    public static final int PHOTOTAKE = 1; // 相册/拍照
    private String photoSavePath;//保存路径
    private String photoSaveName;//图pian名
    private String path;//图片全路径
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private HuoDongCore mHuoDongCore;
    private Intent openCameraIntent;
    private List<PermissonItem> permissonItems;
    private DialogBirthdayStyle mBirthdayStyle;
    private MyWaitDialog mWaitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu_huo_dong);
        //设置状态栏字体颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.white), 0);
        ViewUtils.inject(this);
        initChild();
    }

    protected void initChild() {
        initDateDialog();
        isOpen = 1;
        permissonItems = new ArrayList<>();
        permissonItems.add(new PermissonItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        mHuoDongCore = new HuoDongCore();
        mGson = MyApplication.getGson();
        initHead();
        toggle.setOnToggleChanged(this);
    }

    private String mYear;
    private String mMon;
    private String mDay;

    private void initDateDialog() {

        String dtaeInfo = CommonUtils.getCurrentDate();
        mYear = dtaeInfo.substring(0, 4).trim();
        mMon = dtaeInfo.substring(5, 7).trim();
        mDay = dtaeInfo.substring(8, 10).trim();
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mBirthdayStyle = new DialogBirthdayStyle(this, R.style.BirthdayStyleTheme, false);
        //初始化日期数据
        mBirthdayStyle.initDate(Integer.parseInt(mYear), Integer.parseInt(mMon), Integer.parseInt(mDay));
        //点击对话框外部消失
        mBirthdayStyle.setCanceledOnTouchOutside(true);
        mBirthdayStyle.setOnDialogBtClick(new DialogBirthdayStyle.onDialogBtClick() {
            @Override
            public void onClickComplete(BirthdayDate date, boolean isChange) {
                //如果用户改了生日就显示改了的  如果没改就显示原来的
                if (isChange) {
                    mYear = date.getYear() + "";
                    mMon = (date.getMonth() + 1) + "";
                    mDay = date.getDay() + "";
                    String i = Integer.parseInt(mMon) + "";
                    if (!i.startsWith("0") && i.length() == 1) {
                        i = "0" + i;
                    }
                    tv_time_star.setText(mYear + "-" + i + "-" + mDay);
                } else {
                    String i = Integer.parseInt(mMon) + 1 + "";
                    if (!i.startsWith("0") && i.length() == 1) {
                        i = "0" + i;
                    }
                    tv_time_star.setText(mYear + "-" + i + "-" + mDay);
                }
            }
        });
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
                openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
                openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                HiPermission.create(FaBuHuoDongActivity.this)
                        .title("小主")
                        .permissions(permissonItems)
                        .filterColor(ResourcesCompat.getColor(getResources(), R.color.xtred, getTheme()))//图标的颜色
                        .msg("这要用到相机哦")
                        .style(R.style.PermissionBlueStyle)
                        .permissions(permissonItems)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "用户关闭权限申请");
                            }

                            @Override
                            public void onFinish() {
                                startActivityForResult(openCameraIntent, PHOTOTAKE);
                            }

                            @Override
                            public void onDeny(String permisson, int position) {
                                Log.i(TAG, "onDeny");
                            }

                            @Override
                            public void onGuarantee(String permisson, int position) {
                                Log.i(TAG, "onGuarantee");
                            }
                        });

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
//                String[] proj = {MediaStore.Images.Media.DATA};
//                Cursor cursor = managedQuery(uri, proj, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                path = cursor.getString(column_index);// 图片在的路径
                path = GetPathFromUri4kitkat.getPath(MyApplication.getInstance(), uri);
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

    //点击活动时间
    @OnClick(R.id.ll_time)
    public void clickTime(View view) {
       /* new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .setMinDate(new Date(System.currentTimeMillis()))
                //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_LIGHT)
                .setIndicatorColor(getResources().getColor(R.color.xtred))
                .build()
                .show();*/
        //显示日期对话框
        mBirthdayStyle.show();
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            String timeDate = mFormatter.format(date);
            tv_time_star.setText(timeDate);
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
//            Toast.makeText(YuYueCatgoryActivity.this,
//                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    //返回
    @OnClick(R.id.iv_base_back)
    public void clickBack(View view) {
        finish();
    }


    //点击活动价格
    @OnClick(R.id.ll_price)
    public void clickMoney(View view) {
        et_money.setFocusable(true);
        et_money.setFocusableInTouchMode(true);
        et_money.requestFocus();
        CommonUtils.hideKeyBorad(MyApplication.getInstance(), et_money, false);
    }

    //点击活动人数
    @OnClick(R.id.ll_peoples)
    public void clickPeoples(View view) {
        et_peoples.setFocusable(true);
        et_peoples.setFocusableInTouchMode(true);
        et_peoples.requestFocus();
        CommonUtils.hideKeyBorad(MyApplication.getInstance(), et_peoples, false);
    }

    @Override
    public void onToggle(boolean on) {
        isOpen = on ? 0 : 1;
    }

    //点击提交
    @OnClick(R.id.tv_commit)
    public void clickCommit(View view) {
        String active_name = et_active_name.getText().toString();
        String local_catgory = et_local_catgory.getText().toString();
        String time = tv_time_star.getText().toString();
        String money = et_money.getText().toString();
        String active_content = et_active_content.getText().toString();
        String active_peoples = et_peoples.getText().toString();

        boolean empty = CommonUtils.isEmpty(active_name, local_catgory, time, money, active_content
                , active_peoples, path
        );
        if (empty) {
            ToastUtils.showToast("请完善信息");
            return;
        }
        mHuoDongCore.publishActive(active_name, local_catgory, time,
                Integer.parseInt(money), active_content, isOpen, Integer.parseInt(active_peoples),
                path, new RequersCallBackListener() {
                    @Override
                    public void onRequestSuccess(int what, Response<String> response) {
                        ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                        if (reBackMsg.isSuccess()) {
                            ToastUtils.showToast("发布成功");
                            EventBus.getDefault().post(new CommentEvent(998));
                            finish();
                        } else {
                            ToastUtils.showToast(reBackMsg.getMsg());
                        }
                    }

                    @Override
                    public void onRequestStar(int what) {
                        mWaitDialog.show();
                    }

                    @Override
                    public void onRequestError(int what, Response<String> response) {

                    }

                    @Override
                    public void onRequestFinish(int what) {
                        mWaitDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHuoDongCore.stop();
    }
}
