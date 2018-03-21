package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.ImgPagerAdapter;
import com.cucr.myapplication.bean.fenTuan.QueryFtInfos;
import com.cucr.myapplication.bean.fenTuan.SignleFtInfo;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.List;

public class ImagePagerActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    private TextView mTv_marks;
    private List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList;
    private List<SignleFtInfo.ObjBean.AttrFileListBean> attrFileList_sign;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        initData();
    }

    private void initData() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(Color.BLACK, 100);
        boolean formCatgory = getIntent().getBooleanExtra("formCatgory", false);
        if (formCatgory) {
            attrFileList_sign = (List<SignleFtInfo.ObjBean.AttrFileListBean>) getIntent().getSerializableExtra("imgs");
        } else {
            attrFileList = (List<QueryFtInfos.RowsBean.AttrFileListBean>) getIntent().getSerializableExtra("imgs");
        }
        mPosition = getIntent().getIntExtra("position", -1);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTv_marks = (TextView) findViewById(R.id.marks);
        mPager.addOnPageChangeListener(this);
        mPager.setAdapter(new ImgPagerAdapter(this, attrFileList));
        mPager.setCurrentItem(mPosition);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTv_marks.setText(position + 1 + "/" + attrFileList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
