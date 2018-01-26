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
import com.cucr.myapplication.core.user.PicWallCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.PicWall.PicWallInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.SpacesItemDecoration;
import com.cucr.myapplication.widget.dialog.DialogSort;
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

public class PhotosAlbumActivity extends Activity implements DialogSort.OnClickBt, PicWallAdapter.OnItemClickListener, SwipeRecyclerView.OnLoadListener {

    @ViewInject(R.id.rlv_picwall)
    private SwipeRecyclerView rlv_picwall;

    private DialogSort mDialog;
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


    private void loadData() {
        rlv_picwall.getSwipeRefreshLayout().setRefreshing(true);
        rlv_picwall.getRecyclerView().smoothScrollToPosition(0);
        page = 1;
        mCore.queryPic(page, rows, orderType, false, mStarId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mInfo = mGson.fromJson(response.get(), PicWallInfo.class);
                if (mInfo.isSuccess()) {
                    mAdapter.setData(mInfo.getRows());
                    rlv_picwall.complete();
                    if (mInfo.getRows().size() < rows) {
                        rlv_picwall.onNoMore("");
                    }
                } else {
                    ToastUtils.showToast(mInfo.getErrorMsg());
                }
            }
        });
    }

    private void init() {
        rows = 15;
        page = 1;
        orderType = 1;   //默认按时间排序
        mIntent = new Intent(MyApplication.getInstance(), PersonalMainPagerActivity.class);
        mCore = new PicWallCore();
        mGson = MyApplication.getGson();
        mDialog = new DialogSort(this, R.style.MyDialogStyle);
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
        loadData();
    }

    //按点赞量排序
    @Override
    public void clickSortByGoods() {
        orderType = 0;
        loadData();
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
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    mCore.upLoadPic(mStarId, localMedia, new OnCommonListener() {
                        @Override
                        public void onRequestSuccess(Response<String> response) {
                            CommonRebackMsg msg = MyApplication.getGson().fromJson(response.get(), CommonRebackMsg.class);
                            if (msg.isSuccess()) {
                                ToastUtils.showToast("图集上传成功,请等待审核");
                            } else {
                                ToastUtils.showToast(msg.getMsg());
                            }
                        }
                    });
                    break;

                case 2:
                    PicWallInfo Info = (PicWallInfo) data.getSerializableExtra("data");
                    MyLogger.jLog().i("testInfo2:" + mInfo.getRows().get(3).getGiveUpCount());
                    mInfo.getRows().clear();
                    mInfo.getRows().addAll(Info.getRows());
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    @Override
    public void clickUser(int userId) {
        mIntent.putExtra("userId", userId);
        startActivity(mIntent);
    }

    @Override
    public void clickItem(int posotion, PicWallInfo.RowsBean rowsBean, ImageView imageView) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            imageView.setTransitionName("aaa");
//        }
        Intent intent = new Intent(MyApplication.getInstance(), PicWallCatgoryActivity.class);
        intent.putExtra("data", mInfo);
        intent.putExtra("posotion", posotion);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivity(intent,
//                    ActivityOptions.makeSceneTransitionAnimation(this, imageView, "aaa").toBundle());
//        } else {

        startActivityForResult(intent, 2);
//        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {

        page++;
        rlv_picwall.onLoadingMore();
        mCore.queryPic(page, rows, 1, false, mStarId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                PicWallInfo picWallInfo = mGson.fromJson(response.get(), PicWallInfo.class);
                if (picWallInfo.isSuccess()) {
                    MyLogger.jLog().i("picWallInfosize:" + picWallInfo.getRows().size());
                    rlv_picwall.complete();
                    mAdapter.addData(picWallInfo.getRows());
                    mInfo.getRows().addAll(picWallInfo.getRows());
                    if (picWallInfo.getTotal() < rows * page) {
                        rlv_picwall.onNoMore("");
                    }
                } else {
                    ToastUtils.showToast(picWallInfo.getErrorMsg());
                }
            }
        });
    }

}
