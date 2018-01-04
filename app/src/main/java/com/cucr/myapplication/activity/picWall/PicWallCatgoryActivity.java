package com.cucr.myapplication.activity.picWall;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.PicWallCatgoryAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.PicWall.PicWallInfo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.zackratos.ultimatebar.UltimateBar;

public class PicWallCatgoryActivity extends Activity {

    @ViewInject(R.id.rlv_pics)
    RecyclerView rlv_pics;

    @ViewInject(R.id.tv_goodnum)
    TextView tv_goodnum;

    @ViewInject(R.id.iv_user_head)
    ImageView iv_user_head;

    @ViewInject(R.id.tv_username)
    TextView tv_username;

    private int prePosition;
    private PicWallInfo mData;
    private int mPosotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_wall_catgory);
        ViewUtils.inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        init();
        upData();
    }

    private void init() {
        mData = (PicWallInfo) getIntent().getSerializableExtra("data");
        mPosotion = getIntent().getIntExtra("posotion", 0);
        rlv_pics.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance(), LinearLayoutManager.HORIZONTAL, false));
        rlv_pics.setAdapter(new PicWallCatgoryAdapter(mData));
        rlv_pics.scrollToPosition(mPosotion);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rlv_pics);
        rlv_pics.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    if (position != -1 && prePosition != position) {
                        upData();
                        prePosition = position;
                    }
                }
            }
        });
    }

    //页面滑动了 更新数据
    private void upData() {
        PicWallInfo.RowsBean rowsBean = mData.getRows().get(mPosotion);
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getUser()
                .getUserHeadPortrait(), iv_user_head, MyApplication.getImageLoaderOptions());
        tv_goodnum.setText(rowsBean.getGiveUpCount()+"");
        tv_username.setText(rowsBean.getUser().getName());
    }


    @OnClick(R.id.iv_back)
    public void back(View view) {
        finish();
    }
}
