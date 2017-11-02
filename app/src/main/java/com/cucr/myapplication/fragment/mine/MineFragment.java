package com.cucr.myapplication.fragment.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.dongtai.DongTaiActivity;
import com.cucr.myapplication.activity.journey.MyJourneyActivity;
import com.cucr.myapplication.activity.myHomePager.FocusActivity;
import com.cucr.myapplication.activity.pay.PayCenterActivity_new;
import com.cucr.myapplication.activity.pay.TxRecordActivity;
import com.cucr.myapplication.activity.setting.InvateActivity;
import com.cucr.myapplication.activity.setting.MyRequiresActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.setting.RenZhengActivity;
import com.cucr.myapplication.activity.setting.SettingActivity;
import com.cucr.myapplication.activity.yuyue.MyYuYueActivity;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;

/**
 * Created by 911 on 2017/4/10.
 */

public class MineFragment extends BaseFragment {

    private RelativeLayout head;
    private Intent mIntent;

    @Override
    protected void initView(View childView) {
        head = (RelativeLayout) childView.findViewById(R.id.head);
        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        initHead();

        ImageView iv_mine_msg = (ImageView) childView.findViewById(R.id.iv_header_msg);
        RelativeLayout rl_setting = (RelativeLayout) childView.findViewById(R.id.rl_setting);
        RelativeLayout rl_enter_home_pager = (RelativeLayout) childView.findViewById(R.id.rl_enter_mypager);
        LinearLayout ll_mine_focus = (LinearLayout) childView.findViewById(R.id.ll_mine_focus);
        LinearLayout ll_mine_dongtai = (LinearLayout) childView.findViewById(R.id.ll_mine_dongtai);
        LinearLayout ll_mine_fans = (LinearLayout) childView.findViewById(R.id.ll_mine_fans);
        LinearLayout ll_pay = (LinearLayout) childView.findViewById(R.id.ll_pay);
        RelativeLayout rl_pay_center = (RelativeLayout) childView.findViewById(R.id.rl_pay_center);
        RelativeLayout rl_ren_zheng = (RelativeLayout) childView.findViewById(R.id.rl_ren_zheng);
        RelativeLayout rl_piaowu = (RelativeLayout) childView.findViewById(R.id.rl_piaowu);
        RelativeLayout rl_my_yuyue = (RelativeLayout) childView.findViewById(R.id.rl_my_yuyue);
        RelativeLayout rl_my_journey = (RelativeLayout) childView.findViewById(R.id.rl_my_journey);
        RelativeLayout rl_yaoqing = (RelativeLayout) childView.findViewById(R.id.rl_yaoqing);
        RelativeLayout rl_require = (RelativeLayout) childView.findViewById(R.id.rl_require);

//        如果是企业用户
//        if (){
//            rl_my_yuyue.setVisibility(View.VISIBLE);
//        如果是明星用户
//        }else if(){
//        普通用户
//         }else{
// }

        rl_yaoqing.setOnClickListener(this);
        iv_mine_msg.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_enter_home_pager.setOnClickListener(this);
        ll_mine_focus.setOnClickListener(this);
        ll_mine_fans.setOnClickListener(this);
        ll_mine_dongtai.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        rl_pay_center.setOnClickListener(this);
        rl_ren_zheng.setOnClickListener(this);
        rl_piaowu.setOnClickListener(this);
        rl_my_yuyue.setOnClickListener(this);
        rl_my_journey.setOnClickListener(this);
        rl_require.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            //跳转到消息界面
            case R.id.iv_header_msg:
                mIntent.setClass(mContext,MessageActivity.class);
                mContext.startActivity(mIntent);
                break;

            //设置
            case R.id.rl_setting:
                mIntent.setClass(mContext,SettingActivity.class);
                mContext.startActivity(mIntent);
                break;

            //进入个人资料
            case R.id.rl_enter_mypager:
                mIntent.setClass(mContext,PersonalInfoActivity.class);
                mContext.startActivity(mIntent);
                break;

            //关注
            case R.id.ll_mine_focus:
                mIntent.setClass(mContext,FocusActivity.class);
                mContext.startActivity(mIntent);
                break;


            //粉丝
            case R.id.ll_mine_fans:
                MyLogger.jLog().i("ll_mine_fans");
                mIntent.setClass(mContext,FocusActivity.class);
                mContext.startActivity(mIntent);
                break;


            //动态
            case R.id.ll_mine_dongtai:
                mIntent.setClass(mContext,DongTaiActivity.class);
                mContext.startActivity(mIntent);
                break;

            //星币
            case R.id.ll_pay:
                mIntent.setClass(mContext,TxRecordActivity.class);
                mContext.startActivity(mIntent);
                break;

            //充值中心
            case R.id.rl_pay_center:
                mIntent.setClass(mContext,PayCenterActivity_new.class);
                mContext.startActivity(mIntent);
                break;

            //认证
            case R.id.rl_ren_zheng:
                mIntent.setClass(mContext,RenZhengActivity.class);
                mContext.startActivity(mIntent);
                break;

            //票务
            case R.id.rl_piaowu:

                break;

            //预约
            case R.id.rl_my_yuyue:
                mIntent.setClass(mContext,MyYuYueActivity.class);
                mContext.startActivity(mIntent);
                break;

            //行程
            case R.id.rl_my_journey:
                mIntent.setClass(mContext,MyJourneyActivity.class);
                mContext.startActivity(mIntent);
                break;

            //邀请有礼
            case R.id.rl_yaoqing:
                mIntent.setClass(mContext,InvateActivity.class);
                mContext.startActivity(mIntent);
                break;

             //我的要求
            case R.id.rl_require:
                mIntent.setClass(mContext,MyRequiresActivity.class);
                mContext.startActivity(mIntent);
                break;

        }
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
}
