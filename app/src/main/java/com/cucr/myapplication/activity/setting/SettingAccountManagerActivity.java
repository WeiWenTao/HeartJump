package com.cucr.myapplication.activity.setting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.MainActivity;
import com.cucr.myapplication.activity.regist.NewLoadActivity;
import com.cucr.myapplication.adapter.RlVAdapter.AccountListAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.user.LoadUserInfos;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.dao.DaoCore;
import com.cucr.myapplication.gen.LoadUserInfosDao;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.widget.dialog.DialogQuitAccountStyle;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class SettingAccountManagerActivity extends BaseActivity implements DialogQuitAccountStyle.OnClickConfirmQuit, AccountListAdapter.OnClickItem {

    //账号列表
    @ViewInject(R.id.rlv_account)
    private RecyclerView rlv_account;

    private DialogQuitAccountStyle mQuitDialog;
    private AccountListAdapter adapter;
    private LoadUserInfosDao mUserDao;
    private Intent mIntent;

    @Override
    protected void initChild() {
        mUserDao = DaoCore.getInstance().getUserDao();
        adapter = new AccountListAdapter();
        mIntent = new Intent();
        mIntent.putExtra("isAdd", true);
        getAccounts();
        initTitle("账号管理");
        initViews();
    }

    private void getAccounts() {
        new MyAsyn().execute();
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

        rlv_account.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        rlv_account.setAdapter(adapter);
        rlv_account.setNestedScrollingEnabled(false);
        adapter.setOnClickItem(this);
    }

    //切换账号
    private void changeAccount(LoadUserInfos loadUserInfos) {
        SpUtil.setParam(SpConstant.USER_ID, loadUserInfos.getUserId());
        SpUtil.setParam(SpConstant.SIGN, loadUserInfos.getSign());
        SpUtil.setParam(SpConstant.SP_STATUS, loadUserInfos.getRoleId());
        startActivity(new Intent(SettingAccountManagerActivity.this, MainActivity.class));
        finish();
    }

    //退出当前账号
    @OnClick(R.id.tv_quit_account)
    public void quitAccount(View view) {
        mQuitDialog.show();
    }

    //添加账号
    @OnClick(R.id.rl_add_acount)
    public void addAccount(View view) {
        mIntent.setClass(MyApplication.getInstance(), NewLoadActivity.class);
        startActivity(mIntent);
    }

    //退出对话框确认按钮
    @Override
    public void clickConfirmQuit() {
        //删除这条账号
        LoadUserInfos unique = mUserDao.queryBuilder().where(LoadUserInfosDao.Properties.UserId
                .eq(((int) SpUtil.getParam(SpConstant.USER_ID, -1)))).build().unique();
        mUserDao.delete(unique);

        //再查一遍  登录第一条记录
        LoadUserInfos firstInfo = mUserDao.queryBuilder().limit(1).build().unique();
        //如果集合为空  那就跳转登录
        if (firstInfo == null) {
            mIntent.setClass(MyApplication.getInstance(), MainActivity.class);
            startActivity(mIntent);
            finish();
        } else {
            //如果有数据 那就切换第一条
            changeAccount(firstInfo);
        }

    }

    @Override
    public void onClickItem(LoadUserInfos loadUserInfos) {
        //切换账号
        changeAccount(loadUserInfos);
    }

    class MyAsyn extends AsyncTask<String, Integer, List<LoadUserInfos>> {

        @Override
        protected List<LoadUserInfos> doInBackground(String... strings) {
            List<LoadUserInfos> infos = mUserDao.loadAll();
            return infos;
        }

        @Override
        protected void onPostExecute(List<LoadUserInfos> loadUserInfos) {
            super.onPostExecute(loadUserInfos);
            adapter.setData(loadUserInfos);
        }
    }
}
