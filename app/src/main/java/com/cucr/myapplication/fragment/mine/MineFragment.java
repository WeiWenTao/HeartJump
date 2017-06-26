package com.cucr.myapplication.fragment.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.myHomePager.FansActivity;
import com.cucr.myapplication.activity.myHomePager.FocusActivity;
import com.cucr.myapplication.activity.myHomePager.MyHomePagerActivity;
import com.cucr.myapplication.activity.myHomePager.PayCenterActivity;
import com.cucr.myapplication.activity.myHomePager.StarMoneyActivity;
import com.cucr.myapplication.activity.setting.MyHelperActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.setting.RenZhengActivity;
import com.cucr.myapplication.activity.setting.SettingActivity;
import com.cucr.myapplication.fragment.BaseFragment;

/**
 * Created by 911 on 2017/4/10.
 */

public class MineFragment extends BaseFragment {

    @Override
    protected void initView(View childView) {
        ImageView iv_user = (ImageView) childView.findViewById(R.id.iv_user_mine);
        LinearLayout ll_user_nickname = (LinearLayout)childView.findViewById(R.id.ll_user_nickname);
        ImageView iv_mine_msg = (ImageView) childView.findViewById(R.id.iv_mine_msg);
        RelativeLayout rl_setting = (RelativeLayout) childView.findViewById(R.id.rl_setting);
        LinearLayout ll_enter_home_pager = (LinearLayout) childView.findViewById(R.id.enter_home_pager);
        LinearLayout ll_mine_focus = (LinearLayout) childView.findViewById(R.id.ll_mine_focus);
        LinearLayout ll_mine_fans = (LinearLayout) childView.findViewById(R.id.ll_mine_fans);
        LinearLayout ll_pay = (LinearLayout) childView.findViewById(R.id.ll_pay);
        RelativeLayout rl_my_helper = (RelativeLayout) childView.findViewById(R.id.rl_my_helper);
        RelativeLayout rl_pay_center = (RelativeLayout) childView.findViewById(R.id.rl_pay_center);
        RelativeLayout rl_ren_zheng = (RelativeLayout) childView.findViewById(R.id.rl_ren_zheng);



        iv_user.setOnClickListener(this);
        ll_user_nickname.setOnClickListener(this);
        iv_mine_msg.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        ll_enter_home_pager.setOnClickListener(this);
        ll_mine_focus.setOnClickListener(this);
        ll_mine_fans.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        rl_my_helper.setOnClickListener(this);
        rl_pay_center.setOnClickListener(this);
        rl_ren_zheng.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            //跳转到个人资料界面
            case R.id.ll_user_nickname:
            case R.id.iv_user_mine:
                startActivity(new Intent(mContext, PersonalInfoActivity.class));
                break;

            //跳转到消息界面
            case R.id.iv_mine_msg:
                mContext.startActivity(new Intent(mContext, MessageActivity.class));
                break;

            //设置
            case R.id.rl_setting:
                mContext.startActivity(new Intent(mContext, SettingActivity.class));
                break;

            //进入主页
            case R.id.enter_home_pager:
                mContext.startActivity(new Intent(mContext, MyHomePagerActivity.class));
                break;

            //关注
            case R.id.ll_mine_focus:
                mContext.startActivity(new Intent(mContext, FocusActivity.class));
                break;


            //粉丝
            case R.id.ll_mine_fans:
                mContext.startActivity(new Intent(mContext, FansActivity.class));
                break;

            //星币
            case R.id.ll_pay:
                mContext.startActivity(new Intent(mContext, StarMoneyActivity.class));
                break;

            //我的助手
            case R.id.rl_my_helper:
                mContext.startActivity(new Intent(mContext, MyHelperActivity.class));
                break;

            //充值中心
            case R.id.rl_pay_center:
                mContext.startActivity(new Intent(mContext, PayCenterActivity.class));
                break;

            case R.id.rl_ren_zheng:
                mContext.startActivity(new Intent(mContext, RenZhengActivity.class));

        }

    }

    //不需要头部
    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_mine;
    }
}
