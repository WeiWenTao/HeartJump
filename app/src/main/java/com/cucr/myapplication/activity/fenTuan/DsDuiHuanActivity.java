package com.cucr.myapplication.activity.fenTuan;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.adapter.RlVAdapter.BackPackDuiHuanAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.bean.eventBus.EventDuiHuanSuccess;
import com.cucr.myapplication.bean.fenTuan.FtBackpackInfo;
import com.cucr.myapplication.core.funTuanAndXingWen.QueryFtInfoCore;
import com.cucr.myapplication.core.xinbi.XinBiCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class DsDuiHuanActivity extends BaseActivity implements BackPackDuiHuanAdapter.OnResultChange {

    //兑换列表
    @ViewInject(R.id.rlv_bp_duihuan)
    RecyclerView rlv_bp_duihuan;

    //兑换按钮
    @ViewInject(R.id.tv_to_duihuna)
    Button tv_to_duihuna;

    private int how;
    private Map<Integer, Integer> gifts;
    private XinBiCore mXinBiCore;
    private BackPackDuiHuanAdapter mAdapter;
    private QueryFtInfoCore queryCore;

    //初始化
    @Override
    protected void initChild() {
        mXinBiCore = new XinBiCore();
        queryCore = new QueryFtInfoCore();
        gifts = new HashMap<>();
        rlv_bp_duihuan.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new BackPackDuiHuanAdapter();
        mAdapter.setOnResultChange(this);
        rlv_bp_duihuan.setAdapter(mAdapter);
        duihuanSuccess();

    }

    private void initResult(FtBackpackInfo mFtBackpackInfo) {
        initGiftCoast(mFtBackpackInfo);
        mAdapter.setData(mFtBackpackInfo);
        how = mFtBackpackInfo.getObj().getZjg();
        if (how == 0) {
            tv_to_duihuna.setText("暂无可以兑换的礼物哦");
            tv_to_duihuna.setBackgroundResource(R.drawable.shape_load_bg_nor);
            tv_to_duihuna.setEnabled(false);
        } else {
            tv_to_duihuna.setText("价值" + how + "星币 立即兑换");
            tv_to_duihuna.setBackgroundResource(R.drawable.shape_immediately_pay_bg);
            tv_to_duihuna.setEnabled(true);
        }
    }

    //初始化礼物总价值
    private void initGiftCoast(FtBackpackInfo mFtBackpackInfo) {
        for (FtBackpackInfo.ObjBean.ListBean listBean : mFtBackpackInfo.getObj().getList()) {
            gifts.put(listBean.getUserAccountType().getId(), listBean.getBalance());
        }
    }

    //兑换
    @OnClick(R.id.tv_to_duihuna)
    public void toDuihuan(View view) {
        String giftIds = "";        //记录礼物id
        String giftCounts = "";     //记录礼物个数

        for (Map.Entry<Integer, Integer> entry : gifts.entrySet()) {
            giftIds = giftIds + entry.getKey() + ",";
            giftCounts = giftCounts + entry.getValue() + ",";
        }

        if (giftIds.endsWith(",")) {
            giftIds = giftIds.substring(0, giftIds.length() - 1);
        }

        if (giftCounts.endsWith(",")) {
            giftCounts = giftCounts.substring(0, giftCounts.length() - 1);
        }

        mXinBiCore.giftDuiHuan(giftIds, giftCounts, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    ToastUtils.showToast("兑换成功!");
                    EventBus.getDefault().post(new EventDuiHuanSuccess());
                    //兑换成功后再次初始化数据
                    duihuanSuccess();

                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
            }
        });

    }

    private void duihuanSuccess() {
        //查询背包信息
        queryCore.queryBackpackInfo(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                FtBackpackInfo ftBackpackInfo = mGson.fromJson(response.get(), FtBackpackInfo.class);
                if (ftBackpackInfo.isSuccess()) {
                    mAdapter.setData(ftBackpackInfo);
                    initResult(ftBackpackInfo);
                } else {
                    ToastUtils.showToast(ftBackpackInfo.getMsg());
                }
            }
        });
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_ds_dui_huan;
    }

    @Override
    public void onResultAdd(int giftId, int howMuch, int giftCount) {
        how = how + howMuch;
        tv_to_duihuna.setText("价值" + how + "星币 立即兑换");
        gifts.put(giftId, giftCount);
    }

    @Override
    public void onResultDel(int giftId, int howMuch, int giftCount) {
        how = how - howMuch;
        tv_to_duihuna.setText("价值" + how + "星币 立即兑换");
        gifts.put(giftId, giftCount);
    }
}
