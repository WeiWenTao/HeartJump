package com.cucr.myapplication.fragment.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.dongtai.DongTaiActivity;
import com.cucr.myapplication.activity.fuli.PiaoWuActivity;
import com.cucr.myapplication.activity.journey.MyJourneyActivity;
import com.cucr.myapplication.activity.myHomePager.FocusActivity;
import com.cucr.myapplication.activity.myHomePager.MineFansActivity;
import com.cucr.myapplication.activity.pay.PayCenterActivity_new;
import com.cucr.myapplication.activity.pay.TxRecordActivity;
import com.cucr.myapplication.activity.setting.InvateActivity;
import com.cucr.myapplication.activity.setting.MyRequiresActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.setting.RenZhengActivity;
import com.cucr.myapplication.activity.setting.SettingActivity;
import com.cucr.myapplication.activity.yuyue.MyYuYueActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.CommentEvent;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.editPersonalInfo.QueryPersonalMsgCore;
import com.cucr.myapplication.core.user.UserCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.bean.EditPersonalInfo.PersonMessage;
import com.cucr.myapplication.bean.eventBus.EventQueryPersonalInfo;
import com.cucr.myapplication.bean.user.UserCenterInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

/**
 * Created by 911 on 2017/4/10.
 */

public class MineFragment extends BaseFragment {

    //界面头部
    @ViewInject(R.id.head)
    private RelativeLayout head;

    //用户头像
    @ViewInject(R.id.iv_user_mine)
    private ImageView userPic;

    //用户昵称
    @ViewInject(R.id.textView3)
    private TextView nickName;

    //关注数量
    @ViewInject(R.id.tv_focus)
    private TextView tv_focus;

    //fans数量
    @ViewInject(R.id.tv_fans)
    private TextView tv_fans;

    //星币数量
    @ViewInject(R.id.tv_xb)
    private TextView tv_xb;

    //动态数量
    @ViewInject(R.id.tv_dt)
    private TextView tv_dt;

    //用户签名
    @ViewInject(R.id.tv_sign)
    private TextView userSign;

    //我的要求(明星)
    @ViewInject(R.id.rl_require)
    private RelativeLayout rl_require;

    //我的预约(企业)
    @ViewInject(R.id.rl_my_yuyue)
    private RelativeLayout rl_my_yuyue;

    //我的行程(明星)
    @ViewInject(R.id.rl_my_journey)
    private RelativeLayout rl_my_journey;

    private Intent mIntent;

    private PersonMessage.ObjBean mObj;

    @Override
    protected void initView(View childView) {
        EventBus.getDefault().register(this);
        ViewUtils.inject(this, childView);
        showAndHide();  //分配权限
//        initHead();
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        queryInfos();

        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    //分配权限  先隐藏再根据身份显示
    private void showAndHide() {
        switch (((int) SpUtil.getParam(SpConstant.SP_STATUS, 0))) {
            case Constans.STATUS_EVERYONE:    //无角色

                break;
            case Constans.STATUS_ADMIN:      //管理员

                break;
            case Constans.STATUS_STAR:    //明星
                rl_require.setVisibility(View.VISIBLE);   //要求
                rl_my_journey.setVisibility(View.VISIBLE);//行程
                break;
            case Constans.STATUS_QIYE:    //企业
                rl_my_yuyue.setVisibility(View.VISIBLE);  //预约
                break;
            case Constans.STATUS_COMMON_USER://普通用户

                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //点击保存的时候再查一遍
    public void onDataSynEvent(EventQueryPersonalInfo event) {
        queryInfos();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //取消关注了  更新关注数量
    public void onDataSynEvent(CommentEvent event) {
        if (event.getFlag() == 999) {
            queryInfos();
        }
    }

    //查询用户信息
    private void queryInfos() {
        UserCore userCore = new UserCore();
        QueryPersonalMsgCore qucryCore = new QueryPersonalMsgCore();

        userCore.queryUserCenterInfo((int) SpUtil.getParam(SpConstant.USER_ID, -1), new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                UserCenterInfo userInfo = MyApplication.getGson().fromJson(response.get(), UserCenterInfo.class);
                if (userInfo.isSuccess()) {
                    setData(userInfo.getObj());
                } else {
                    ToastUtils.showToast(userInfo.getMsg());
                }
            }
        });


        qucryCore.queryPersonalInfo((SpUtil.getParam(SpConstant.USER_ID, -1)+""),new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                PersonMessage personMessage = mGson.fromJson(response.get().toString(), PersonMessage.class);
                if (personMessage.isSuccess()) {
                    mObj = mGson.fromJson(personMessage.getMsg(), PersonMessage.ObjBean.class);
                    initViews();
                } else {
                    ToastUtils.showToast(personMessage.getMsg());
                }
            }
        });
    }

