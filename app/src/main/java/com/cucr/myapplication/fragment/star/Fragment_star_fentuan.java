package com.cucr.myapplication.fragment.star;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.FenTuanCatgoryActiviry;
import com.cucr.myapplication.activity.fenTuan.PublishActivity;
import com.cucr.myapplication.adapter.RlVAdapter.FtAdapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.funTuan.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.Serializable;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

import static android.app.Activity.RESULT_OK;
import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_fentuan extends Fragment implements View.OnClickListener, SwipeRecyclerView.OnLoadListener, FtAdapter.OnClickBt {

    private View view;
    private Context mContext;
    private FloatingActionsMenu mFam;
    private FloatingActionButton action_a, action_b;
    private QueryFtInfoCore queryCore;
    private Gson mGson;
    // TODO: 2017/9/22 eventBus 获取
    private int starId = 29;
    private int page = 1;
    private int rows = 5;
    private SwipeRecyclerView rlv_fentuan;  //这不是RecyclerView  而是RecyclerView + swipeRefreshLayout
    private QueryFtInfos mQueryFtInfos;
    private QueryFtInfos mQueryFtInfoss;
    private FtAdapter mAdapter;
    private Integer giveNum;
    private int position = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyLogger.jLog().i("onCreateView");
        mContext = container.getContext();
        queryCore = new QueryFtInfoCore(mContext);
        mGson = new Gson();
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_other_fans_fentuan, container, false);
            initView();
            initRlV();
        }
        queryFtInfo();
        return view;
    }


    private void queryFtInfo() {
        MyLogger.jLog().i("queryFtInfo");
        queryCore.queryFtInfo(starId, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfos = mGson.fromJson(response.get(), QueryFtInfos.class);
                if (mQueryFtInfos.isSuccess()) {
                    mAdapter.setData(mQueryFtInfos);
                } else {
                    ToastUtils.showToast(mQueryFtInfos.getErrorMsg());
                }
            }
        });
    }

    private void initRlV() {
        MyLogger.jLog().i("initRlV");
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        rlv_fentuan.getRecyclerView().setLayoutManager(layout);
        mAdapter = new FtAdapter(mContext);
        rlv_fentuan.setAdapter(mAdapter);
        mAdapter.setOnClickBt(this);
//        TextView textView = new TextView(mContext);
//        textView.setText("empty view");
//        rlv_fentuan.setEmptyView(textView);

    }

    private void initView() {
        MyLogger.jLog().i("initView");
//        rlv_fentuan = (RecyclerView) view.findViewById(R.id.rlv_fentuan);
//        rlv_fentuan.setItemAnimator(new DefaultItemAnimator());

        rlv_fentuan = (SwipeRecyclerView) view.findViewById(R.id.rlv_fentuan);
        rlv_fentuan.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        rlv_fentuan.setOnLoadListener(this);

        mFam = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        action_a = (FloatingActionButton) view.findViewById(R.id.action_a);
        action_b = (FloatingActionButton) view.findViewById(R.id.action_b);
        mFam.attachToRecyclerView(rlv_fentuan.getRecyclerView());
        action_a.setOnClickListener(this);
        action_b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mFam.collapse();
        switch (v.getId()) {
            //照片
            case R.id.action_a:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(9)
                        .imageSpanCount(4)
                        .selectionMode(PictureConfig.MULTIPLE)
                        .previewImage(false)
                        .compressGrade(Luban.THIRD_GEAR)
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .sizeMultiplier(0.5f)
                        .compress(true)
                        .compressMode(LUBAN_COMPRESS_MODE)
                        .isGif(true)
                        .previewEggs(true)
                        .forResult(Constans.TYPE_ONE);
                break;

            //视频
            case R.id.action_b:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofVideo())
                        .imageSpanCount(4)
                        .selectionMode(PictureConfig.SINGLE)
                        .previewVideo(true)
                        .videoQuality(1)
                        .compress(true)
                        .recordVideoSecond(10)
                        .forResult(Constans.TYPE_TWO);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLogger.jLog().i("onActivityResult");
        Intent intent = new Intent(mContext, PublishActivity.class);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constans.TYPE_ONE:
                    // 图片选择结果回调
                    List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    intent.putExtra("data", (Serializable) localMedias);
                    intent.putExtra("type", Constans.TYPE_PICTURE);
                    break;

                case Constans.TYPE_TWO:
                    // 图片选择结果回调
                    List<LocalMedia> video = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    intent.putExtra("data", (Serializable) video);
                    intent.putExtra("type", Constans.TYPE_VIDEO);
                    break;
            }
            startActivityForResult(intent, 3);
        }

        MyLogger.jLog().i("requestCode=" + requestCode);
        MyLogger.jLog().i("resultCode=" + resultCode);
        if (requestCode == 3 && resultCode == 10) {
            onRefresh();
            rlv_fentuan.getRecyclerView().smoothScrollToPosition(0);
        }


        if (requestCode == Constans.REQUEST_CODE && resultCode == Constans.RESULT_CODE) {
            QueryFtInfos.RowsBean mRowsBean = (QueryFtInfos.RowsBean) data.getSerializableExtra("rowsBean");
            final QueryFtInfos.RowsBean rowsBean = mQueryFtInfos.getRows().get(position);
            rowsBean.setGiveUpCount(mRowsBean.getGiveUpCount());
            rowsBean.setIsGiveUp(mRowsBean.isIsGiveUp());
            rowsBean.setCommentCount(mRowsBean.getCommentCount());
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLogger.jLog().i("onDestroy");
        queryCore.stopRequest();
    }


    //刷新的时候查询最新数据 page = 1
    @Override
    public void onRefresh() {
        page = 1;
        queryFtInfo();
        rlv_fentuan.complete();

    }

    @Override
    public void onLoadMore() {
        MyLogger.jLog().i("onLoadMore");
        page++;
        queryCore.queryFtInfo(starId, false, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                mQueryFtInfoss = mGson.fromJson(response.get(), QueryFtInfos.class);
                //判断是否还有数据
                if (mQueryFtInfoss.getTotal() <= page * rows) {
                    rlv_fentuan.onNoMore("没有更多了");
                }
                if (mQueryFtInfoss.isSuccess()) {
                    mQueryFtInfos.getRows().addAll(mQueryFtInfoss.getRows());
                    mAdapter.addData(mQueryFtInfoss.getRows());
                } else {
                    ToastUtils.showToast(mQueryFtInfoss.getErrorMsg());
                }
                rlv_fentuan.complete();
            }
        });
    }

    @Override
    public void onClickGoods(int position, final QueryFtInfos.RowsBean rowsBean) {
        MyLogger.jLog().i("onClickGoods");
        queryCore.ftGoods(rowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (rowsBean.isIsGiveUp()) {
                        giveNum = rowsBean.getGiveUpCount() - 1;
                        rowsBean.setIsGiveUp(false);
                        rowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = rowsBean.getGiveUpCount() + 1;
                        rowsBean.setIsGiveUp(true);
                        rowsBean.setGiveUpCount(giveNum);
                    }
                    mAdapter.notifyDataSetChanged();

                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }

            }
        });


    }

    //评论
    @Override
    public void onClickCommends(int position, QueryFtInfos.RowsBean rowsBean, boolean hasPicture, boolean isFormConmmomd) {
        MyLogger.jLog().i("onClickCommends");
        this.position = position;
        MyLogger.jLog().i("Commendposition:"+position);
        Intent intent = new Intent(MyApplication.getInstance(), FenTuanCatgoryActiviry.class);
        intent.putExtra("hasPicture", hasPicture);
        intent.putExtra("rowsBean", rowsBean);
        intent.putExtra("isFormConmmomd", isFormConmmomd);
        startActivityForResult(intent, Constans.REQUEST_CODE);

    }


    //分享
    @Override
    public void onClickshare(int position) {

    }

}
