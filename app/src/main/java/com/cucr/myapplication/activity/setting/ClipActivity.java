package com.cucr.myapplication.activity.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cucr.myapplication.R;
import com.cucr.myapplication.utils.ImageTools;
import com.cucr.myapplication.widget.clip.ClipImageLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;

/**
 * 裁剪头像的activity
 */
public class ClipActivity extends Activity {

    private ClipImageLayout mClipImageLayout;
    private String path;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipimage);
        ViewUtils.inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);

        //这步必须要加
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setBitmap(bitmap);
        ((TextView) findViewById(R.id.id_action_clip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = mClipImageLayout.clip();
                        String path = Environment.getExternalStorageDirectory() + "/ClipHeadPhoto/cache/" + System.currentTimeMillis() + ".png";
                        new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ClipHeadPhoto/cache/").mkdirs();
                        ImageTools.savePhotoToSDCard(bitmap, path);
                        loadingDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("path", path);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).start();
            }
        });
    }

    @OnClick(R.id.iv_adjust_pic_back)
    public void clickBack(View view) {
        finish();
    }

}
