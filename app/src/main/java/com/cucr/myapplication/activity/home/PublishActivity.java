package com.cucr.myapplication.activity.home;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.widget.dialog.DialogPublishStyle;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PublishActivity extends Activity {

    private EditText mEt_publish;
    private LinearLayout mLl_et_foot;
    private TextView mTv_publish_send;
    private MyLogger myLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_publish);
        ViewUtils.inject(this);
        myLogger = MyLogger.jLog();

        initView();
    }

    private void initView() {
        mLl_et_foot = (LinearLayout) findViewById(R.id.ll_et_foot);
        mEt_publish = (EditText) findViewById(R.id.et_publish);
        mTv_publish_send = (TextView) findViewById(R.id.tv_publish_send);


        mEt_publish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //获取文本长度  根据长度判断发送按钮的状态
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                initBtSend(length);
            }
        });
        mEt_publish.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight =  getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
//                initFoot(heightDifference);
            }
        });
    }

    private void initBtSend(int length) {
        mTv_publish_send.setEnabled(!(length==0));
    }

    private void initFoot(int h) {
        mLl_et_foot.setVisibility(h==0? View.GONE:View.VISIBLE);
    }

    @OnClick(R.id.iv_publish_back)
    public void backToHome(View view){
        showDialog();
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    public void showDialog() {
        final DialogPublishStyle publishDialog = new DialogPublishStyle(this,R.style.ShowAddressStyleTheme);
        publishDialog.setOnClickConfirm(new DialogPublishStyle.OnClickConfirm() {
            @Override
            public void OnConfirm() {
                publishDialog.dismiss();
                finish();
            }
        });
        publishDialog.setCanceledOnTouchOutside(true);
        publishDialog.show();
    }

}
