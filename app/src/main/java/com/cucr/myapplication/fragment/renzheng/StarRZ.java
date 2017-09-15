package com.cucr.myapplication.fragment.renzheng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.renZheng.CommitStarRzCore;
import com.cucr.myapplication.core.renZheng.QueryRzResult;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.RZ.RzResult;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.picture.MyLoader;
import com.google.gson.Gson;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 911 on 2017/6/16.
 */

public class StarRZ extends Fragment {
    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private TextView photograph, albums;
    private LinearLayout cancel;
    public static final int REQUEST_CODE = 123;

    public static final int PHOTOZOOM = 0; // 相册/拍照
    public static final int PHOTOTAKE = 1; // 相册/拍照
    public static final int IMAGE_COMPLETE = 2; // 结果
    public static final int CROPREQCODE = 3; // 截取
    private String photoSavePath;//保存路径
    private String photoSaveName;//图pian名
    private String path;//图片全路径
    private CommitStarRzCore mCore;
    private QueryRzResult mQueryCore;
    private Context mContext;
    private Activity activity;
    private Gson mGson;
    private ImageConfig imageConfig;
    private ImageLoader instance;
    private Integer dataId;

    //身份证正面路径
    private String img_postive_path;
    //身份证反面路径
    private String img_nagetive_path;

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg)
    FrameLayout fl_pop_bg;

    //正面
    @ViewInject(R.id.img_star_positive)
    ImageView iv_add_pic_positive;

    //提交
    @ViewInject(R.id.tv_commit_check)
    TextView tv_commit_check;

    //反面
    @ViewInject(R.id.img_star_nagetive)
    ImageView iv_add_pic_nagetive;

    //姓名
    @ViewInject(R.id.et_name)
    EditText et_name;

    //失败原因
    @ViewInject(R.id.tv_fail_info)
    TextView tv_fail_info;

    //失败标题
    @ViewInject(R.id.tv_fail_title)
    TextView tv_fail_title;

    //联系方式
    @ViewInject(R.id.et_contact)
    EditText et_contact;

    //所属公司
    @ViewInject(R.id.et_belone)
    EditText et_belone;

    //明星商演费用
    @ViewInject(R.id.et_star_price)
    EditText et_star_price;

    //身份证反面
    @ViewInject(R.id.rl_star_shenfenzheng_negative)
    RelativeLayout rl_star_shenfenzheng_negative;

    //身份证正面
    @ViewInject(R.id.rl_star_shenfenzheng_positive)
    RelativeLayout rl_star_shenfenzheng_positive;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        mContext = container.getContext();
        mGson = new Gson();
        mCore = new CommitStarRzCore(activity);
        mQueryCore = new QueryRzResult(activity);
        View rootView = inflater.inflate(R.layout.fragment_ren_zheng_star, container, false);
        ViewUtils.inject(this, rootView);
        initView();
        initHead();
        return rootView;

    }

    private void initView() {
        mQueryCore.queryRz(Constans.RZ_STAR, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyLogger.jLog().i("来自缓存："+response.isFromCache()+"，明星认证："+response.get());
                RzResult rzResult = mGson.fromJson(response.get(), RzResult.class);
                if (rzResult.isSuccess()) {
                    RzResult.ObjBean obj = rzResult.getObj();
                    //如果信息为空 说明未提交过审核 直接返回
                    if (obj == null) {
                        return;
                    }
                    String getUserName = obj.getUserName();  //姓名
                    String contact = obj.getContact();  //联系方式
                    String belongCompany = obj.getBelongCompany();  //所属公司
                    int result = obj.getResult();   //审核结果
                    int startCost = obj.getStartCost(); //商演费用
                    String pic1 = obj.getPic1();
                    String info = obj.getInfo();
                    dataId = obj.getId();
                    String pic2 = obj.getPic2();
                    backShow(getUserName, contact, belongCompany, startCost, result, pic1, pic2, info);
                } else {
                    ToastUtils.showToast(mContext, rzResult.getMsg() + "");
                }
            }
        });
    }


    //回显数据   result 0待审  1没通过  2通过
    private void backShow(String auditor, String contact, String belongCompany, int startCost, int result, String pic1, String pic2, String info) {
        instance = ImageLoader.getInstance();
        et_name.setText(auditor);
        et_contact.setText(contact);
        et_belone.setText(belongCompany);
        et_star_price.setText("" + startCost);
        iv_add_pic_positive.setVisibility(View.VISIBLE);
        iv_add_pic_nagetive.setVisibility(View.VISIBLE);
        instance.displayImage(HttpContans.HTTP_HOST + pic1, iv_add_pic_positive);
        instance.displayImage(HttpContans.HTTP_HOST + pic2, iv_add_pic_nagetive);
        //控件不可用
        setView(false, "审核中");

        switch (result) {
//            case 0:
//                break;

            case 1:
                //"1"代表用户审核未通过 且未重新选择上传照片
                img_postive_path = "1";
                img_nagetive_path = "1";
                setView(true, "审核未通过，点我重新提交");
                tv_fail_info.setText(info);

                break;

            case 2:
                tv_commit_check.setText("审核通过，重新登录账号才有效哦");
                break;
        }
    }


    private void initHead() {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        File file = new File(Environment.getExternalStorageDirectory(), "StarAttestation/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/StarAttestation/cache/";
        photoSaveName = System.currentTimeMillis() + ".png";
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

        //点击拍照
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
            public void onClick(View v) {
                //点击去相册
                imageConfig = new ImageConfig.Builder(
                        new MyLoader())
                        .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                        .titleBgColor(getResources().getColor(R.color.titleBlue))
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启单选   （默认为多选）
                        .singleSelect()
                        // 裁剪 (只有单选可裁剪)
//                        .crop(1, 2, 500, 1000)
                        // 开启拍照功能 （默认关闭）
                        .showCamera()
                        //设置显示容器
//                        .setContainer(llContainer)
                        .requestCode(REQUEST_CODE)
                        .build();

                ImageSelector.open(StarRZ.this, imageConfig);   // 开启图片选择器
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
                CommonUtils.initPopBg(false, fl_pop_bg);
            }
        });
    }

    /**
     * 图片选择及拍照结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MyLogger.jLog().i("onActivityResult");
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = null;
        switch (requestCode) {
            case REQUEST_CODE:
                if (data != null) {
                    if (popWindow.isShowing()) {
                        popWindow.dismiss();
                        fl_pop_bg.setVisibility(View.GONE);
                    }
                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    //当不可见时在显示
                    if (whichView.getVisibility() == View.GONE) {
                        whichView.setVisibility(View.VISIBLE);
                    }
                    //用缩略图显示
                    String s = pathList.get(0);
                    whichView.setImageBitmap(CommonUtils.decodeBitmap(s));
                    //如果是正面
                    if (whichView == iv_add_pic_positive) {
                        img_postive_path = s;

                    } else {
                        img_nagetive_path = s;
                    }
                    break;
                }


            case PHOTOTAKE://拍照
                path = photoSavePath + photoSaveName;
                uri = Uri.fromFile(new File(path));
                //当不可见时在显示
                if (whichView.getVisibility() == View.GONE) {
                    whichView.setVisibility(View.VISIBLE);
                }
                //用缩略图显示
                whichView.setImageBitmap(CommonUtils.decodeBitmap(path));
                //如果是正面
                if (whichView == iv_add_pic_positive) {
                    img_postive_path = path;
                } else {
                    img_nagetive_path = path;
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private ImageView whichView;

    //身份证正面
    @OnClick(R.id.rl_star_shenfenzheng_positive)
    public void positive(View view) {
        whichView = iv_add_pic_positive;
        MyLogger.jLog().i("whichView = iv_add_pic_positive;");
        CommonUtils.initPopBg(true, fl_pop_bg);
        showPopupWindow(iv_add_pic_positive);

    }

    //身份证反面
    @OnClick(R.id.rl_star_shenfenzheng_negative)
    public void negative(View view) {
        whichView = iv_add_pic_nagetive;
        MyLogger.jLog().i("whichView = iv_add_pic_nagetive;");
        CommonUtils.initPopBg(true, fl_pop_bg);
        showPopupWindow(iv_add_pic_nagetive);
    }

    //提交审核
    @OnClick(R.id.tv_commit_check)
    public void commitCheck(View view) {

        if (TextUtils.isEmpty(et_name.getText())) {
            ToastUtils.showToast(activity, "请输入姓名哦");
            return;
        }

        if (TextUtils.isEmpty(et_contact.getText())) {
            ToastUtils.showToast(activity, "请输入联系方式哦");
            return;
        }

        if (TextUtils.isEmpty(et_belone.getText())) {
            ToastUtils.showToast(activity, "请输入所属公司哦");
            return;
        }

        if (TextUtils.isEmpty(et_star_price.getText())) {
            ToastUtils.showToast(activity, "请输入商演费用哦");
            return;
        }

        if (TextUtils.isEmpty(img_postive_path) || TextUtils.isEmpty(img_nagetive_path)) {
            ToastUtils.showToast(activity, "请上传照片哦");
            return;
        }

        mCore.onCommStarRZ(et_name.getText().toString(),
                et_belone.getText().toString(),
                et_contact.getText().toString(),
                et_star_price.getText().toString(),
                img_postive_path, img_nagetive_path,
                dataId,
                new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                        if (reBackMsg.isSuccess()) {
                            ToastUtils.showToast(activity, "明星认证上传成功！");
                            setView(false, "审核中");
                        } else {
                            ToastUtils.showToast(activity, reBackMsg.getMsg());
                        }
                    }


                });

    }

    //提交认证后 各个控件的初始化话
    private void setView(boolean enable, String text) {
        et_name.setEnabled(enable);
        et_contact.setEnabled(enable);
        et_belone.setEnabled(enable);
        tv_commit_check.setEnabled(enable);
        tv_commit_check.setText(text);
        et_star_price.setEnabled(enable);
        rl_star_shenfenzheng_negative.setEnabled(enable);
        rl_star_shenfenzheng_positive.setEnabled(enable);

        if (enable) {
            tv_fail_info.setVisibility(View.VISIBLE);
            tv_fail_title.setVisibility(View.VISIBLE);
        } else {
            tv_fail_info.setVisibility(View.GONE);
            tv_fail_title.setVisibility(View.GONE);
        }
    }

    //生命周期方法绑定
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCore.stopReques();
    }
}
