package com.cucr.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.PagerAdapter.CommonPagerAdapter;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.fragment.msgFragment.CommendFragment;
import com.cucr.myapplication.fragment.msgFragment.GoodsFragment;
import com.cucr.myapplication.utils.SpUtil;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;


public class MessageActivity extends FragmentActivity implements View.OnClickListener {

    private List<Fragment> mFragmentList;
    private List<String> titles;
    private Fragment mConversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(getResources().getColor(R.color.zise), 0);
        TabLayout tl_tab = (TabLayout) findViewById(R.id.tl_tab);
        findViewById(R.id.iv_base_back).setOnClickListener(this);

        mFragmentList = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("评论");
        titles.add("点赞");
        mFragmentList.add(new CommendFragment());
        mFragmentList.add(new GoodsFragment());
        ViewPager vp = (ViewPager) findViewById(R.id.vp_msg);
        vp.setAdapter(new CommonPagerAdapter(getSupportFragmentManager(), mFragmentList, titles));
        tl_tab.setupWithViewPager(vp);
    }

    //跳转客服
    public void kefu(View view) {

        //首先需要构造使用客服者的用户信息
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName((String) SpUtil.getParam(SpConstant.USER_NAEM,"")).
                userId((int) SpUtil.getParam(SpConstant.USER_ID, -1) + "").build();

        RongIM.getInstance().startCustomerServiceChat(this,
                "KEFU152211570248279",
                "在线客服",csInfo);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

   /* private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .build();
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }*/
}
