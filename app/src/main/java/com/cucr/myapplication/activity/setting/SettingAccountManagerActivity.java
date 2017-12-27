package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.regist.NewLoadActivity;
import com.cucr.myapplication.adapter.RlVAdapter.AccountListAdapter;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.login.LoginCore;
import com.cucr.myapplication.listener.OnLoginListener;
import com.cucr.myapplication.model.login.UserAccountInfo;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.dialog.DialogQuitAccountStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class SettingAccountManagerActivity extends BaseActivity {

    //账号列表
    @ViewInject(R.id.rlv_account)
    private RecyclerView rlv_account;

    private DialogQuitAccountStyle mQuitDialog;
    private AccountListAdapter adapter;
    private LoginCore mLoginCore;
    private List<String> mKeys;

    @Override
    protected void initChild() {
        mLoginCore = new LoginCore(this);
        //包含所有账户信息的key
        mKeys = new ArrayList<>(SpUtil.getAccountSp().getAll().keySet());
        adapter = new AccountListAdapter(mKeys);
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
        mQuitDialog.setOnClickConfirmQuit(new DialogQuitAccountStyle.OnClickConfirmQuit() {
            @Override
            //确认
            public void clickConfirmQuit() {
                finish();
            }
        });

        /*
        * recyclerView
        * */
        rlv_account.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));


        /**
         * 回显对勾  找到当前账号设置上对勾
         */
        String str = (String) SpUtil.getParam(SpConstant.USER_NAEM, "");
        adapter.setSelect(mKeys.indexOf(str));
        //获取账号信息

        rlv_account.setAdapter(adapter);
        rlv_account.setNestedScrollingEnabled(false);
        adapter.setOnClickItem(new AccountListAdapter.OnClickItem() {
            @Override
            public void onClickItem(View view, int position) {
                //切换账号
                changeAccount(position);
            }
        });
    }

    //切换账号
    private void changeAccount(final int position) {
        String string = SpUtil.getAccountSp().getString(mKeys.get(position), "");
        UserAccountInfo accountInfo = mGson.fromJson(string, UserAccountInfo.class);
        mLoginCore.login(accountInfo.getUserName(), accountInfo.getPassWord(), new OnLoginListener() {
            @Override
            public void onSuccess(Response<String> response) {
                adapter.setSelect(position);
                finish();
                startActivity(new Intent(SettingAccountManagerActivity.this, MainActivity.class));
            }

            @Override
            public void onFailed() {

            }
        });

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

}
