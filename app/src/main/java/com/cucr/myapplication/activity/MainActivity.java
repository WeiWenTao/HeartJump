package com.cucr.myapplication.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.EventChageAccount;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.fragment.DaBang.DaBangFragment;
import com.cucr.myapplication.fragment.fuLiHuoDong.FragmentHuoDongAndFuLi;
import com.cucr.myapplication.fragment.home.FragmentHotAndFocusNews;
import com.cucr.myapplication.fragment.mine.MineFragment;
import com.cucr.myapplication.fragment.other.FragmentFans;
import com.cucr.myapplication.fragment.yuyue.ApointmentFragmentA;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.cucr.myapplication.utils.ToastUtils;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\  =  /O
 * ____/`---'\____
 * .'  \\|     |//  `.
 * /  \\|||  :  |||//  \
 * |  _||||| -:- |||||-  \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |   |
 * \  .-\__  `-`  ___/-. |
 * __`. .'  /--.--\  `. . __
 * ."" '<  `.___\_<|>_/___.'  >'"".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * <p>
 * ......................................
 * 佛祖保佑                 代码无BUG
 */
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private List<Fragment> mFragments;
    private RadioGroup mRg_mian_fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);

        //获取从 我的-明星-右上角加关注 界面跳转过来的数据
        findView();
        initView();
        initFragment(0);
        initRadioGroup();
        //TODO: 2017/4/28 Splash界面完成
        DisplayMetrics dm = new DisplayMetrics();
        // MI NOTE LET : DisplayMetrics{density=2.75, width=1080, height=1920, scaledDensity=2.75, xdpi=386.366, ydpi=387.047}
        // MI 6        :DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=428.625, ydpi=427.789}
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        String s = "屏幕的分辨率为：" + dm.widthPixels + "*" + dm.heightPixels;
        MyLogger.jLog().i(s);

    }

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
        fragmentManager.beginTransaction().replace(R.id.ll_container, mFragments.get(i)).commit();
    }

    private void initView() {
        mFragments = new ArrayList<>();
//        mFragments.add(new HomeFragment());            //首页
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


}