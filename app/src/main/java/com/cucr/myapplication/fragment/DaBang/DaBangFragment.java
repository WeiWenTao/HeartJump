package com.cucr.myapplication.fragment.DaBang;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.DaBangAdapter;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.dabang.BangDanCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.CommonRebackMsg;
import com.cucr.myapplication.model.dabang.BangDanInfo;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogDaBangStyle;
import com.cucr.myapplication.widget.refresh.RefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by 911 on 2017/6/22.
 */

public class DaBangFragment extends BaseFragment implements DialogDaBangStyle.ClickconfirmListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private BangDanCore mCore;
    private DaBangAdapter mAdapter;
    private View headView;
    private DialogDaBangStyle mDialog;
    private int starId;
    private BangDanInfo.RowsBean mRowsBean1;
    private BangDanInfo.RowsBean mRowsBean2;
    private BangDanInfo.RowsBean mRowsBean3;

    @Override
    protected void initView(View childView) {
        RefreshLayout myRefreshListView = (RefreshLayout) childView.findViewById(R.id.swipe_layout);
        ListView lv_dabang = (ListView) childView.findViewById(R.id.lv_dabang);
        myRefreshListView.setOnLoadListener(this);
        myRefreshListView.setOnRefreshListener(this);

        headView = View.inflate(mContext, R.layout.head_dabang, null);
        ViewUtils.inject(this, headView);
        mDialog = new DialogDaBangStyle(childView.getContext(), R.style.BirthdayStyleTheme);
        mDialog.setConfirmListener(this);

        queryBdInfo();
        lv_dabang.addHeaderView(headView);

        mAdapter = new DaBangAdapter(mContext);
        lv_dabang.setAdapter(mAdapter);
        mAdapter.setOnDaBang(new DaBangAdapter.OnDaBang() {
            @Override
            public void daBang(BangDanInfo.RowsBean rowsBean) {
                mDialog.show();
                starId = rowsBean.getId();
            }
        });
    }

    private void initHead(List<BangDanInfo.RowsBean> rows) {

        mRowsBean1 = rows.get(0);
        mRowsBean2 = rows.get(1);
        mRowsBean3 = rows.get(2);

        ImageView userHead1 = (ImageView) headView.findViewById(R.id.iv_head1);
        ImageView userHead2 = (ImageView) headView.findViewById(R.id.iv_head2);
        ImageView userHead3 = (ImageView) headView.findViewById(R.id.iv_head3);
        TextView tv_name1 = (TextView) headView.findViewById(R.id.tv_name1);
        TextView tv_name2 = (TextView) headView.findViewById(R.id.tv_name2);
        TextView tv_name3 = (TextView) headView.findViewById(R.id.tv_name3);
        TextView tv_num1 = (TextView) headView.findViewById(R.id.tv_num1);
        TextView tv_num2 = (TextView) headView.findViewById(R.id.tv_num2);
        TextView tv_num3 = (TextView) headView.findViewById(R.id.tv_num3);

        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean1.getUserHeadPortrait(), userHead1, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean2.getUserHeadPortrait(), userHead2, MyApplication.getImageLoaderOptions());
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mRowsBean3.getUserHeadPortrait(), userHead3, MyApplication.getImageLoaderOptions());

        tv_name1.setText(mRowsBean1.getRealName());
        tv_name2.setText(mRowsBean2.getRealName());
        tv_name3.setText(mRowsBean3.getRealName());

        tv_num1.setText(mRowsBean1.getUserMoney() + "");
        tv_num2.setText(mRowsBean2.getUserMoney() + "");
        tv_num3.setText(mRowsBean3.getUserMoney() + "");
    }

    private void queryBdInfo() {
        mCore = new BangDanCore(mContext);
        mCore.queryBangDanInfo(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyLogger.jLog().i("queryBangDanInfo:" + response.get());
                BangDanInfo bangDanInfo = mGson.fromJson(response.get(), BangDanInfo.class);
                if (bangDanInfo.isSuccess()) {
                    mAdapter.setData(bangDanInfo.getRows());
                    initHead(bangDanInfo.getRows());
                } else {
                    ToastUtils.showToast(bangDanInfo.getErrorMsg());
                }
            }
        });
    }

    @OnClick(R.id.iv_dabang_first)
    public void daBangFirst(View view) {
        mDialog.show();
        starId = mRowsBean1.getId();
    }

    @OnClick(R.id.iv_dabang_second)
    public void daBangSecond(View view) {
        mDialog.show();
        starId = mRowsBean2.getId();
    }

    @OnClick(R.id.iv_dabang_third)
    public void daBangThird(View view) {
        mDialog.show();
        starId = mRowsBean3.getId();
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_dabang;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCore.stopRequest();
    }

    @Override
    public void onClickConfirm(int howMuch) {
        mCore.daBang(howMuch, starId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {

                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("打榜成功!");
                    queryBdInfo();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
            }
        });
    }

    @Override
    public void onLoad() {
        MyLogger.jLog().i("onLoad");
    }

    @Override
    public void onRefresh() {
        MyLogger.jLog().i("onRefresh");
    }
}
