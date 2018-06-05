package com.cucr.myapplication.fragment.mine;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.TestWebViewActivity;
import com.cucr.myapplication.activity.dongtai.DongTaiActivity;
import com.cucr.myapplication.activity.fenTuan.DsDuiHuanActivity;
import com.cucr.myapplication.activity.fuli.PiaoWuActivity;
import com.cucr.myapplication.activity.journey.MyJourneyActivity;
import com.cucr.myapplication.activity.myHomePager.FocusActivity;
import com.cucr.myapplication.activity.myHomePager.MineFansActivity;
import com.cucr.myapplication.activity.pay.PayCenterActivity_new;
import com.cucr.myapplication.activity.pay.TxRecordActivity;
import com.cucr.myapplication.activity.picWall.MyPicWallActivity;
import com.cucr.myapplication.activity.setting.LearnningActivity;
import com.cucr.myapplication.activity.setting.MyActivesActivity;
import com.cucr.myapplication.activity.setting.MyRequiresActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.setting.RenZhengActivity;
import com.cucr.myapplication.activity.setting.SettingActivity;
import com.cucr.myapplication.activity.yuyue.MyYuYueActivity;
import com.cucr.myapplication.adapter.RlVAdapter.MineAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.EditPersonalInfo.PersonMessage;
import com.cucr.myapplication.bean.eventBus.EventQueryPersonalInfo;
import com.cucr.myapplication.bean.user.UserCenterInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.core.editPersonalInfo.QueryPersonalMsgCore;
import com.cucr.myapplication.core.user.UserCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.ItemDecoration.MineSpaceItemDecoration;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by 911 on 2017/4/10.
 */

public class MineFragment extends BaseFragment implements MineAdapter.OnClickItems {

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

    //九宫格
    @ViewInject(R.id.rlv)
    private RecyclerView rlv;

    private Intent mIntent;
    private PersonMessage.ObjBean mObj;
    private UserCore mUserCore;
    private QueryPersonalMsgCore mQucryCore;
    private MineAdapter mAdapter;

    @Override
    protected void initView(View childView) {
        EventBus.getDefault().register(this);
        ViewUtils.inject(this, childView);
        mUserCore = new UserCore();
        mQucryCore = new QueryPersonalMsgCore();
        map = new HashMap<>();
        map.put(Conversation.ConversationType.PRIVATE.getName(), false);
        map.put(Conversation.ConversationType.GROUP.getName(), false);
        queryInfos();
        initRlv();
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    private void initRlv() {
        rlv.setLayoutManager(new GridLayoutManager(MyApplication.getInstance(), 4));
        rlv.addItemDecoration(new MineSpaceItemDecoration(1, 0, getResources().getColor(R.color.ccc)));
        mAdapter = new MineAdapter();
        mAdapter.getDatas();
        mAdapter.setOnClickItems(this);
        rlv.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //点击保存的时候再查一遍
    public void onDataSynEvent(EventQueryPersonalInfo event) {
        queryInfos();
    }

    @Override
    public void onResume() {
        super.onResume();
        queryInfos();
        mAdapter.getDatas();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    //查询用户信息
    private void queryInfos() {
        mUserCore.queryUserCenterInfo((int) SpUtil.getParam(SpConstant.USER_ID, -1), new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                UserCenterInfo userInfo = MyApplication.getGson().fromJson(response.get(), UserCenterInfo.class);
                if (userInfo.isSuccess()) {
                    setData(userInfo.getObj());
                } else {
                    ToastUtils.showToast(userInfo.getMsg());
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
        });

        mQucryCore.queryPersonalInfo(new OnCommonListener() {
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
        String userHeadPortrait = mObj.getUserHeadPortrait();
        if (TextUtils.isEmpty(userHeadPortrait)) {
            userPic.setImageResource(R.drawable.empty_touxiang);
        } else {
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + userHeadPortrait,
                    userPic, MyApplication.getImageLoaderOptions());
        }

        nickName.setText(mObj.getName());
        if (TextUtils.isEmpty(mObj.getSignName())) {
            userSign.setText("还木有设置签名哦!");
        } else {
            userSign.setText(mObj.getSignName());
        }
    }


    //跳转到消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        mIntent.setClass(mContext, MessageActivity.class);
        mContext.startActivity(mIntent);
    }

    //进入个人资料
    @OnClick(R.id.rl_enter_mypager)
    public void goPersonalInfo(View view) {
        mIntent.setClass(mContext, PersonalInfoActivity.class);
        mIntent.putExtra("data", mObj);
        startActivity(mIntent);
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


    private Map<String, Boolean> map;

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

    @Override
    public void clickItem(int flag) {
        switch (flag) {

            //私信
            case 1:
                RongIM.getInstance().startConversationList(MyApplication.getInstance(), map);
                break;

            //认证
            case 2:
                mIntent.setClass(mContext, RenZhengActivity.class);
                mContext.startActivity(mIntent);
                break;

            //充值中心
            case 3:
                mIntent.setClass(mContext, PayCenterActivity_new.class);
                mContext.startActivity(mIntent);
                break;

            //我的图集
            case 4:
                mIntent.setClass(mContext, MyPicWallActivity.class);
                mContext.startActivity(mIntent);
                break;

            //邀请有礼
            case 5:
//                mIntent.setClass(mContext, InvateActivity.class);
//                mContext.startActivity(mIntent);

                mIntent.setClass(mContext, TestWebViewActivity.class);
                mIntent.putExtra("url", HttpContans.ADDRESS_CHOU_JIANG + ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
                mIntent.putExtra("title", "邀请有礼");
                mIntent.putExtra("choujiang", 1);
                mContext.startActivity(mIntent);
                break;

            //新手教程
            case 6:
                mIntent.setClass(mContext, LearnningActivity.class);
                mContext.startActivity(mIntent);
                break;

            //福利票务
            case 7:
                mIntent.setClass(mContext, PiaoWuActivity.class);
                mContext.startActivity(mIntent);
                break;

            //礼物背包
            case 8:
                mIntent.setClass(mContext, DsDuiHuanActivity.class);
                mContext.startActivity(mIntent);
                break;

            //我的行程
            case 9:
                mIntent.setClass(mContext, MyJourneyActivity.class);
                mContext.startActivity(mIntent);
                break;

            //我的要求
            case 10:
                mIntent.setClass(mContext, MyRequiresActivity.class);
                mContext.startActivity(mIntent);
                break;

            //我的预约
            case 11:
                mIntent.setClass(mContext, MyYuYueActivity.class);
                mContext.startActivity(mIntent);
                break;

            //我的活动
            case 12:
                mIntent.setClass(mContext, MyActivesActivity.class);
                mContext.startActivity(mIntent);
                break;

            //设置
            case 13:
                mIntent.setClass(mContext, SettingActivity.class);
                mContext.startActivity(mIntent);
                break;
        }
    }
}
