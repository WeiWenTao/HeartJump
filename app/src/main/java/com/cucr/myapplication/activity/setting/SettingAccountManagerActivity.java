package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.regist.NewLoadActivity;
import com.cucr.myapplication.adapter.RlVAdapter.AccountListAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.login.UserAccountInfo;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.DialogQuitAccountStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

public class SettingAccountManagerActivity extends BaseActivity implements DialogQuitAccountStyle.OnClickConfirmQuit {

    //账号列表
    @ViewInject(R.id.rlv_account)
    private RecyclerView rlv_account;

    private DialogQuitAccountStyle mQuitDialog;
    private AccountListAdapter adapter;
    private LoginCore mLoginCore;
    private List<String> mKeys;

    @Override
    protected void initChild() {
        mLoginCore = new LoginCore();
        adapter = new AccountListAdapter();
        initTitle("账号管理");
        initViews();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_setting_account_manager;
    }

    private void initViews() {
        /*
        * dialog
        * */
        mQuitDialog = new DialogQuitAccountStyle(this, R.style.ShowAddressStyleTheme);
        mQuitDialog.setOnClickConfirmQuit(this);

        /*
        * recyclerView
        * */
        rlv_account.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));

        String keys = (String) SpUtil.getParam("keys", "");
        mKeys = MyApplication.getGson().fromJson(keys, List.class);
        /**
         * 回显对勾  找到当前账号设置上对勾
         */
//        String str = (String) SpUtil.getParam(SpConstant.USER_NAEM, "");
//        adapter.setSelect(mKeys.indexOf(str));
        //获取账号信息

        rlv_account.setAdapter(adapter);
        rlv_account.setNestedScrollingEnabled(false);
        adapter.setOnClickItem(new AccountListAdapter.OnClickItem() {
            @Override
            public void onClickItem(View view, String getKey, int position) {
                //切换账号
                changeAccount(getKey, position);
            }
        });
    }

    //切换账号
    private void changeAccount(String getKey, int position) {

        String string = SpUtil.getAccountSp().getString(getKey, "");
        UserAccountInfo accountInfo = mGson.fromJson(string, UserAccountInfo.class);
        mLoginCore.login(accountInfo.getUserName(), accountInfo.getPassWord(), new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                // TODO: 2018/3/11 MD5加密错误
                CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                if (msg.isSuccess()) {
                    startActivity(new Intent(SettingAccountManagerActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.showToast(msg.getMsg());
                }
            }

            @Override
            public void onRequestStar(int what) {

            }

            @Override
            public void onRequestFinish(int what) {

            }


        });

        //如果没有网
        if (!CommonUtils.isNetworkConnected(MyApplication.getInstance()) || position == -1) {
            return;
        }
        adapter.setSelect(position);

    }

    //退出当前账号
    @OnClick(R.id.tv_quit_account)
    public void quitAccount(View view) {
        mQuitDialog.show();
    }

    //添加账号
    @OnClick(R.id.rl_add_acount)
    public void addAccount(View view) {
        Intent intent = new Intent(MyApplication.getInstance(), NewLoadActivity.class);
        intent.putExtra("isAdd", true);
        startActivity(intent);
    }

    //退出对话框确认按钮
    @Override
    public void clickConfirmQuit() {

        if (mKeys.size() > 1) {
            //删除第一个账号
            mKeys.remove(0);
            SpUtil.setParam("keys", MyApplication.getGson().toJson(mKeys).toString());
//            adapter.setKeys(mKeys);
            changeAccount(mKeys.get(0), -1);
        } else {
            //删除sp中的所有数据
            mKeys.remove(0);
            SpUtil.setParam(SpConstant.SIGN, "null");
//            SpUtil.getSp().edit().clear().commit();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
