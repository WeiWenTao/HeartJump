package com.cucr.myapplication.activity.fuli;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.widget.dialog.DialogDingDanStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DingDanActivity extends BaseActivity {

    //兑换规则
    @ViewInject(R.id.iv_rule)
    private ImageView iv_rule;

    //数量显示框
    @ViewInject(R.id.tv_show_goods_num)
    private TextView tv_show_goods_num;

    //兑换规则对话框
    private DialogDingDanStyle mDialogDingDanStyle;

    //定义一个变量记录商品数量;
    private int goodsNum;

    @Override
    protected void initChild() {
        mDialogDingDanStyle = new DialogDingDanStyle(this,R.style.BirthdayStyleTheme);
    }

    //兑换规则
    @OnClick(R.id.iv_rule)
    public void clickShow(View view){
        mDialogDingDanStyle.show();
    }


    //添加商品数量
    @OnClick(R.id.iv_goods_add)
    public void addGoods(View view){
        goodsNum++;
        tv_show_goods_num.setText(goodsNum+"");
    }

    //删减商品数量
    @OnClick(R.id.iv_goods_subtract)
    public void addSubtract(View view){
        if (goodsNum>0){
            goodsNum--;
        }
        tv_show_goods_num.setText(goodsNum+"");
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ding_dan;
    }
}
