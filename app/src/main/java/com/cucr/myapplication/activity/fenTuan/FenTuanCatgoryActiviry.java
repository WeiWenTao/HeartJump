package com.cucr.myapplication.activity.fenTuan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.GvAdapter.GridAdapter;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.funTuan.FtCommentCore;
import com.cucr.myapplication.core.funTuan.QueryFtInfoCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.fenTuan.FtCommentInfo;
import com.cucr.myapplication.model.fenTuan.QueryFtInfos;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaShangStyle;
import com.cucr.myapplication.widget.gridView.NoScrollGridView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.Serializable;
import java.util.List;

import static com.cucr.myapplication.utils.MyLogger.jLog;

public class FenTuanCatgoryActiviry extends BaseActivity {

    //评论列表
    @ViewInject(R.id.lv_ft_catgory)
    ListView lv_ft_catgory;

    //点赞
    @ViewInject(R.id.iv_zan)
    ImageView iv_zan;

    //点赞数量
    @ViewInject(R.id.tv_givecount)
    TextView tv_givecount;


    private DialogDaShangStyle mDialogDaShangStyle;
    private boolean mHasPicture;
    private boolean mIsFormConmmomd;
    private QueryFtInfos.RowsBean mRowsBean;
    private FtCommentCore mCommentCore;
    private Integer page;
    private Integer rows;
    private Integer giveNum;
    private QueryFtInfoCore queryCore;
    private Intent mIntent;
    private FtCatgoryAadapter mAdapter;

    @Override
    protected void initChild() {
        initTitle("详情");
        initData();
        initLV();
        getDatas();
    }

    //获取传过来的数据
    private void initData() {
        mIntent = getIntent();
        mHasPicture = mIntent.getBooleanExtra("hasPicture", false);
        mIsFormConmmomd = mIntent.getBooleanExtra("isFormConmmomd", false);
        mRowsBean = (QueryFtInfos.RowsBean) mIntent.getSerializableExtra("rowsBean");
        jLog().i("mRowsBean:" + mRowsBean);
        mCommentCore = new FtCommentCore(this);
        upDataInfo();
        queryCore = new QueryFtInfoCore(this);
    }

    private void upDataInfo() {
        tv_givecount.setText(mRowsBean.getGiveUpCount() + "");
        iv_zan.setImageResource(mRowsBean.isIsGiveUp() ? R.drawable.icon_good_sel : R.drawable.icon_good_nor);
    }

    private void getDatas() {
        page = 1;
        rows = 10;
//        mRowsBean.getId()
        mCommentCore.queryFtComment(2, page, rows, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtCommentInfo ftCommentInfo = mGson.fromJson(response.get(), FtCommentInfo.class);
                if (ftCommentInfo.isSuccess()) {
                    MyLogger.jLog().i("ftCommentInfo:"+ftCommentInfo);
                    mAdapter.setData(ftCommentInfo.getRows());
                } else {
                    ToastUtils.showToast(ftCommentInfo.getErrorMsg());
                }
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_fen_tuan_catgory_activiry;
    }

    private void initLV() {
        mDialogDaShangStyle = new DialogDaShangStyle(this, R.style.ShowAddressStyleTheme);
        mDialogDaShangStyle.setCanceledOnTouchOutside(true);
        mDialogDaShangStyle.setConfirmListener(new DialogDaShangStyle.ClickconfirmListener() {
            @Override
            public void onClickConfirm(int howMuch) {
                if (howMuch != 0) {
                    ToastUtils.showToast(FenTuanCatgoryActiviry.this, howMuch + "" + " 星币");
                }
                mDialogDaShangStyle.dismiss();
            }
        });

        View lvHead = View.inflate(this, R.layout.item_ft_catgory_header, null);
        initLvHeader(lvHead);

        lv_ft_catgory.addHeaderView(lvHead, null, true);
        lv_ft_catgory.setHeaderDividersEnabled(false);
        mAdapter = new FtCatgoryAadapter(this);
        lv_ft_catgory.setAdapter(mAdapter);
        if (mIsFormConmmomd) {
            lv_ft_catgory.setSelection(1);
        }
    }

    private void initLvHeader(View lvHead) {
        //头像
        ImageView iv_pic = (ImageView) lvHead.findViewById(R.id.iv_pic);
        //昵称
        TextView tv_neckname = (TextView) lvHead.findViewById(R.id.tv_neckname);
        //时间来源
        TextView tv_time_form = (TextView) lvHead.findViewById(R.id.tv_time_form);
        //浏览量
        TextView tv_lookcount = (TextView) lvHead.findViewById(R.id.tv_lookcount);
        //评论内容
        TextView tv_content = (TextView) lvHead.findViewById(R.id.tv_content);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.ic_launcher)  // 加载时的占位图
                .showImageOnFail(R.drawable.ic_launcher)  // 加载失败占位图
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        //设置数据
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean.getUserHeadPortrait(), iv_pic, options);
        tv_neckname.setText(mRowsBean.getCreateUserName());
        tv_time_form.setText(mRowsBean.getCreaetTime());
        tv_lookcount.setText(mRowsBean.getReadCount()+"");
        tv_content.setText(mRowsBean.getContent());

        lvHead.findViewById(R.id.tv_dashang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDaShangStyle.show();
            }
        });

        NoScrollGridView gridview = (NoScrollGridView) lvHead.findViewById(R.id.gridview);
        gridview.setVisibility(mHasPicture ? View.VISIBLE : View.GONE);
        gridview.setAdapter(new GridAdapter(this, mRowsBean.getAttrFileList()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FenTuanCatgoryActiviry.this, ImagePagerActivity.class);
                List<QueryFtInfos.RowsBean.AttrFileListBean> attrFileList = mRowsBean.getAttrFileList();
                Bundle bundle = new Bundle();
                bundle.putSerializable("imgs", (Serializable) attrFileList);//序列化,要注意转化(Serializable)
                intent.putExtras(bundle);//发送数据
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    //点击评论时
    @OnClick(R.id.ll_comment)
    public void comment(View view) {
        lv_ft_catgory.setSelection(1);
    }

    //点赞时
    @OnClick(R.id.ll_goods)
    public void zan(View view) {
        queryCore.ftGoods(mRowsBean.getId(), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg commonRebackMsg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (commonRebackMsg.isSuccess()) {
                    if (mRowsBean.isIsGiveUp()) {
                        giveNum = mRowsBean.getGiveUpCount() - 1;
                        mRowsBean.setIsGiveUp(false);
                        mRowsBean.setGiveUpCount(giveNum);
                    } else {
                        giveNum = mRowsBean.getGiveUpCount() + 1;
                        mRowsBean.setIsGiveUp(true);
                        mRowsBean.setGiveUpCount(giveNum);
                    }
                    upDataInfo();
                } else {
                    ToastUtils.showToast(commonRebackMsg.getMsg());
                }

            }
        });
    }

    public void setData() {
        Intent intent = getIntent();
        intent.putExtra("rowsBean", mRowsBean);
        setResult(Constans.RESULT_CODE, intent);
    }

    //返回操作
    @Override
    protected void onBackBefore() {
        setData();
    }

    @Override
    public void onBackPressed() {
        setData();
        finish();
    }

}
