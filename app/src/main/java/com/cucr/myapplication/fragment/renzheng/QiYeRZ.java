package com.cucr.myapplication.fragment.renzheng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.core.renZheng.CommitQiYeRzCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.login.ReBackMsg;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.picture.MyLoader;
import com.google.gson.Gson;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.cucr.myapplication.fragment.renzheng.StarRZ.REQUEST_CODE;

/**
 * Created by 911 on 2017/6/16.
 */

public class QiYeRZ extends Fragment {

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
    private CommitQiYeRzCore mCore;

    private Context mContext;
    private Activity activity;
    private Gson mGson;
    private ImageConfig imageConfig;

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg_qiye)
    FrameLayout fl_pop_bg_qiye;

    //正面
    @ViewInject(R.id.img_qiye_positive)
    ImageView img_qiye_positive;

    //反面
    @ViewInject(R.id.img_qiye_nagetive)
    ImageView img_qiye_nagetive;

    //营业执照
    @ViewInject(R.id.img_qieye_zhizhao)
    ImageView img_qieye_zhizhao;

    //企业名称
    @ViewInject(R.id.et_qiye_name)
    EditText et_qiye_name;

    //企业联系方式
    @ViewInject(R.id.et_qiye_contact)
    EditText et_qiye_contact;

    //认证人姓名
    @ViewInject(R.id.et_person_name)
    EditText et_person_name;

    //认证人联系方式
    @ViewInject(R.id.et_person_phone)
    EditText et_person_phone;


    //营业执照
    private String licenseBitmap;

    //身份证正面
    private String positiveBitmap;

    //身份证反面
    private String nagetiveBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        mCore = new CommitQiYeRzCore(activity);
        mContext = container.getContext();
        mGson = new Gson();
        View rootView = inflater.inflate(R.layout.fragment_ren_zheng_qiye, container, false);
        ViewUtils.inject(this, rootView);
        initHead();
        return rootView;

    }

    private void initHead() {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        File file = new File(Environment.getExternalStorageDirectory(), "QiYeAttestation/cache");
        if (!file.exists())
            file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/QiYeAttestation/cache/";
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

        //拍照
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

        //相册

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

                ImageSelector.open(QiYeRZ.this, imageConfig);   // 开启图片选择器
            }
        });


        //取消
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
                CommonUtils.initPopBg(false, fl_pop_bg_qiye);
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
            case REQUEST_CODE:
                if (data != null) {
                    if (popWindow.isShowing()) {
                        popWindow.dismiss();
//                        fl_pop_bg_qiye.setVisibility(View.GONE);
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
                    if (whichView == img_qiye_positive) {
                        positiveBitmap = s;
                        //反面
                    } else if (whichView == img_qiye_nagetive) {
                        nagetiveBitmap = s;

                        //营业执照
                    } else {
                        licenseBitmap = s;
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
                Bitmap bmByCrame = CommonUtils.decodeBitmap(path);
                whichView.setImageBitmap(bmByCrame);

                //如果是正面
                if (whichView == img_qiye_positive) {
                    positiveBitmap = path;

                    //反面
                } else if (whichView == img_qiye_nagetive) {
                    nagetiveBitmap = path;

                    //营业执照
                } else {
                    licenseBitmap = path;
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private ImageView whichView;

    //身份证正面
    @OnClick(R.id.rl_sfz_positive)
    public void positive(View view) {
        whichView = img_qiye_positive;
        CommonUtils.initPopBg(true, fl_pop_bg_qiye);
        showPopupWindow(img_qiye_positive);
    }

    //身份证反面
    @OnClick(R.id.rl_sfz_nagetive)
    public void negative(View view) {
        whichView = img_qiye_nagetive;
        CommonUtils.initPopBg(true, fl_pop_bg_qiye);
        showPopupWindow(img_qiye_nagetive);
    }

    //营业执照
    @OnClick(R.id.rl_yinyezhizhao)
    public void yinYeZhiZhao(View view) {
        whichView = img_qieye_zhizhao;
        CommonUtils.initPopBg(true, fl_pop_bg_qiye);
        showPopupWindow(img_qieye_zhizhao);
    }

    //提交审核
    @OnClick(R.id.tv_commit_check)
    public void commitCheck(View view) {
        if (TextUtils.isEmpty(et_qiye_name.getText())) {
            ToastUtils.showToast(activity, "请输入企业名称哦");
            return;
        }

        if (TextUtils.isEmpty(et_qiye_contact.getText())) {
            ToastUtils.showToast(activity, "请输入企业联系方式哦");
            return;
        }

        if (TextUtils.isEmpty(et_person_name.getText())) {
            ToastUtils.showToast(activity, "请输入认证人姓名哦");
            return;
        }
        if (TextUtils.isEmpty(et_person_phone.getText())) {
            ToastUtils.showToast(activity, "请输入认证人联系方式哦");
            return;
        }

        if (licenseBitmap == null || positiveBitmap == null || nagetiveBitmap == null) {
            ToastUtils.showToast(activity, "请上传照片哦");
            return;
        }

        mCore.onCommStarRZ(et_qiye_name.getText().toString(), et_person_name.getText().toString(),
                et_person_phone.getText().toString(), et_qiye_contact.getText().toString(),
                positiveBitmap, nagetiveBitmap, licenseBitmap, new OnCommonListener() {
                    @Override
                    public void onRequestSuccess(Response<String> response) {
                        ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                        if (reBackMsg.isSuccess()) {
                            ToastUtils.showToast(activity, "企业认证提交成功");
                        } else {
                            ToastUtils.showToast(activity, reBackMsg.getMsg());
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCore.stopReques();
    }
}
