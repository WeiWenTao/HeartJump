package com.cucr.myapplication.activity.fenTuan;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.GridImageAdapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.funTuanAndXingWen.FtPublishCore;
import com.cucr.myapplication.listener.OnUpLoadListener;
import com.cucr.myapplication.model.RZ.RzResult;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogPublishStyle;
import com.cucr.myapplication.widget.recyclerView.FullyGridLayoutManager;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;
import com.yanzhenjie.nohttp.rest.Response;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

public class PublishActivity extends Activity {

    private List<LocalMedia> mData;
    private ItemTouchHelper mItemTouchHelper;
    private int starId;

    //预览列表
    @ViewInject(R.id.picture_list)
    RecyclerView recyclerView;

    //发布内容
    @ViewInject(R.id.et_publish)
    EditText et_publish;

    private int mType;
    private int fileType;
    private GridImageAdapter mAdapter;
    private FtPublishCore core;
    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_publish);
        ViewUtils.inject(this);
        initHead();
        getData();
        initTouchHelper();
        initView();

    }

    private void initTouchHelper() {
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder

                int toPosition = target.getAdapterPosition();

                if (toPosition > mData.size() - 1) {
                    toPosition = mData.size() - 1;
                }

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mData, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                Log.i("test", mData.toString());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                myAdapter.notifyItemRemoved(position);
//                datas.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setAlpha(0.5f);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
            }
        });

        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initView() {
        core = new FtPublishCore(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
//        mAdapter.setList(mData);
        mAdapter.setSelectMax(mType == Constans.TYPE_PICTURE ? 9 : 1); //根据类型决定最大个数
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemLongClickListener(new GridImageAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, View v) {
                mItemTouchHelper.startDrag(viewHolder);
                //获取系统震动服务
                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                //震动70毫秒
                vib.vibrate(70);
            }
        });

        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = mData.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        PictureSelector.create(PublishActivity.this).externalPicturePreview(position, mData);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(PublishActivity.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(PublishActivity.this).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });


        et_publish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {

                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            switch (mType) {
                case Constans.TYPE_PICTURE:
                    PictureSelector.create(PublishActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(9)
                            .imageSpanCount(4)
                            .selectionMode(PictureConfig.MULTIPLE)
                            .previewImage(false)
//                            .selectionMedia(mData)
                            .compressGrade(Luban.THIRD_GEAR)
                            .isCamera(true)// 是否显示拍照按钮 true or false
                            .sizeMultiplier(0.5f)
                            .compress(true)
                            .cropCompressQuality(90)
                            .compressMode(LUBAN_COMPRESS_MODE)
                            .isGif(true)
                            .previewEggs(true)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                    break;

                case Constans.TYPE_VIDEO:
                    PictureSelector.create(PublishActivity.this)
                            .openGallery(PictureMimeType.ofVideo())
                            .imageSpanCount(4)
//                            .selectionMedia(mData)
                            .selectionMode(PictureConfig.SINGLE)
                            .previewVideo(true)
                            .compress(true)
                            .videoQuality(1)
                            .recordVideoSecond(10)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mData = PictureSelector.obtainMultipleResult(data);
                    mAdapter.setList(mData);
                    mAdapter.notifyDataSetChanged();
                    DebugUtil.i(TAG, "onActivityResult:" + mData.size());
                    break;
            }
        }
    }

    private void getData() {
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", -1);
        starId = intent.getIntExtra("starId", -1);
        mData = new ArrayList<>();
    }

    private void initHead() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.blue_black), 0);
    }

    //返回键
    @OnClick(R.id.iv_publish_back)
    public void backToHome(View view) {
        showDialog();
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    public void showDialog() {
        final DialogPublishStyle publishDialog = new DialogPublishStyle(this, R.style.ShowAddressStyleTheme);
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

    @OnClick(R.id.tv_publish)
    public void toPublish(View view) {

//        mType不能为零   另外再定义变量
        if (mData == null || mData.size() == 0) {
            fileType = 0;
        } else {
            fileType = mType;
        }

//        photoUpload();

        core.publishFtInfo(starId, fileType, et_publish.getText().toString(), mData, new OnUpLoadListener() {
            @Override
            public void OnUpLoadPicListener(Response<String> response) {
                MyLogger.jLog().i("response:"+response.get());
                RzResult rzResult = new Gson().fromJson(response.get(), RzResult.class);
                if (rzResult.isSuccess()) {
                    ToastUtils.showToast("发布成功");
                    MyLogger.jLog().i("发布pic成功");
                    needRefresh = true;
                    Intent intent = getIntent();
                    intent.putExtra("needRefresh",needRefresh);
                    setResult(10,intent);
                    finish();
                } else {
                    ToastUtils.showToast(rzResult.getMsg());
                }
            }

            @Override
            public void OnUpLoadVideoListener(ResponseInfo<String> arg0) {
                MyLogger.jLog().i("response:"+arg0.result);
                RzResult rzResult = new Gson().fromJson(arg0.result, RzResult.class);
                if (rzResult.isSuccess()) {
                    ToastUtils.showToast("发布视频成功");
                    MyLogger.jLog().i("发布video成功");
                    needRefresh = true;
                    Intent intent = getIntent();
                    intent.putExtra("needRefresh",needRefresh);
                    setResult(10,intent);
                    finish();
                } else {
                    ToastUtils.showToast(rzResult.getMsg());
                }
            }

            @Override
            public void OnUpLoadTextListener(Response<String> response) {

                MyLogger.jLog().i("response:"+response.get());
                RzResult rzResult = new Gson().fromJson(response.get(), RzResult.class);
                if (rzResult.isSuccess()) {
                    ToastUtils.showToast("发布成功");
                    MyLogger.jLog().i("发布text成功");
                    Intent intent = getIntent();
                    intent.putExtra("needRefresh",needRefresh);
                    setResult(10,intent);
                    finish();
                } else {
                    ToastUtils.showToast(rzResult.getMsg());
                }
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        core.stopRequest();

    }
}