    private void setData(UserCenterInfo.ObjBean obj) {
        tv_focus.setText(obj.getGzsl() + "");
        tv_fans.setText(obj.getFssl() + "");
        tv_xb.setText(obj.getXbsl() + "");
        tv_dt.setText(obj.getDtsl() + "");
    }

    private void initViews() {
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mObj.getUserHeadPortrait(),
                userPic, MyApplication.getImageLoaderOptions());
        nickName.setText(mObj.getName());
        if (TextUtils.isEmpty(mObj.getSignName())) {
            userSign.setText("还木有设置签名哦!");
        } else {
            userSign.setText(mObj.getSignName());
        }

    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(mContext, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //跳转到消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        mIntent.setClass(mContext, MessageActivity.class);
        mContext.startActivity(mIntent);
    }

    //设置
    @OnClick(R.id.rl_setting)
    public void goSetting(View view) {
        mIntent.setClass(mContext, SettingActivity.class);
        mContext.startActivity(mIntent);
    }

    //进入个人资料
    @OnClick(R.id.rl_enter_mypager)
    public void goPersonalInfo(View view) {
        mIntent.setClass(mContext, PersonalInfoActivity.class);
        mIntent.putExtra("data", mObj);
        mContext.startActivity(mIntent);
    }

    //关注
    @OnClick(R.id.ll_mine_focus)
    public void goFocus(View view) {
        mIntent.setClass(mContext, FocusActivity.class);
        mContext.startActivity(mIntent);
    }

    //粉丝
    @OnClick(R.id.ll_mine_fans)
    public void goFans(View view) {
        mIntent.setClass(mContext, MineFansActivity.class);
        mContext.startActivity(mIntent);
    }

    //动态
    @OnClick(R.id.ll_mine_dongtai)
    public void goDongTai(View view) {
        mIntent.setClass(mContext, DongTaiActivity.class);
        mContext.startActivity(mIntent);
    }

    //星币
    @OnClick(R.id.ll_pay)
    public void goXb(View view) {
        mIntent.setClass(mContext, TxRecordActivity.class);
        mContext.startActivity(mIntent);
    }

    //充值中心
    @OnClick(R.id.rl_pay_center)
    public void goPayCenter(View view) {
        mIntent.setClass(mContext, PayCenterActivity_new.class);
        mContext.startActivity(mIntent);
    }

    //认证
    @OnClick(R.id.rl_ren_zheng)
    public void goRz(View view) {
        mIntent.setClass(mContext, RenZhengActivity.class);
        mContext.startActivity(mIntent);
    }

    //预约
    @OnClick(R.id.rl_my_yuyue)
    public void goYuYue(View view) {
        mIntent.setClass(mContext, MyYuYueActivity.class);
        mContext.startActivity(mIntent);
    }

    //票务
    @OnClick(R.id.rl_piaowu)
    public void goPw(View view) {
        mIntent.setClass(mContext, PiaoWuActivity.class);
        mContext.startActivity(mIntent);
    }

    //行程
    @OnClick(R.id.rl_my_journey)
    public void goJourney(View view) {
        mIntent.setClass(mContext, MyJourneyActivity.class);
        mContext.startActivity(mIntent);
    }

    //邀请有礼
    @OnClick(R.id.rl_yaoqing)
    public void goInvate(View view) {
        mIntent.setClass(mContext, InvateActivity.class);
        mContext.startActivity(mIntent);
    }

    //我的要求
    @OnClick(R.id.rl_require)
    public void goMyRequire(View view) {
        mIntent.setClass(mContext, MyRequiresActivity.class);
        mContext.startActivity(mIntent);
    }

    //是否需要头部
    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
