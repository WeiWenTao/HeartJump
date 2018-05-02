package com.cucr.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.EditPersonalInfo.IMHytInfo;
import com.cucr.myapplication.bean.EditPersonalInfo.IMPersonalInfo;
import com.cucr.myapplication.bean.app.AppInfo;
import com.cucr.myapplication.bean.app.TokenMsg;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.AppCore;
import com.cucr.myapplication.core.chat.ChatCore;
import com.cucr.myapplication.core.editPersonalInfo.QueryPersonalMsgCore;
import com.cucr.myapplication.fragment.DaBang.DaBangFragment;
import com.cucr.myapplication.fragment.fuLiHuoDong.FragmentHuoDongAndFuLi;
import com.cucr.myapplication.fragment.home.FragmentHotAndFocusNews;
import com.cucr.myapplication.fragment.mine.MineFragment;
import com.cucr.myapplication.fragment.other.FragmentFans;
import com.cucr.myapplication.fragment.yuyue.ApointmentFragmentA;
import com.cucr.myapplication.listener.LoadChatServer;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.AppUtils;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.utils.upDataUtils.DownLoadApk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, RongIM.UserInfoProvider, LoadChatServer, RongIM.GroupInfoProvider, RequersCallBackListener, RongIM.GroupUserInfoProvider, OnCommonListener {

    private List<Fragment> mFragments;
    private RadioGroup mRg_mian_fragments;
    private QueryPersonalMsgCore qucryCore;
    private UserInfo mUserInfo;
    private Group mGroupInfo;
    private GroupUserInfo mGroupUserInfo;
    private ChatCore mChatCore;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        initIM();
        qucryCore = new QueryPersonalMsgCore();
        //获取从 我的-明星-右上角加关注 界面跳转过来的数据
        findView();
        initView();
        initFragment(0);
        initRadioGroup();
        CheckUpData();    //检查更新
        DisplayMetrics dm = new DisplayMetrics();
        // MI NOTE LET : DisplayMetrics{density=2.75, width=1080, height=1920, scaledDensity=2.75, xdpi=386.366, ydpi=387.047}
        // MI 6        :DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=428.625, ydpi=427.789}
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        String s = "屏幕的分辨率为：" + dm.widthPixels + "*" + dm.heightPixels;
        MyLogger.jLog().i(s);
    }

    private void CheckUpData() {
        AppCore core = new AppCore();
        core.queryCode(this);
    }

    private void normalUpdate(String text, final AppInfo appInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(text + appInfo.getKeyFild());
        builder.setMessage(appInfo.getRemark());
        AlertDialog.Builder builder1 = builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!AppUtils.canDownloadState()) {
                    showDownloadSetting();
                    return;
                }
                ToastUtils.showToast("已开始后台下载");
                DownLoadApk.download(MainActivity.this, appInfo.getValueFild(), "正在下载", "心跳互娱");
            }
        }).setCancelable(appInfo.getGroupFild() == 0);
        if (appInfo.getGroupFild() == 0) {
            //非强制更新
            builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder1.create().show();
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }

    private void initIM() {
        mChatCore = new ChatCore();
        String param = (String) SpUtil.getParam(SpConstant.TOKEN, "");
        mChatCore.connect(param, this);
        RongIM.setUserInfoProvider(this, true);
        RongIM.setGroupInfoProvider(this, true);
        RongIM.setGroupUserInfoProvider(this, true);
    }

    //============================聊天服务器回调=======================
    @Override
    public void onLoadSuccess(String userid) {
        MyLogger.jLog().i("登录聊天服务器成功 userId =" + userid);
    }

    @Override
    public void onLoadFial(RongIMClient.ErrorCode errorCode) {
        MyLogger.jLog().i("登录聊天服务器失败 errorCode =" + errorCode);
    }

    @Override
    public void onTokenIncorrect() {
        mChatCore.reLoad(this);
    }
    //================================================================

    private void findView() {
        RadioButton rb_1 = (RadioButton) findViewById(R.id.rb_1);
        RadioButton rb_2 = (RadioButton) findViewById(R.id.rb_2);
        RadioButton rb_3 = (RadioButton) findViewById(R.id.rb_3);
        RadioButton rb_4 = (RadioButton) findViewById(R.id.rb_4);
        RadioButton rb_mid = (RadioButton) findViewById(R.id.rb_mid);
//      rb_4.setVisibility(View.GONE);
        //底部导航栏距离
        initDrawable(rb_1, 0, 0, 22, 25);
        initDrawable(rb_2, 0, 0, 21, 23);
        initDrawable(rb_mid, 0, 0, 23, 23);
        initDrawable(rb_3, 0, 0, 22, 25);
        initDrawable(rb_4, 0, 0, 22, 22);

    }

    //初始化rb中的图片大小
    public void initDrawable(RadioButton v, int left, int top, int right, int bottom) {
        Drawable drawable = v.getCompoundDrawables()[1];
        drawable.setBounds(CommonUtils.dip2px(this, left), CommonUtils.dip2px(this, top), CommonUtils.dip2px(this, right), CommonUtils.dip2px(this, bottom));
        v.setCompoundDrawables(null, drawable, null, null);
    }

    private void initRadioGroup() {
        mRg_mian_fragments.setOnCheckedChangeListener(this);
    }

    //根据常过来的索引切换fragment
    private void initFragment(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction().setCustomAnimations(R.anim.bg_gray_in, R.anim.bg_gray_out)
                .replace(R.id.ll_container, mFragments.get(i))
                .commit();
    }

    private void initView() {
        mFragments = new ArrayList<>();
//        mFragments.add(new HomeHotFragment());            //首页
        mFragments.add(new FragmentHotAndFocusNews());   //首页
        mFragments.add(new FragmentHuoDongAndFuLi());    //福利
        mFragments.add(new DaBangFragment());            //打榜
        mFragments.add(new MineFragment());              //我的

        if (((int) SpUtil.getParam(SpConstant.SP_STATUS, -1)) == Constans.STATUS_QIYE) {//如果是企业用户
            mFragments.add(new ApointmentFragmentA());
        } else {
            mFragments.add(new FragmentFans());          //其他
        }
        mRg_mian_fragments = (RadioGroup) findViewById(R.id.rg_mian_fragments);

    }

    //切换RadioGroup的监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //首页
            case R.id.rb_1:
                initFragment(0);
                break;

            //福利
            case R.id.rb_2:
                initFragment(1);
                break;

            //打榜
            case R.id.rb_3:
                initFragment(2);
                break;

            //我的
            case R.id.rb_4:
                initFragment(3);
                break;

            //中间的other
            case R.id.rb_mid:
                initFragment(4);
                break;

        }
    }

    private long firstTime;
    private long secondTime;

    //双击退出程序
    @Override
    public void onBackPressed() {
        secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showToast("再按一次就要退出啦");
            firstTime = secondTime;
        } else {
            Intent intent = new Intent(MyApplication.getInstance(), SplishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            EventBus.getDefault().postSticky(new EventChageAccount());

            //实现只在冷启动时显示启动页，即点击返回键与点击HOME键退出效果一致
        /*      Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);*/

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    //获取用户信息返回给融云
    @Override
    public UserInfo getUserInfo(String userId) {
        MyLogger.jLog().i("userId:" + userId);
        qucryCore.queryPersonalById(userId, this);
        return mUserInfo;
    }

    //获取群组信息返回给融云
    @Override
    public Group getGroupInfo(String id) {
        MyLogger.jLog().i("id:" + id);
        qucryCore.queryHytInfoById(id, this);
        return mGroupInfo;
    }

    //群组用户信息
    @Override
    public GroupUserInfo getGroupUserInfo(String groupId, String userId) {
        mId = userId;
        qucryCore.queryHytInfoById(groupId, this);
        return mGroupUserInfo;
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        switch (what) {
            case Constans.TYPE_ONE:
                AppInfo appInfo = MyApplication.getGson().fromJson(response.get(), AppInfo.class);
                if (AppUtils.getVersionCode(MyApplication.getInstance()) < appInfo.getExtra1()) {
                    normalUpdate("心跳互娱有新版本了 V", appInfo);
                }
                break;

            case Constans.TYPE_TWO:
                IMPersonalInfo info = MyApplication.getGson().fromJson(response.get().toString(), IMPersonalInfo.class);
                if (info.isSuccess()) {
                    IMPersonalInfo.ObjBean obj = info.getObj();
                    if (obj != null) {
                        mUserInfo = new UserInfo(obj.getId() + "", obj.getName(), Uri.parse(HttpContans.IMAGE_HOST + obj.getUserHeadPortrait()));
                    }
                } else {
                    ToastUtils.showToast(info.getMsg());
                }
                break;

            case Constans.TYPE_THREE:
                IMHytInfo groupInfo = MyApplication.getGson().fromJson(response.get().toString(), IMHytInfo.class);
                if (groupInfo.isSuccess()) {
                    IMHytInfo.ObjBean objBean = groupInfo.getObj();
                    if (objBean != null) {
                        mGroupInfo = new Group(objBean.getHytId() + "", objBean.getName(), Uri.parse(HttpContans.IMAGE_HOST + objBean.getPicUrl()));
                        mGroupUserInfo = new GroupUserInfo(objBean.getHytId() + "", mId, "后援团成员");
                    }
                } else {
                    ToastUtils.showToast(groupInfo.getMsg());
                }
                break;
        }

    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {

    }


    //重新获取Token监听
    @Override
    public void onRequestSuccess(Response<String> response) {
        TokenMsg commonRebackMsg = MyApplication.getGson().fromJson(response.get(), TokenMsg.class);
        if (commonRebackMsg.isSuccess()) {
            SpUtil.setParam(SpConstant.TOKEN, commonRebackMsg.getMsg());
            mChatCore.connect(commonRebackMsg.getMsg(), this);
        } else {
            ToastUtils.showToast(commonRebackMsg.getMsg());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        qucryCore.cancleAll();
        mChatCore.stopRequest();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

}