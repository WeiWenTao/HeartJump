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
import com.cucr.myapplication.activity.myHomePager.FocusActivity;
import com.cucr.myapplication.activity.pay.PayCenterActivity;
import com.cucr.myapplication.activity.pay.StarMoneyActivity;
import com.cucr.myapplication.activity.setting.MyHelperActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.setting.RenZhengActivity;
import com.cucr.myapplication.activity.setting.SettingActivity;
import com.cucr.myapplication.activity.yuyue.MyYuYueActivity;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.utils.CommonUtils;

/**
 * Created by 911 on 2017/4/10.
 */

public class MineFragment extends BaseFragment {

    private RelativeLayout head;

    @Override
    protected void initView(View childView) {
        head = (RelativeLayout) childView.findViewById(R.id.head);
        initHead();

        ImageView iv_mine_msg = (ImageView) childView.findViewById(R.id.iv_mine_msg);
        RelativeLayout rl_setting = (RelativeLayout) childView.findViewById(R.id.rl_setting);
        RelativeLayout rl_enter_home_pager = (RelativeLayout) childView.findViewById(R.id.rl_enter_mypager);
        LinearLayout ll_mine_focus = (LinearLayout) childView.findViewById(R.id.ll_mine_focus);
        LinearLayout ll_mine_fans = (LinearLayout) childView.findViewById(R.id.ll_mine_dongtai);
        LinearLayout ll_pay = (LinearLayout) childView.findViewById(R.id.ll_pay);
        RelativeLayout rl_my_helper = (RelativeLayout) childView.findViewById(R.id.rl_my_helper);
        RelativeLayout rl_pay_center = (RelativeLayout) childView.findViewById(R.id.rl_pay_center);
        RelativeLayout rl_ren_zheng = (RelativeLayout) childView.findViewById(R.id.rl_ren_zheng);
        RelativeLayout rl_piaowu = (RelativeLayout) childView.findViewById(R.id.rl_piaowu);
        RelativeLayout rl_my_yuyue = (RelativeLayout) childView.findViewById(R.id.rl_my_yuyue);


        iv_mine_msg.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_enter_home_pager.setOnClickListener(this);
        ll_mine_focus.setOnClickListener(this);
        ll_mine_fans.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        rl_my_helper.setOnClickListener(this);
        rl_pay_center.setOnClickListener(this);
        rl_ren_zheng.setOnClickListener(this);
        rl_piaowu.setOnClickListener(this);
        rl_my_yuyue.setOnClickListener(this);

    }

    //
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
            case R.id.iv_mine_msg:
                mContext.startActivity(new Intent(mContext, MessageActivity.class));
                break;

            //设置
            case R.id.rl_setting:
                mContext.startActivity(new Intent(mContext, SettingActivity.class));
                break;

            //进入个人资料
            case R.id.rl_enter_mypager:
                startActivity(new Intent(mContext, PersonalInfoActivity.class));
                break;

            //关注
            case R.id.ll_mine_focus:
                mContext.startActivity(new Intent(mContext, FocusActivity.class));
                break;


            //动态
            case R.id.ll_mine_dongtai:
//                mContext.startActivity(new Intent(mContext, ));
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

            //认证
            case R.id.rl_ren_zheng:
                mContext.startActivity(new Intent(mContext, RenZhengActivity.class));
                break;

            //票务
            case R.id.rl_piaowu:

                break;


            //预约
            case R.id.rl_my_yuyue:
                mContext.startActivity(new Intent(mContext, MyYuYueActivity.class));
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
