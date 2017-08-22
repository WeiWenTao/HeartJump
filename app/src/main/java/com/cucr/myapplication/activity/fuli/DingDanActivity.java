package com.cucr.myapplication.activity.fuli;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.core.shangPing.DingDanCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.login.LoadUserInfo;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDingDanStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

public class DingDanActivity extends BaseActivity implements TextWatcher {

    //兑换规则
    @ViewInject(R.id.iv_rule)
    private ImageView iv_rule;

    //确定兑换
    @ViewInject(R.id.tv_perform_duihuan)
    private TextView tv_perform_duihuan;

    //数量显示框
    @ViewInject(R.id.tv_show_goods_num)
    private TextView tv_show_goods_num;

    //收件人
    @ViewInject(R.id.et_receive_person_name)
    private EditText et_receive_person_name;

    //收件人电话
    @ViewInject(R.id.et_receive_person_num)
    private EditText et_receive_person_num;

    //收件人大概地址
    @ViewInject(R.id.tv_receive_person_local)
    private TextView tv_receive_person_local;

    //收件人详细地址
    @ViewInject(R.id.et_receive_person_local_catgory)
    private EditText et_receive_person_local_catgory;

    //兑换规则对话框
    private DialogDingDanStyle mDialogDingDanStyle;

    //定义一个变量记录商品数量;
    private int goodsNum;
    private DingDanCore mCore;
    private int mNum;
    private String mReceive_person;
    private String mReceive_num;
    private String mReceived_local;
    private String mReceived_address;
    private int mShopId;

    @Override
    protected void initChild() {
        mDialogDingDanStyle = new DialogDingDanStyle(this, R.style.BirthdayStyleTheme);
        mCore = new DingDanCore(this);

        et_receive_person_name.addTextChangedListener(this);
        et_receive_person_local_catgory.addTextChangedListener(this);
        tv_receive_person_local.addTextChangedListener(this);
        et_receive_person_num.addTextChangedListener(this);


        //商品数量
        mNum = Integer.parseInt(tv_show_goods_num.getText().toString().trim());
        //收件人
        mReceive_person = et_receive_person_name.getText().toString();
        //收件人电话
        mReceive_num = et_receive_person_num.getText().toString();
        //收件人地址
        mReceived_local = tv_receive_person_local.getText().toString();
        //详细地址
        mReceived_address = et_receive_person_local_catgory.getText().toString();
        //商品id  由上个页面跳转获得
        mShopId = 2;

    }

    //兑换规则
    @OnClick(R.id.iv_rule)
    public void clickShow(View view) {
        mDialogDingDanStyle.show();
    }


    //添加商品数量
    @OnClick(R.id.iv_goods_add)
    public void addGoods(View view) {
        goodsNum++;
        tv_show_goods_num.setText(goodsNum + "");
    }

    //删减商品数量
    @OnClick(R.id.iv_goods_subtract)
    public void addSubtract(View view) {
        if (goodsNum > 0) {
            goodsNum--;
        }
        tv_show_goods_num.setText(goodsNum + "");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ding_dan;
    }


    //确定兑换
    @OnClick(R.id.tv_perform_duihuan)
    public void performDuihuan(View view) {
        mCore.onDuiHuan(this, mReceived_local, mReceived_address, mReceive_person, mReceive_num, mNum, mShopId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                LoadUserInfo loadUserInfo = mGson.fromJson(response.get(), LoadUserInfo.class);
                if (loadUserInfo.isSuccess()){
                    MyLogger.jLog().i("订单提交成功");
                }else {
                    ToastUtils.showToast(DingDanActivity.this,loadUserInfo.getMsg());
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mNum > 0 && !TextUtils.isEmpty(mReceive_person) && !TextUtils.isEmpty(mReceive_num) && !TextUtils.isEmpty(mReceived_local) && !TextUtils.isEmpty(mReceived_address)){
            tv_perform_duihuan.setEnabled(true);
        }else {
            tv_perform_duihuan.setEnabled(false);
        }
    }
}
