package com.cucr.myapplication.activity.picWall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.adapter.RlVAdapter.PicWallAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.user.PicWallCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpacesItemDecoration;
import com.cucr.myapplication.widget.dialog.DialogSort;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.List;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

public class PhotosAlbumActivity extends Activity implements DialogSort.OnClickBt, PicWallAdapter.OnItemClickListener, SwipeRecyclerView.OnLoadListener, RequersCallBackListener {

    @ViewInject(R.id.rlv_picwall)
    private SwipeRecyclerView rlv_picwall;

    private DialogSort mDialog;
    private MyWaitDialog mWaitDialog;
    private PicWallCore mCore;
    private PictureSelectionModel mPictureModel;
    private int mStarId;
    private PicWallAdapter mAdapter;
    private Gson mGson;
    private Intent mIntent;
    private PicWallInfo mInfo;
    private int page;
    private int rows;
    private int orderType;
    private boolean isRefresh;
    private List<LocalMedia> mLocalMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_album);
        ViewUtils.inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        init();
        onRefresh();

    }

    private void init() {
        rows = 15;
        page = 1;
        orderType = 1;   //默认按时间排序
        mIntent = new Intent();
        mCore = new PicWallCore();
        mGson = MyApplication.getGson();
        mDialog = new DialogSort(this, R.style.MyDialogStyle);
        mWaitDialog = new MyWaitDialog(this, R.style.MyWaitDialog);
        mDialog.setOnClickBt(this);
        mStarId = getIntent().getIntExtra("starId", -1);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        rlv_picwall.getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PicWallAdapter();
        mAdapter.setOnItemClickListener(this);
        rlv_picwall.setAdapter(mAdapter);
        rlv_picwall.setOnLoadListener(this);
        rlv_picwall.getRecyclerView().addItemDecoration(new SpacesItemDecoration(CommonUtils.dip2px(MyApplication.getInstance(), 2.5f)));

        //                            .selectionMedia(mData)
// 是否显示拍照按钮 true or false
        mPictureModel = PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(5)
                .imageSpanCount(3)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(false)
                .isGif(false)   //不显示gif
//                            .selectionMedia(mData)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(true)// 是否显示拍照按钮 true or false
                .sizeMultiplier(0.5f)
                .compress(true)
                .cropCompressQuality(90)
                .compressMode(LUBAN_COMPRESS_MODE)
                .previewEggs(true);

    }

    //退出
    @OnClick(R.id.iv_base_back)
    public void back(View view) {
        finish();
    }

    //排序Dialog
    @OnClick(R.id.iv_sort)
    public void sort(View view) {
        mDialog.show();
    }

    //按时间排序
    @Override
    public void clickSortByTime() {
        orderType = 1;
        onRefresh();
    }

    //按点赞量排序
    @Override
    public void clickSortByGoods() {
        orderType = 0;
        onRefresh();
    }

    //选择图片上传
    @OnClick(R.id.iv_upload)
    public void upLoad(View view) {
        mPictureModel.forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mLocalMedia = PictureSelector.obtainMultipleResult(data);
                    mCore.upLoadPic(mStarId, mLocalMedia, this);
                    break;

                case 2:
                    PicWallInfo Info = (PicWallInfo) data.getSerializableExtra("data");
                    mInfo.getRows().clear();
                    mInfo.getRows().addAll(Info.getRows());
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void clickUser(int userId) {
        mIntent.setClass(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mIntent.putExtra("userId", userId);
        startActivity(mIntent);
    }

    @Override
    public void clickItem(int posotion, PicWallInfo.RowsBean rowsBean, ImageView imageView) {
        mIntent.setClass(MyApplication.getInstance(), PicWallCatgoryActivity.class);
        mIntent.putExtra("data", mInfo);
        mIntent.putExtra("posotion", posotion);
        startActivityForResult(mIntent, 2);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        rlv_picwall.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryPic(page, rows, orderType, false, mStarId, this);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        rlv_picwall.onLoadingMore();
        mCore.queryPic(page, rows, orderType, false, mStarId, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        if (what == Constans.TYPE_ONE) {
            PicWallInfo picWallInfo = mGson.fromJson(response.get(), PicWallInfo.class);
            if (picWallInfo.isSuccess()) {
                if (isRefresh) {
                    mAdapter.setData(picWallInfo.getRows());
                    mInfo = picWallInfo;
                } else {
                    mAdapter.addData(picWallInfo.getRows());
                    mInfo.getRows().addAll(picWallInfo.getRows());
                }
                if (picWallInfo.getTotal() <= page * rows) {
                    rlv_picwall.onNoMore("没有更多了");
                } else {
                    rlv_picwall.complete();
                }
            } else {
                ToastUtils.showToast(picWallInfo.getErrorMsg());
            }
        } else if (what == Constans.TYPE_TWO) {
            CommonRebackMsg msg = MyApplication.getGson().fromJson(response.get(), CommonRebackMsg.class);
            if (msg.isSuccess()) {
                ToastUtils.showToast("图集上传成功,可在<我的图集>界面查看审核状态");
                mLocalMedia.clear();
            } else {
                ToastUtils.showToast(msg.getMsg());
            }
        }
    }

    @Override
    public void onRequestStar(int what) {
        if (what == Constans.TYPE_TWO) {
            mWaitDialog.show();
        }
    }

    @Override
    public void onRequestFinish(int what) {
        if (what == Constans.TYPE_ONE) {
            if (rlv_picwall.isRefreshing()) {
                rlv_picwall.getSwipeRefreshLayout().setRefreshing(false);
            }
        } else if (what == Constans.TYPE_TWO) {
            mWaitDialog.dismiss();
        }
    }
}
