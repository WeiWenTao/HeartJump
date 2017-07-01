package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaShangStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class FenTuanCatgoryActiviry extends Activity {

    @ViewInject(R.id.lv_ft_catgory)
    ListView lv_ft_catgory;

    @ViewInject(R.id.head)
    RelativeLayout head;
    private DialogDaShangStyle mDialogDaShangStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_tuan_catgory_activiry);
        ViewUtils.inject(this);
        initHead();
        initLV();
    }

    private void initLV() {
        mDialogDaShangStyle = new DialogDaShangStyle(this, R.style.ShowAddressStyleTheme);
        mDialogDaShangStyle.setCanceledOnTouchOutside(true);
        mDialogDaShangStyle.setConfirmListener(new DialogDaShangStyle.ClickconfirmListener() {
            @Override
            public void onClickConfirm(int howMuch) {
                if (howMuch != 0){
                    ToastUtils.showToast(FenTuanCatgoryActiviry.this,howMuch+""+" 星币");
                }
                mDialogDaShangStyle.dismiss();
            }
        });

        View lvHead = View.inflate(this, R.layout.item_ft_catgory_header, null);
        lvHead.findViewById(R.id.tv_dashang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDaShangStyle.show();
            }
        });
        lv_ft_catgory.addHeaderView(lvHead,null,true);
        lv_ft_catgory.setHeaderDividersEnabled(false);

        lv_ft_catgory.setAdapter(new FtCatgoryAadapter(this));
    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this,73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //返回
    @OnClick(R.id.iv_ft_catgory_back)
    public void back(View view){
        finish();
    }
}
